package backend;// Fig. 27.13: backend.TicTacToeServer.java
// Server side of client/server Tic-Tac-Toe program.

import database.DBListing;
import database.DBPopulation;
import database.DBUpdating;
import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicTacToeServer extends JFrame {
    private final static int PLAYER_X = 0; // constant for first player
    private final static int PLAYER_O = 1; // constant for second player
    private final static String[] MARKS = {"X", "O"}; // array of marks
    private final String[] board = new String[9]; // tic-tac-toe board
    private final JTextArea outputArea; // for outputting moves
    private final Player[] players; // array of Players
    private ServerSocket server; // server socket to connect with clients
    private int currentPlayer; // keeps track of player with current move
    private final ExecutorService runGame; // will run players
    private final Lock gameLock; // to lock game for synchronization
    private final Condition otherPlayerConnected; // to wait for other player
    private final Condition otherPlayerTurn; // to wait for other player's turn
    private boolean playing;
    private int winner = -1;
    private int onlinePlayers;
    private Game game;
    private boolean lastGame = false;
    private int nextToMove = PLAYER_X;

    // set up tic-tac-toe server and GUI that displays messages
    public TicTacToeServer() {
        super("Tic-Tac-Toe Server"); // set title of window

        // create ExecutorService with a thread for each player
        runGame = Executors.newFixedThreadPool(2);
        gameLock = new ReentrantLock(); // create lock for game

        // condition variable for both players being connected
        otherPlayerConnected = gameLock.newCondition();

        // condition variable for the other player's turn
        otherPlayerTurn = gameLock.newCondition();

        for (int i = 0; i < 9; i++)
            board[i] = ""; // create tic-tac-toe board
        players = new Player[2]; // create array of players

        try {
            server = new ServerSocket(12345, 2); // set up ServerSocket
        } // end try
        catch (IOException ioException) {
            ioException.printStackTrace();
            System.exit(1);
        } // end catch

        this.game = new DBListing().listFirst();

        if (game == null || game.gameHasEnded()) {
            this.game = new Game();
        } else {
            lastGame = true;
            String newBoard = game.getBoard();
            for (int i = 0; i < 9; i++) {
                char pos = newBoard.charAt(i);
                if (pos == '0')
                    continue;

                board[i] = String.valueOf(pos);
            }
        }
        currentPlayer = PLAYER_X;

        outputArea = new JTextArea(); // create JTextArea for output
        add(outputArea, BorderLayout.CENTER);
        outputArea.setText("Server awaiting connections\n");

        setSize(300, 300); // set size of window
        setVisible(true); // show window

        addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                serverEnded();
            }
        });
    } // end backend.TicTacToeServer constructor

    private void serverEnded() {
        String serverShutdown = "Server shutdown.\n";
        players[PLAYER_X].output.format(serverShutdown);
        players[PLAYER_X].output.flush();

        players[PLAYER_O].output.format(serverShutdown);
        players[PLAYER_O].output.flush();
    }

    // wait for two connections so game can be played
    // wait for two connections so game can be played
    public void execute() {
        // wait for each client to connect
        for (int i = 0; i < players.length; i++) {
            try // wait for connection, create Player, start runnable
            {
                players[i] = new Player(server.accept(), i);
                runGame.execute(players[i]); // execute player runnable
            } // end try
            catch (IOException ioException) {
                ioException.printStackTrace();
                System.exit(1);
            } // end catch
        } // end for

        gameLock.lock(); // lock game to signal player X's thread

        try {
            players[currentPlayer].setSuspended(false); // resume player X
            otherPlayerConnected.signal(); // wake up player X's thread
        } // end try
        finally {
            gameLock.unlock(); // unlock game after signalling player X
        } // end finally
    } // end method execute


    // display message in outputArea
    private void displayMessage(final String messageToDisplay) {
        // display message from event-dispatch thread of execution
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() // updates outputArea
                    {
                        outputArea.append(messageToDisplay); // add message
                    } // end  method run
                } // end inner class
        ); // end call to SwingUtilities.invokeLater
    } // end method displayMessage

    private void gameEndedMessage(Formatter output) {
        if (winner == 3) {
            displayMessage("\nGame ended with a tie.\n");
            output.format("\nGame ended with a tie.\n");
        } else {
            displayMessage("\nGame ended. Winner: " + MARKS[winner] + '\n');
            output.format("\nYou won!\n");
        }
        output.flush();
    }

    // determine if move is valid
    public boolean validateAndMove(int location, int player) {
        // while not current player, must wait for turn
        while (player != currentPlayer) {
            gameLock.lock(); // lock game to wait for other player to go
            try {
                otherPlayerTurn.await(); // wait for player's turn
            } // end try
            catch (InterruptedException exception) {
                exception.printStackTrace();
            } // end catch
            finally {
                if (onlinePlayers == 2)
                    gameLock.unlock(); // unlock game after waiting
            } // end finally
        } // end while

        // if location not occupied, make move
        if (locationEmpty(location) && playing) {
            board[location] = MARKS[currentPlayer]; // set move on board
            currentPlayer = (currentPlayer + 1) % 2; // change player
            winCheck();

            // let new current player know that move occurred
            players[currentPlayer].otherPlayerMoved(location);

            gameLock.lock(); // lock game to signal other player to go
            if (!playing)
                return true;
            try {
                otherPlayerTurn.signal(); // signal other player to continue
            } // end try
            finally {
                gameLock.unlock(); // unlock game after signaling
            } // end finally

            return true; // notify player that move was valid
        } // end if
        else // move was not valid
            return false; // notify player that move was invalid
    } // end method validateAndMove

    // check whether a valid move resulted in win or tie
    public void winCheck() {
        int[][] winCases = {
                {1, 2, 3}, {4, 5, 6},
                {7, 8, 9}, {1, 4, 7},
                {2, 5, 8}, {3, 6, 9},
                {1, 5, 9}, {3, 5, 7}
        };

        for (int[] winCase : winCases) {
            int f = winCase[0] - 1, s = winCase[1] - 1, t = winCase[2] - 1;
            if (locationEmpty(f) || locationEmpty(s) || locationEmpty(t))
                continue;

            if (board[f].equals(board[s]) && board[s].equals(board[t])) {
                playing = false;
                winner = (currentPlayer + 1) % 2;
                game.setStatus(true);
                return;
            }
        }

        for (int i = 0; i < board.length; i++)
            if (locationEmpty(i))
                return;
        winner = 3;
        playing = false;
        game.setStatus(true);
    }

    // determine whether location is occupied
    public boolean isOccupied(int location) {
        // location is not occupied
        return board[location].equals(MARKS[PLAYER_X]) ||
                board[location].equals(MARKS[PLAYER_O]); // location is occupied
    } // end method isOccupied

    public boolean locationEmpty(int location) {
        return !isOccupied(location);
    }

    private void playerDisconnected(int playerNumber) {
        players[(playerNumber + 1) % 2].output.format("Opponent left. Closing window.\n");
        players[(playerNumber + 1) % 2].output.flush();

        onlinePlayers--; // Reduz o número de jogadores online

        displayMessage("\nPlayer " + MARKS[playerNumber] + " disconnected.\n");
    }

    // private inner class Player manages each Player as a runnable
    private class Player implements Runnable {
        private final Socket connection; // connection to client
        private Scanner input; // input from client
        private Formatter output; // output to client
        private final int playerNumber; // tracks which player this is
        private final String mark; // mark for this player
        private boolean suspended = true; // whether thread is suspended

        // set up Player thread
        public Player(Socket socket, int number) {
            playerNumber = number; // store this player's number
            mark = MARKS[playerNumber]; // specify player's mark
            connection = socket; // store socket for client

            try // obtain streams from Socket
            {
                input = new Scanner(connection.getInputStream());
                output = new Formatter(connection.getOutputStream());
            } // end try
            catch (IOException ioException) {
                ioException.printStackTrace();
                System.exit(1);
            } // end catch
        } // end Player constructor

        // send message that other player moved
        public void otherPlayerMoved(int location) {
            output.format("Opponent moved\n");
            output.format(location + " " + winner + '\n'); // send location of move
            output.flush(); // flush output
        } // end method otherPlayerMoved

        // control thread's execution
        // control thread's execution
        public void run() {
            // send client its mark (X or O), process messages from client
            try {
                if (lastGame && mark.equals(MARKS[PLAYER_X])) {
                    displayMessage("Loading last game...\n");
                    displayMessage("Current player: " + currentPlayer + '\n');
                    displayMessage("Key (playerX, playerO, dateTime): "
                            + game.getPlayerX() + ", " + game.getPlayerO() + ", " + game.getDateTime() + "\n\n");
                }
                displayMessage("Player " + mark + " connected\n");
                output.format("%s\n", mark); // send player's mark
                output.flush(); // flush output

                if (playerNumber == PLAYER_X) {
                    output.format("%s\n%s", "Player X connected",
                            "Waiting for another player\n");
                    output.flush(); // flush output

                    gameLock.lock(); // lock game to wait for second player
                    try {
                        while (suspended) {
                            otherPlayerConnected.await(); // wait for player O
                        } // end while
                    } catch (InterruptedException exception) {
                        exception.printStackTrace();
                    } finally {
                        gameLock.unlock(); // unlock game after second player
                    } // end finally

                    output.format("Other player connected. Your move.\n");
                    output.flush(); // flush output
                } else {
                    output.format("Player O connected, please wait\n");
                    output.flush(); // flush output
                }

                playing = true;
                onlinePlayers = 2;

                // Main game loop
                while (playing) {
                    int location = 0; // initialize move location
                    if (input.hasNext()) {
                        location = input.nextInt();
                        if (location == -1) { // Verifica se o jogador enviou uma mensagem de desconexão
                            int closingPlayer = input.nextInt();
                            input.nextLine();
                            String key = input.nextLine();
                            if (mark.equals(MARKS[PLAYER_X]))
                                game.setPlayerX(key);
                            else
                                game.setPlayerO(key);
                            gameLock.lock();
                            playerDisconnected(closingPlayer); // Chama o método para tratar a desconexão
                            gameLock.unlock();
                            continue;
                        }
                    }

                    // Check if the game has ended due to disconnection
                    if (onlinePlayers == 0) {
                        playing = false;
                    }
                    // check for valid move
                    else if (validateAndMove(location, playerNumber)) {
                        displayMessage("\nlocation: " + location);
                        output.format("Valid move.\n"); // notify client
                        output.flush(); // flush output
                    } // end if
                    else // move was invalid
                    {
                        output.format("Invalid move, try again\n");
                        output.flush(); // flush output
                    } // end else
                }

                // Finalize the game
                if (game.readyToClose()) {
                    String boardStatus = "";
                    for (int i = 0; i < board.length; i++) {
                        String empty = boardStatus.concat("0");
                        String occupied = boardStatus.concat(board[i]);

                        boardStatus = isOccupied(i) ? occupied : empty;
                    }
                    game.setBoard(boardStatus);
                    if (lastGame && game.gameHasEnded()) {
                        new DBUpdating().update(game);
                    } else if (!game.gameHasEnded()) {
                        game.setDateTime(new Timestamp(System.currentTimeMillis()));

                        game.setNext_move(playerNumber);
                        new DBPopulation().insert(game);
                        dispose();
                    }
                }
                if (onlinePlayers == 2) {
                    gameEndedMessage(output);
                    onlinePlayers--;
                }
            } // end try
            finally {
                try {
                    connection.close(); // close connection to client
                    playing = false;
                } // end try
                catch (IOException ioException) {
                    ioException.printStackTrace();
                } // end catch
                if (onlinePlayers == 0)
                    System.exit(1);
            } // end finally
        } // end method run


        // set whether or not thread is suspended
        public void setSuspended(boolean status) {
            suspended = status; // set value of suspended
        } // end method setSuspended
    } // end class Player
} // end class backend.TicTacToeServer

/**************************************************************************
 * (C) Copyright 1992-2010 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/

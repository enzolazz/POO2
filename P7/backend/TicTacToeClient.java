package backend;

import database.DBListing;
import game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicTacToeClient extends JFrame implements Runnable {
    private final String X_MARK = "X"; // mark for first client
    private final String O_MARK = "O"; // mark for second client
    private final JTextField idField; // textfield to display player's mark
    private final JTextArea displayArea; // JTextArea to display output
    private final JPanel boardPanel; // panel for tic-tac-toe board
    private final JPanel panel2; // panel to hold board
    private final Square[][] board; // tic-tac-toe board
    private Square currentSquare; // current square
    private Socket connection; // connection to server
    private Scanner input; // input from server
    private Formatter output; // output to server
    private final String ticTacToeHost; // host name for server
    private String myMark; // this client's mark
    private boolean myTurn; // determines which client's turn it is
    private boolean playing;
    private int winner;

    // set up user-interface and board
    public TicTacToeClient(String host) {
        ticTacToeHost = host; // set name of server
        displayArea = new JTextArea(4, 30); // set up JTextArea
        displayArea.setEditable(false);
        add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        boardPanel = new JPanel(); // set up panel for squares in board
        boardPanel.setLayout(new GridLayout(3, 3, 0, 0));

        board = new Square[3][3]; // create board

        Game game = new DBListing().listFirst();
        String[] oldBoard = new String[9];
        int boardId = 0;
        if (game != null && !game.gameHasEnded()) {
            String board = game.getBoard();
            for (char pos : board.toCharArray()) {
                String newPos = String.valueOf(pos);
                oldBoard[boardId++] = newPos.equals("0") ? " " : newPos;
            }
        } else {
            for (int i = 0; i < 9; i++)
                oldBoard[i] = " ";
        }
        boardId = 0;
        // loop over the rows in the board
        for (int row = 0; row < board.length; row++) {
            // loop over the columns in the board
            for (int column = 0; column < board[row].length; column++) {
                // create square
                board[row][column] = new Square(oldBoard[boardId++], row * 3 + column);
                boardPanel.add(board[row][column]); // add square
            } // end inner for
        } // end outer for

        idField = new JTextField(); // set up textfield
        idField.setEditable(false);
        add(idField, BorderLayout.NORTH);

        panel2 = new JPanel(); // set up panel to contain boardPanel
        panel2.add(boardPanel, BorderLayout.CENTER); // add board panel
        add(panel2, BorderLayout.CENTER); // add container panel

        setSize(300, 225); // set size of window
        setVisible(true); // show window

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (winner == -1)
                    savingInfo();
            }
        });

        startClient();
    } // end backend.TicTacToeClient constructor

    private void savingInfo() {
        output.format("%d\n", -1);
        output.format("%d\n", (myMark.equals(X_MARK) ? 0 : 1));
        output.format("%s\n", JOptionPane.showInputDialog(this,
                "Digite a chave identificadora:",
                "Salvando informações",
                JOptionPane.PLAIN_MESSAGE));
        output.flush();
    }

    public void dispose() {
        if (winner == -1)
            savingInfo();
        super.dispose();
    }
    // start the client thread
    public void startClient() {
        try // connect to server and get streams
        {
            // make connection to server
            connection = new Socket(
                    InetAddress.getByName(ticTacToeHost), 12345);

            // get streams for input and output
            input = new Scanner(connection.getInputStream());
            output = new Formatter(connection.getOutputStream());
        } // end try
        catch (IOException ioException) {
            ioException.printStackTrace();
        } // end catch

        // create and start worker thread for this client
        ExecutorService worker = Executors.newFixedThreadPool(1);
        worker.execute(this); // execute client
    } // end method startClient

    // control thread that allows continuous update of displayArea
    public void run() {
        myMark = input.nextLine(); // get player's mark (X or O)

        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        // display player's mark
                        idField.setText("You are player \"" + myMark + "\"");
                    } // end method run
                } // end anonymous inner class
        ); // end call to SwingUtilities.invokeLater

        myTurn = (myMark.equals(X_MARK)); // determine if client's turn
        playing = true;
        // receive messages sent to client and output them
        while (playing) {
            if (input.hasNextLine())
                processMessage(input.nextLine());
        } // end while
    } // end method run

    // process messages received by client
    private void processMessage(String message) {
        String opponentsMark = myMark.equals(X_MARK) ? O_MARK : X_MARK;
        // valid move occurred
        switch (message) {
            case "Server shutdown." -> {
                playing = false;
                System.exit(1);
            }
            case "Opponent left. Closing window." -> {
                if (winner != -1)
                    playing = false;
                dispose();
                System.exit(1);
            }
            case "Valid move." -> {
                displayMessage("Valid move, please wait.\n");
                setMark(currentSquare, myMark); // set mark in square
            }
            case "Invalid move, try again" -> {
                displayMessage(message + "\n"); // display invalid move
                myTurn = true; // still this client's turn
            }
            case "Opponent moved" -> {
                int location = input.nextInt();
                winner = input.nextInt(); // get move location
                input.nextLine(); // skip newline after int location
                int row = location / 3; // calculate row
                int column = location % 3; // calculate column

                setMark(board[row][column], opponentsMark); // mark move

                if (winner == -1) {
                    displayMessage("Opponent moved. Your turn.\n");
                    myTurn = true; // now this client's turn
                } else {
                    playing = false;

                    if (winner == 3)
                        displayMessage("\nGame ended with a tie.\n");
                    else
                        displayMessage("\nGame ended. Winner: " + opponentsMark + '\n');
                }
            }
            case "You won!" -> {
                playing = false;
                winner = myMark.equals(X_MARK) ? 0 : 1;

                displayMessage("You won! Congratulations!\n");
            }

            case "Game ended with a tie." -> {
                playing = false;
                winner = 3;

                displayMessage(message + "\n");
            }
            default -> displayMessage(message + "\n"); // display the message
        }
    } // end method processMessage

    // manipulate displayArea in event-dispatch thread
    private void displayMessage(final String messageToDisplay) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        displayArea.append(messageToDisplay); // updates output
                    } // end method run
                }  // end inner class
        ); // end call to SwingUtilities.invokeLater
    } // end method displayMessage

    // utility method to set mark on board in event-dispatch thread
    private void setMark(final Square squareToMark, final String mark) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        squareToMark.setMark(mark); // set mark in square
                    } // end method run
                } // end anonymous inner class
        ); // end call to SwingUtilities.invokeLater
    } // end method setMark

    // send message to server indicating clicked square
    public void sendClickedSquare(int location) {
        // if it is my turn
        if (myTurn) {
            output.format("%d\n", location); // send location to server
            output.flush();
            myTurn = false; // not my turn any more
        } // end if
    } // end method sendClickedSquare

    // set current Square
    public void setCurrentSquare(Square square) {
        currentSquare = square; // set current square to argument
    } // end method setCurrentSquare

    // private inner class for the squares on the board
    private class Square extends JPanel {
        private String mark; // mark to be drawn in this square
        private final int location; // location of square

        public Square(String squareMark, int squareLocation) {
            mark = squareMark; // set mark for this square
            location = squareLocation; // set location of this square

            addMouseListener(
                    new MouseAdapter() {
                        public void mouseReleased(MouseEvent e) {
                            setCurrentSquare(Square.this); // set current square

                            // send location of this square
                            sendClickedSquare(getSquareLocation());
                        } // end method mouseReleased
                    } // end anonymous inner class
            ); // end call to addMouseListener
        } // end Square constructor

        // return preferred size of Square
        public Dimension getPreferredSize() {
            return new Dimension(30, 30); // return preferred size
        } // end method getPreferredSize

        // return minimum size of Square
        public Dimension getMinimumSize() {
            return getPreferredSize(); // return preferred size
        } // end method getMinimumSize

        // set mark for Square
        public void setMark(String newMark) {
            mark = newMark; // set mark of square
            repaint(); // repaint square
        } // end method setMark

        // return Square location
        public int getSquareLocation() {
            return location; // return location of square
        } // end method getSquareLocation

        // draw Square
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.drawRect(0, 0, 29, 29); // draw square
            g.drawString(mark, 11, 20); // draw mark
        } // end method paintComponent
    } // end inner-class Square
} // end class backend.TicTacToeClient

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

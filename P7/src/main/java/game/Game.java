package game;

import java.sql.Timestamp;
import static interfaces.Constants.lowestDate;
public class Game {
    private String playerX;
    private String playerO;
    private String board;
    private Timestamp date_time;
    private boolean status;
    private int next_move;

    public Game() {
        this.playerX = "";
        this.playerO = "";
        this.board = "000000000";
        this.date_time = Timestamp.valueOf(lowestDate);
        this.status = false;
        this.next_move = 0;
    }
    public Game(String playerX, String playerO, Timestamp date_time, String board, boolean status, int next_move) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.date_time = date_time;
        this.board = board;
        this.next_move = next_move;
        this.status = status;
    }

    public String getPlayerX() {
        return playerX;
    }
    public String getPlayerO() {
        return playerO;
    }
    public String getBoard() {
        return board;
    }
    public Timestamp getDateTime() {
        return date_time;
    }
    public boolean gameHasEnded() {
        return status;
    }
    public int getNext_move() {
        return next_move;
    }


    public void setPlayerX(String playerX) {
        this.playerX = playerX;
    }
    public void setPlayerO(String playerO) {
        this.playerO = playerO;
    }
    public void setBoard(String board) {
        this.board = board;
    }
    public void setDateTime(Timestamp date_time) {
        this.date_time = date_time;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    public void setNext_move(int next_move) {
        this.next_move = next_move;
    }

    public boolean readyToClose() {
        return !playerX.isEmpty() && !playerO.isEmpty();
    }
}

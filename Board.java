package blackhole;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Board {

    private ArrayList<Player> players;
    private Field[][] board;
    private final int boardSize;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        board = new Field[this.boardSize][this.boardSize];
        for (int i = 0; i < this.boardSize; ++i) {
            for (int j = 0; j < this.boardSize; ++j) {
                board[i][j] = new Field();
            }
        }
        //board[boardSize/2][boardSize/2].setColor(Color.black);
        
        Player player1 = new Player(this, "Red", 1);
        Player player2 = new Player(this, "Blue", -1);
    }
    
    public boolean isOver() {return true;}
    
    public Field get(int x, int y) {
        return board[x][y];
    }
    
    public Field get(Point point) {
        int x = (int)point.getX();
        int y = (int)point.getY();
        return get(x, y);
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }
}

class Player {
    private Board board;
    private String name;
    private int ships;
    private int shipsInBlackHole = 0;
    private int direction;

    public Player(Board board, String name, int direction) {
        this.board = board;
        this.name = name;
        this.ships = board.getBoardSize() - 1;
        this.direction = direction;
    }
}

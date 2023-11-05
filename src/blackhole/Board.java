package blackhole;

import java.awt.Point;
import java.util.HashMap;
import javax.swing.JToggleButton;

/**
 * @author Éles Eszter
 * @version 1.0.0 Nov 07, 2022.
 */

public class Board {

    private final int boardSize;
    private int redPlayers;
    private int bluePlayers;
    private String playing;

    public Board(int boardSize) {
        this.boardSize = boardSize;
        this.redPlayers = 0;
        this.bluePlayers = 0;
        this.playing = "red";
    }
    
    public void setRedPlayers() {
        redPlayers++;
    }
    
    public void setBluePlayers() {
        bluePlayers++;
    }
    
    public int getBoardSize() {
        return boardSize;
    }
    
    public String getPlaying() {
        return playing;
    }
    
    public void setPlaying(String playing) {
        this.playing = playing;
    }
    
    /**
     * This method checks if the game is over - if either player's number of spaceships
     * in the black hole reaches half of their spaceship's number, it returns true.
     * @return 
     */
    public boolean isOver() {
        if(redPlayers == (boardSize - 1) / 2 
                || bluePlayers == (boardSize - 1) / 2) {
            return true;
        }
        return false;
    }
}

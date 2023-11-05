package blackhole;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * @author Éles Eszter
 * @version 1.0.0 Nov 07, 2022.
 */

public class BoardGUI {

    private int boardSize;
    private JFrame frame;
    private Board board;
    private JPanel boardPanel;
    private ButtonGroup btnGroup;
    private HashMap<Point, JToggleButton> buttons;

    public BoardGUI(int boardSize, JFrame frame) {
        this.boardSize = boardSize;
        this.frame = frame;
        this.board = new Board(boardSize);
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(board.getBoardSize(), board.getBoardSize()));
        btnGroup = new ButtonGroup();
        
        init();
        setActiveButtons();
    }
    
    /**
     * This method initializes the cells and buttons on the board. If the loop
     * reaches a cell which is on the diagonal of the board, it creates a button
     * with the spaceship's image on it; in case that button is in the middle of
     * the board, the method turns it into the black hole.
     */
    public void init() {
        buttons = new HashMap<Point, JToggleButton>();
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                ImageIcon icon = null;
                JPanel cell = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                cell.setPreferredSize(new Dimension(40, 40));
                cell.setBorder(BorderFactory.createLineBorder(Color.black));
                JToggleButton button = new JToggleButton();
                if (i == j || i + j + 1 == board.getBoardSize()) {
                    if (j < board.getBoardSize() / 2) {
                        java.net.URL imgURL = BoardGUI.class.getResource("player1.png");
                        icon = new ImageIcon(imgURL);
                        Image image = icon.getImage();
                        Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        icon = new ImageIcon(newimg);
                        button = new JToggleButton(icon, false);
                        button.setBackground(Color.WHITE);
                        button.setName("red");
                    } else if (j > board.getBoardSize() / 2) {
                        java.net.URL imgURL = BoardGUI.class.getResource("player2.png");
                        icon = new ImageIcon(imgURL);
                        Image image = icon.getImage(); // transform it 
                        Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        icon = new ImageIcon(newimg);
                        button = new JToggleButton(icon, false);
                        button.setName("blue");
                        button.setBackground(Color.WHITE);
                    } else {
                        button = new JToggleButton();
                        button.setBackground(Color.BLACK);
                        button.setName("black");
                        button.setEnabled(false);
                    }
                    button.setPreferredSize(new Dimension(40, 40));
                    btnGroup.add(button);
                    cell.add(button);
                    buttons.put(new Point(i, j), button);
                }
                boardPanel.add(cell);
            }
        }
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    public HashMap<Point, JToggleButton> getButtons() {
        return buttons;
    }

    public int getBoardSize() {
        return boardSize;
    }

    /**
     * This method places a spaceship's button on another cell by removing the
     * previous button and creating a new one in the HashMap. It calls the refresh()
     * method with the actual player's color.
     * @param p
     * @param x
     * @param y 
     */
    public void setButton(Point p, int x, int y) {
        String player = buttons.get(p).getName();
        JToggleButton btn = buttons.get(p);
        buttons.remove(p);
        Point np = new Point(x, y);
        buttons.put(np, btn);
        refresh(player);
    }

    /**
     * This method changes the player from red to blue or blue to red after
     * the previous player made a step. It calls the setButtons() method to
     * enable the new player's buttons.
     */
    public void changePlayer() {
        if (board.getPlaying().equals("red")) {
            board.setPlaying("blue");
        } else {
            board.setPlaying("red");
        }
                
        setActiveButtons();
    }

    /**
     * This method removes a player's button (spaceship) after it got into a 
     * black hole. It calls the board's setRedPlayers() or setBluePlayers() method
     * to increase the number of spaceships the player has in the black hole.
     * It calls the refresh() method to redraw the board,
     * @param p 
     */
    public void removeButton(Point p) {
        String player = buttons.get(p).getName();
        switch(player) {
            case "red":
                board.setRedPlayers();
                break;
            case "blue":
                board.setBluePlayers();
                break;
        }
        buttons.remove(p);
        refresh(player);
    }

    /**
     * This method enables the buttons of the player who has to make a step and 
     * disables the other player's buttons.
     */
    public void setActiveButtons() {
        for (Point p : buttons.keySet()) {
            String btn = buttons.get(p).getName();
            if (btn != "black" && btn == board.getPlaying()) {
                buttons.get(p).setEnabled(true);
            } else if (btn != "black") {
                buttons.get(p).setEnabled(false);
            }
        }
    }

    /**
     * This method redraws the board after a step was made by a player.
     * It checks if the game is over afterwards by calling the board's isOver()
     * method. If the isOver() method returns true, this method shows a message 
     * to the players with the winner's name. After that, it calls the init()
     * method and itself to initialize the buttons and redraw the board - therefore
     * start a new game.
     * @param player 
     */
    public void refresh(String player) {
        boardPanel.removeAll();
        frame.getContentPane().remove(boardPanel);

        for (int i = 0; i < board.getBoardSize(); ++i) {
            for (int j = 0; j < board.getBoardSize(); ++j) {
                JPanel cell = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                cell.setPreferredSize(new Dimension(40, 40));
                cell.setBorder(BorderFactory.createLineBorder(Color.black));
                Point p = new Point(i, j);
                if (buttons.containsKey(p)) {
                    cell.add(buttons.get(p));
                }
                boardPanel.add(cell);
            }
        }
        frame.getContentPane().add(boardPanel, BorderLayout.WEST);
        frame.pack();
        if (board.isOver()) {
            switch (player) {
                case "red":
                    JOptionPane.showMessageDialog(boardPanel, "Red won!", "Congrats!",
                    JOptionPane.PLAIN_MESSAGE);
                    break;
                case "blue":
                    JOptionPane.showMessageDialog(boardPanel, "Blue won!", "Congrats!",
                    JOptionPane.PLAIN_MESSAGE);
                    break;
            }
            init();
            this.board = new Board(this.boardSize);
            refresh("");
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackhole;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.Timer;

/**
 *
 * @author elese
 */
public class BoardGUI {

    private JToggleButton[][] buttons;
    private JPanel[][] cells;
    private Board board;
    private JPanel boardPanel;
    private JLabel timeLabel;
    private ButtonGroup btnGroup;
    private HashMap<Point, JToggleButton> collection;

    private Random random = new Random();
    private int clickNum = 0;
    private long startTime;
    private Timer timer;

    public BoardGUI(int boardSize) {
        board = new Board(boardSize);
        collection = new HashMap<Point, JToggleButton>();
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(board.getBoardSize(), board.getBoardSize()));
        cells = new JPanel[board.getBoardSize()][board.getBoardSize()];
        buttons = new JToggleButton[board.getBoardSize()][board.getBoardSize()];
        for (int i = 0; i < board.getBoardSize(); i++) {
            for (int j = 0; j < board.getBoardSize(); j++) {
                ImageIcon icon = null;
                btnGroup = new ButtonGroup();
                JPanel cell = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                cell.setPreferredSize(new Dimension(40, 40));
                cell.setBorder(BorderFactory.createLineBorder(Color.black));
                cells[i][j] = cell;
                boardPanel.add(cell);
                JToggleButton button;
                if (i == j || i + j + 1 == board.getBoardSize()) {
                    if (j < board.getBoardSize() / 2) {
                        java.net.URL imgURL = BoardGUI.class.getResource("player1.jpg");
                        icon = new ImageIcon(imgURL);
                        Image image = icon.getImage();
                        Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        icon = new ImageIcon(newimg);
                        button = new JToggleButton(icon, false);
                        button.setName("red");
                        button.setBackground(Color.WHITE);
                        button.addActionListener(new ButtonListener(i, j));
                        button.setPreferredSize(new Dimension(40, 40));
                        buttons[i][j] = button;
                        btnGroup.add(button);
                        cells[i][j].add(button);
                        //boardPanel.add(cell);
                        collection.put(new Point(i, j), button);
                    } else if (j > board.getBoardSize() / 2) {
                        java.net.URL imgURL = BoardGUI.class.getResource("player2.jpg");
                        icon = new ImageIcon(imgURL);
                        Image image = icon.getImage(); // transform it 
                        Image newimg = image.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
                        icon = new ImageIcon(newimg);
                        button = new JToggleButton(icon, false);
                        button.setName("blue");
                        button.setBackground(Color.WHITE);
                        button.addActionListener(new ButtonListener(i, j));
                        button.setPreferredSize(new Dimension(40, 40));
                        buttons[i][j] = button;
                        btnGroup.add(button);
                        cells[i][j].add(button);
                        //boardPanel.add(button);
                        collection.put(new Point(i, j), button);
                    } else {
                        button = new JToggleButton();
                        button.setBackground(Color.BLACK);
                        button.setName("black");
                        button.addActionListener(new ButtonListener(i, j));
                        button.setPreferredSize(new Dimension(40, 40));
                        button.setEnabled(false);
                        buttons[i][j] = button;
                        cells[i][j].add(button);
                        //boardPanel.add(button);
                        collection.put(new Point(i, j), button);
                    }
                }
            }
            
        }
        System.out.println(collection.toString());

        //refresh();
    }

    public JPanel getBoardPanel() {
        return boardPanel;
    }

    public JLabel getTimeLabel() {
        return timeLabel;
    }

    class ButtonListener implements ActionListener {

        private int x, y;

        public ButtonListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Point p = new Point(x, y);
            if (buttons[x][y].getBackground() == Color.WHITE) {
                cells[x][y].remove(buttons[x][y]);
                System.out.println("BackGround color changed!");
                buttons[x][y].setBackground(Color.MAGENTA);
                //cells[x][y].add(buttons[x][y]);
                //boardPanel.add(cells[x][y]);
            } else {
                cells[x][y].remove(buttons[x][y]);
                buttons[x][y].setBackground(Color.WHITE);
                //cells[x][y].add(buttons[x][y]);
            }
                //repaint();//repaint your frame
                
        }
    }

        /*public void refresh() {
        for (int i = 0; i < board.getBoardSize(); ++i) {
            for (int j = 0; j < board.getBoardSize(); ++j) {
                Field field = board.get(i, j);
                JButton button = buttons[i][j];
                button.setBackground(field.getColor());
            }
        }
        if (board.isOver()) {
            timer.stop();
            JOptionPane.showMessageDialog(boardPanel, "You have won in " + elapsedTime() + " ms.", "Congrats!",
                    JOptionPane.PLAIN_MESSAGE);
            
        }
    }*/
    }

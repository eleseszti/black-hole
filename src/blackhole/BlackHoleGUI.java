package blackhole;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * @author Éles Eszter
 * @version 1.0.0 Nov 07, 2022.
 */
public class BlackHoleGUI {

    private JFrame frame;
    private BoardGUI boardGUI;
    private JPanel dirPanel;

    private final int INITIAL_BOARD_SIZE = 5;

    /**
     * This method is the constructor of the BlackHoleGUI class, it creates a
     * menu on top of frame, a board on the left side and a panel for the
     * directions on the right side. The user can start a new game and choose
     * from 3 board sizes: 5x5, 7x7 or 9x9 boards are available.
     */
    public BlackHoleGUI() {
        frame = new JFrame("BlackHole");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boardGUI = new BoardGUI(INITIAL_BOARD_SIZE, frame);
        frame.getContentPane().add(boardGUI.getBoardPanel(), BorderLayout.WEST);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Game");
        menuBar.add(gameMenu);
        JMenu newMenu = new JMenu("New");
        gameMenu.add(newMenu);
        int[] boardSizes = new int[]{5, 7, 9};
        for (int boardSize : boardSizes) {
            JMenuItem sizeMenuItem = new JMenuItem(boardSize + "x" + boardSize);
            newMenu.add(sizeMenuItem);
            sizeMenuItem.addActionListener(new ActionListener() {
                /**
                 * This method starts a new game with the selected board size.
                 * @param ae
                 */
                @Override
                public void actionPerformed(ActionEvent ae) {
                    frame.getContentPane().remove(boardGUI.getBoardPanel());
                    boardGUI = new BoardGUI(boardSize, frame);
                    frame.getContentPane().add(boardGUI.getBoardPanel(),
                            BorderLayout.WEST);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                }
            });

        }
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            /**
             * This method exits the game.
             * @param ae
             */
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        dirPanel = new JPanel();
        dirPanel.setLayout(new GridLayout(4, 1));
        String dirs[] = {"Up", "Down", "Left", "Right"};
        for (String dir : dirs) {
            JButton dirButton = new JButton(dir);
            dirButton.addActionListener(new BlackHoleActionListener(dir));
            dirPanel.add(dirButton);
        }
        frame.getContentPane().add(dirPanel, BorderLayout.EAST);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    class BlackHoleActionListener implements ActionListener {

        private String direction;

        public BlackHoleActionListener(String direction) {
            this.direction = direction;
        }

        /**
         * This method is for moving the spaceship in selected direction; it warns
         * the user if there are no spaceships chosen or if the spaceship's way
         * is blocked in selected direction. In case the spaceship is free to
         * move, it moves until it meets either the side of the board, another
         * spaceship or until it gets into the black hole.
         *
         * @param ae
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            boolean chosenSH = true;
            HashMap<Point, JToggleButton> buttons = boardGUI.getButtons();
            Point selectedButton = new Point(-1, -1);
            for (Point p : buttons.keySet()) {
                if (buttons.get(p).isSelected() && buttons.get(p).isEnabled()) {
                    selectedButton = p;
                }
            }
            if (selectedButton.getX() == -1) {
                JOptionPane.showMessageDialog(frame, "Please choose a spaceship!");
                chosenSH = false;
            }
            boolean collision = false;
            if (chosenSH) {
                switch (direction) {
                    case "Up":
                        for (Point p : buttons.keySet()) {
                            if (!p.equals(selectedButton)
                                    && (p.getX() != boardGUI.getBoardSize() / 2
                                    || p.getY() != boardGUI.getBoardSize() / 2)
                                    && p.getX() == selectedButton.getX() - 1
                                    && p.getY() == selectedButton.getY()) {
                                collision = true;
                                break;
                            }
                        }
                        if (selectedButton.getX() == 0 || collision) {
                            JOptionPane.showMessageDialog(frame, "You cannot move upwards, please choose another direction or another spaceship!");
                            break;
                        }
                        for (int i = (int) selectedButton.getX() - 1; i >= 0; i--) {
                            for (Point p : buttons.keySet()) {
                                if (buttons.get(p).isSelected()) {
                                    selectedButton = p;
                                }
                            }
                            if (selectedButton.getY() == boardGUI.getBoardSize() / 2 && i == boardGUI.getBoardSize() / 2) {
                                boardGUI.removeButton(selectedButton);
                            }
                            for (Point p : buttons.keySet()) {
                                if (!p.equals(selectedButton) && p.getX() == i && p.getY() == selectedButton.getY()) {
                                    collision = true;
                                    break;
                                }
                            }
                            if (!collision) {
                                boardGUI.setButton(selectedButton, i, (int) selectedButton.getY());
                            }
                        }
                        boardGUI.changePlayer();
                        break;
                    case "Down":
                        for (Point p : buttons.keySet()) {
                            if (!p.equals(selectedButton)
                                    && (p.getX() != boardGUI.getBoardSize() / 2
                                    || p.getY() != boardGUI.getBoardSize() / 2)
                                    && p.getX() == selectedButton.getX() + 1
                                    && p.getY() == selectedButton.getY()) {
                                collision = true;
                                break;
                            }
                        }
                        if (selectedButton.getX() == boardGUI.getBoardSize() - 1 || collision) {
                            JOptionPane.showMessageDialog(frame, "You cannot move downwards, please choose another direction or another spaceship!");
                            break;
                        }
                        for (int i = (int) selectedButton.getX() + 1; i < boardGUI.getBoardSize(); i++) {
                            for (Point p : buttons.keySet()) {
                                if (buttons.get(p).isSelected()) {
                                    selectedButton = p;
                                }
                            }
                            if (selectedButton.getY() == boardGUI.getBoardSize() / 2 && i == boardGUI.getBoardSize() / 2) {
                                boardGUI.removeButton(selectedButton);
                            }
                            for (Point p : buttons.keySet()) {
                                if (!p.equals(selectedButton) && p.getX() == i && p.getY() == selectedButton.getY()) {
                                    collision = true;
                                    break;
                                }
                            }
                            if (!collision) {
                                boardGUI.setButton(selectedButton, i, (int) selectedButton.getY());
                            }
                        }
                        boardGUI.changePlayer();
                        break;
                    case "Left":
                        for (Point p : buttons.keySet()) {
                            if (!p.equals(selectedButton)
                                    && (p.getX() != boardGUI.getBoardSize() / 2
                                    || p.getY() != boardGUI.getBoardSize() / 2)
                                    && p.getX() == selectedButton.getX()
                                    && p.getY() == selectedButton.getY() - 1) {
                                collision = true;
                                break;
                            }
                        }
                        if (selectedButton.getY() == 0 || collision) {
                            JOptionPane.showMessageDialog(frame, "You cannot move leftwards, please choose another direction or another spaceship!");
                            break;
                        }
                        for (int i = (int) selectedButton.getY() - 1; i >= 0; i--) {
                            for (Point p : buttons.keySet()) {
                                if (buttons.get(p).isSelected()) {
                                    selectedButton = p;
                                }
                            }
                            if (selectedButton.getX() == boardGUI.getBoardSize() / 2 && i == boardGUI.getBoardSize() / 2) {
                                boardGUI.removeButton(selectedButton);
                            }
                            for (Point p : buttons.keySet()) {
                                if (!p.equals(selectedButton) && p.getX() == selectedButton.getX() && p.getY() == i) {
                                    collision = true;
                                    break;
                                }
                            }
                            if (!collision) {
                                boardGUI.setButton(selectedButton, (int) selectedButton.getX(), i);
                            }
                        }
                        boardGUI.changePlayer();
                        break;
                    case "Right":
                        for (Point p : buttons.keySet()) {
                            if (!p.equals(selectedButton)
                                    && (p.getX() != boardGUI.getBoardSize() / 2
                                    || p.getY() != boardGUI.getBoardSize() / 2)
                                    && p.getX() == selectedButton.getX()
                                    && p.getY() == selectedButton.getY() + 1) {
                                collision = true;
                                break;
                            }
                        }
                        if (selectedButton.getY() == boardGUI.getBoardSize() - 1 || collision) {
                            JOptionPane.showMessageDialog(frame, "You cannot move rightwards, please choose another direction or another spaceship!");
                            break;
                        }
                        for (int i = (int) selectedButton.getY() + 1; i < boardGUI.getBoardSize(); i++) {
                            for (Point p : buttons.keySet()) {
                                if (buttons.get(p).isSelected()) {
                                    selectedButton = p;
                                }
                            }
                            if (selectedButton.getX() == boardGUI.getBoardSize() / 2 && i == boardGUI.getBoardSize() / 2) {
                                boardGUI.removeButton(selectedButton);
                            }
                            for (Point p : buttons.keySet()) {
                                if (!p.equals(selectedButton) && p.getX() == selectedButton.getX() && p.getY() == i) {
                                    collision = true;
                                    break;
                                }
                            }
                            if (!collision) {
                                boardGUI.setButton(selectedButton, (int) selectedButton.getX(), i);
                            }
                        }
                        boardGUI.changePlayer();
                        break;
                }
            }
        }
    }
}

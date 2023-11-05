/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackhole;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author elese
 */
public class BlackHoleGUI {
    
    private JFrame frame;
    private BoardGUI boardGUI;
    private JPanel dirPanel;
    private JPanel nextPanel;
    
    private final int INITIAL_BOARD_SIZE = 5;
    
    public BlackHoleGUI() {
        frame = new JFrame("BlackHole");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        boardGUI = new BoardGUI(INITIAL_BOARD_SIZE);
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
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().remove(boardGUI.getBoardPanel());
                    boardGUI = new BoardGUI(boardSize);
                    frame.getContentPane().add(boardGUI.getBoardPanel(),
                            BorderLayout.WEST);
                    frame.pack();
                }
            });
        }
        
        dirPanel = new JPanel();
        dirPanel.setLayout(new GridLayout(4, 1));
        String dirs[] = {"Up", "Down", "Left", "Right"};
        for(String dir : dirs) {
            JButton dirButton = new JButton(dir);
            dirButton.addActionListener(new BlackHoleActionListener(dir));
            dirPanel.add(dirButton);
        }
        frame.getContentPane().add(dirPanel, BorderLayout.EAST);
        
        nextPanel = new JPanel();
        frame.getContentPane().add(nextPanel, BorderLayout.NORTH);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    class BlackHoleActionListener implements ActionListener {
        private String direction;
        
        public BlackHoleActionListener(String direction) {
            this.direction = direction;
        }
        
         @Override
        public void actionPerformed(ActionEvent ae) {}
    }
}

package main;

import game.Game;
import javax.swing.*;
import java.awt.*;
import static game.Game.MAP_SIZE;

public class Main {
    static final int WINDOW_WIDTH = 256;
    static final int WINDOW_HEIGHT = 300;
    public static void main(String[] args) {
        setMainFrame();
    }
    static void setMainFrame() {
        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Snake");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        mainFrame.setBounds(
                dimension.width / 2 - WINDOW_WIDTH / 2,
                dimension.height / 2 - WINDOW_HEIGHT / 2,
                WINDOW_WIDTH,
                WINDOW_HEIGHT);
        Game game = new Game();
        game.gamePanel.setSize(MAP_SIZE, MAP_SIZE);
        mainFrame.add(game.gamePanel);
        mainFrame.setVisible(true);
        game.startNewGame();
    }
}

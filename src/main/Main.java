package main;

import game.Game;
import javax.swing.*;
import java.awt.*;

import static game.Game.MAP_SIZE;

public class Main {
    //Размеры окна, константы
static final int WINDOW_WIDTH = 300;
static final int WINDOW_HEIGHT = 300;

    public static void main(String[] args){
        setMainFrame();
    }
    //Создание основного фрейма, начало игры
    static void setMainFrame() {
        //Создание основного фрейма
        JFrame mainFrame = new JFrame();
        //Название окна
        mainFrame.setTitle("Змейка");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        //Размещение окна на экране
        mainFrame.setBounds(dimension.width/2 - WINDOW_WIDTH/2, dimension.height/2 - WINDOW_HEIGHT/2, WINDOW_WIDTH, WINDOW_HEIGHT);
        //Создание, начало игры
        Game game = new Game();
        game.startNewGame();
        //добавление панели игры на основной фрейм
        game.gamePanel.setSize(MAP_SIZE, MAP_SIZE);
        mainFrame.add(game.gamePanel);
        mainFrame.setVisible(true);
    }


}

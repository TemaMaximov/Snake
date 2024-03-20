package game;

import gamePanel.GamePanel;
import restartPanel.RestartPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;

import static java.awt.event.KeyEvent.*;

public class Game implements ActionListener{
    //Размеры поля игры
    static public final int MAP_SIZE = 240;
    //Паузы между движением змейки, миллисекунды
    Timer timer = new Timer(200, this);
    Snake snake = new Snake();
    int pressedKey = VK_RIGHT;
    public GamePanel gamePanel = new GamePanel();
    static final public RestartPanel restartPanel = new RestartPanel();
    public Game(){
    }
    //Новая игра
    public void startNewGame(){
        snake.resurrect();
        AppleController.clear();
        AppleController.generateApple(snake);
        gamePanel.setApples(AppleController.apples);
        gamePanel.setSnake(snake);
        gamePanel.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                super.keyPressed(e);
                int c = e.getKeyCode();
                assert c == VK_LEFT || c == VK_DOWN || c == VK_RIGHT || c == VK_UP;
                if(pressedKey != c) {
                    if (Direction.opposite(Objects.requireNonNull(Direction.keyCodeToDirection(pressedKey))) != Direction.keyCodeToDirection(c)) {
                        pressedKey = e.getKeyCode();
                        snake.turn(Direction.keyCodeToDirection(e.getKeyCode()));
                    }
                }
            }
        });
        timer.start();
    }
    static public int stayInFrame(int x){
        return (MAP_SIZE + x) % MAP_SIZE;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(snake.alive()) {
            if (AppleController.checkForApple(snake.xAhead(), snake.yAhead())){
                snake.grow();
                AppleController.generateApple(snake);
                gamePanel.repaint();
                return;
            }
            if (!snake.checkCollisions()) {
                snake.move();
                gamePanel.repaint();
            } else snake.die();
        }else{
            if(restartPanel.buttonPushed){
                restartPanel.buttonPushed = false;
                restartPanel.setVisible(false);
                gamePanel.setVisible(true);
                startNewGame();
            }else {
                gamePanel.setVisible(false);
                restartPanel.setVisible(true);
            }
        }
    }
}

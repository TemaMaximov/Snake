package game;

import gamepanel.GamePanel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;

public class Game implements ActionListener{
    public static final int MAP_SIZE = 240;
    Timer timer = new Timer(200, this);
    Snake snake = new Snake();
    public final GamePanel gamePanel = new GamePanel();
    public void startNewGame(){
        AppleController.clear();
        AppleController.generateApple(snake);
        gamePanel.setApples(AppleController.apples);
        gamePanel.setSnake(snake);
        gamePanel.addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                super.keyPressed(e);
                snake.turn(Direction.keyCodeToDirection(e.getKeyCode()));
            }
        });
        timer.start();
    }

    public static int stayInFrame(int x){
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
        }
    }
}

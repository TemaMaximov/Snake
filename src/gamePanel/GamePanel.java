package gamePanel;

import game.Snake;
import game.Unit;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

import static game.Game.MAP_SIZE;
public class GamePanel extends JPanel {
    Snake snake;
    LinkedList<Unit> apples;
    public GamePanel(Snake snake, LinkedList<Unit> apples){
        setBackground(Color.black);
        this.snake = snake;
        this.apples = apples;
        setFocusable(true);
    }
    public GamePanel(){
        setBackground(Color.black);
        setFocusable(true);
    }
    public void setSnake(Snake snake){
        this.snake = snake;
    }
    public void setApples(LinkedList<Unit> apples) {
        this.apples = apples;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawRect(0, 0, MAP_SIZE, MAP_SIZE);
        for (int i = 0; i < snake.size(); i++) {
            g.drawImage(snake.getUnit(i).getImage(), snake.getUnit(i).x, snake.getUnit(i).y, this);
        }
        for (Unit unit : apples) {
            g.drawImage(unit.getImage(), unit.x, unit.y, this);
        }
    }
}

package game;

import GameImages.Icons;
import java.util.LinkedList;

import static game.Game.stayInFrame;
import static game.Unit.UNIT_SIZE;

public class AppleController {
    static public LinkedList<Unit> apples = new LinkedList<>();
    static public void generateApple(Snake snake){
        boolean collisionFlag = true;
        int X, Y;
        loop: do{
            X = stayInFrame((int)(Math.random() * 100) * UNIT_SIZE);
            Y = stayInFrame((int)(Math.random() * 100) * UNIT_SIZE);
            for(Unit unit : snake.body){
                if(unit.x == X && unit.y == Y){
                    continue loop;
                }
            }
            collisionFlag = false;
        }while(collisionFlag);
        apples.add(new Unit(X, Y, Icons.appleIcon));
    }
    static public boolean checkForApple(int x, int y){
        for(int i = 0; i < apples.size(); i++){
            if(x == apples.get(i).x && y == apples.get(i).y){
                apples.remove(i);
                return true;
            }
        }
        return false;
    }
    static public void clear(){
        apples.clear();
    }
}

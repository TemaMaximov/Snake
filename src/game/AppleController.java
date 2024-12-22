package game;

import gameimages.Icons;
import java.util.LinkedList;
import java.util.Random;

import static game.Game.stayInFrame;
import static game.Unit.UNIT_SIZE;

public class AppleController {
    private AppleController(){}
    private static final Random rn = new Random();
    public static LinkedList<Unit> apples = new LinkedList<>();

    public static void generateApple(Snake snake){
        boolean collisionFlag = true;
        int x = 0;
        int y = 0;
        while(collisionFlag){
            x = stayInFrame(rn.nextInt(100) * UNIT_SIZE);
            y = stayInFrame(rn.nextInt(100) * UNIT_SIZE);
            collisionFlag = false;
            for(Unit unit : snake.getBody()){
                if (unit.x == x && unit.y == y) {
                    collisionFlag = true;
                    break;
                }
            }
        }
        apples.add(new Unit(x, y, Icons.appleIcon));
    }

    public static boolean checkForApple(int x, int y){
        for(int i = 0; i < apples.size(); i++){
            if(x == apples.get(i).x && y == apples.get(i).y){
                apples.remove(i);
                return true;
            }
        }
        return false;
    }

    public static void clear(){
        apples.clear();
    }
}

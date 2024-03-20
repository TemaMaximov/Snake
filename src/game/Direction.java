package game;


import static java.awt.event.KeyEvent.*;

public enum Direction {
    LEFT, UP, RIGHT, DOWN;

    public static Direction opposite(Direction dir){
        switch(dir){
            case LEFT -> {
                return RIGHT;
            }
            case RIGHT ->{
                return LEFT;
            }
            case UP ->{
                return DOWN;
            }
            case DOWN ->{
                return UP;
            }
            default ->{
                return null;
            }
        }
    }

    public static Direction keyCodeToDirection(int e){
        switch(e){
            case VK_LEFT -> {return LEFT;}
            case VK_RIGHT -> {return RIGHT;}
            case VK_DOWN -> {return DOWN;}
            case VK_UP -> {return UP;}
            default -> {return null;}
        }
    }
}

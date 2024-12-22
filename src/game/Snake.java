package game;

import gameimages.Icons;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import static game.Game.stayInFrame;

import static game.Unit.UNIT_SIZE;

public class Snake {
    private Queue<Direction> turningsQueue;
    private Direction currentDirection;
    private boolean alive;
    private List<Unit> body;
    private String neckFlag;
    public Snake() {
        alive = true;
        setBody(new ArrayList<>());
        getBody().add(new Unit(Unit.UNIT_SIZE * 6, Unit.UNIT_SIZE * 3, Icons.headIconRight));
        getBody().add(new Unit(Unit.UNIT_SIZE * 5, Unit.UNIT_SIZE * 3, Icons.bodyIconHorizontalUp));
        getBody().add(new Unit(Unit.UNIT_SIZE * 4, Unit.UNIT_SIZE * 3, Icons.bodyIconHorizontalDown));
        getBody().add(new Unit(Unit.UNIT_SIZE * 3, Unit.UNIT_SIZE * 3, Icons.bodyIconHorizontalUp));
        getBody().add(new Unit(Unit.UNIT_SIZE * 2, Unit.UNIT_SIZE * 3, Icons.bodyIconHorizontalDown));
        getBody().add(new Unit(Unit.UNIT_SIZE, Unit.UNIT_SIZE * 3, Icons.tailIconRight));
        turningsQueue = new LinkedList<>();
        currentDirection = Direction.RIGHT;
        neckFlag = "straight";
    }

    public int size(){
        return getBody().size();
    }
    public Unit getUnit(int index){
        return getBody().get(index);
    }
    public void die(){
        alive = false;
    }
    public boolean alive(){return alive;}
    public boolean checkCollisions(){
        int x = xAhead();
        int y = yAhead();
        for(int i = 1; i < getBody().size(); i++){
            if(x == getBody().get(i).x && y == getBody().get(i).y){
                return true;
            }
        }
        return false;
    }
    public void turn(Direction direction){
        if (direction != null && (!turningsQueue.isEmpty() && turningsQueue.peek() != direction && turningsQueue.peek() != Direction.opposite(direction) || turningsQueue.isEmpty() && currentDirection != direction && currentDirection != Direction.opposite(direction)))
                turningsQueue.offer(direction);
    }
    public void move(){
        Image img = getBody().get(getBody().size() - 1).getImage();
        if(img == Icons.tailIconRight.getImage()){
            if(getBody().get(getBody().size() - 2).getImage() == Icons.bodyIconRightDown.getImage())
                getBody().get(getBody().size() - 1).setImage(Icons.tailIconDown);
            if(getBody().get(getBody().size() - 2).getImage() == Icons.bodyIconRightUp.getImage())
                getBody().get(getBody().size() - 1).setImage(Icons.tailIconUp);
        }
        else if(img == Icons.tailIconLeft.getImage()){
            if(getBody().get(getBody().size() - 2).getImage() == Icons.bodyIconLeftDown.getImage())
                getBody().get(getBody().size() - 1).setImage(Icons.tailIconDown);
            if(getBody().get(getBody().size() - 2).getImage() == Icons.bodyIconDownRight.getImage())
                getBody().get(getBody().size() - 1).setImage(Icons.tailIconUp);
        }
        else if(img == Icons.tailIconUp.getImage()){
            if(getBody().get(getBody().size() - 2).getImage() == Icons.bodyIconLeftDown.getImage())
                getBody().get(getBody().size() - 1).setImage(Icons.tailIconRight);
            if(getBody().get(getBody().size() - 2).getImage() == Icons.bodyIconRightDown.getImage())
                getBody().get(getBody().size() - 1).setImage(Icons.tailIconLeft);
        }
        else if(img == Icons.tailIconDown.getImage()){
            if(getBody().get(getBody().size() - 2).getImage() == Icons.bodyIconRightUp.getImage())
                getBody().get(getBody().size() - 1).setImage(Icons.tailIconLeft);
            if(getBody().get(getBody().size() - 2).getImage() == Icons.bodyIconDownRight.getImage())
                getBody().get(getBody().size() - 1).setImage(Icons.tailIconRight);
        }
        getBody().get(getBody().size() - 1).x = getBody().get(getBody().size() - 2).x;
        getBody().get(getBody().size() - 1).y = getBody().get(getBody().size() - 2).y;
        for(int i = getBody().size() - 2; i > 1; i--){
            getBody().get(i).copy(getBody().get(i - 1));
        }
        Direction direction = turningsQueue.poll();
        if (direction != null && currentDirection != Direction.opposite(direction) && currentDirection != direction) {
            neckFlag = currentDirection.toString() + direction;
            currentDirection = direction;
            switch (neckFlag) {
                case "RIGHTDOWN", "UPLEFT" -> getBody().get(1).setImage(Icons.bodyIconRightDown);
                case "RIGHTUP", "DOWNLEFT" -> getBody().get(1).setImage(Icons.bodyIconRightUp);
                case "LEFTDOWN", "UPRIGHT" -> getBody().get(1).setImage(Icons.bodyIconLeftDown);
                case "DOWNRIGHT", "LEFTUP" -> getBody().get(1).setImage(Icons.bodyIconDownRight);
            }
        }
        switch (neckFlag) {
            case "horizontal straight 1" -> {
                getBody().get(1).setImage(Icons.bodyIconHorizontalUp);
                neckFlag = "horizontal straight 2";
            }
            case "vertical straight 1" -> {
                getBody().get(1).setImage(Icons.bodyIconVerticalLeft);
                neckFlag = "vertical straight 2";
            }
            case "horizontal straight 2" -> {
                getBody().get(1).setImage(Icons.bodyIconHorizontalDown);
                neckFlag = "horizontal straight 1";
            }
            case "vertical straight 2" -> {
                getBody().get(1).setImage(Icons.bodyIconVerticalRight);
                neckFlag = "vertical straight 1";
            }
        }
        if(neckFlag.equals("RIGHTDOWN") || neckFlag.equals("LEFTDOWN") || neckFlag.equals("RIGHTUP") || neckFlag.equals("LEFTUP")){
            neckFlag = "vertical straight 1";
        }
        else if(neckFlag.equals("DOWNRIGHT") || neckFlag.equals("DOWNLEFT") || neckFlag.equals("UPRIGHT") || neckFlag.equals("UPLEFT")){
            neckFlag = "horizontal straight 1";
        }
        //Измерение координат шеи
        getBody().get(1).x = getBody().get(0).x;
        getBody().get(1).y = getBody().get(0).y;
        //Передвижение головы
        switch (currentDirection) {
            case RIGHT -> {
                getBody().get(0).x = stayInFrame(getBody().get(0).x + UNIT_SIZE);
                getBody().get(0).setImage(Icons.headIconRight);
            }
            case LEFT -> {
                getBody().get(0).x = stayInFrame(getBody().get(0).x - UNIT_SIZE);
                getBody().get(0).setImage(Icons.headIconLeft);
            }
            case DOWN -> {
                getBody().get(0).y = stayInFrame(getBody().get(0).y + UNIT_SIZE);
                getBody().get(0).setImage(Icons.headIconDown);
            }
            case UP -> {
                getBody().get(0).y = stayInFrame(getBody().get(0).y - UNIT_SIZE);
                getBody().get(0).setImage(Icons.headIconUp);
            }
        }
    }
    public void grow(){
        Unit newUnit = new Unit();
        newUnit.copy(getBody().get(getBody().size() - 1));
        getBody().add(newUnit);
        getBody().get(getBody().size() - 1).copy(getBody().get(getBody().size() - 2));
        //Передвижение тела
        for(int i = getBody().size() - 2; i > 1; i--){
            getBody().get(i).copy(getBody().get(i - 1));
        }
        Direction direction = turningsQueue.poll();
        if(direction != null && currentDirection != Direction.opposite(direction)) {
            neckFlag = currentDirection.toString() + direction;
            currentDirection = direction;
            switch (neckFlag) {
                case "RIGHTDOWN", "UPLEFT" -> getBody().get(1).setImage(Icons.bodyIconRightDown);
                case "RIGHTUP", "DOWNLEFT" -> getBody().get(1).setImage(Icons.bodyIconRightUp);
                case "LEFTDOWN", "UPRIGHT" -> getBody().get(1).setImage(Icons.bodyIconLeftDown);
                case "DOWNRIGHT", "LEFTUP" -> getBody().get(1).setImage(Icons.bodyIconDownRight);
            }
        }
        switch (neckFlag) {
            case "horizontal straight 1" -> {
                getBody().get(1).setImage(Icons.bodyIconHorizontalUp);
                neckFlag = "horizontal straight 2";
            }
            case "vertical straight 1" -> {
                getBody().get(1).setImage(Icons.bodyIconVerticalLeft);
                neckFlag = "vertical straight 2";
            }
            case "horizontal straight 2" -> {
                getBody().get(1).setImage(Icons.bodyIconHorizontalDown);
                neckFlag = "horizontal straight 1";
            }
            case "vertical straight 2" -> {
                getBody().get(1).setImage(Icons.bodyIconVerticalRight);
                neckFlag = "vertical straight 1";
            }
        }
        if(neckFlag.equals("RIGHTDOWN") || neckFlag.equals("LEFTDOWN") || neckFlag.equals("RIGHTUP") || neckFlag.equals("LEFTUP")){
            neckFlag = "vertical straight 1";
        }
        else if(neckFlag.equals("DOWNRIGHT") || neckFlag.equals("DOWNLEFT") || neckFlag.equals("UPRIGHT") || neckFlag.equals("UPLEFT")){
            neckFlag = "horizontal straight 1";
        }
        getBody().get(1).x = getBody().get(0).x;
        getBody().get(1).y = getBody().get(0).y;
        switch (currentDirection) {
            case RIGHT -> {
                getBody().get(0).x = stayInFrame(getBody().get(0).x + UNIT_SIZE);
                getBody().get(0).setImage(Icons.headIconRight);
            }
            case LEFT -> {
                getBody().get(0).x = stayInFrame(getBody().get(0).x - UNIT_SIZE);
                getBody().get(0).setImage(Icons.headIconLeft);
            }
            case DOWN -> {
                getBody().get(0).y = stayInFrame(getBody().get(0).y + UNIT_SIZE);
                getBody().get(0).setImage(Icons.headIconDown);
            }
            case UP -> {
                getBody().get(0).y = stayInFrame(getBody().get(0).y - UNIT_SIZE);
                getBody().get(0).setImage(Icons.headIconUp);
            }
        }
    }
    public int xAhead(){
        int x = 0;
        Direction direction;
        if(turningsQueue.peek() == null)
            direction = currentDirection;
        else
            direction = turningsQueue.peek();
        switch (direction) {
            case RIGHT -> x = stayInFrame(getBody().get(0).x + UNIT_SIZE);
            case LEFT -> x = stayInFrame(getBody().get(0).x - UNIT_SIZE);
            case DOWN, UP -> x = getBody().get(0).x;
        }
        return x;
    }
    public int yAhead() {
        int y = 0;
        Direction direction;
        if (turningsQueue.peek() == null)
            direction = currentDirection;
        else
            direction = turningsQueue.peek();
        switch (direction) {
            case RIGHT, LEFT -> y = getBody().get(0).y;
            case DOWN -> y = stayInFrame(getBody().get(0).y + UNIT_SIZE);
            case UP -> y = stayInFrame(getBody().get(0).y - UNIT_SIZE);
        }
        return y;
    }

    public List<Unit> getBody() {
        return body;
    }

    public void setBody(List<Unit> body) {
        this.body = body;
    }
}

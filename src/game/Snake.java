package game;

import gameimages.Icons;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import static game.Game.stayInFrame;

import static game.Unit.UNIT_SIZE;

public class Snake {
    private Queue<Direction> turningsQueue;
    private Direction currentDirection;
    private boolean alive;
    public ArrayList<Unit> body;
    private String neckFlag;
    public Snake() {
        //Змея жива
        alive = true;
        //Создание тела змеи
        body = new ArrayList<>();
        body.add(new Unit(Unit.UNIT_SIZE * 6, Unit.UNIT_SIZE * 3, Icons.headIconRight));
        body.add(new Unit(Unit.UNIT_SIZE * 5, Unit.UNIT_SIZE * 3, Icons.bodyIconHorizontalUp));
        body.add(new Unit(Unit.UNIT_SIZE * 4, Unit.UNIT_SIZE * 3, Icons.bodyIconHorizontalDown));
        body.add(new Unit(Unit.UNIT_SIZE * 3, Unit.UNIT_SIZE * 3, Icons.bodyIconHorizontalUp));
        body.add(new Unit(Unit.UNIT_SIZE * 2, Unit.UNIT_SIZE * 3, Icons.bodyIconHorizontalDown));
        body.add(new Unit(Unit.UNIT_SIZE, Unit.UNIT_SIZE * 3, Icons.tailIconRight));

        // Назначение начального направления
        turningsQueue = new LinkedList<>();
        currentDirection = Direction.RIGHT;
        neckFlag = "straight";
    }
    //Возврат размера тела змеи
    public int size(){
        return body.size();
    }
    //Получение точки змеи по индексу
    public Unit getUnit(int index){
        return body.get(index);
    }
    public void die(){
        alive = false;
    }
    public boolean alive(){return alive;}
    //Проверка, что впереди нет тела змеи (проверка на коллизии)
    public boolean checkCollisions(){
        int x = xAhead();
        int y = yAhead();
        for(int i = 1; i < body.size(); i++){
            if(x == body.get(i).x && y == body.get(i).y){
                return true;
            }
        }
        return false;
    }
    //Смена направления движения змеи
    public void turn(Direction direction){
        if (direction != null && (!turningsQueue.isEmpty() && turningsQueue.peek() != direction && turningsQueue.peek() != Direction.opposite(direction) || turningsQueue.isEmpty() && currentDirection != direction && currentDirection != Direction.opposite(direction)))
                turningsQueue.offer(direction);
    }
    //Движение змейки
    public void move(){
        //Передвижение хвоста в случае без яблока
        //Выбор картинки хвоста
        Image img = body.get(body.size() - 1).getImage();
        if(img == Icons.tailIconRight.getImage()){
            if(body.get(body.size() - 2).getImage() == Icons.bodyIconRightDown.getImage())
                body.get(body.size() - 1).setImage(Icons.tailIconDown);
            if(body.get(body.size() - 2).getImage() == Icons.bodyIconRightUp.getImage())
                body.get(body.size() - 1).setImage(Icons.tailIconUp);
        }
        else if(img == Icons.tailIconLeft.getImage()){
            if(body.get(body.size() - 2).getImage() == Icons.bodyIconLeftDown.getImage())
                body.get(body.size() - 1).setImage(Icons.tailIconDown);
            if(body.get(body.size() - 2).getImage() == Icons.bodyIconDownRight.getImage())
                body.get(body.size() - 1).setImage(Icons.tailIconUp);
        }
        else if(img == Icons.tailIconUp.getImage()){
            if(body.get(body.size() - 2).getImage() == Icons.bodyIconLeftDown.getImage())
                body.get(body.size() - 1).setImage(Icons.tailIconRight);
            if(body.get(body.size() - 2).getImage() == Icons.bodyIconRightDown.getImage())
                body.get(body.size() - 1).setImage(Icons.tailIconLeft);
        }
        else if(img == Icons.tailIconDown.getImage()){
            if(body.get(body.size() - 2).getImage() == Icons.bodyIconRightUp.getImage())
                body.get(body.size() - 1).setImage(Icons.tailIconLeft);
            if(body.get(body.size() - 2).getImage() == Icons.bodyIconDownRight.getImage())
                body.get(body.size() - 1).setImage(Icons.tailIconRight);
        }
        //Измерение координат хвоста
        body.get(body.size() - 1).x = body.get(body.size() - 2).x;
        body.get(body.size() - 1).y = body.get(body.size() - 2).y;
        //Передвижение тела
        for(int i = body.size() - 2; i > 1; i--){
            body.get(i).copy(body.get(i - 1));
        }
        //Передвижение шеи - картинка меняется в зависимости от поворотов
        //Изменение картинки для шеи в случае поворота
        Direction direction = turningsQueue.poll();
        if(direction != null){
            if(currentDirection != Direction.opposite(direction) && currentDirection != direction) {
                neckFlag = currentDirection.toString() + direction;
                currentDirection = direction;
                switch (neckFlag) {
                    case "RIGHTDOWN", "UPLEFT" -> body.get(1).setImage(Icons.bodyIconRightDown);
                    case "RIGHTUP", "DOWNLEFT" -> body.get(1).setImage(Icons.bodyIconRightUp);
                    case "LEFTDOWN", "UPRIGHT" -> body.get(1).setImage(Icons.bodyIconLeftDown);
                    case "DOWNRIGHT", "LEFTUP" -> body.get(1).setImage(Icons.bodyIconDownRight);
                }
            }
        }
        //Выбор картинки в случае, когда змейка ползет прямо
        switch (neckFlag) {
            case "horizontal straight 1" -> {
                body.get(1).setImage(Icons.bodyIconHorizontalUp);
                neckFlag = "horizontal straight 2";
            }
            case "vertical straight 1" -> {
                body.get(1).setImage(Icons.bodyIconVerticalLeft);
                neckFlag = "vertical straight 2";
            }
            case "horizontal straight 2" -> {
                body.get(1).setImage(Icons.bodyIconHorizontalDown);
                neckFlag = "horizontal straight 1";
            }
            case "vertical straight 2" -> {
                body.get(1).setImage(Icons.bodyIconVerticalRight);
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
        body.get(1).x = body.get(0).x;
        body.get(1).y = body.get(0).y;
        //Передвижение головы
        switch (currentDirection) {
            case RIGHT -> {
                body.get(0).x = stayInFrame(body.get(0).x + UNIT_SIZE);
                body.get(0).setImage(Icons.headIconRight);
            }
            case LEFT -> {
                body.get(0).x = stayInFrame(body.get(0).x - UNIT_SIZE);
                body.get(0).setImage(Icons.headIconLeft);
            }
            case DOWN -> {
                body.get(0).y = stayInFrame(body.get(0).y + UNIT_SIZE);
                body.get(0).setImage(Icons.headIconDown);
            }
            case UP -> {
                body.get(0).y = stayInFrame(body.get(0).y - UNIT_SIZE);
                body.get(0).setImage(Icons.headIconUp);
            }
        }
    }
    public void grow(){
        //Увеличение длины змеи, передвижение хвоста в случае с яблоком
        Unit newUnit = new Unit();
        newUnit.copy(body.get(body.size() - 1));
        body.add(newUnit);
        body.get(body.size() - 1).copy(body.get(body.size() - 2));
        //Передвижение тела
        for(int i = body.size() - 2; i > 1; i--){
            body.get(i).copy(body.get(i - 1));
        }
        //Передвижение шеи - картинка меняется в зависимости от поворотов
        //Изменение картинки для шеи в случае поворота
        Direction direction = turningsQueue.poll();
        if(direction != null){
            if(currentDirection != Direction.opposite(direction)) {
                neckFlag = currentDirection.toString() + direction;
                currentDirection = direction;
                switch (neckFlag) {
                    case "RIGHTDOWN", "UPLEFT" -> body.get(1).setImage(Icons.bodyIconRightDown);
                    case "RIGHTUP", "DOWNLEFT" -> body.get(1).setImage(Icons.bodyIconRightUp);
                    case "LEFTDOWN", "UPRIGHT" -> body.get(1).setImage(Icons.bodyIconLeftDown);
                    case "DOWNRIGHT", "LEFTUP" -> body.get(1).setImage(Icons.bodyIconDownRight);
                }
            }
        }
        //Выбор картинки в случае, когда змейка ползет прямо
        switch (neckFlag) {
            case "horizontal straight 1" -> {
                body.get(1).setImage(Icons.bodyIconHorizontalUp);
                neckFlag = "horizontal straight 2";
            }
            case "vertical straight 1" -> {
                body.get(1).setImage(Icons.bodyIconVerticalLeft);
                neckFlag = "vertical straight 2";
            }
            case "horizontal straight 2" -> {
                body.get(1).setImage(Icons.bodyIconHorizontalDown);
                neckFlag = "horizontal straight 1";
            }
            case "vertical straight 2" -> {
                body.get(1).setImage(Icons.bodyIconVerticalRight);
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
        body.get(1).x = body.get(0).x;
        body.get(1).y = body.get(0).y;
        //Передвижение головы
        switch (currentDirection) {
            case RIGHT -> {
                body.get(0).x = stayInFrame(body.get(0).x + UNIT_SIZE);
                body.get(0).setImage(Icons.headIconRight);
            }
            case LEFT -> {
                body.get(0).x = stayInFrame(body.get(0).x - UNIT_SIZE);
                body.get(0).setImage(Icons.headIconLeft);
            }
            case DOWN -> {
                body.get(0).y = stayInFrame(body.get(0).y + UNIT_SIZE);
                body.get(0).setImage(Icons.headIconDown);
            }
            case UP -> {
                body.get(0).y = stayInFrame(body.get(0).y - UNIT_SIZE);
                body.get(0).setImage(Icons.headIconUp);
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
        //Проверка: в какую сторону смотрит голова, такой стороне и проверяем точку
        switch (direction) {
            case RIGHT -> x = stayInFrame(body.get(0).x + UNIT_SIZE);
            case LEFT -> x = stayInFrame(body.get(0).x - UNIT_SIZE);
            case DOWN, UP -> x = body.get(0).x;
        }
        return x;
    }
    public int yAhead(){
        int y = 0;
        Direction direction;
        if(turningsQueue.peek() == null)
            direction = currentDirection;
        else
            direction = turningsQueue.peek();
        //Проверка: в какую сторону смотрит голова, такой стороне и проверяем точку
        switch (direction) {
            case RIGHT, LEFT -> y = body.get(0).y;
            case DOWN -> y = stayInFrame(body.get(0).y + UNIT_SIZE);
            case UP -> y = stayInFrame(body.get(0).y - UNIT_SIZE);
        }
        return y;
    }
    public void resurrect(){
        //Змея жива
        alive = true;
        //Создание тела змеи
        body = new ArrayList<>();
        body.add(new Unit(Unit.UNIT_SIZE * 6, Unit.UNIT_SIZE * 3, Icons.headIconRight));
        body.add(new Unit(Unit.UNIT_SIZE * 5, Unit.UNIT_SIZE * 3, Icons.bodyIconHorizontalUp));
        body.add(new Unit(Unit.UNIT_SIZE * 4, Unit.UNIT_SIZE * 3, Icons.bodyIconHorizontalDown));
        body.add(new Unit(Unit.UNIT_SIZE * 3, Unit.UNIT_SIZE * 3, Icons.bodyIconHorizontalUp));
        body.add(new Unit(Unit.UNIT_SIZE * 2, Unit.UNIT_SIZE * 3, Icons.bodyIconHorizontalDown));
        body.add(new Unit(Unit.UNIT_SIZE, Unit.UNIT_SIZE * 3, Icons.tailIconRight));

        // Назначение начального направления
        turningsQueue = new LinkedList<>();
        currentDirection = Direction.RIGHT;
        neckFlag = "straight";
    }
}

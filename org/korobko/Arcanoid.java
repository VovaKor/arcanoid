package org.korobko;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Arcanoid
{

    private int width;
    private int height;
    private ArrayList<Brick> bricks = new ArrayList<Brick>();
    private Ball ball;
    private Stand stand;
    private boolean isGameOver = false;

    public Arcanoid(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public ArrayList<Brick> getBricks()
    {
        return bricks;
    }

    public void setBall(Ball ball)
    {
        this.ball = ball;
    }

    public void setStand(Stand stand)
    {
        this.stand = stand;
    }

    public void draw(Canvas canvas)
    {
        Arcanoid.game.drawBoders(canvas);
        for (Brick b:bricks)
        {
            b.draw(canvas);
        }
        ball.draw(canvas);
        stand.draw(canvas);
    }

    private void drawBoders(Canvas canvas)
    {

        for (int i = 0; i < width + 2; i++)
        {
            for (int j = 0; j < height + 2; j++)
            {
                canvas.setPoint(i, j, '.');
            }
        }

        for (int i = 0; i < width + 2; i++)
        {
            canvas.setPoint(i, 0, '-');
            canvas.setPoint(i, height + 1, '-');
        }

        for (int i = 0; i < height + 2; i++)
        {
            canvas.setPoint(0, i, '|');
            canvas.setPoint(width + 1, i, '|');
        }
    }

    public void run() throws Exception
    {

        Canvas canvas = new Canvas(width, height);
        KeyboardObserver keyboardObserver = new KeyboardObserver();
        keyboardObserver.start();

        while (!isGameOver)
        {

            if (keyboardObserver.hasKeyEvents())
            {
                KeyEvent event = keyboardObserver.getEventFromTop();

                if (event.getKeyCode() == KeyEvent.VK_LEFT)
                    stand.moveLeft();

                else if (event.getKeyCode() == KeyEvent.VK_RIGHT)
                    stand.moveRight();

                else if (event.getKeyCode() == KeyEvent.VK_SPACE)
                    ball.start();
            }

            move();
            checkBricksBump();
            checkStandBump();
            checkEndGame();
            canvas.clear();
            draw(canvas);
            canvas.print();

            Thread.sleep(300);
        }

        System.out.println("Game Over!");
    }

    public void move()
    {
        ball.move();
        stand.move();
    }

    public void checkBricksBump()
    {
        ArrayList<Brick> toRemove = new ArrayList<>();
        for (Brick b:bricks)
        {
            if (b.isIntersec(ball)){
                toRemove.add(b);
                double angel = -(80 + Math.random()*20);
                ball.setDirection(angel);

            }
        }
        bricks.removeAll(toRemove);
    }

    public void checkStandBump()
    {
        if (stand.isIntersec(ball)){
            double angel = 80 + Math.random()*20;
            ball.setDirection(angel);
        }

    }

    public void checkEndGame()
    {
        if (this.ball.y> Arcanoid.game.getHeight())this.isGameOver=true;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public static Arcanoid game;

    public static void main(String[] args) throws Exception
    {
        game = new Arcanoid(20, 30);

        Ball ball = new Ball(10, 29, 2,  95);
        game.setBall(ball);

        Stand stand = new Stand(10, 30);
        game.setStand(stand);

        game.getBricks().add(new Brick(3, 3));
        game.getBricks().add(new Brick(7, 5));
        game.getBricks().add(new Brick(12, 5));
        game.getBricks().add(new Brick(16, 3));

        game.run();
    }
}




















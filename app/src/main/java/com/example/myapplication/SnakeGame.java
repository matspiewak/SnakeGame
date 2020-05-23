package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.Random;

public class SnakeGame extends SurfaceView implements Runnable {
    SnakeActivity snakeActivity = (SnakeActivity) getContext();

    private Thread thread = null;
    private Context context;

    public enum Heading {UP, RIGHT, DOWN, LEFT}
    private Heading heading = Heading.RIGHT;

    private int screenX;
    private int screenY;

    private int snakeLength;

    private int preyX;
    private int preyY;

    private int blockSize;

    private final int numBlocksWide = 20;
    private int numBlocksHigh;

    private long nextFrameTime;
    private final long fps = 10;
    private final long milPerSecond = 1000;

    public int score;

    private int[] snakeXs;
    private int[] snakeYs;

    private volatile boolean isPlaying;

    private Canvas canvas;

    private SurfaceHolder surfaceHolder;

    private Paint paint;

    public SnakeGame(Context context, Point size){
        super(context);
        context = context;

        screenX = size.x*4;
        screenY = size.y*4;

        blockSize = (screenX/numBlocksWide)/4;
        numBlocksHigh = (screenY/blockSize)/4;

        surfaceHolder = getHolder();
        paint = new Paint();

        snakeXs = new int[200];
        snakeYs = new int[200];

        newGame();

    }
    @Override
    public void run(){
        while (isPlaying){
            if(updateRequired()){
                update();
                draw();
            }
        }
    }

    public void pause(){
        isPlaying = false;
        try {
            thread.join();
        } catch (InterruptedException e){ }
    }

    public void resume(){
        isPlaying = true;
        thread = new Thread(this);
        thread.start();
    }

    public void newGame(){
        snakeLength = 1;
        snakeXs[0] = numBlocksWide/2;
        snakeYs[0] = numBlocksHigh/2;

        spawnPrey();
        score=0;

        nextFrameTime = System.currentTimeMillis();
    }

    public void spawnPrey(){
        Random random = new Random();
        preyX = random.nextInt(numBlocksWide -4 ) +4;
        preyY = random.nextInt(numBlocksHigh -4) +4;
    }

    public void eatPrey(){
        snakeLength++;
        spawnPrey();
        score++;
    }

    private void moveSnake(){
        for (int i = snakeLength; i>0; i--){
            snakeXs[i] = snakeXs[i-1];
            snakeYs[i] = snakeYs[i-1];
        }
        switch (heading) {
            case UP:
                snakeYs[0]--;
                break;

            case RIGHT:
                snakeXs[0]++;
                break;

            case DOWN:
                snakeYs[0]++;
                break;

            case LEFT:
                snakeXs[0]--;
                break;
        }
    }

    private boolean death(){
        boolean dead = false;

        if (snakeXs[0] == -1)
            dead = true;
        if (snakeXs[0] >= numBlocksWide)
            dead = true;
        if (snakeYs[0] == -1)
            dead = true;
        if (snakeYs[0] == numBlocksHigh)
            dead = true;

        for (int i = snakeLength -1; i>0; i--){
            if ((i > 4) && (snakeXs[0] == snakeXs[i]) && (snakeYs[0] == snakeYs[i]))
                dead = true;
        }

        return dead;
    }

    public void update(){
        if (snakeXs[0] == preyX && snakeYs[0] == preyY)
            eatPrey();

        moveSnake();

        if(death()){
            String points = String.valueOf(score);
            Intent intent = new Intent(snakeActivity,NewGameActivity.class);
            intent.putExtra("points",points);
            snakeActivity.startActivity(intent);
        }
    }

    public void  draw(){
     if (surfaceHolder.getSurface().isValid()){
         canvas = surfaceHolder.lockCanvas();

         canvas.drawColor(Color.argb(255, 204, 204, 204));
         paint.setColor(Color.argb(255, 250, 124, 45));
         paint.setTextSize(90);
         canvas.drawText("Wynik: "+ score, 10,70, paint);

         for (int i=0; i<snakeLength;i++){
             canvas.drawRect(snakeXs[i]*blockSize,
                     (snakeYs[i]*blockSize),
                     (snakeXs[i]*blockSize) + blockSize,
                     (snakeYs[i]*blockSize) + blockSize,
                     paint);
         }
         Random random = new Random();

         paint.setColor(Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)));

         canvas.drawRect(preyX*blockSize,
                 (preyY*blockSize),
                 (preyX*blockSize) + blockSize,
                 (preyY*blockSize) + blockSize,
                 paint);

         surfaceHolder.unlockCanvasAndPost(canvas);
     }
    }

    public boolean updateRequired(){
        if(nextFrameTime <= System.currentTimeMillis()){
            nextFrameTime = System.currentTimeMillis() + milPerSecond/fps;

            return true;
        }

        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                if (motionEvent.getX() >= screenX / 2) {
                    switch(heading){
                        case UP:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.UP;
                            break;
                    }
                } else {
                    switch(heading){
                        case UP:
                            heading = Heading.LEFT;
                            break;
                        case LEFT:
                            heading = Heading.DOWN;
                            break;
                        case DOWN:
                            heading = Heading.RIGHT;
                            break;
                        case RIGHT:
                            heading = Heading.UP;
                            break;
                    }
                }
        }
        return true;
    }
}

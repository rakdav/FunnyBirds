package com.example.funnybirds;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
    private Sprit playerBird;
    private final int timerInterval = 30;
    public GameView(Context context) {

        super(context);
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.bird);

        int w = b.getWidth()/5;
        int h = b.getHeight()/3;

        Rect firstFrame = new Rect(0, 0, w, h);

        playerBird = new Sprit(10, 0, 0, 100, firstFrame, b);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                if (i == 2 && j == 3) {
                    continue;
                }
                playerBird.addFrame(new Rect(j * w, i * h, j * w + w, i * w + w));
            }
        }
        Timer t = new Timer();
        t.start();
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(250, 127, 199, 255); // цвет фона
        playerBird.draw(canvas);
    }
    protected void update () {
        playerBird.update(timerInterval);
        invalidate();
    }
    public class Timer extends CountDownTimer {


        public Timer() {
            super(Integer.MAX_VALUE,timerInterval );
        }

        @Override
        public void onTick(long millisUntilFinished) {
            update();
        }
        @Override
        public void onFinish() {

        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int eventAction = event.getAction();

        if (eventAction == MotionEvent.ACTION_DOWN)  {
            // Движение вверх
            if (event.getY() < playerBird.getBoundingBoxRect().top) {
                playerBird.setVelocityY(-100);
            }
            else if (event.getY() > (playerBird.getBoundingBoxRect().bottom)) {
                playerBird.setVelocityY(100);
            }
        }
        return true;
    }
}

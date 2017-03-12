package com.linsh.lshutils.view.waveview;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;

public class Wave {
    protected int startX;
    protected int waveWidth;
    protected int waveHeight;
    protected int speed;
    protected int yOff;

    protected Paint paint;
    protected Path path;
    protected boolean shouldRefreshPath;

    public Wave() {
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(WaveView.sDefaultColor);
        shouldRefreshPath = true;
    }

    public Wave(int waveWidth, int waveHeight, int speed, int yOff, int color) {
        this.waveWidth = waveWidth;
        this.waveHeight = waveHeight;
        this.speed = speed;
        this.yOff = yOff;

        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color == 0 ? WaveView.sDefaultColor : color);
        shouldRefreshPath = true;
    }

    public Wave(int waveWidth, int waveHeight, int speed, int yOff, Shader shader) {
        this.waveWidth = waveWidth;
        this.waveHeight = waveHeight;
        this.speed = speed;
        this.yOff = yOff;

        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(shader);
        shouldRefreshPath = true;
    }

    public int getWaveWidth() {
        return waveWidth;
    }

    public void setWaveWidth(int waveWidth) {
        if (this.waveWidth != waveWidth) {
            this.waveWidth = waveWidth;
            shouldRefreshPath = true;
        }
    }

    public int getWaveHeight() {
        return waveHeight;
    }

    public void setWaveHeight(int waveHeight) {
        if (this.waveHeight != waveHeight) {
            this.waveHeight = waveHeight;
            shouldRefreshPath = true;
        }
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if (this.speed != speed) {
            this.speed = speed;
            shouldRefreshPath = true;
        }
    }

    public int getyOff() {
        return yOff;
    }

    public void setyOff(int yOff) {
        if (this.yOff != yOff) {
            this.yOff = yOff;
            shouldRefreshPath = true;
        }
    }

    public void setColor(int color) {
        if (paint.getColor() != color) {
            paint.setColor(color);
            shouldRefreshPath = true;
        }
    }

    public void setShader(Shader shader) {
        paint.setShader(shader);
        shouldRefreshPath = true;
    }

    public void checkWave(int viewWidth, int viewHeight) {
        if (waveWidth <= 0) {
            waveWidth = viewWidth;
        }
        if (waveHeight < 0) {
            waveHeight = viewHeight;
        }
    }
}
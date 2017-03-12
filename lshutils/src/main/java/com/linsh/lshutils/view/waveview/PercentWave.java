package com.linsh.lshutils.view.waveview;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;

/**
 * Created by Senh Linsh on 17/3/11.
 */

public class PercentWave extends Wave {

    protected float percentWaveWidth;
    protected float percentWaveHeight;
    protected float percentSpeed;
    protected float percentYOff;

    public PercentWave() {
    }

    public PercentWave(int waveWidth, int waveHeight, int speed, int yOff, int color) {
        super(waveWidth, waveHeight, speed, yOff, color);
    }

    public PercentWave(int waveWidth, int waveHeight, int speed, int yOff, Shader shader) {
        super(waveWidth, waveHeight, speed, yOff, shader);
    }

    public PercentWave(float percentWaveWidth, float percentWaveHeight, float percentSpeed, float percentYOff, int color) {
        this.percentWaveWidth = percentWaveWidth;
        this.percentWaveHeight = percentWaveHeight;
        this.percentSpeed = percentSpeed;
        this.percentYOff = percentYOff;

        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color == 0 ? WaveView.sDefaultColor : color);
        shouldRefreshPath = true;
    }

    public PercentWave(float percentWaveWidth, float percentWaveHeight, float percentSpeed, float percentYOff, Shader shader) {
        this.percentWaveWidth = percentWaveWidth;
        this.percentWaveHeight = percentWaveHeight;
        this.percentSpeed = percentSpeed;
        this.percentYOff = percentYOff;

        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(shader);
        shouldRefreshPath = true;
    }

    @Override
    public void setWaveWidth(int waveWidth) {
        super.setWaveWidth(waveWidth);
        percentWaveWidth = 0;
    }

    @Override
    public void setWaveHeight(int waveHeight) {
        super.setWaveHeight(waveHeight);
        percentWaveHeight = 0;
    }

    @Override
    public void setSpeed(int speed) {
        super.setSpeed(speed);
        percentSpeed = 0;
    }

    @Override
    public void setyOff(int yOff) {
        super.setyOff(yOff);
        percentYOff = 0;
    }

    public void setPercentWaveWidth(float percentWaveWidth) {
        this.percentWaveWidth = percentWaveWidth;
        waveWidth = 0;
        shouldRefreshPath = true;
    }

    public void setPercentWaveHeight(float percentWaveHeight) {
        this.percentWaveHeight = percentWaveHeight;
        waveHeight = 0;
        shouldRefreshPath = true;
    }

    public void setPercentSpeed(float percentSpeed) {
        this.percentSpeed = percentSpeed;
        speed = 0;
        shouldRefreshPath = true;
    }

    public void setPercentYOff(float percentYOff) {
        this.percentYOff = percentYOff;
        yOff = 0;
        shouldRefreshPath = true;
    }

    public void checkWave(int viewWidth, int viewHeight) {
        if (waveWidth <= 0) {
            waveWidth = (int) (viewWidth * percentWaveWidth);
        }
        if (waveHeight <= 0) {
            waveHeight = (int) (viewHeight * percentWaveHeight);
        }
        if (speed == 0) {
            speed = (int) (viewWidth * percentSpeed);
        }
        if (yOff == 0) {
            yOff = (int) (viewHeight * percentYOff);
        }
    }
}

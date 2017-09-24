package com.doschechko.matylionak.wcguide.graf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Стрелка назад
 */

public class BackArrow extends View {


    private Paint paint = new Paint();


    private void initialize() {
        paint.setAntiAlias(true);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);

        int radius = Math.min(getWidth(), getHeight())/2-10;
        canvas.drawCircle(getWidth()/2, getHeight()/2, radius, paint);
        canvas.drawLine(getWidth()/2,getHeight()/4,getWidth()/4, getHeight()/2, paint );
        canvas.drawLine(getWidth()/4, getHeight()/2,getWidth()/2, getHeight()/4*3,  paint );
        canvas.drawLine(getWidth()/4, getHeight()/2, getWidth()/4*3, getHeight()/2, paint);
    }

    public BackArrow(Context context) {
        super(context);
        initialize();
    }

    public BackArrow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public BackArrow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

}

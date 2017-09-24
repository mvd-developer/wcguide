package com.doschechko.matylionak.wcguide.graf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.doschechko.matylionak.wcguide.R;

/**
 * Custom menu
 */

public class MenuButton extends View {

    private Paint paint = new Paint();
    Path path = new Path();

    private void initialize() {
        paint.setAntiAlias(true);
        paint.setTextSize(50);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        paint.setColor(getResources().getColor(R.color.specialBlueColor));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(1);
        path.close();


        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() / 5 * 4, getHeight());
        path.lineTo(getWidth() / 5, getHeight());
        path.lineTo(0, 0);
        canvas.drawPath(path, paint);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(getHeight()/2);
        canvas.drawText("МЕНЮ", getWidth() / 2, getHeight() / 1.6f, paint);
        //canvas.drawText("МЕНЮ", getWidth()/img_3, getHeight()/img_3*img_2, paint);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawLine(getWidth()/5,getHeight()-20, getWidth()/5*4,getHeight()-20, paint);

    }

    public MenuButton(Context context) {
        super(context);
        initialize();
    }

    public MenuButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MenuButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }
}

package com.example.bolsointeligente.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class FundoCurvado extends View {

    private Paint paint;
    private Path path;

    public FundoCurvado(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFF429690);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        path.reset();
        path.moveTo(0, 0);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth(), getHeight() - 100);
        path.quadTo(getWidth() / 2f, getHeight(), 0, getHeight() - 100);
        path.close();

        canvas.drawPath(path, paint);
    }
}

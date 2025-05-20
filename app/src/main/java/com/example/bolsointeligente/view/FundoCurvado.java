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

        // Cria a curva na parte inferior
        path.reset();
        path.moveTo(0, 0); // Inicia no canto superior esquerdo
        path.lineTo(getWidth(), 0); // Linha até o canto superior direito
        path.lineTo(getWidth(), getHeight() - 100); // Linha até o final da altura menos um pouco
        path.quadTo(getWidth() / 2f, getHeight(), 0, getHeight() - 100); // Curva para o canto inferior esquerdo
        path.close();

        // Desenha o caminho com a cor sólida
        canvas.drawPath(path, paint);
    }
}

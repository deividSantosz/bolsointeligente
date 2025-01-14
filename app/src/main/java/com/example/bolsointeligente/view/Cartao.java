package com.example.bolsointeligente.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class Cartao extends View {

    private Paint paint;
    private RectF rectF;
    private float cornerRadius;

    public Cartao(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xFF29756F); // Cor do cartão (hexadecimal)

        // Configuração inicial do retângulo e do raio dos cantos
        rectF = new RectF();
        cornerRadius = 50f; // Raio dos cantos arredondados (em pixels)
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Define o retângulo com base nas dimensões da View
        rectF.set(0, 0, getWidth(), getHeight());

        // Desenha o retângulo com cantos arredondados
        canvas.drawRoundRect(rectF, cornerRadius, cornerRadius, paint);
    }
}

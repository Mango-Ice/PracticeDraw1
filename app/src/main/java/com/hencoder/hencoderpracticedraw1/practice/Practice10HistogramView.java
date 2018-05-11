package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice10HistogramView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String[] drawText = {"Froyo", "GB", "ICS", "JB", "KitKat", "L", "M"};
    private float[] top = {595, 570, 570, 400, 250, 150, 420};

    public Practice10HistogramView(Context context) {
        super(context);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice10HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        综合练习
//        练习内容：使用各种 Canvas.drawXXX() 方法画直方图

        //画坐标系
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(3);
        canvas.drawLine(100, 50, 100, 600, paint);  //y
        canvas.drawLine(100, 600, 1000, 600, paint);   //x
        //画X轴文字
        paint.setTextSize(27);
        paint.setTextAlign(Paint.Align.CENTER);
        int j = 0;
        for (String text : drawText) {
            canvas.drawText(text, j + 170, 630, paint);
            j += 120;
        }
        //画内部
        paint.reset();
        paint.setColor(Color.GREEN);
        int width = 100;
        int startX = 120;
        for (float fromTop : top) {
            canvas.drawRect(startX, fromTop, startX + width, 600, paint);
            startX += 120;
        }

    }
}

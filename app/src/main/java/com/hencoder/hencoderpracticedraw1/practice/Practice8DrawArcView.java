package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Practice8DrawArcView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public Practice8DrawArcView(Context context) {
        super(context);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice8DrawArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        练习内容：使用 canvas.drawArc() 方法画弧形和扇形
        //扇形
        paint.setStyle(Paint.Style.FILL);
        canvas.drawArc(400, 300, 700, 500, -110, 100, true, paint);
        //弧形
        canvas.drawArc(400, 300, 700, 500, 20, 140, false, paint);
        //不封闭的弧形
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(400, 300, 700, 500, 180, 60, false, paint);
    }
}

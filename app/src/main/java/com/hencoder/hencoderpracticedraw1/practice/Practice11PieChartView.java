package com.hencoder.hencoderpracticedraw1.practice;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Practice11PieChartView extends View {
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF rectF = new RectF(250, 100, 750, 600);
    private Path path = new Path();
    float lineStartX = 0f;  // 直线开始的X坐标
    float lineStartY = 0f;  // 直线开始的Y坐标
    float lineEndX;         // 直线结束的X坐标
    float lineEndY;         // 直线结束的Y坐标
    private float radius, centerX, centerY;
    float halfAngle;        // 当前扇形一半的角度=(startAngle+sweepAngle)/2
    float startAngle;       // 开始角度
    float sweepAngle;       // 绘制的角度
    float maxValue;
    float totalValue;
    int outerLineLength;    //直线向外的长度
    private List<ChartData> datas = new ArrayList<>();


    public Practice11PieChartView(Context context) {
        super(context);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Practice11PieChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        datas.add(new ChartData("Lollipop", 45.0f, Color.RED));
        datas.add(new ChartData("Marshmallow", 20.0f, Color.YELLOW));
        datas.add(new ChartData("Froyo", 1.0f, Color.CYAN));
        datas.add(new ChartData("Gingerbread", 3.0f, Color.MAGENTA));
        datas.add(new ChartData("Ice Cream Sandwich", 3.0f, Color.GRAY));
        datas.add(new ChartData("Jelly Bean", 20.0f, Color.GREEN));
        datas.add(new ChartData("KitKat", 35.0f, Color.BLUE));
        maxValue = Float.MIN_VALUE;
        for (ChartData data: datas) {
            totalValue += data.getSize();
            maxValue = Math.max(data.getSize(), maxValue);
        }
        radius = (int)(rectF.right - rectF.left) / 2;   //半径
        centerX = (int)(rectF.right - rectF.left) / 2 + rectF.left;    //圆心X
        centerY = (int)(rectF.bottom - rectF.top) /2 + rectF.top;      //圆心Y
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        startAngle = -180f;

        for (ChartData data : datas) {
            sweepAngle = data.getSize() / totalValue * 360f;
            halfAngle = startAngle + sweepAngle / 2;
            lineStartX = radius * (float) Math.cos(halfAngle / 180 * Math.PI);
            lineStartY = radius * (float) Math.sin(halfAngle / 180 * Math.PI);
            lineEndX = (radius + 40) * (float) Math.cos(halfAngle / 180 * Math.PI);
            lineEndY = (radius + 40) * (float) Math.sin(halfAngle / 180 * Math.PI);

            //画扇形
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(data.getColor());
            if (data.getSize() < 3f) {
                canvas.drawArc(rectF, startAngle - 2.0f, sweepAngle + 1.0f, true, paint);
            } else if (data.getSize() == maxValue) {
                //最大的块向外扩展 暂时先这样处理
                rectF.set(250 - 20, 100 - 20, 750 - 20, 600 - 20);
                canvas.drawArc(rectF, startAngle, sweepAngle, true, paint);
                lineStartX -=20;
                lineStartY -=20;
                lineEndX -=20;
                lineEndY -=20;
                rectF.set(250, 100, 750, 600);
            } else {
                canvas.drawArc(rectF, startAngle, sweepAngle - 2.0f, true, paint);
            }

            //画线
            canvas.save();
            canvas.translate(centerX, centerY);      //移动到中心
            paint.reset();
            paint.setAntiAlias(true);
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(2);
            paint.setStyle(Paint.Style.STROKE);

            if (sweepAngle < 10) {
                outerLineLength = 40;
            } else {
                outerLineLength = 100;
            }

            path.moveTo(lineStartX, lineStartY);
            path.lineTo(lineEndX, lineEndY);
            if (lineEndX > 0) {
                path.rLineTo(outerLineLength, 0);
            } else {
                path.rLineTo(-outerLineLength, 0);
            }
            canvas.drawPath(path, paint);

            //画文字
            paint.reset();
            paint.setAntiAlias(true);
            paint.setColor(Color.WHITE);
            paint.setTextSize(28);
            if (lineEndX > 0) {
                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText(data.getName(), lineEndX + outerLineLength + 10, lineEndY + 10, paint);
            } else {
                paint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText(data.getName(), lineEndX - outerLineLength - 10, lineEndY + 10, paint);
            }

            canvas.restore();
            startAngle += sweepAngle;
        }
    }


    private class ChartData {
        private String name;
        private float size;
        private int color;

        ChartData(String name, float size, int color) {
            this.name = name;
            this.size = size;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public float getSize() {
            return size;
        }

        public int getColor() {
            return color;
        }
    }
}

package yxh.dundun.customclock.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import yxh.dundun.customclock.util.MeasureUtil;


/**
 * Created by dundun on 16/3/3.
 */
public class CustomView extends View {
    private Paint paint_bg, paint_cir, paint_line, paint_txt;
    private Paint paint_min, paint_hour, paint_sec;
    private Context context;
    private Rect rect;
    private float radiu;

    private int w, h;//屏幕的宽高
    private int width, heigh;//自定义view宽高
    private float clockX, clockY;//时钟中心点坐标
    private float angle;//角度

    private double angle_hour, angle_min, angle_sec;

    public CustomView(Context context) {
        super(context);
        this.context = context;
        //处理无法预览layout的问题
        if (isInEditMode()) {
            return;
        }
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        if (isInEditMode()) {
            return;
        }
        init();
    }

    /**
     * 初始化时钟数据
     * date 格式 HH:mm:ss
     */
    public void initData(String date) {
        try {
            angle_sec = Math.toRadians(Double.valueOf(date.substring(6, 8)) * 6);
            angle_min = Math.toRadians(Double.valueOf(date.substring(3, 5)) * 6 + Double.valueOf(date.substring(6, 8)) * 6 / 60);
            angle_hour = Math.toRadians(Double.valueOf(date.substring(0, 2)) * 30 + Double.valueOf(date.substring(3, 5)) * 6 / 12);
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        invalidate();
    }

    /**
     * 初始化 数据准备
     */
    private void init() {
        int screen[] = MeasureUtil.getScreenXY((Activity) context);
        w = screen[0];
        h = screen[1];
        clockX = w / 2;
        clockY = h / 3;
        radiu = w * 7 / 18;//覆盖两个白色圆的大圆半径 （注：不是时钟的半径）


        rect = new Rect(0, 0, w, h * 4 / 5);

        //设置画笔抗锯齿
        paint_bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        //画笔颜色
        paint_bg.setColor(0xffDA5A4D);
        //画笔风格 填充
        paint_bg.setStyle(Paint.Style.FILL);

        paint_txt = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint_txt.setColor(0xffffffff);
        paint_txt.setTextSize(40);

        paint_cir = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_line = new Paint(Paint.ANTI_ALIAS_FLAG);

        paint_hour = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint_hour.setStyle(Paint.Style.FILL);
        paint_hour.setColor(0xffffffff);

        paint_min = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_min.setStyle(Paint.Style.STROKE);
        paint_min.setStrokeWidth(5);
        paint_min.setColor(0xffffffff);

        paint_sec = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_sec.setStyle(Paint.Style.STROKE);
        paint_sec.setStrokeWidth(2);
        paint_sec.setColor(0xff4BB700);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        heigh = MeasureSpec.getSize(heightMeasureSpec);
        //自定义view的宽高设置
        setMeasuredDimension(width, heigh * 4 / 5);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (isInEditMode()) {
            return;
        }

        //第五步 绘制指针

        //时针
        Path path = new Path();
        path.moveTo(clockX, clockY);
        path.lineTo((float) (clockX - 3 * Math.cos(angle_hour)), (float) (clockY - 3 * Math.sin(angle_hour)));
        path.lineTo((float) (clockX + (radiu - 230) * Math.sin(angle_hour) - 8 * Math.cos(angle_hour)),
                (float) (clockY - (radiu - 230) * Math.cos(angle_hour) - 8 * Math.sin(angle_hour)));
        path.lineTo((float) (clockX + (radiu - 210) * Math.sin(angle_hour)),
                (float) (clockY - (radiu - 210) * Math.cos(angle_hour)));
        path.lineTo((float) (clockX + (radiu - 230) * Math.sin(angle_hour) + 8 * Math.cos(angle_hour)),
                (float) (clockY - (radiu - 230) * Math.cos(angle_hour) + 8 * Math.sin(angle_hour)));
        path.lineTo((float) (clockX + 3 * Math.cos(angle_hour)), (float) (clockY + 3 * Math.sin(angle_hour)));
        path.close();
        canvas.drawPath(path, paint_hour);

        //分针
        canvas.drawLine(clockX, clockY, (float) (clockX + (radiu - 150) * Math.sin(angle_min)),
                (float) (clockY - (radiu - 200) * Math.cos(angle_min)), paint_min);

        //绘制时钟中间绿色圆点
        paint_cir.setColor(0xff4BB700);
        paint_cir.setStyle(Paint.Style.FILL);
        paint_cir.setStrokeWidth(5);
        canvas.drawCircle(clockX, clockY, 10, paint_cir);

        //秒针
        canvas.drawLine(clockX, clockY, (float) (clockX + (radiu - 130) * Math.sin(angle_sec)),
                (float) (clockY - (radiu - 170) * Math.cos(angle_sec)), paint_sec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        if (isInEditMode()) {
            return;
        }
        //第一步
        //绘制自定义view橘红色矩形背景
        canvas.drawRect(rect, paint_bg);

        //第二步
        //绘制时钟的两个耳朵，两个纯白的圆
        paint_cir.setColor(0xffffffff);
        paint_cir.setStyle(Paint.Style.FILL);
        paint_cir.setStrokeWidth(1);
        canvas.drawCircle(w * 5 / 18, h / 5, w / 6, paint_cir);
        canvas.drawCircle(w * 13 / 18, h / 5, w / 6, paint_cir);

        //第三步
        //绘制时钟外部大圆
        paint_cir.setColor(0xffDA5A4D);
        paint_cir.setStyle(Paint.Style.FILL);
        canvas.drawCircle(clockX, clockY, radiu, paint_cir);
        //绘制时钟白色边
        paint_cir.setColor(0xffffffff);
        paint_cir.setStyle(Paint.Style.STROKE);
        paint_cir.setStrokeWidth(60);
        canvas.drawCircle(clockX, clockY, radiu - 60, paint_cir);
        //绘制时钟中间白色圆点
        paint_cir.setStrokeWidth(1);
        paint_cir.setStyle(Paint.Style.FILL);
        canvas.drawCircle(clockX, clockY, 20, paint_cir);


        //第四步
        //绘制表盘刻度

        //上下左右  四个刻度
        paint_line.setColor(0xff4BB700);
        paint_line.setStrokeWidth(5);
        angle = 90;

        for (int i = 0; i < 4; i++) {
            canvas.drawLine((float) (clockX + (radiu - 90) * Math.sin(Math.toRadians(angle))),
                    (float) (clockY - (radiu - 90) * Math.cos(Math.toRadians(angle))),
                    (float) (clockX + (radiu - 120) * Math.sin(Math.toRadians(angle))),
                    (float) (clockY - (radiu - 120) * Math.cos(Math.toRadians(angle))), paint_line);

            angle += 90;
        }

        //每个时段的刻度
        paint_line.setColor(0xff4BB700);
        paint_line.setStrokeWidth(3);
        angle = 30;
        for (int i = 0; i < 12; i++) {
            canvas.drawLine((float) (clockX + (radiu - 90) * Math.sin(Math.toRadians(angle))),
                    (float) (clockY - (radiu - 90) * Math.cos(Math.toRadians(angle))),
                    (float) (clockX + (radiu - 110) * Math.sin(Math.toRadians(angle))),
                    (float) (clockY - (radiu - 110) * Math.cos(Math.toRadians(angle))), paint_line);
            angle += 30;
        }

        //最小刻度
        paint_line.setColor(0xff4BB700);
        paint_line.setStrokeWidth(1);
        angle = 6;
        for (int i = 0; i < 60; i++) {
            canvas.drawLine((float) (clockX + (radiu - 90) * Math.sin(Math.toRadians(angle))),
                    (float) (clockY - (radiu - 90) * Math.cos(Math.toRadians(angle))),
                    (float) (clockX + (radiu - 100) * Math.sin(Math.toRadians(angle))),
                    (float) (clockY - (radiu - 100) * Math.cos(Math.toRadians(angle))), paint_line);
            angle += 6;
        }

        //绘制表盘数字
        angle = 0;

        canvas.drawText("12", 0, 2, (float) (clockX + (radiu - 150) * Math.sin(Math.toRadians(angle - 5))),
                (float) (clockY - (radiu - 160) * Math.cos(Math.toRadians(angle))), paint_txt);
        angle += 90;
        canvas.drawText("3", 0, 1, (float) (clockX + (radiu - 150) * Math.sin(Math.toRadians(angle))),
                (float) (clockY - (radiu - 150) * Math.cos(Math.toRadians(angle + 3))), paint_txt);
        angle += 90;
        canvas.drawText("6", 0, 1, (float) (clockX + (radiu - 150) * Math.sin(Math.toRadians(angle + 2.5))),
                (float) (clockY - (radiu - 130) * Math.cos(Math.toRadians(angle))), paint_txt);
        angle += 90;
        canvas.drawText("9", 0, 1, (float) (clockX + (radiu - 130) * Math.sin(Math.toRadians(angle))),
                (float) (clockY - (radiu - 150) * Math.cos(Math.toRadians(angle - 3))), paint_txt);
    }
}

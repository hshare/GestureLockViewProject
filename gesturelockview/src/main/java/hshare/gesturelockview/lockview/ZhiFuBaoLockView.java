package hshare.gesturelockview.lockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import hshare.gesturelockview.impl.LockViewImpl;


/**
 * @author huzeliang
 *         2017-11-14 10:51:03
 */
public class ZhiFuBaoLockView extends LockViewImpl {

    private int radius;
    private int strokeWidth = 4;
    private int centerX;
    private int centerY;
    private Paint paint;
    private float innerCircleRadiusRate = 0.3F;
    private int colorNormal = 0xFFFFFFFF;
    private int colorSelected = 0xFFFFFFFF;
    private int colorError = 0xFFFFFFFF;

    public ZhiFuBaoLockView(Context context) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    public void afterMeasure(int childWidth, int childHeight) {
        radius = centerX = centerY = childWidth / 2;
        radius -= strokeWidth / 2;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStateError(Canvas canvas) {
        // 绘制外圆
        paint.setColor(colorError);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(centerX, centerY, radius, paint);
        // 绘制内圆
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius * innerCircleRadiusRate, paint);
    }

    @Override
    public void onStateSelect(Canvas canvas) {
        // 绘制外圆
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(colorSelected);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(centerX, centerY, radius, paint);
        // 绘制内圆
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius * innerCircleRadiusRate, paint);
    }

    @Override
    public void onStateNormal(Canvas canvas) {
        // 绘制外圆
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(colorNormal);
        paint.setStrokeWidth(strokeWidth/2);
        canvas.drawCircle(centerX, centerY, radius, paint);
    }


    public void setColorNormal(int mColorNormal) {
        this.colorNormal = mColorNormal;
        invalidate();
    }

    public void setColorSelected(int mColorSelected) {
        this.colorSelected = mColorSelected;
        invalidate();
    }

    public void setColorError(int mColorError) {
        this.colorError = mColorError;
        invalidate();
    }

}

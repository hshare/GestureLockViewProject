package hshare.gesturelockview.lockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import hshare.gesturelockview.impl.LockViewImpl;


/**
 * @author huzeliang
 *         2017-11-14 10:51:03
 */
public class NormalLockView extends LockViewImpl {

    private int radius;
    private int strokeWidth = 2;
    private int centerX;
    private int centerY;
    private Paint paint;
    private float innerCircleRadiusRate = 0.3F;
    private int colorNormal = 0xFFAAAAAA;
    private int colorSelected = 0xFF000000;
    private int colorError = 0xFFFF0000;

    public NormalLockView(Context context) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
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

    @Override
    public void onStateError(Canvas canvas) {
        paint.setColor(colorError);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawCircle(centerX, centerY, radius, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius * innerCircleRadiusRate, paint);
    }

    @Override
    public void onStateSelect(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(colorSelected);
        paint.setStrokeWidth(2);
        canvas.drawCircle(centerX, centerY, radius, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius * innerCircleRadiusRate, paint);
    }

    @Override
    public void onStateNormal(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(colorNormal);
        paint.setStrokeWidth(2);
        canvas.drawCircle(centerX, centerY, radius, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colorNormal);
        canvas.drawCircle(centerX, centerY, radius * innerCircleRadiusRate, paint);
    }

    @Override
    public void afterMeasure(int childWidth, int childHeight) {
        radius = centerX = centerY = childWidth / 2;
        radius -= strokeWidth / 2;
    }

    @Override
    public void onDestroy() {

    }

}

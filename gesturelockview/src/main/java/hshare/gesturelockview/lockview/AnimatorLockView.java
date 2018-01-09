package hshare.gesturelockview.lockview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import hshare.gesturelockview.impl.LockViewImpl;


/**
 * @author huzeliang
 */
public class AnimatorLockView extends LockViewImpl {

    private int radius;
    private int strokeWidth = 2;
    private int centerX;
    private int centerY;
    private Paint paint;
    private float innerCircleRadiusRate = 0.3F;
    private int colorNormal = 0xFFAAAAAA;
    private int colorSelected = 0xFF999999;
    private int colorError = 0xFFFF0000;

    ValueAnimator valueAnimator;
    ValueAnimator endAnimator;

    public AnimatorLockView(Context context) {
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
        isSelStarted = false;
        if (!endAnimator.isRunning() && !isErrorStarted) {
            endAnimator.start();
            isErrorStarted = true;
            Log.i("------", "endAnimator.start();" + getId());
        }
        paint.setColor(colorError);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawCircle(centerX, centerY, tempRadius, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius * innerCircleRadiusRate, paint);
    }

    private boolean isSelStarted = false;
    private boolean isErrorStarted = false;

    @Override
    public void onStateSelect(Canvas canvas) {
        isErrorStarted = false;
        if (!valueAnimator.isRunning() && !isSelStarted) {
            valueAnimator.start();
            isSelStarted = true;
            Log.i("------", "valueAnimator.start();" + getId());
        }


        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(colorSelected);
        paint.setStrokeWidth(2);
        canvas.drawCircle(centerX, centerY, tempRadius, paint);

        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(centerX, centerY, radius * innerCircleRadiusRate, paint);
    }


    private int tempRadius;

    @Override
    public void onStateNormal(Canvas canvas) {
        isSelStarted = false;
        isErrorStarted = false;
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(colorNormal);
//        paint.setStrokeWidth(2);
//        canvas.drawCircle(centerX, centerY, tempRadius, paint);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(colorNormal);
        canvas.drawCircle(centerX, centerY, radius * innerCircleRadiusRate, paint);
    }

    @Override
    public void afterMeasure(int childWidth, int childHeight) {
        radius = centerX = centerY = childWidth / 2;
        radius = radius - strokeWidth / 2;
        tempRadius = (int) (radius * innerCircleRadiusRate);

        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofInt((int) (radius * innerCircleRadiusRate), radius);
            valueAnimator.setDuration(300);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    tempRadius = (int) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
        }
        if (endAnimator == null) {
            endAnimator = ValueAnimator.ofInt(radius, (int) (radius * innerCircleRadiusRate));
            endAnimator.setDuration(300);
            endAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    tempRadius = (int) valueAnimator.getAnimatedValue();
                    invalidate();
                }
            });
        }
    }

    @Override
    public void onDestroy() {

    }

}

package hshare.gesturelockview.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import hshare.gesturelockview.base.BaseLockView;

/**
 * base lock view
 *
 * @author huzeliang
 *         2017-11-14 10:35:16
 */
public abstract class LockViewImpl extends BaseLockView {

    /**
     * state for lock view
     */
    private State lockState = State.STATE_NORMAL;

    @Override
    public void setState(State state) {
        this.lockState = state;
        invalidate();
    }

    @Override
    public State getState() {
        return lockState;
    }

    public LockViewImpl(Context context) {
        super(context);
    }

    @Override
    protected final void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childWidth = MeasureSpec.getSize(widthMeasureSpec);
        int childHeight = MeasureSpec.getSize(heightMeasureSpec);

        childWidth = childWidth < childHeight ? childWidth : childHeight;
        setMeasuredDimension(childWidth, childHeight);

        afterMeasure(childWidth, childHeight);
    }

    @Override
    protected final void onDraw(Canvas canvas) {
        switch (getState()) {
            case STATE_SELECTED:
                onStateSelect(canvas);
                break;
            case STATE_ERROR:
                onStateError(canvas);
                break;
            case STATE_NORMAL:
                onStateNormal(canvas);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDestroy();
    }

    public abstract void onStateError(Canvas canvas);

    public abstract void onStateSelect(Canvas canvas);

    public abstract void onStateNormal(Canvas canvas);

    public abstract void afterMeasure(int childWidth, int childHeight);

    public abstract void onDestroy();
}

package hshare.gesturelockview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hshare.gesturelockview.base.BaseLineView;
import hshare.gesturelockview.base.BaseLockView;
import hshare.gesturelockview.listener.OnGestureCompleteListener;


/**
 * @author huzeliang
 */
public class GestureLockView extends ViewGroup {

    private int pointWidth;
    private float innerMargin = 0;
    private float outerMargin = 0;
    private BaseLineView baseLineView;


    public GestureLockView(Context context) {
        this(context, null, 0);
    }

    public GestureLockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureLockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GestureLockView);
        innerMargin = a.getDimension(R.styleable.GestureLockView_innerMargin, innerMargin);
        outerMargin = a.getDimension(R.styleable.GestureLockView_outerMargin, outerMargin);
        a.recycle();
    }

    private BaseLineView getBaseLineView() {
        if (baseLineView == null) {
            baseLineView = GestureLockHelper.getInstance().getLineView(getContext(), getTag());
        }
        return baseLineView;
    }

    private boolean isInit = false;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        width = height = width > height ? height : width;
        pointWidth = (int) ((width - innerMargin * 2 - outerMargin * 2) / (3.0));

        if (getBaseLineView() != null && !isInit) {
            isInit = true;
            List<BaseLockView> lockViews = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                BaseLockView lockPointView = GestureLockHelper.getInstance().getLockView(getContext(), getTag());
                lockPointView.setId(i + 1);
                this.addView(lockPointView);
                lockViews.add(lockPointView);
            }
            this.addView(getBaseLineView());
            getBaseLineView().initLockViews(lockViews, pointWidth);
        }

        setMeasuredDimension(width, height);

        if (getChildCount() > 0) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getId() > 0) {
                    child.measure(MeasureSpec.makeMeasureSpec(pointWidth, MeasureSpec.EXACTLY)
                            , MeasureSpec.makeMeasureSpec(pointWidth, MeasureSpec.EXACTLY));
                } else {
                    child.measure(widthMeasureSpec, heightMeasureSpec);
                }
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childcount = getChildCount();
        for (int i = 0; i < childcount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE && childView.getId() > 0) {
                int hang = i / 3 + 1;
                int lie = i % 3 + 1;
                int left = (int) (outerMargin + (lie - 1) * (innerMargin + pointWidth));
                int top = (int) (outerMargin + (hang - 1) * (innerMargin + pointWidth));
                int right = left + pointWidth;
                int bottom = top + pointWidth;
                childView.layout(left, top, right, bottom);
            } else {
                childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }
        }
    }

    public void setOnGestureCompleteListener(OnGestureCompleteListener onGestureCompleteListener) {
        getBaseLineView().setOnGestureCompleteListener(onGestureCompleteListener);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

}

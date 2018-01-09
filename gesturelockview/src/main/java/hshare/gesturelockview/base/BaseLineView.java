package hshare.gesturelockview.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import hshare.gesturelockview.listener.OnGestureCompleteListener;

/**
 * @author huzeliang
 */
public abstract class BaseLineView extends View {
    public BaseLineView(Context context) {
        super(context);
    }

    public BaseLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void initLockViews(List<BaseLockView> lockViews, int pointWidth);

    public abstract void setOnGestureCompleteListener(OnGestureCompleteListener onGestureCompleteListener);
}

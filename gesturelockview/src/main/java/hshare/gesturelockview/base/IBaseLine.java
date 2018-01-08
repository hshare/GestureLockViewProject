package hshare.gesturelockview.base;

import android.view.ViewGroup;

import java.util.List;

/**
 * @author huzeliang
 */
public interface IBaseLine {
    void initLockViews(List<ILockView> lockViews, int pointWidth);

    void setOnGestureVerifyListener(String password, OnGestureVerifyListener onGestureVerifyListener);

    void setOnGestureCompleteListener(OnGestureCompleteListener onGestureCompleteListener);
}

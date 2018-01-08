package hshare.gesturelockview;

import android.content.Context;

import hshare.gesturelockview.base.BaseLineView;
import hshare.gesturelockview.base.BaseLockView;
import hshare.gesturelockview.lineview.NormalLineView;
import hshare.gesturelockview.lockview.NormalLockView;


/**
 * lock view helper
 *
 * @author huzeliang
 *         2017-11-14 10:48:04
 */
public class GestureLockHelper {

    private static GestureLockHelper LOCKVIEWHELPER;
    private OnGestureLockNewListener onLockViewNewListener;

    public void setOnLockViewNewListener(OnGestureLockNewListener onLockViewNewListener) {
        this.onLockViewNewListener = onLockViewNewListener;
    }

    public interface OnGestureLockNewListener {
        BaseLockView onLockViewNew(Context context, Object tag);

        BaseLineView onLineViewNew(Context context, Object tag);
    }

    public static GestureLockHelper getInstance() {
        if (LOCKVIEWHELPER == null) {
            LOCKVIEWHELPER = new GestureLockHelper();
        }
        return LOCKVIEWHELPER;
    }

    public BaseLockView getLockView(Context context, Object tag) {
        if (onLockViewNewListener != null) {
            BaseLockView lockView = onLockViewNewListener.onLockViewNew(context, tag);
            if (lockView == null) {
                return new NormalLockView(context);
            }
            return lockView;
        }
        return new NormalLockView(context);
    }

    public BaseLineView getLineView(Context context, Object tag) {
        if (onLockViewNewListener != null) {
            BaseLineView baseLine = onLockViewNewListener.onLineViewNew(context, tag);
            if (baseLine == null) {
                return new NormalLineView(context);
            }
            return baseLine;
        }
        return new NormalLineView(context);
    }

}

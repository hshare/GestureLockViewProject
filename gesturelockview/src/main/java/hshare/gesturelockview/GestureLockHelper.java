package hshare.gesturelockview;

import android.content.Context;

import hshare.gesturelockview.base.IBaseLine;
import hshare.gesturelockview.base.ILockView;
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
        ILockView onLockViewNew(Context context, Object tag);

        IBaseLine onLineViewNew(Context context, Object tag);
    }

    public static GestureLockHelper getInstance() {
        if (LOCKVIEWHELPER == null) {
            LOCKVIEWHELPER = new GestureLockHelper();
        }
        return LOCKVIEWHELPER;
    }

    public ILockView getLockView(Context context, Object tag) {
        if (onLockViewNewListener != null) {
            ILockView lockView = onLockViewNewListener.onLockViewNew(context, tag);
            if (lockView == null) {
                return new NormalLockView(context);
            }
            return lockView;
        }
        return new NormalLockView(context);
    }

    public IBaseLine getLineView(Context context, Object tag) {
        if (onLockViewNewListener != null) {
            IBaseLine baseLine = onLockViewNewListener.onLineViewNew(context, tag);
            if (baseLine == null) {
                return new NormalLineView(context);
            }
            return baseLine;
        }
        return new NormalLineView(context);
    }

}

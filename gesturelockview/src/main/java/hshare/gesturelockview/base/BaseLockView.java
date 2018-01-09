package hshare.gesturelockview.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author huzeliang
 */
public abstract class BaseLockView extends View {

    public BaseLockView(Context context) {
        super(context);
    }

    public BaseLockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseLockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public enum State {
        STATE_NORMAL, STATE_SELECTED, STATE_ERROR
    }

    public abstract void setState(State state);

    public abstract State getState();


}

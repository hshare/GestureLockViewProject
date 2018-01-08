package hshare.gesturelockview.base;

import android.view.View;

/**
 * lock view
 *
 * @author huzeliang
 * @version 1.0 2017-11-13 10:07:13
 * @see ***
 * @since ***
 */
public interface ILockView {

    enum State {
        STATE_NORMAL, STATE_SELECTED, STATE_ERROR
    }

    void setState(State state);

    State getState();

    void setId(int id);

    int getId();

    int getLeft();

    int getRight();

    int getTop();

    int getBottom();

//    View getView();
}

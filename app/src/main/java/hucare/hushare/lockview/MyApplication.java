package hucare.hushare.lockview;

import android.app.Application;
import android.content.Context;

import hshare.gesturelockview.GestureLockHelper;
import hshare.gesturelockview.base.BaseLineView;
import hshare.gesturelockview.base.ILockView;
import hshare.gesturelockview.lineview.GradientNoLineView;
import hshare.gesturelockview.lineview.NormalLineView;
import hshare.gesturelockview.lockview.AnimatorLockView;


/**
 * 功能/模块 ：***
 *
 * @author huzeliang
 * @version 1.0 ${date} ${time}
 * @see ***
 * @since ***
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        GestureLockHelper.getInstance().setOnLockViewNewListener(new GestureLockHelper.OnGestureLockNewListener() {
            @Override
            public ILockView onLockViewNew(Context context, Object tag) {
//                ZhiFuBaoLockView lockView = new ZhiFuBaoLockView(context);
//                lockView.setColorNormal(Color.parseColor("#1B94EA"));
//                lockView.setColorSelected(Color.parseColor("#108EE9"));
//                lockView.setColorError(Color.parseColor("#F84545"));
//                return lockView;
//                return new PicLockView(context);
                return null;
            }

            @Override
            public BaseLineView onLineViewNew(Context context, Object tag) {
//                NormalLineView normalLineView = new NormalLineView(context);
//                normalLineView.setErrorDelay(3000);
//                normalLineView.setSelectedLineColor(Color.parseColor("#108EE9"));
//                normalLineView.setErrorLineColor(Color.parseColor("#F84545"));
//                normalLineView.setLineWidthDp(2);
//                normalLineView.setLineAlpha(255);
//                normalLineView.setPadding(20);
//                return new GradientNoLineView(context);
                return null;
            }
        });
    }
}

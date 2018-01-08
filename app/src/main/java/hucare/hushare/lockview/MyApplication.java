package hucare.hushare.lockview;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import hshare.gesturelockview.GestureLockHelper;
import hshare.gesturelockview.impl.LineViewImpl;
import hshare.gesturelockview.base.BaseLockView;
import hshare.gesturelockview.lineview.GradientNoLineView;
import hshare.gesturelockview.lineview.NormalLineView;
import hshare.gesturelockview.lockview.AnimatorLockView;
import hshare.gesturelockview.lockview.PicLockView;
import hshare.gesturelockview.lockview.ZhiFuBaoLockView;


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
            public BaseLockView onLockViewNew(Context context, Object tag) {
                switch ("" + tag) {
                    case "默认":
                        return null;
                    case "京东金融":
                        return null;
                    case "国元OA":
                        return null;
                    case "支付宝":
                        return new ZhiFuBaoLockView(context);
                    case "动画圆圈":
                        return new AnimatorLockView(context);
                    case "图片圆圈":
                        return new PicLockView(context);
                    case "渐变线条":
                        return null;
                    default:
                        return null;
                }
            }

            @Override
            public LineViewImpl onLineViewNew(Context context, Object tag) {
                switch ("" + tag) {
                    case "默认":
                        return null;
                    case "京东金融":
                        return null;
                    case "国元OA":
                        return new NormalLineView(context, 0xFF2D8FDB, 0xFFF5A52A, 3000, 155,8);
                    case "支付宝":
                        return new NormalLineView(context, 0xFF108EE9, 0xFFF84545, 3000, 255,2);
                    case "动画圆圈":
                        return null;
                    case "图片圆圈":
                        return new NormalLineView(context, 0xFFFF8F09, 0xFFEF0E23, 3000, 155,8);
                    case "渐变线条":
                        return new GradientNoLineView(context);
                    default:
                        return null;
                }
            }
        });
    }
}

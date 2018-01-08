package hucare.hushare.lockview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import hshare.gesturelockview.GestureLockView;
import hshare.gesturelockview.listener.OnGestureCompleteListener;

public class MainActivity22 extends Activity {
    GestureLockView gestureLockView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        gestureLockView = (GestureLockView) findViewById(R.id.guvMy);
        gestureLockView.setOnGestureCompleteListener(new OnGestureCompleteListener() {
            @Override
            public boolean onOutputPassword(String password) {
                Toast.makeText(MainActivity22.this, "" + password, Toast.LENGTH_SHORT).show();
                if (password.equals("1,2,3,6")) {
                    return true;
                }
                return false;
            }
        });
//        gestureLockView.setOnGestureVerifyListener("1,2,3,6", new OnGestureVerifyListener() {
//            @Override
//            public void onSuccess() {
//                Toast.makeText(GestureActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError() {
//                Toast.makeText(GestureActivity.this, "onError", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}

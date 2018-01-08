package hucare.hushare.lockview;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hshare.gesturelockview.GestureLockView;
import hshare.gesturelockview.listener.OnGestureCompleteListener;

public class MainActivity extends AppCompatActivity {
    //    GestureLockView gestureLockView;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        List<NormalViewPageBaseBean> normalFragmentBaseBeen = new ArrayList<>();
        normalFragmentBaseBeen.add(new NormalViewPageBaseBean("默认"));
        normalFragmentBaseBeen.add(new NormalViewPageBaseBean("支付宝"));
        normalFragmentBaseBeen.add(new NormalViewPageBaseBean("动画圆圈"));
        normalFragmentBaseBeen.add(new NormalViewPageBaseBean("图片圆圈"));
        normalFragmentBaseBeen.add(new NormalViewPageBaseBean("渐变线条"));
        normalFragmentBaseBeen.add(new NormalViewPageBaseBean("京东金融"));
        normalFragmentBaseBeen.add(new NormalViewPageBaseBean("国元OA"));

        NormalViewPageAdapter adapter =
                new NormalViewPageAdapter(getSupportFragmentManager(), normalFragmentBaseBeen, GesturesFragment.class);

        viewPager.setOffscreenPageLimit(normalFragmentBaseBeen.size());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabsFromPagerAdapter(adapter);
//        gestureLockView = (GestureLockView) findViewById(R.id.guvMy);
//        gestureLockView.setOnGestureCompleteListener(new OnGestureCompleteListener() {
//            @Override
//            public boolean onOutputPassword(String password) {
//                Toast.makeText(MainActivity.this, "" + password, Toast.LENGTH_SHORT).show();
//                if (password.equals("1,2,3,6")) {
//                    return true;
//                }
//                return false;
//            }
//        });
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

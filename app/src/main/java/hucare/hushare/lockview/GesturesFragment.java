package hucare.hushare.lockview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import hshare.gesturelockview.GestureLockView;
import hshare.gesturelockview.listener.OnGestureCompleteListener;
import hshare.gesturelockview.util.MD5Utils;
import hshare.gesturelockview.util.SPUtils;

/**
 * 功能/模块 ：***
 *
 * @author huzeliang
 * @version 1.0 ${date} ${time}
 * @see ***
 * @since ***
 */
public class GesturesFragment extends Fragment {

    private GestureLockView gestureLockView;
    private TextView tvTips;
    private TextView tvReset;
    private String myPassword = "";
    private String tempPassword = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gestures, container, false);
        gestureLockView = (GestureLockView) view.findViewById(R.id.gestureLockView);
        tvTips = (TextView) view.findViewById(R.id.tvTips);
        tvReset = (TextView) view.findViewById(R.id.tvReset);
        if (getArguments() != null) {
            NormalViewPageBaseBean dailyRecipesFragmentBean = (NormalViewPageBaseBean) getArguments().getSerializable("databean");
            gestureLockView.setTag(dailyRecipesFragmentBean.getTitle());
        }
        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestureLockView.setOnGestureCompleteListener(new setGestureListener());
                myPassword = "";
                tempPassword = "";
                tvTips.setText("请设置手势密码");
                tvReset.setVisibility(View.GONE);
                SPUtils.put(getContext(),"PS","ps" + gestureLockView.getTag(), "");
            }
        });
        myPassword = (String) SPUtils.get(getContext(),"PS","ps" + gestureLockView.getTag(),"");
        if (TextUtils.isEmpty(myPassword)){
            tvReset.setVisibility(View.GONE);
            tvTips.setText("请设置手势密码");
            gestureLockView.setOnGestureCompleteListener(new setGestureListener());
        }else {
            tvTips.setText("");
            tvReset.setVisibility(View.VISIBLE);
            gestureLockView.setOnGestureCompleteListener(new verifyGestureListener());
        }
        return view;
    }

    class setGestureListener implements OnGestureCompleteListener{

        @Override
        public boolean onOutputPassword(String password) {
            if (("" + password).length() < 7){
                tvTips.setText("至少绘制四个点");
                return false;
            }
            password = MD5Utils.encryptStr(password);
            if (TextUtils.isEmpty(tempPassword)){
                tempPassword = password;
                tvTips.setText("请确认您的手势");
            }else {
                if (tempPassword.equals(password)){
                    myPassword = tempPassword;
                    tvTips.setText("设置成功...");
                    SPUtils.put(getContext(),"PS","ps" + gestureLockView.getTag(), password);
                    gestureLockView.setOnGestureCompleteListener(new verifyGestureListener());
                    tvReset.setVisibility(View.VISIBLE);
                }else {
                    tvTips.setText("2次输入不一致");
                    tvReset.setVisibility(View.VISIBLE);
                    return false;
                }
            }
            return true;
        }
    }

    class verifyGestureListener implements OnGestureCompleteListener{

        @Override
        public boolean onOutputPassword(String password) {
            password = MD5Utils.encryptStr(password);
            if (password.equals(myPassword)){
                tvTips.setText("校验成功...");
                tvReset.setVisibility(View.VISIBLE);
                return true;
            }else {
                tvTips.setText("校验失败...");
                tvReset.setVisibility(View.VISIBLE);
                return false;
            }
        }
    }


}

package hucare.hushare.lockview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import hshare.gesturelockview.GestureLockView;
import hshare.gesturelockview.listener.OnGestureCompleteListener;
import hshare.gesturelockview.listener.OnGestureVerifyListener;

/**
 * 功能/模块 ：***
 *
 * @author huzeliang
 * @version 1.0 ${date} ${time}
 * @see ***
 * @since ***
 */
public class GesturesFragment extends Fragment {

    GestureLockView gestureLockView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gestures, container, false);
        gestureLockView = (GestureLockView) view.findViewById(R.id.gestureLockView);
        if (getArguments() != null) {
            NormalViewPageBaseBean dailyRecipesFragmentBean = (NormalViewPageBaseBean) getArguments().getSerializable("databean");
            gestureLockView.setTag(dailyRecipesFragmentBean.getTitle());
        }


        gestureLockView.setOnGestureCompleteListener(new OnGestureCompleteListener() {
            @Override
            public boolean onOutputPassword(String password) {
                Toast.makeText(getActivity(), "" + password, Toast.LENGTH_SHORT).show();
                if (password.equals("1,2,3,6")) {
                    return true;
                }
                return false;
            }
        });
        gestureLockView.setOnGestureVerifyListener("1,2,3,6", new OnGestureVerifyListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), "onSuccess", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError() {
                Toast.makeText(getActivity(), "onError", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}

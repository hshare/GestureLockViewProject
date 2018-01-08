package hucare.hushare.lockview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * viewpager的适配器，通用的
 *
 * @param <T> fragment传递参数用的bean
 * @author hzl
 */
public class NormalViewPageAdapter<T extends NormalViewPageBaseBean> extends FragmentStatePagerAdapter {
    /**
     * fragment传递参数用的bean
     */
    private List<T> normalFragmentBaseBean;
    /**
     * fragment列表
     */
    private List<Fragment> fragments;

    public NormalViewPageAdapter(FragmentManager fm, List<T> temps, Class<?> fragment) {
        super(fm);
        normalFragmentBaseBean = temps;
        fragments = new ArrayList<>();
        for (T t : normalFragmentBaseBean) {
            fragments.add(createFragment(fragment, t));
        }
    }

    public NormalViewPageAdapter(FragmentManager fm, List<T> temps) {
        super(fm);
        normalFragmentBaseBean = temps;
        fragments = new ArrayList<>();
        for (T t : normalFragmentBaseBean) {
            if (t != null && t.getFragmentClass() != null) {
                fragments.add(createFragment(t.getFragmentClass(), t));
            }
        }
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        if (normalFragmentBaseBean == null) {
            return 0;
        }
        return normalFragmentBaseBean.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return normalFragmentBaseBean.get(position).getTitle();
    }

    /**
     * 创建fragment
     *
     * @param clazz Fragment的类名
     * @param bean  数据bena
     * @return fragment
     */
    private Fragment createFragment(Class<?> clazz, NormalViewPageBaseBean bean) {
        Fragment resultFragment = null;
        String className = clazz.getName();
        try {
            try {
                resultFragment = (Fragment) Class.forName(className).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (resultFragment != null && bean != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(bean.getBundleString(), bean);
            resultFragment.setArguments(bundle);
        }

        return resultFragment;
    }
}

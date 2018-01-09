package hucare.hushare.lockview;

import java.io.Serializable;

/**
 * @author huzeliang
 */
public class NormalViewPageBaseBean implements Serializable {
    private String title;
    private String bundleString;
    private int what;
    private String arg1;
    private String arg2;
    private Object object;
    private Class<?> fragmentClass;

    public NormalViewPageBaseBean(String title) {
        this.title = title;
        bundleString = "databean";
    }

    public NormalViewPageBaseBean(String title, Class<?> fragmentClass) {
        this.title = title;
        this.fragmentClass = fragmentClass;
        bundleString = "databean";
    }

    public NormalViewPageBaseBean(String title, int what) {
        this.what = what;
        this.title = title;
        bundleString = "databean";
    }

    public NormalViewPageBaseBean(String title, String bundleString) {
        this.title = title;
        this.bundleString = bundleString;
    }

    public Class<?> getFragmentClass() {
        return fragmentClass;
    }

    public void setFragmentClass(Class<?> fragmentClass) {
        this.fragmentClass = fragmentClass;
    }

    public String getBundleString() {
        return bundleString;
    }

    public void setBundleString(String bundleString) {
        this.bundleString = bundleString;
    }

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

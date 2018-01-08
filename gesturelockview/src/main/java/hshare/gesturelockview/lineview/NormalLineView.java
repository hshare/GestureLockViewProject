package hshare.gesturelockview.lineview;

import android.content.Context;
import android.graphics.Paint;

import hshare.gesturelockview.base.BaseLineView;


/**
 * @author huzeliang
 *         2017-11-14 18:27:53
 */
public class NormalLineView extends BaseLineView {

    private int selectedLineColor = 0xFF000000;
    private int errorLineColor = 0xFFFF0000;
    private int errorDelay = 1000;
    private int lineAlpha = 150;

    public NormalLineView(Context context) {
        super(context);
    }


    @Override
    protected void setErrorPaint(Paint connectPaint, Paint movePaint) {
        connectPaint.setColor(errorLineColor);
        connectPaint.setAlpha(lineAlpha);

        movePaint.setColor(errorLineColor);
        movePaint.setAlpha(lineAlpha);
    }

    @Override
    protected int getErrorDelay() {
        return errorDelay;
    }

    @Override
    public void setMovePaint(Paint connectPaint, Paint movePaint) {
        connectPaint.setColor(selectedLineColor);
        connectPaint.setAlpha(lineAlpha);

        movePaint.setColor(selectedLineColor);
        movePaint.setAlpha(lineAlpha);
    }

    public void setSelectedLineColor(int selectedLineColor) {
        this.selectedLineColor = selectedLineColor;
    }

    public void setErrorLineColor(int errorLineColor) {
        this.errorLineColor = errorLineColor;
    }

    public void setErrorDelay(int errorDelay) {
        this.errorDelay = errorDelay;
    }

    public void setLineWidthDp(float lineWidth) {
        this.lineWidth = dip2px(lineWidth);
    }

    public void setLineAlpha(int lineAlpha) {
        this.lineAlpha = lineAlpha;
    }

    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

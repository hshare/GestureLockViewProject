package hshare.gesturelockview.lineview;

import android.content.Context;
import android.graphics.Paint;

import hshare.gesturelockview.impl.LineViewImpl;


/**
 * @author huzeliang
 *         2017-11-14 18:27:53
 */
public class NormalLineView extends LineViewImpl {

    private int selectedLineColor = 0xFF000000;
    private int errorLineColor = 0xFFFF0000;
    private int errorDelay = 1000;
    private int lineAlpha = 150;

    public NormalLineView(Context context) {
        super(context);
    }

    public NormalLineView(Context context, int selectedLineColor, int errorLineColor, int errorDelay, int lineAlpha,int lineWidth) {
        super(context);
        this.selectedLineColor = selectedLineColor;
        this.errorLineColor = errorLineColor;
        this.errorDelay = errorDelay;
        this.lineAlpha = lineAlpha;
        setLineWidthDp(lineWidth);
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
        paint1.setStrokeWidth(this.lineWidth);
        paint2.setStrokeWidth(this.lineWidth);
    }

    public void setLineAlpha(int lineAlpha) {
        this.lineAlpha = lineAlpha;
    }

    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

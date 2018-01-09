package hshare.gesturelockview.lineview;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;

import hshare.gesturelockview.impl.LineViewImpl;


/**
 * @author huzeliang
 */
public class GradientNoLineView extends LineViewImpl {


    public GradientNoLineView(Context context) {
        super(context);
    }

    @Override
    protected void setErrorPaint(Paint connectPaint, Paint movePaint) {
        connectPaint.setColor(0xFFFF0000);
        connectPaint.setAlpha(150);

        movePaint.setColor(0xFFFF0000);
        movePaint.setAlpha(150);
    }

    @Override
    protected int getErrorDelay() {
        return 2000;
    }

    @Override
    public void setMovePaint(Paint connectPaint, Paint movePaint) {
        LinearGradient shader = new LinearGradient(
                lastPathX,
                lastPathY,
                tmpTarget.x, tmpTarget.y,
                new int[]{0xFFaaaaaa, 0xFF666666, 0xFF000505}, null, Shader.TileMode.REPEAT);
        movePaint.setShader(shader);

        connectPaint.setColor(0xFF000000);
        connectPaint.setAlpha(150);
    }


}

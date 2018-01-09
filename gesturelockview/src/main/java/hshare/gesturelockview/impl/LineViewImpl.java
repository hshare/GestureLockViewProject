package hshare.gesturelockview.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import hshare.gesturelockview.base.BaseLineView;
import hshare.gesturelockview.base.BaseLockView;
import hshare.gesturelockview.listener.OnGestureCompleteListener;


/**
 * @author huzeliang
 */
public abstract class LineViewImpl extends BaseLineView {

    protected List<BaseLockView> lockViews;
    private OnGestureCompleteListener onGestureCompleteListener;
    private int padding = 0;
    private int pointWidth = 0;

    protected Path path;
    public Paint paint1;
    public Paint paint2;

    protected int lastPathX;
    protected int lastPathY;
    protected Point tmpTarget = new Point();
    private StringBuilder passWordSb;
    private boolean isShowLine = true;
    private List<Integer> chooseList = new ArrayList<Integer>();

    protected float lineWidth = 30;

    public LineViewImpl(Context context) {
        super(context);
        initPaint();
    }

    @Override
    public void initLockViews(List<BaseLockView> lockViews, int width) {
        this.lockViews = lockViews;
        pointWidth = width;
    }

    @Override
    public void setOnGestureCompleteListener(OnGestureCompleteListener onGestureCompleteListener) {
        this.onGestureCompleteListener = onGestureCompleteListener;
    }

    protected BaseLockView getChildIdByPos(int x, int y) {
        for (BaseLockView gestureLockView : lockViews) {
            if (checkPositionInChild(gestureLockView, x, y)) {
                return gestureLockView;
            }
        }
        return null;
    }

    private boolean checkPositionInChild(BaseLockView child, int x, int y) {
        if (padding == 0) {
            padding = (int) (pointWidth * 0.15);
        }
        if (x >= child.getLeft() + padding && x <= child.getRight() - padding && y >= child.getTop() + padding && y <= child.getBottom() - padding) {
            return true;
        }
        return false;
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void setShowLine(boolean showLine) {
        isShowLine = showLine;
    }

    public void initPaint() {
        path = new Path();
        this.passWordSb = new StringBuilder();
        paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setStrokeWidth(lineWidth);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeCap(Paint.Cap.ROUND);
        paint1.setStrokeJoin(Paint.Join.ROUND);
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setStrokeWidth(lineWidth);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isShowLine) {
            return;
        }
        // 绘制GestureLockView间的连线
        if (path != null) {
            canvas.drawPath(path, paint1);
        }
        // 绘制指引线
        if (chooseList.size() > 0) {
            if (lastPathX != 0 && lastPathY != 0) {
                canvas.drawLine(lastPathX, lastPathY, tmpTarget.x, tmpTarget.y, paint2);
            }
        }
    }

    private void reset() {
        this.removeCallbacks(runnable);
        chooseList.clear();
        path.reset();
        for (BaseLockView gestureLockView : lockViews) {
            gestureLockView.setState(BaseLockView.State.STATE_NORMAL);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                reset();
                break;
            case MotionEvent.ACTION_MOVE:
                setMovePaint(paint1, paint2);
                BaseLockView child = getChildIdByPos(x, y);
                if (child != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    int cId = child.getId();
                    if (!chooseList.contains(cId)) {
                        chooseList.add(cId);
                        child.setState(BaseLockView.State.STATE_SELECTED);
                        // 设置指引线的起点
                        lastPathX = child.getLeft() / 2 + child.getRight() / 2;
                        lastPathY = child.getTop() / 2 + child.getBottom() / 2;
                        tmpTarget.x = lastPathX;
                        tmpTarget.y = lastPathY;
                        if (chooseList.size() == 1) {
                            // 当前添加为第一个
                            path.moveTo(lastPathX, lastPathY);
                        } else {
                            // 非第一个，将两者使用线连上
                            path.lineTo(lastPathX, lastPathY);
                        }
                    }
                }
                // 指引线的终点
                tmpTarget.x = x;
                tmpTarget.y = y;

                break;
            case MotionEvent.ACTION_UP:

                if (chooseList.size() > 0) {
                    passWordSb = new StringBuilder();
                    for (int i = 0; i < chooseList.size(); i++) {
                        passWordSb.append((i == 0 ? "" : ",") + chooseList.get(i));
                    }
                    if (onGestureCompleteListener != null &&
                            !onGestureCompleteListener.onOutputPassword(passWordSb.toString())) {
                        setAllError();
                    } else {
                        reset();
                    }
                } else {
                }
                // 将终点设置位置为起点，即取消指引线
                tmpTarget.x = lastPathX;
                tmpTarget.y = lastPathY;
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    private void setAllError() {
        for (int i = 0; i < chooseList.size(); i++) {
            if (chooseList.get(i) > 0 && chooseList.get(i) <= lockViews.size()) {
                lockViews.get(chooseList.get(i) - 1).setState(BaseLockView.State.STATE_ERROR);
                setErrorPaint(paint1, paint2);
            }
        }
        postDelayed(runnable, getErrorDelay());
    }

    protected abstract void setErrorPaint(Paint connectPaint, Paint movePaint);

    protected abstract int getErrorDelay();

    public abstract void setMovePaint(Paint connectPaint, Paint movePaint);

    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            reset();
            invalidate();
        }
    };

}

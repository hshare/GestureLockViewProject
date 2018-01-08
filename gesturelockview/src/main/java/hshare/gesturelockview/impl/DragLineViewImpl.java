package hshare.gesturelockview.impl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import hshare.gesturelockview.base.BaseLineView;
import hshare.gesturelockview.base.BaseLockView;
import hshare.gesturelockview.listener.OnGestureCompleteListener;
import hshare.gesturelockview.listener.OnGestureVerifyListener;


/**
 * @author huzeliang
 *         2017-11-14 18:27:04
 */
public abstract class DragLineViewImpl extends BaseLineView {

    protected List<BaseLockView> lockViews;
    private String verifyPassword;
    private OnGestureVerifyListener onGestureVerifyListener;
    private OnGestureCompleteListener onGestureCompleteListener;
    private int padding = 0;
    private int pointWidth = 0;

    protected Path path;
    private Paint paint1;
    private Paint paint2;

    protected int lastPathX;
    protected int lastPathY;
    protected Point tmpTarget = new Point();
    private StringBuilder passWordSb;
    private boolean isShowLine = true;
    private List<Integer> chooseList = new ArrayList<Integer>();

    protected float lineWidth = 30;

    public DragLineViewImpl(Context context) {
        super(context);
        initPaint();
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mStatusHeight = getStatusHeight(context);
        mVibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void initLockViews(List<BaseLockView> lockViews, int width) {
        this.lockViews = lockViews;
        pointWidth = width;
    }

    @Override
    public void setOnGestureVerifyListener(String password, OnGestureVerifyListener onGestureVerifyListener) {
        this.onGestureVerifyListener = onGestureVerifyListener;
        this.verifyPassword = password;
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

    private void createDragImage(Bitmap bitmap, int downX, int downY) {
        mWindowLayoutParams = new WindowManager.LayoutParams();
        mWindowLayoutParams.format = PixelFormat.TRANSLUCENT; // 图片之外的其他地方透明
        mWindowLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        mWindowLayoutParams.x = downX - mPoint2ItemLeft + mOffset2Left;
        mWindowLayoutParams.y = downY - mPoint2ItemTop + mOffset2Top - mStatusHeight;
        mWindowLayoutParams.alpha = 0.55f; // 透明度
        mWindowLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

        mDragImageView = new ImageView(getContext());
        mDragImageView.setImageBitmap(bitmap);
        mWindowManager.addView(mDragImageView, mWindowLayoutParams);
    }

    Runnable longClickRunnable = new Runnable() {
        @Override
        public void run() {
            isDrag = true;
            mVibrator.vibrate(50); // 震动一下
            // 开启mDragItemView绘图缓存
            dragView.setDrawingCacheEnabled(true);
            // 获取mDragItemView在缓存中的Bitmap对象
            Bitmap mDragBitmap = Bitmap.createBitmap(dragView.getDrawingCache());
            // 这一步很关键，释放绘图缓存，避免出现重复的镜像
            dragView.destroyDrawingCache();
            dragView.setVisibility(GONE);
            createDragImage(mDragBitmap, downX, downY);
        }
    };

    private boolean isDrag = false;
    private View dragView = null;
    private WindowManager mWindowManager;
    private int downX = 0;
    private int downY = 0;
    private ImageView mDragImageView;
    WindowManager.LayoutParams mWindowLayoutParams;
    private int mPoint2ItemTop;
    private int mPoint2ItemLeft;
    private int mOffset2Top;
    private int mOffset2Left;
    private int mStatusHeight;
    private Vibrator mVibrator;

    private void onDragItem(int moveX, int moveY) {
        mWindowLayoutParams.x = moveX - mPoint2ItemLeft + mOffset2Left;
        mWindowLayoutParams.y = moveY - mPoint2ItemTop + mOffset2Top - mStatusHeight;
        mWindowManager.updateViewLayout(mDragImageView, mWindowLayoutParams); // 更新镜像的位置
//        onSwapItem(moveX, moveY);

    }
    public int getStatusHeight(Context context) {
        int statusHeight = 0;
        Rect localRect = new Rect();
        ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = downX = (int) event.getX();
        int y = downY = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                reset();
                isDrag = false;
                if ((dragView =  getChildIdByPos(x, y)) != null) {
                    postDelayed(longClickRunnable, 2000);
                    mPoint2ItemTop = downY - dragView.getTop();
                    mPoint2ItemLeft = downX - dragView.getLeft();

                    mOffset2Top = (int) (event.getRawY() - downY);
                    mOffset2Left = (int) (event.getRawX() - downX);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDrag) {
                    onDragItem(downX, downY);
                } else {
                    removeCallbacks(longClickRunnable);

                    setMovePaint(paint1, paint2);
                    BaseLockView child = getChildIdByPos(x, y);
                    if (child != null) {
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
                }

                break;
            case MotionEvent.ACTION_UP:

                removeCallbacks(longClickRunnable);
                if (isDrag) {

                } else {
                    if (chooseList.size() > 0) {
                        passWordSb = new StringBuilder();
                        for (int i = 0; i < chooseList.size(); i++) {
                            passWordSb.append((i == 0 ? "" : ",") + chooseList.get(i));
                        }
                        if (!TextUtils.isEmpty(verifyPassword)) {
                            if (verifyPassword.equals(passWordSb.toString())) {
                                reset();
                                if (onGestureVerifyListener != null) {
                                    onGestureVerifyListener.onSuccess();
                                }
                            } else {
                                if (onGestureVerifyListener != null) {
                                    onGestureVerifyListener.onError();
                                }
                                setAllError();
                            }
                            if (onGestureCompleteListener != null) {
                                onGestureCompleteListener.onOutputPassword(passWordSb.toString());
                            }
                        } else {
                            if (onGestureCompleteListener != null &&
                                    !onGestureCompleteListener.onOutputPassword(passWordSb.toString())) {
                                setAllError();
                            } else {
                                reset();
                            }
                        }
                    } else {
                    }
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

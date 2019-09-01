package pri.ky2.ky2coderepos.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.blankj.utilcode.util.ColorUtils;

import pri.ky2.ky2coderepos.R;

public class CircleProgressBar extends View {
    /**
     * 画笔对象的引用
     */
    private Paint paintArcPoint;
    private Paint paintBigCircleShader;
    private Paint paintBigCircle;
    private Paint paintSmallCircle;

    /**
     * 圆环的颜色
     */
    private int roundColor;

    /**
     * 小圆点边界颜色
     */
    private int mIndicatorBorderColor;

    /**
     * 圆环进度的颜色
     */
    private int roundProgressColor;

    /**
     * 中间进度百分比的字符串的颜色
     */
    private int textColor;

    /**
     * 中间进度百分比的字符串的字体
     */
    private float textSize;

    /**
     * 圆环的宽度
     */
    private float roundWidth;

    /**
     * 最大进度
     */
    private int max;

    /**
     * 当前进度
     */
    private int progress;
    /**
     * 是否显示中间的进度
     */
    private boolean textIsDisplayable;

    /**
     * 进度的风格，实心或者空心
     */
    private int style;

    public static final int STROKE = 0;
    public static final int FILL = 1;

    public CircleProgressBar(Context context) {
        this(context, null);
    }

    public CircleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initPaints();
        initAttrs(context, attrs);
    }

    private void initPaints() {
        paintArcPoint = new Paint();
        paintBigCircleShader = new Paint();
        paintBigCircle = new Paint();
        paintSmallCircle = new Paint();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);

        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED);
        roundProgressColor =
                mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN);
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15);
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, 100);
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);

        mIndicatorBorderColor = mTypedArray.getColor(R.styleable.RoundProgressBar_indicatorBorderColor,
                getContext().getResources().getColor(R.color.common_divider));

        mTypedArray.recycle();
    }


    /**
     * 圆心坐标
     */
    private int centre;
    /**
     * 进度圆弧所在圆半径
     */
    private int radius;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        centre = getWidth() / 2; //获取圆心的x坐标
        radius = (int) ((int) ((centre - roundWidth / 2) - roundWidth) - dp2px(30)); //圆环的半径

        // 画底部大圆
        drawBigCircle(canvas);

        // 画底部小圆
        drawSmallCircle(canvas);

        // 画进度百分比、进度弧
        drawProgress(canvas);

    }


    private void drawBigCircle(Canvas canvas) {
        // 画底部大圆阴影
        Shader shader = new RadialGradient(centre, centre,
                radius + dp2px(15),
                Color.parseColor("#808080"), Color.parseColor("#FFFFFFFF"), Shader.TileMode.CLAMP);
        paintBigCircleShader.setShader(shader);
        paintBigCircleShader.setStyle(Paint.Style.FILL);
        paintBigCircleShader.setAntiAlias(true);
        canvas.drawCircle(centre, centre, radius + dp2px(15), paintBigCircleShader);

        // 画大圆
        paintBigCircle.setStyle(Paint.Style.FILL);
        paintBigCircle.setColor(ColorUtils.getColor(R.color.white));
        paintBigCircle.setAntiAlias(true);
        canvas.drawCircle(centre, centre, radius + dp2px(7), paintBigCircle);

    }

    private void drawSmallCircle(Canvas canvas) {
        paintSmallCircle.setColor(Color.parseColor("#F9F9F9")); //设置圆环的颜色
        paintSmallCircle.setStyle(Paint.Style.FILL);
        paintSmallCircle.setAntiAlias(true);
        canvas.drawCircle(centre, centre, radius - dp2px(10),
                paintSmallCircle); //画出圆环
    }

    private void drawProgress(Canvas canvas) {
        paintArcPoint.setStrokeWidth(0);
        paintArcPoint.setColor(textColor);
        paintArcPoint.setTextSize(textSize);
        paintArcPoint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
        int percent =
                (int) (((float) mCurrentProgress / (float) max) * 100);  //中间的进度百分比，先转换成float在进行除法运算，不然都为0
        float textWidth = paintArcPoint.measureText(percent + "%");   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间

        if (textIsDisplayable && percent != 0 && style == STROKE) {
            canvas.drawText(percent + "%", centre - textWidth / 2, centre + textSize / 2,
                    paintArcPoint); //画出进度百分比
        }

        float pointX = (float) (radius * Math.cos(Math.toRadians(270 + 360 * mCurrentProgress / max)));
        float pointY = (float) (radius * Math.sin(Math.toRadians(270 + 360 * mCurrentProgress / max)));

        Shader shader = new LinearGradient(0, centre, centre + pointX, centre + pointY,
                Color.parseColor("#E9C4A0"), Color.parseColor("#E5A361"), Shader.TileMode.CLAMP);
        paintArcPoint.setShader(shader);
        //设置圆环的宽度
        paintArcPoint.setStrokeWidth(roundWidth);
        //设置进度的颜色
        paintArcPoint.setColor(roundProgressColor);
        paintArcPoint.setStrokeCap(Paint.Cap.ROUND); //用于定义的圆弧的形状和大小的界限

        RectF oval = new RectF(centre - radius, centre - radius, centre
                + radius, centre + radius);

        switch (style) {
            case STROKE: {
                paintArcPoint.setStyle(Paint.Style.STROKE);
                canvas.drawArc(oval, -90, 360 * mCurrentProgress / max, false, paintArcPoint);  //根据进度画圆弧

                // 画点
                paintArcPoint.setStyle(Paint.Style.FILL);
                paintArcPoint.setStrokeWidth(2.5f * roundWidth);
                canvas.drawPoint(centre + pointX, centre + pointY, paintArcPoint);

                break;
            }
            case FILL: {
                paintArcPoint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (progress != 0) {
                    canvas.drawArc(oval, -90, 360 * mCurrentProgress / max, true, paintArcPoint);  //根据进度画圆弧
                }
                break;
            }
            default:
                break;
        }
    }


    public synchronized int getMax() {
        return max;
    }

    /**
     * 设置进度的最大值
     */
    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度.需要同步
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > max) {
            progress = max;
        }
        if (progress <= max) {
            this.progress = progress;
            if (mCurrentProgress < progress) {
                postDelayed(mUpdateRunnable, 0);
            } else {
                mCurrentProgress = progress;
                postInvalidate();
            }
        }
    }

    /**
     * 取消当前进度显示
     */
    public synchronized void cancel() {
        progress = 0;
        mCurrentProgress = 0;
        if (mListener != null) {
            mListener.onProgressChanged(0);
        }
        removeCallbacks(mUpdateRunnable);
        postInvalidate();
    }

    public interface OnProgressChangedListener {
        void onProgressChanged(int newProgress);
    }

    private OnProgressChangedListener mListener;

    public void setOnProgressChanged(OnProgressChangedListener listener) {
        mListener = listener;
    }

    private int mCurrentProgress = 0;

    private int mUpdateInterval = 15;

    public void setUpdateInterval(int interval) {
        mUpdateInterval = interval;
    }

    private Runnable mUpdateRunnable = new Runnable() {
        @Override
        public synchronized void run() {
            if (mCurrentProgress < progress) {
                mCurrentProgress++;
                if (mListener != null) {
                    mListener.onProgressChanged(mCurrentProgress);
                }

                invalidate();
                postDelayed(mUpdateRunnable, mUpdateInterval);
            } else {

            }

        }
    };


    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

    public float dp2px(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}

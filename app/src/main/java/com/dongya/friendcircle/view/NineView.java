package com.dongya.friendcircle.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dongya.friendcircle.R;
import com.dongya.friendcircle.utils.ScreenUtil;

/**
 * FileName: NineView
 * Author: dongya
 * Date: 2021/2/8 11:21 AM
 * Description:
 */
public class NineView extends ViewGroup {

    private NineAdapter nineAdapter;
    private Context mContext;

    /**
     * 行数
     */
    private int mRows = 0;
    /**
     * 列数
     */
    private int mColumns = 0;
    /**
     * 大小
     */
    private int mSize = 0;

    /**
     * 单张图片的宽度
     */
    private int mSingleWidth = 0;
    /**
     * 单张图片的高度
     */
    private int mSingleHeight = 0;

    /**
     *
     */
    private int dividerSpace = 0;

    public NineView(Context context) {
        this(context,null);
    }

    public NineView(Context context, AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public NineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray t = mContext.obtainStyledAttributes(attrs,R.styleable.NineView);
        dividerSpace = t.getDimensionPixelOffset(R.styleable.NineView_space,0);
        t.recycle();

    }

    public void initView(NineAdapter nineAdapter){
        this.nineAdapter = nineAdapter;
        mSize = nineAdapter.getSize();
        removeAllViews();
        calculationRows();
        addChildrenData();
        requestLayout();
    }

    private void addChildrenData() {
        for (int i = 0; i < nineAdapter.getSize(); i++) {
            addViewInLayout(nineAdapter.getView(), i, nineAdapter.getView().getLayoutParams(), true);
        }
    }

    private void calculationRows() {
        if (mSize <= 3) {
            mRows = 1;
            mColumns = mSize;
        }else {
            mRows = mSize /3;
            if (mSize % 3 != 0){
                mRows = mRows+1;
            }
            mColumns = 3;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (nineAdapter == null || nineAdapter.getSize() <1){
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

        /**
         * 计算单张图片的宽高··目前设置的是宽高相等··也可配置成自定义属性
         */
        mSingleWidth = (MeasureSpec.getSize(widthMeasureSpec)- dividerSpace*2)/3;
        mSingleHeight = mSingleWidth;
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), mSingleHeight * mRows+(mRows -1)*dividerSpace);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mRows <= 0 || mColumns <= 0) {
            return;
        }
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView view = (ImageView) getChildAt(i);
            int row = i / mColumns;
            int col = i % mColumns;
            int left = (mSingleWidth + dividerSpace) * col;
            int top = (mSingleHeight + dividerSpace) * row;
            int right = left + mSingleWidth;
            int bottom = top + mSingleHeight;
            view.layout(left, top, right, bottom);
            Log.e("dongya","imageView的宽高:H--"+view.getHeight()+"W--"+view.getWidth());
        }
    }

    public interface NineAdapter{
        int getSize();
        View getView();
    }
}

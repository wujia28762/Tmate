package com.honyum.elevatorMan.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.honyum.elevatorMan.data.StatResult;

import java.util.ArrayList;

import static com.uuzuche.lib_zxing.DisplayUtil.dip2px;

/**
 * Created by Administrator on 2015/10/20.
 */
public class ChartTableView extends View {

    private int width;//控件宽
    private int height;//控件高
    private int dataNum;//数据量

    private int undealCount;//总访问量
    private int dealingCount;//员工总数目
    private int finishCount;//平均访问量


    private Paint mPaintText;//用于绘制文本
    private Paint mPaintWhiteBg;//用于绘制白色背景
    private Paint mPaintGreyBg;//用于绘制灰色背景
    private Paint mPaintLightGrey;//用于绘制浅灰色背景
    private Paint mPaintLine;//用于画表格的列线

    private int textSize = dip2px(getContext(), 13);//文本尺寸，dp转px

    private ArrayList<StatResult> dataList = new ArrayList<StatResult>();//总数据
    private int cancelCount;
    private int totalCount;

    public ChartTableView(Context context) {
        super(context);
    }

    public ChartTableView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaintLine = new Paint();
        mPaintLine.setColor(Color.LTGRAY);
        mPaintLine.setStrokeWidth(1);
        mPaintLine.setAntiAlias(true);

        mPaintText = new Paint();
        mPaintText.setColor(Color.BLACK);
        mPaintText.setTextSize(textSize);
        mPaintText.setTextAlign(Paint.Align.CENTER);
        mPaintText.setAntiAlias(true);

        mPaintWhiteBg = new Paint();
        mPaintWhiteBg.setColor(Color.WHITE);
        mPaintWhiteBg.setStyle(Paint.Style.FILL);
        mPaintWhiteBg.setAntiAlias(true);


        mPaintGreyBg = new Paint();
        mPaintGreyBg.setColor(Color.argb(255, 240, 240, 240));
        mPaintGreyBg.setStyle(Paint.Style.FILL);
        mPaintGreyBg.setAntiAlias(true);

        mPaintLightGrey = new Paint();
        mPaintLightGrey.setColor(Color.argb(255, 250, 250, 250));
        mPaintLightGrey.setStyle(Paint.Style.FILL);
        mPaintLightGrey.setAntiAlias(true);

        dataList = new ArrayList<>();

    }

    /**
     * 得到dataList并确定一部分变量的值
     *
     * @param dataList
     */
    public void setDataList(ArrayList<StatResult> dataList) {
        this.dataList = dataList;
        dataNum = dataList.size();
        for(StatResult data:dataList){
            undealCount += data.getUnDeal();
            dealingCount += data.getDealing();
            finishCount += data.getFinsh();
            cancelCount += data.getCancel();
            totalCount += data.getTotal();
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        //根据数据数量来得到控件高
        if (dataNum != 0) {
            height = (dataNum) * tableItemHeight;
        }
        //表格单元宽
        tableItemWidth = width / 6;
        setMeasuredDimension(width, height);
    }

    private int tableItemWidth;
    private int tableItemHeight = dip2px(getContext(), 36);//表格单元高
    /**
     * 表格左上角的横纵坐标
     */
    private float startX;
    private float startY;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制统计表表第一列
//        canvas.drawRect(startX, startY, startX + tableItemWidth, startY + tableItemHeight, mPaintLightGrey);
//        canvas.drawRect(startX + tableItemWidth, startY, width, startY + tableItemHeight, mPaintGreyBg);
//        canvas.drawText("合计(" + dataNum + ")", startX + tableItemWidth / 2, startY + tableItemHeight / 2 + textSize / 2, mPaintText);
//        canvas.drawText(undealCount + "", startX + tableItemWidth * 3 / 2, startY + tableItemHeight / 2 + textSize / 2, mPaintText);
//        canvas.drawText(dealingCount + "", startX + tableItemWidth * 5 / 2, startY + tableItemHeight / 2 + textSize / 2, mPaintText);
//        canvas.drawText(finishCount + "", startX + tableItemWidth * 7 / 2, startY + tableItemHeight / 2 + textSize / 2, mPaintText);
//        canvas.drawText(cancelCount + "", startX + tableItemWidth * 9 / 2, startY + tableItemHeight / 2 + textSize / 2, mPaintText);
//        canvas.drawText(totalCount + "", startX + tableItemWidth * 11 / 2, startY + tableItemHeight / 2 + textSize / 2, mPaintText);

        for (int i = 0; i < dataNum; i++) {

            canvas.drawRect(startX, startY, startX + tableItemWidth, startY + tableItemHeight, mPaintLightGrey);
            if (i % 2 == 1) {
                canvas.drawRect(startX + tableItemWidth, startY, width, startY + tableItemHeight, mPaintGreyBg);
            } else {
                canvas.drawRect(startX + tableItemWidth, startY, width, startY + tableItemHeight, mPaintWhiteBg);
            }
            canvas.drawText(dataList.get(i).getTitle(), startX + tableItemWidth * 1 / 2, startY + tableItemHeight / 2 + textSize / 2, mPaintText);
            canvas.drawText(dataList.get(i).getUnDeal() + "", startX + tableItemWidth * 3 / 2, startY + tableItemHeight / 2 + textSize / 2, mPaintText);
            canvas.drawText(dataList.get(i).getDealing() + "", startX + tableItemWidth * 5 / 2, startY + tableItemHeight / 2 + textSize / 2, mPaintText);
            canvas.drawText(dataList.get(i).getFinsh() + "", startX + tableItemWidth * 7 / 2, startY + tableItemHeight / 2 + textSize / 2, mPaintText);
            canvas.drawText(dataList.get(i).getCancel() + "", startX + tableItemWidth * 9 / 2, startY + tableItemHeight / 2 + textSize / 2, mPaintText);
            canvas.drawText(dataList.get(i).getTotal() + "", startX + tableItemWidth * 11 / 2, startY + tableItemHeight / 2 + textSize / 2, mPaintText);
            canvas.drawLine(0, tableItemHeight * (i + 1), width, tableItemHeight * (i + 1), mPaintLine);
            startY += tableItemHeight;
        }

        startX = 0;
        startY = 0;

    }
}
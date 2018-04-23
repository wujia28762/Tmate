package com.honyum.elevatorMan.activity.workOrder;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.honyum.elevatorMan.R;
import com.honyum.elevatorMan.base.BaseActivityWraper;
import com.honyum.elevatorMan.constant.IntentConstant;
import com.honyum.elevatorMan.data.MaintenanceContenInfo;

import static android.view.View.GONE;

/**
 * Created by star on 2018/3/14.
 */


public class WorkOrderMaintResultActivity extends BaseActivityWraper {

private MaintenanceContenInfo info;
    @Override
    public String getTitleString() {
        return "处理结果查看";
    }

    @Override
    protected void initView() {

        findViewById(R.id.title_service_result).setVisibility(GONE);
        findViewById(R.id.title).setVisibility(View.VISIBLE);
        info = (MaintenanceContenInfo)getIntent().getSerializableExtra(IntentConstant.INTENT_DATA);
        if (info==null )
            return;
        TextView text = findView(R.id.tv_appearance);
        text.setText("本次维保内容");
        EditText textContent = findView(R.id.et_remark);
        textContent.setText(info.getContent());
        textContent.setFocusable(false);
        LinearLayout ll_image = findView(R.id.image_area);
        ll_image.setVisibility(GONE);
        ImageView image1 = findView(R.id.iv_image1);
        ImageView image2 = findView(R.id.iv_image2);
        ImageView image3 = findView(R.id.iv_image3);


        findView(R.id.tv_fix_complete).setVisibility(GONE);

        //LinearLayout image1View = findView(R.id.image_view1);
        LinearLayout image2View = findView(R.id.image_view2);
        LinearLayout image3View = findView(R.id.image_view3);


        String[] pics = info.getPic().split(",");
        if (info.getPicNum() == 0) {
        }
        else if (info.getPicNum() == 1)
        {
            ll_image.setVisibility(View.VISIBLE);
            image2View.setVisibility(GONE);
            image3View.setVisibility(GONE);
            if (pics.length==1)
            Glide.with(this).load(pics[0]).into(image1);
        }
        else if (info.getPicNum() == 2)
        {
            ll_image.setVisibility(View.VISIBLE);
            image3View.setVisibility(GONE);
            if (pics.length==2) {
                Glide.with(this).load(pics[0]).into(image1);
                Glide.with(this).load(pics[1]).into(image2);
            }
        }
        else if (info.getPicNum() == 3)
        {
            ll_image.setVisibility(View.VISIBLE);
            if (pics.length==3) {
                Glide.with(this).load(pics[0]).into(image1);
                Glide.with(this).load(pics[1]).into(image2);
                Glide.with(this).load(pics[2]).into(image3);
            }
        }



    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_maint_result;
    }
}

package cn.edu.gdmec.android.mobileguard.m3communicationguard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m3communicationguard.adapter.BlackContactAdapter;
import cn.edu.gdmec.android.mobileguard.m3communicationguard.db.dao.BlackNumberDao;
import cn.edu.gdmec.android.mobileguard.m3communicationguard.entity.BlackContactInfo;

/**
 * Created by as on 2017/10/30.
 */

public class SecurityPhoneActivity extends AppCompatActivity implements View.OnClickListener{
    /**有黑名单时，显示的帧布局*/
    private FrameLayout mHaveBlackNumber;
    /**没有黑名单时，显示的帧布局*/
    private FrameLayout mNoBlackNumber;
    private BlackNumberDao dao;
    private ListView mListView;
    private int pagenumber = 0;
    private int pagesize = 40;
    private int totalNumber;
    private List<BlackContactInfo> pageBlackNumber = new ArrayList<BlackContactInfo>();
    private BlackContactAdapter adapter;

        private void fillData(){
        dao = new BlackNumberDao(SecurityPhoneActivity.this);
        totalNumber = dao.getTotalNumber();
        if (totalNumber == 0){
            //数据库中没有黑名单数据
            mHaveBlackNumber.setVisibility(View.GONE);
            mNoBlackNumber.setVisibility(View.VISIBLE);
        }else if (totalNumber > 0){
            //数据库中有黑名单数据
            mHaveBlackNumber.setVisibility(View.VISIBLE);
            mNoBlackNumber.setVisibility(View.GONE);
            pagenumber = 0;
            if (pageBlackNumber.size() > 0){
                pageBlackNumber.clear();
            }
            pageBlackNumber
                    .addAll(dao.getPageBlackNumber(pagenumber,pagesize));
            for (BlackContactInfo b:pageBlackNumber) {
                Log.d("tag", "fillData: "+b.phoneNumber+"-----------------------------");
            }

            if (adapter == null){
//                adapter = new BlackContactAdapter(pageBlackNumber,SecurityPhoneActivity.this);
//                adapter.setCallBack(new BlackContactAdapter.BlackContactCallBack() {
//                    @Override
//                    public void DataSizeChanged() {
//
//                        fillData();
//                    }
//                });
//                mListView.setAdapter(adapter);
                bindAdapter();
            }else {
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void initView(){
        findViewById(R.id.rl_titlebar).setBackgroundColor(
                getResources().getColor(R.color.bright_purple));
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView)findViewById(R.id.tv_title)).setText("通讯卫士");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        mHaveBlackNumber = (FrameLayout) findViewById(R.id.fl_haveblacknumber);
        mNoBlackNumber = (FrameLayout) findViewById(R.id.fl_noblacknumber);
        findViewById(R.id.btn_addblacknumber).setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.lv_blacknumbers);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener(){

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){ //scrollState是列表滚动状态
                    //case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:  //列表滑动后静止
                    //case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL: //手指正拖着列表滑动
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:   //列表正自由滑动
                        //获取最后一个可见条目
                        int lastVisiablePosition = mListView.getLastVisiblePosition();
                        //如果当前条目是最后一个 增加查询更多数据
                        if (lastVisiablePosition == pageBlackNumber.size() - 1){
                            pagenumber ++;
                            if (pagenumber * pagesize >= totalNumber){
                                Toast.makeText(SecurityPhoneActivity.this,
                                        "没有更多的数据了", Toast.LENGTH_SHORT).show();
                            }else {
                                pageBlackNumber.addAll(dao.getPageBlackNumber(
                                        pagenumber,pagesize));
                                adapter.notifyDataSetChanged();
                            }
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_phone);
        //隐藏标题栏
        getSupportActionBar().hide();
        initView();
        fillData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
            case R.id.btn_addblacknumber:
                //跳转至添加黑名单页面
                startActivity(new Intent(this,AddBlackNumberActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dao.getTotalNumber() > 0){
            mHaveBlackNumber.setVisibility(View.VISIBLE);
            mNoBlackNumber.setVisibility(View.GONE);
        }else {
            mHaveBlackNumber.setVisibility(View.GONE);
            mNoBlackNumber.setVisibility(View.VISIBLE);
        }
        pagenumber = 0;
        pageBlackNumber.clear();
        pageBlackNumber
                .addAll(dao.getPageBlackNumber(pagenumber,pagesize));
        for (BlackContactInfo b:pageBlackNumber) {
            Log.d("tag", "onResume: "+b.phoneNumber+"-----------------------------");
        }
        if (adapter != null){
            Log.d("tag", "adapter: ----------------------------------------");
            adapter.notifyDataSetChanged();
        }else{
            Log.d("tag", "adapter: ----初始化------------------------------------");
            /*
            *  由于之前的源码的逻辑中 当没有黑名单时  选择黑名单时 并没有对适配器进行初始化  且list也没有绑定适配器  所以导致了 BUG
            *  所有在该分支判断中加入了适配器的初始化与绑定
            *
            *  考虑到代码的复用性   所以封装成了方法
            *
            *
            * */

            bindAdapter();
            adapter.notifyDataSetChanged();
        }
    }

    private void bindAdapter(){
        if (adapter == null){
            adapter = new BlackContactAdapter(pageBlackNumber,SecurityPhoneActivity.this);
            adapter.setCallBack(new BlackContactAdapter.BlackContactCallBack() {
                @Override
                public void DataSizeChanged() {
                    fillData();
                }
            });
            mListView.setAdapter(adapter);

        }
    }

}

package cn.edu.gdmec.android.mobileguard.m2theftguard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by as on 2017/9/29.
 */

public class SetupPasswordDialog extends Dialog implements View.OnClickListener{
    /*标题栏*/
    private TextView mTitleTV;
    /*首次输入密码文本框*/
    public EditText mFirstPWDET;
    /*确认密码文本框*/
    public EditText mAffirmET;
    /*接口回调*/
    private MyCallBack myCallBack;

    public SetupPasswordDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_password_dialog);
        initView();
    }

    /**初始化控件*/
    private void initView(){
        mTitleTV = (TextView) findViewById(R.id.tv_setuppwd_title);
        mFirstPWDET = (EditText) findViewById(R.id.et_firstpwd);
        mAffirmET = (EditText) findViewById(R.id.et_affirm_password);
        findViewById(R.id.et_firstpwd).setOnClickListener(this);
        findViewById(R.id.et_affirm_password).setOnClickListener(this);
    }

    /**设置对话框标题*/
    public void setTitle(String title){
        if(!TextUtils.isEmpty(title)){
            mTitleTV.setText(title);
        }
    }

    public void setCallBack(MyCallBack myCallBack){
        this.myCallBack = myCallBack;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ok:
                System.out.print("SetupPasswordDialog");
                myCallBack.ok();
                break;
            case R.id.btn_cancel:
                myCallBack.cancel();
                break;
        }
    }

    public interface MyCallBack{
        void ok();
        void cancel();
    }


}

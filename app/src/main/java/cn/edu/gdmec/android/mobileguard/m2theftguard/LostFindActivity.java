package cn.edu.gdmec.android.mobileguard.m2theftguard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import cn.edu.gdmec.android.mobileguard.R;

public class LostFindActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mStafePhoneTV;
    private RelativeLayout mInterSetupRL;
    private SharedPreferences mSharedPreferences;
    private ToggleButton mToggleButton;
    private TextView mProtecStatusTV;

    private boolean isSetup(){
        return mSharedPreferences.getBoolean("isSetup",false);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_find);
        mSharedPreferences = getSharedPreferences("config",MODE_PRIVATE);
        if(!isSetup()) {
            startSetUp1Activity();
        }
        initView();
    }
    private void initView(){
        TextView mTitleTV = (TextView)findViewById(R.id.tv_title);
        mTitleTV.setText("手机防盗");
    }
    public void startSetUp1Activity(){
        Intent intent = new Intent(LostFindActivity.this,Setup1Activity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {

    }
}

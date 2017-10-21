package cn.edu.gdmec.android.mobileguard.m2theftguard;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.edu.gdmec.android.mobileguard.R;

/**
 * Created by as on 2017/10/11.
 */

public class Setup4Activity extends BaseSetUpActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_4);
        //设置第4个小圆点的颜色
        ((RadioButton)findViewById(R.id.rb_fourth)).setChecked(true);
    }

    @Override
    public void showNext() {
        Toast.makeText(this,"完成",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPre() {
        startActivityAndFinishSelf(Setup3Activity.class);
    }
}

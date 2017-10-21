package cn.edu.gdmec.android.mobileguard;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by as on 2017/10/19.
 */


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //correctSIM();
    }

    public void correctSIM(){
        //检查SIM卡是否发生变化
        SharedPreferences sp = getSharedPreferences("config", Context.MODE_PRIVATE);
        //获取防盗保护状态
        boolean protecting = sp.getBoolean("protecting",true);
        if(protecting){
            //得到绑定的SIM卡串号
            String bindsim = sp.getString("sim","");
            //得到现在手机的SIM卡串号
            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            //为了测试在手机序列号上data 已模拟SIM卡被更换的情况
            String realsim = tm.getSimSerialNumber();
            //因为虚拟机无法更换SIM卡,所以使用虚拟机测试要有此代码，真机测试要注释这段代码
            realsim = "999";

            if (bindsim.equals(realsim)){
                Log.i("","sim卡未发生变化，还是您的手机");
            }else{
                Log.i("","SIM卡变化了");
                //由于系统版本的原因，这里的发短信可能与其他手机版本不兼容
                String safenumber = sp.getString("safephone","");
                if(!TextUtils.isEmpty(safenumber)){
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(safenumber,null,
                            "您的亲友手机的SIM卡已经被更换！", null , null);
                }
            }

        }
    }
}

package cn.edu.gdmec.android.mobileguard.m5virusscan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.gdmec.android.mobileguard.R;
import cn.edu.gdmec.android.mobileguard.m5virusscan.entity.ScanAppInfo;

/**
 * Created by Administrator on 2017/11/13.
 */

public class ScanVirusAdapter extends BaseAdapter {
    private List<ScanAppInfo> mScanAppInfos;
    private Context context;
    public ScanVirusAdapter(List<ScanAppInfo> scanAppInfo , Context context){
        super();
        mScanAppInfos = scanAppInfo;
        this.context=context;
    }
    static class ViewHoder{
        ImageView mAppIconImgv;
        TextView mAppNameTV;
        ImageView mScanIconImgv;
    }
    @Override
    public int getCount() {
        return mScanAppInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return mScanAppInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder hoder;
        if(view == null){
            view = View.inflate(context, R.layout.item_list_applock,null);
            hoder = new ViewHoder();
            hoder.mAppIconImgv = (ImageView) view.findViewById(R.id.imgv_appicon);
            hoder.mAppNameTV=(TextView) view.findViewById(R.id.tv_appname);
            hoder.mScanIconImgv =(ImageView) view.findViewById(R.id.imgv_lock);
            view.setTag(hoder);
        }
        else{
            hoder = (ViewHoder)view.getTag();
        }
        ScanAppInfo scanAppInfo = mScanAppInfos.get(i);
        if (!scanAppInfo.isVirus){
            hoder.mScanIconImgv.setBackgroundResource(R.drawable.blue_right_icon);
            hoder.mAppNameTV.setTextColor(context.getResources().getColor(R.color.black));
            hoder.mAppNameTV.setText(scanAppInfo.appName);
        }else {
            hoder.mAppNameTV.setTextColor(context.getResources().getColor(R.color.bright_red));
            hoder.mAppNameTV.setText(scanAppInfo.appName+"("+scanAppInfo.description+")");
        }
        hoder.mAppIconImgv.setImageDrawable(scanAppInfo.appicon);
        return view;
    }
}

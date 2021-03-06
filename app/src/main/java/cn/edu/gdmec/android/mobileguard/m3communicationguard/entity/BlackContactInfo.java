package cn.edu.gdmec.android.mobileguard.m3communicationguard.entity;

/**
 * Created by as on 2017/10/30.
 */

public class BlackContactInfo {
    /**黑名单号码*/
    public String phoneNumber;
    /**黑名单联系人名称*/
    public String contactName;
    /**黑名单拦截模式
     * 1为电话拦截
     * 2为短信拦截
     * 3为电话、短信都拦截
     */
    public int mode;
    /** 黑名单类型 */
    public String blackType;

    public String type;

    public String getModeString(int mode){
        switch (mode){
            case 1:
                return "电话拦截";
            case 2:
                return "短信拦截";
            case 3:
                return "电话、短信都拦截";
        }
        return "";
    }
}

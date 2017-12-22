package com.jianfeng.jianfengdriving.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.jianfeng.jianfengdriving.Myjava.Tools;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * Created by 吴剑锋 on 2017/5/7.
 *
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(this, "wxdd133ace8085c8a1", false);
        api.handleIntent(getIntent(), this);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onReq(BaseReq arg0) { }

    @Override
    public void onResp(BaseResp resp) {

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                //分享成功
                Tools.show_log("分享成功");
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //分享取消
                Tools.show_log("分享取消");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Tools.show_log("分享拒绝");
                //分享拒绝
                break;
        }
    }
}
package com.taikun.jindouzi.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taikun.jindouzi.R;
import com.taikun.jindouzi.been.User;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by A on 2016/12/12.
 */
public class SplashActivity extends BaseActivity{

//    private final String baseUrl = NetUtils.BASE_URL;
    private String token;
    private User user;
    // 跳向主页面的text
    @Bind(R.id.tv_into_main)
     TextView tv_into_main;
    // 广告图片
    @Bind(R.id.iv_bg)
     ImageView iv_bg;
    // 倒计时
    private final int TIME_DEC = 0;
    // 默认广告时间
    private int time = 3;
    private Handler hander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case TIME_DEC :
                    time--;
                    if(time<=0){
                        // 移除消息
                        removeMessages(TIME_DEC);
                        // 关闭页面
                        SplashActivity.this.finish();

                        // 跳向主页
                        gotoHome();
                    } else {
                        tv_into_main.setText("跳过 " + time + "S");
                        sendEmptyMessageDelayed(TIME_DEC, 1000);
                    }
                    break;
            }
        }
    };

    /**
     * 跳向主页面
     */
    private void gotoHome() {
        if(MainActivity.is_land) {
            Intent gotoHome = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(gotoHome);
        }
        else {
            Intent gotoLogin = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(gotoLogin);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
//        token = (String) SpUtils.get(SplashActivity.this, "secret", "");
//        if (!TextUtils.isEmpty(token)) {
//            user = SpUtils.getObject(SplashActivity.this, "user", User.class);
//        }
    }
    @Override
    protected int getContentId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this); //注入view和事件
        tv_into_main.setText("跳过 3S");
        hander.sendEmptyMessageDelayed(TIME_DEC,1000);

        tv_into_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 移除消息
                hander.removeMessages(TIME_DEC);
                // 关闭页面
                SplashActivity.this.finish();
                // 跳向主页面
                gotoHome();
            }
        });
        iv_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(SplashActivity.this, "广告时间", Toast.LENGTH_SHORT).show();
                // 移除消息
                hander.removeMessages(TIME_DEC);
//
            }
        });
        //wv_show.setVisibility(View.VISIBLE);
//                wv_show.loadUrl(baseUrl+"");
//                wv_show.getSettings().setDomStorageEnabled(true);
    }

    @Override
    protected void initData() {
    }
    @Override
    public void onBackPressed() {
        this.finish();
        gotoHome();

    }
}

package com.taikun.jindouzi.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by A on 2016/12/12.
 */
public abstract class BaseActivity extends FragmentActivity {
    public DisplayMetrics dm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // 设置activity的内容
        setContentView(getContentId());
        // 设置为没有title
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //测量控件
        dm=new DisplayMetrics();
        // 初始化布局
        initView();

        // 初始化数据
        initData();

    }
    /**
     * 获取布局文件的资源Id
     *
     * @return 布局文件的资源Id
     */
    protected abstract int getContentId();
    /**
     * 初始化View
     *
     * @return View
     */
    public abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

}
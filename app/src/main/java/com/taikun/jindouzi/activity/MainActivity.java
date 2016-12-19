package com.taikun.jindouzi.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.taikun.jindouzi.R;
import com.taikun.jindouzi.fragment.ClassificationFragment;
import com.taikun.jindouzi.fragment.HomeFragment;
import com.taikun.jindouzi.fragment.OrderFragment;
import com.taikun.jindouzi.fragment.PersonalCenterFragment;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by A on 2016/12/12.
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.fl_content)
     FrameLayout fl_content;
    @Bind(R.id.id_tab_home)
     LinearLayout llHome;
    @Bind(R.id.id_tab_classification)
    LinearLayout llClassification;
    @Bind(R.id.id_tab_order)
    LinearLayout llOrder;
    @Bind(R.id.id_tab_personalcenter)
    LinearLayout llPersonalcenter;
    @Bind(R.id.btn_tab_bottom_home)
    ImageButton btnHome;
    @Bind(R.id.btn_tab_bottom_classification)
    ImageButton btnClassification;
    @Bind(R.id.btn_tab_bottom_order)
    ImageButton btnOrder;
    @Bind(R.id.btn_tab_bottom_personalcenter)
    ImageButton btnPersonalcenter;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    //首页
    private HomeFragment mTab01;
    //分类
    private ClassificationFragment mTab02;
    //订单 详情
    private OrderFragment mTab03;
    //个人中心
    private PersonalCenterFragment mTab04;
    //是否已经登陆，默认是没有
    public static boolean is_land = false;
    private int menuPosition = -1;
    // 是否点击过返回
    private boolean isClick = false;
    // 第一次点击返回的时间
    private int firstTime = 0;
    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this); //注入view和事件
//        //设置状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        manager = getSupportFragmentManager();
        //设置默认显示的Fragment
        setTabSelection(0);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.id_tab_home:
                setTabSelection(0);
                break;
            case R.id.id_tab_classification:
                setTabSelection(1);
                break;
            case R.id.id_tab_order:
                setTabSelection(2);
                break;
            case R.id.id_tab_personalcenter:
                setTabSelection(3);
                break;
            default:
                break;
        }
    }
    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     */
    private void setTabSelection(int index)
    {
        // 重置按钮
          resetBtn();
        // 开启一个Fragment事务
        transaction = manager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index)
        {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                        btnHome.setImageResource(R.mipmap.tab_home_h);
                if (mTab01 == null)
                {
                    // 如果GaodeMapFragment为空，则创建一个并添加到界面上
                    mTab01 = new HomeFragment();
                    transaction.add(R.id.fl_content,mTab01);
                } else
                {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(mTab01);
                }
                break;
            case 1:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                        btnClassification.setImageResource(R.mipmap.tab_sys_h);
                if (mTab02 == null)
                {
                    // 如果NewsFragment为空，则创建一个并添加到界面上
                    mTab02 = new ClassificationFragment();
                    transaction.add(R.id.fl_content, mTab02);
                } else
                {
                    // 如果NewsFragment不为空，则直接将它显示出来
                    transaction.show(mTab02);
                }
                break;
            case 2:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                btnOrder.setImageResource(R.mipmap.tab_shopping_h);
                if (mTab03 == null)
                {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    mTab03 = new OrderFragment();
                    transaction.add(R.id.fl_content, mTab03);
                } else
                {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(mTab03);
                }
                break;
            case 3:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                btnPersonalcenter.setImageResource(R.mipmap.tab_shop_h);
                if (mTab04 == null)
                {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    mTab04 = new PersonalCenterFragment();
                    transaction.add(R.id.fl_content, mTab04);
                } else
                {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(mTab04);
                }
                break;
        }
        transaction.commit();
    }
    /**
     * 清除掉所有的选中状态。
     */
    private void resetBtn()
    {
               btnHome .setImageResource(R.mipmap.tab_home_n);
               btnClassification .setImageResource(R.mipmap.tab_sys_n);
               btnOrder .setImageResource(R.mipmap.tab_shopping_n);
               btnPersonalcenter.setImageResource(R.mipmap.tab_shop_n);
    }
    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction
     *            用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction)
    {
        if (mTab01 != null)
        {
            transaction.hide(mTab01);
        }
        if (mTab02 != null)
        {
            transaction.hide(mTab02);
        }
        if (mTab03 != null)
        {
            transaction.hide(mTab03);
        }
        if (mTab04!= null)
        {
            transaction.hide(mTab04);
        }

    }

    @Override
    protected void initData() {
        btnHome.setOnClickListener(this);
        btnClassification.setOnClickListener(this);
        btnOrder.setOnClickListener(this);
        btnPersonalcenter.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        is_land=true;
    }
    @Override
    public void onBackPressed() {
            // 点击两次退出
            if (isClick) {
                int secondTime = (int) System.currentTimeMillis();
                if ((secondTime - firstTime) < 2000) {
                    MainActivity.this.finish();
                    isClick = false;
                } else {
                    firstTime = (int) System.currentTimeMillis();
                    Toast.makeText(MainActivity.this, "再点一次退出", Toast.LENGTH_SHORT).show();
                    isClick = true;
                }
            } else {
                firstTime = (int) System.currentTimeMillis();
                Toast.makeText(MainActivity.this, "再点一次退出", Toast.LENGTH_SHORT).show();
                isClick = true;
            }
        }
    }




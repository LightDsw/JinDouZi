package com.taikun.jindouzi.fragment;

import android.view.View;

import com.taikun.jindouzi.R;

import butterknife.ButterKnife;

/**
 * 个人中心页面
 * Created by A on 2016/12/12.
 */
public class PersonalCenterFragment extends BaseFragment{
    private View view;
    @Override
    protected View initView() {
        view = View.inflate(context, R.layout.fragment_personalcenter, null);
        // 注册
        ButterKnife.bind(this, view);
        return view ;
    }

    @Override
    protected void initData() {

    }
}

package com.taikun.jindouzi.fragment;

import android.view.View;

import com.taikun.jindouzi.R;

import butterknife.ButterKnife;

/**
 * Created by A on 2016/12/12.
 */
public class OrderFragment extends BaseFragment {
    private View view;
    @Override
    protected View initView() {
        view = View.inflate(context, R.layout.fragment_order, null);
        // 注册
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {

    }
}

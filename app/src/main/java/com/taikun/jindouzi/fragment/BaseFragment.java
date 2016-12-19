package com.taikun.jindouzi.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by A on 2016/12/12.
 */
public abstract class BaseFragment extends Fragment {

    public Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // 获取上下文
        this.context = getActivity();
        // 初始化view
        View view = initView();
        // 初始化数据
        initData();
        return view;
    }

    /**
     * 初始化view
     * @return view
     */
    protected abstract View initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();


}

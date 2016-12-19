package com.taikun.jindouzi.fragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.taikun.jindouzi.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 分类页面
 * Created by A on 2016/12/12.
 */
public class ClassificationFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    @Bind(R.id.gv_home)
    GridView gridView;
    private View view;


    /**
     * 6个模块的图片的资源ID
     */
    private int [] imageIds = new int[]{R.mipmap.pic_touxaing,R.mipmap.pic_touxaing,
            R.mipmap.pic_touxaing,R.mipmap.pic_touxaing,R.mipmap.pic_touxaing,R.mipmap.pic_touxaing
           };

    @Override
    protected View initView() {
        view = View.inflate(context, R.layout.fragment_classification, null);
        // 注册
        ButterKnife.bind(this, view);
        adapter = new MyGridAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        return view ;
    }

    @Override
    protected void initData() {

    }
    private MyGridAdapter adapter;
    private class MyGridAdapter extends BaseAdapter {

        @Override
        /**
         * 返回 条目的个数
         */
        public int getCount() {
            return imageIds.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        /**
         * 返回指定位置的条目的样子
         * position 条目的位置
         */
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = getActivity().getLayoutInflater().inflate(R.layout.class_fication_grid_item, null);
			/*
			 * 注意，此处的 view.findViewById 前面的 view 别忘了，
			 */
            ImageView icon = (ImageView) view.findViewById(R.id.iv_icon_grid_item);

            icon.setBackgroundResource(imageIds[position]);
            return view;
        }
    }
    /**
     * 响应九宫格条目点击事件
     * @param position 就是点击的条目的位置
     */
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        switch (position) {
            case 0: // 豆芽类
                Toast.makeText(getActivity(),"豆芽类，期待吧..",Toast.LENGTH_SHORT).show();

                break;
            case 1: // 芽菜类
                Toast.makeText(getActivity(),"芽菜类，期待吧..",Toast.LENGTH_SHORT).show();

                break;
            case 2: // 面食类

                Toast.makeText(getActivity(),"面食类，期待吧..",Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getActivity(),"该功能还没开发，期待吧..",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

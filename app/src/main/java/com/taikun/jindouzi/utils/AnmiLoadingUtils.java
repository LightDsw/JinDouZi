package com.taikun.jindouzi.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import com.taikun.jindouzi.R;


/**
 * Created by A on 2016/12/17.
 */
public class AnmiLoadingUtils {

    /**
     * 使用方法：
     * 1、在自己的布局里包含动画布局（自己的布局最好用相对布局包括）
     *      <include layout="@layout/loading_anmi_layout"/>
     *
     * 2、查找控件
     *      @ViewInject(R.id.ll_loading_gif)
             private LinearLayout ll_loading_gif;
             @ViewInject(R.id.iv_loading_gif)
            private ImageView iv_loading_gif;

     *3、在需要显示的时候调用：
     *      AnmiLoadingUtils.startLoadingAnmi(context,ll_loading_gif,iv_loading_gif);
     *
     *4、在需要隐藏的时候调用：
     *      AnmiLoadingUtils.stopLoadingAnmi(ll_loading_gif,iv_loading_gif);
     */

    /**
     * 开始动画
     * @param context 上下文
     * @param parent  显示动画的ImageView的跟节点
     * @param view    显示动画的ImageView
     */
    public static void startLoadingAnmi(Context context, View parent, ImageView view){
        // 设置背景资源
        view.setBackgroundResource(R.drawable.loading_gif);
        AnimationDrawable ad = (AnimationDrawable) view.getBackground();
        if(parent!=null){
            parent.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.VISIBLE);
        }
        ad.start();
    }

    /**
     * 清除动画
     * @param parent  显示动画的ImageView的跟节点
     * @param view    显示动画的ImageView
     */
    public static void stopLoadingAnmi(View parent,ImageView view){
        view.clearAnimation();
        if(parent!=null){
            parent.setVisibility(View.GONE);
        }else{
            view.setVisibility(View.GONE);
        }
    }
}

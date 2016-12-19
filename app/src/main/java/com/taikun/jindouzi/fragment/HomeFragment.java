package com.taikun.jindouzi.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.taikun.jindouzi.R;
import com.taikun.jindouzi.utils.AnmiLoadingUtils;
import com.taikun.jindouzi.view.RollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by A on 2016/12/12.
 */
public class HomeFragment extends BaseFragment {

    private View view;
    private List<String> mPicUrls;
    // 轮播图
    @Bind(R.id.vp_home_show_pics)
    RollViewPager vp_home_show_pics;
    @Bind(R.id.gv_home_show)
    GridView gv_home_show;
    // 加载进度
    @ViewInject(R.id.ll_loading_gif)
    private LinearLayout ll_loading_gif;
    @ViewInject(R.id.iv_loading_gif)
    private ImageView iv_loading_gif;
    private int RequestCount = 0;
    private long mStartTime;

    private final int FINISH_GIF = 0;
    /**
     * 消息处理
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case FINISH_GIF:  // 关闭动画
                    AnmiLoadingUtils.stopLoadingAnmi(ll_loading_gif, iv_loading_gif);
                    break;
            }
        }
    };
    /**
     * 增加请求次数
     */
    private synchronized void addCount() {
        RequestCount++;
    }

    /**
     * 减少请求次数
     */
    private synchronized void decCount() {
        RequestCount--;
    }

    /**
     * 获取请求数量
     *
     * @return
     */
    private synchronized int getCount() {
        return RequestCount;
    }
    /**
     * 开始请求网络数据
     */
    private void startRequest() {
        if (getCount() == 0) {
            // 为了看到动画加一个时间
            mStartTime = System.currentTimeMillis();
            // 第一个请求，显示动画
            //ll_loading_gif.setVisibility(View.VISIBLE);
            AnmiLoadingUtils.startLoadingAnmi(context, ll_loading_gif, iv_loading_gif);
        }
        addCount();
    }

    /**
     * 结束请求网络数据
     */
    private void finishRequest() {
        decCount();
        if (getCount() == 0) {
            long stayTime = System.currentTimeMillis() - mStartTime;
            if (stayTime >= 2000) { // 联网时间超过2000
                // 关闭动画
                AnmiLoadingUtils.stopLoadingAnmi(ll_loading_gif, iv_loading_gif);
            } else {// 联网时间不超过2000
                handler.sendEmptyMessageDelayed(FINISH_GIF,2000-stayTime);
            }
            //ll_loading_gif.setVisibility(View.GONE);
        }

    }
    // 轮播图的条目点击事件
    private RollViewPager.OnItemClickedListener rollViewPagerClickListener = new RollViewPager.OnItemClickedListener() {
        @Override
        public void onItemClicked(int position) {
            // TODO 轮播图的条目点击事件
        }
    };
    // 轮播图的条目改变监听
    private ViewPager.OnPageChangeListener rollViewPagerChanged = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        // 发生改变
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected View initView() {
        view = View.inflate(context, R.layout.fragment_home, null);
        // 注册
        ButterKnife.bind(this, view);
        mPicUrls = new ArrayList<String>();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //开始请求网络
        startRequest();
    }

    @Override
    protected void initData() {
        //加载网络图片

        // 设置轮播图
        vp_home_show_pics.setAllPics(mPicUrls);
        vp_home_show_pics.startPlay(2000);
        // 设置vp的条目点击事件
        vp_home_show_pics.setOnItemClickedListener(rollViewPagerClickListener);
        //轮播图条目改变的监听
        vp_home_show_pics.addOnPageChangeListener(rollViewPagerChanged);
        // 初始化grideview
        setGridViewWidth(3, 100, 10, gv_home_show);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * 使Grideview单行显示且实现滑动（布局为：外层水平滑动,中层线性，内层grideview）
     *
     * @param count   要显示的数据数量
     * @param width   显示数据的宽度（dp）
     * @param spacing 数据间的间隙（dp）
     * @param view    grideview
     */
    private void setGridViewWidth(int count, int width, int spacing, GridView view) {
        // 获取手机的像素密度
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;

        // 计算grideview的宽度
        int gridViewWidth = (int) ((width + spacing) * count * density);
        // 计算条目的宽度
        int itemWidth = (int) (width * density);

        // 获取布局参数
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gridViewWidth, LinearLayout.LayoutParams.MATCH_PARENT);

        // 设置布局参数
        view.setLayoutParams(params);

        // 设置条目的宽度
        view.setColumnWidth(itemWidth);

        // 设置间隙
        view.setHorizontalSpacing(spacing);

        // 设置模式
        view.setStretchMode(GridView.NO_STRETCH);

        // 设置列数
        view.setNumColumns(count);

    }

    /**
     * 解析首页的数据（图片）
     *
     * @param resultJson 请求回来的Json字符串
     */
    private void parseViewPagerJson(String resultJson) {
        // 解析Json数据
        Gson gson = new Gson();
        // 拿到数据
//        mHomePicBean = gson.fromJson(resultJson, HomePicBean.class);

        // 获取响应
//        String response = mHomePicBean.response;

//        if ("error".equals(response)) {
//            // 请求错误
//            LogUtils.i("首页请求数据失败！");
//            Toast.makeText(context, "Sorry,服务器睡着了", Toast.LENGTH_SHORT).show();
//        } else if ("home".equals(response)) {  // 获取到正确的数据
//            // 取出图片地址和标题
//            mPicUrls = new ArrayList<String>();
//            for (int i = 0; i < mHomePicBean.home_banner.size(); i++) {
//                // 添加图片地址
//                mPicUrls.add(baseUrl + mHomePicBean.home_banner.get(i).pic);
//            }
//
//            // 输出一下
//            LogUtils.i("首页的图片地址：" + mPicUrls);
//        }
    }
}

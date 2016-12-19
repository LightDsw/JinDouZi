package com.taikun.jindouzi.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.taikun.jindouzi.R;

import java.util.List;


/**
 * Created by A on 2016/12/17.
 */
public class RollViewPager extends ViewPager {
    private static final int AUTO_PLAY_PIC = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AUTO_PLAY_PIC:
                    int index = getCurrentItem() + 1;
                    setCurrentItem(index);
                    sendEmptyMessageDelayed(AUTO_PLAY_PIC, delay);
                    break;
            }
        }
    };
    private List<String> picUrls;
    private BitmapUtils mBitmapUtils;
    private RollPicPagerAdapter mAdapter;
    private int delay = 2000;
    private OnItemClickedListener listener;
    private int mDowmTime;
    private float mDownX;
    private float mDownY;

    private boolean isSeetting = false;

    public RollViewPager(Context context) {
        super(context);
    }

    public RollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 设置所有的图片地址
     *
     * @param picUrls 所有图片地址的集合
     */
    public void setAllPics(List<String> picUrls) {
        this.picUrls = picUrls;
    }

    /**
     * 开始轮播图片
     */
    public void startPlay(int delay) {
        this.delay = delay;
        if (picUrls == null) {
            throw new RuntimeException("先调用 setAllPics(List<String> picUrls)方法设置数据源 或数据源为空");
        }
        mAdapter = new RollPicPagerAdapter();

        setAdapter(mAdapter);

        isSeetting = true;// 为了解决加载慢（发现没什么用，不去掉了，也算遇到的问题的思考）
        // 设置初始图片位置(/10000没有选择/2因为/2得到的结果非常大加载非常慢，测试大概是1000倍时候不怎么影响性能，此处设置为10000)
        int startPosition = (Integer.MAX_VALUE/1000000)-(Integer.MAX_VALUE/1000000%picUrls.size());
        setCurrentItem(startPosition,false);
        isSeetting = false;

        // 开始轮播
        handler.sendEmptyMessageDelayed(AUTO_PLAY_PIC, delay);
    }

    private class RollPicPagerAdapter extends PagerAdapter {

        private ImageView mImageView;
        @Override
        public int getCount() {
            // 不是无限轮播
            // return picUrls.size();
            // 是无限轮播
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if(isSeetting){
                return mImageView;
            }else{
                mImageView = (ImageView) View.inflate(getContext(), R.layout.roll_viewpager_item, null);
                //Toast.makeText(getContext(),imageView.toString() , Toast.LENGTH_SHORT).show();
                //显示网络图片
                if (mBitmapUtils == null) {
                    mBitmapUtils = new BitmapUtils(getContext());
                }
                // 处理当前的图片url
                position = position % picUrls.size();
                mBitmapUtils.display(mImageView, picUrls.get(position));
                container.addView(mImageView);

                return mImageView;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
    /**
     * 添加到屏幕上的时候回调
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    /**
     * 从屏幕上移除的时候回调
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //移除消息
        handler.removeCallbacksAndMessages(null);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: // 按下
                // 记录按下时间和记录按下点的坐标
                mDowmTime = (int) System.currentTimeMillis();
                mDownX = ev.getX();
                mDownY = ev.getY();

                // 停止图片轮播（移除消息）
                handler.removeMessages(AUTO_PLAY_PIC);
                break;
            case MotionEvent.ACTION_MOVE: // 移动
                // 停止图片轮播（移除消息）
                break;
            case MotionEvent.ACTION_CANCEL: // 取消
                // 继续轮播图片(防止手指从当前控件滑到其他控件时不执行UP事件，而执行CANCEl事件)
                handler.sendEmptyMessageDelayed(AUTO_PLAY_PIC, delay);
                break;
            case MotionEvent.ACTION_UP: // 抬起
                // 记录抬起时间和记录抬起点的坐标
                int UpTime = (int) System.currentTimeMillis();
                float UpX = ev.getX();
                float UpY = ev.getY();
                float disX = Math.abs(UpX - mDownX);
                float disY = Math.abs(UpY - mDownY);

                if (UpTime - mDowmTime < 400 && disX < 5 && disY < 5) {
                    if (listener != null) {
                        // 判定为单击
                        listener.onItemClicked(getCurrentItem());
                    }

                }

                // 继续轮播图片
                handler.sendEmptyMessageDelayed(AUTO_PLAY_PIC, delay);
                break;
        }
        return super.onTouchEvent(ev);
    }


    // 设置点击监听
    public interface OnItemClickedListener {
        public abstract void onItemClicked(int position);
    }
    public void setOnItemClickedListener(OnItemClickedListener listener) {
        this.listener = listener;
    }

}

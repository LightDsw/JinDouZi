package com.taikun.jindouzi.been;

import java.util.List;

/**
 * Created by A on 2016/12/14.
 */
public class HomePicBeen {
        // 响应信息
        public String response;
        // 所有的图片信息
        public List<PicInfo> home_banner;
        public class PicInfo {
            // 图片ID
            public int id;
            // 图片的url（缺少baseUrl）
            public String pic;
            // 图片的标题
            public String title;
            // 展示数据用
            @Override
            public String toString() {
                return "HomePicBean{" +
                        "id=" + id +
                        ", pic='" + pic + '\'' +
                        ", title='" + title + '\'' +
                        '}';
            }
        }


        // 外部的toString方法
        @Override
        public String toString() {
            return "HomePicBean{" +
                    "response='" + response + '\'' +
                    ", home_banner=" + home_banner +
                    '}';
        }
    }


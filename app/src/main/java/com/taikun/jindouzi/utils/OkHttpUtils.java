package com.taikun.jindouzi.utils;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by A on 2016/12/17.
 */
public class OkHttpUtils {
    private static final OkHttpClient okHttpClient = new OkHttpClient();

    /**
     * 自定义获取request请求对象
     *
     * @param urlString
     * @return
     */
    private static Request getRequestFromUrl(String urlString) {
        Request request = new Request.Builder()
                .url(urlString)
                .build();
        return request;
    }

    /**
     * 自定义获取response响应对象
     *
     * @param urlString
     * @return
     */
    private static Response getResponseFromUrl(String urlString) throws IOException {
        Request request = getRequestFromUrl(urlString);
        Response response = okHttpClient.newCall(request).execute();
        return response;
    }

    /**
     * 获取ResponseBody对象
     * @param urlString
     * @return
     * @throws IOException
     */
    private static ResponseBody getResponseBodyFromUrl(String urlString) throws IOException {
        Response response = getResponseFromUrl(urlString);
        if (response.isSuccessful()) {
            return response.body();
        }
        return null;
    }

    /**
     * 通过网络返回字符串
     * @param urlString
     * @return
     * @throws IOException
     */
    public static  String loadStringFromUrl(String urlString) throws IOException {
        ResponseBody responseBody=getResponseBodyFromUrl(urlString);
        if(responseBody!=null){
            return responseBody.string();
        }
        return null;
    }
    /**
     * 通过网络返回字符数组
     * @param urlString
     * @return
     * @throws IOException
     */
    public static byte[] loadByteFromUrl(String urlString) throws IOException {
        ResponseBody responseBody=getResponseBodyFromUrl(urlString);
        if(responseBody!=null){
            return responseBody.bytes();
        }
        return null;
    }
    /**
     * 通过网络返回输入流
     * @param urlString
     * @return
     * @throws IOException
     */
    public static InputStream loadStreamFromUrl(String urlString) throws IOException {
        ResponseBody responseBody=getResponseBodyFromUrl(urlString);
        if(responseBody!=null){
            return responseBody.byteStream();
        }
        return null;
    }
    /**
     * 开启异步线程,通过实现回调方法加载数据
     * @param urlString
     * @param callback
     */
    public  static  void loadDataByBewThread(String urlString, Callback callback){
        Request request=getRequestFromUrl(urlString);
        okHttpClient.newCall(request).enqueue(callback);
    }
    /**
     * get方式异步访问网络
     * @param urlString
     * @param callback
     */
    public static void getDataAsync(String urlString, Callback callback){
        Request request=getRequestFromUrl(urlString);
        okHttpClient.newCall(request).enqueue(callback);
    }
    //////// POST同步网络访问/////////////////////
    /**
     *
     * @param urlString
     * @param requestBody
     * @return
     */
    private static  Request buildPostRequest(String urlString,RequestBody requestBody){
        Request.Builder builder=new Request.Builder();
        builder.url(urlString).post(requestBody);
        return builder.build();
    }
    /**
     *
     * @param urlString
     * @param requestBody
     * @return
     * @throws IOException
     */
    private static String postRequestBody(String urlString, RequestBody requestBody) throws IOException {
        Request request=buildPostRequest(urlString,requestBody);
        Response response=okHttpClient.newCall(request).execute();
        if(response.isSuccessful()){
            return response.body().string();
        }
        return null;
    }
    /**
     *
     * @param map
     * @return
     */
    private static  RequestBody buildRequestBody(Map<String,String > map){
        FormEncodingBuilder builder=new FormEncodingBuilder();
        if(map!=null&&!map.isEmpty()){
            for (Map.Entry<String,String> entry:map.entrySet()){
                builder.add(entry.getKey(),entry.getValue());
            }
        }
        return builder.build();
    }

    /**
     * POST请求网络提交键值对
     * @param urlString
     * @param map
     * @return
     * @throws IOException
     */
    public static  String postKeyValuePair(String urlString, Map<String,String> map) throws IOException {
        RequestBody requestBody=buildRequestBody(map);
        return postRequestBody(urlString,requestBody);
    }
    ///////////////POST同步请求网络/////////////

    /**
     * 使用POST异步请求网络,提交requestBody对象
     * @param urlString
     * @param requestBody
     * @param callback
     */
    private static  void postRequestBodyAsync(String urlString,RequestBody requestBody,Callback callback){
        Request request=buildPostRequest(urlString,requestBody);
        okHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * POST异步请求,提交键值对
     * @param urlString
     * @param map
     * @param callback
     */
    public static void postKeyValuePairAsync(String urlString ,Map<String,String> map,Callback callback) {
        RequestBody requestBody = buildRequestBody(map);
        postRequestBodyAsync(urlString, requestBody, callback);
    }
}

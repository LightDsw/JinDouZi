package com.taikun.jindouzi.activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.taikun.jindouzi.R;
import com.taikun.jindouzi.been.User;
import com.taikun.jindouzi.utils.NetUtils;
import com.taikun.jindouzi.utils.OkHttpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
/**
 * Created by A on 2016/12/13.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private String phone;
    private String pwd;
    private Intent intent;
    private SharedPreferences preferences;
    // 是否点击过返回
    private boolean isClick = false;
    // 第一次点击返回的时间
    private int firstTime = 0;
    private Handler handler=new Handler();
    @Bind(R.id.et_input_username)
     EditText editPhone;
    @Bind(R.id.et_input_psw)
     EditText editPwd;
    @Bind(R.id.iv_forg_psw)
     ImageView forgPwd;
    @Bind(R.id.btn_login)
     Button btn_login;
    @Bind(R.id.iv_registe)
     ImageView iv_registe;
    public static User user;
    @Override
    protected int getContentId() {
        return R.layout.activity_login;

    }
    @Override
    public void initView() {
        ButterKnife.bind(this);
        preferences = getSharedPreferences("pwd", MODE_PRIVATE);
        boolean isFirst = preferences.getBoolean("isFirst",false);
        if(isFirst){//不是第一次   说明存有账号密码
            editPhone.setText(preferences.getString("mobile",""));
            editPwd.setText(preferences.getString("pswd",""));
        }
        forgPwd.setOnClickListener(this);
        iv_registe.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }
    @Override
    protected void initData() {

    }
    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_registe:
                intent=new Intent(LoginActivity.this,RegisteActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_forg_psw:
                intent=new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_login:
//                intent=new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(intent);
                phone = editPhone.getText().toString().trim();
                pwd = editPwd.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
//                }
//                  else if(!PublicmethodUtils.checkPhone(phone)){
//                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
               }else if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else {
                   Login(phone,pwd);
                }
                break;
        }
    }
    /**
     * 请求登陆
     */
    private void Login(final String phone, final String pwd){
        String  url= NetUtils.BASE_URL+"/Customer/Customer/CheckLogin";
        Map<String,String> map=new HashMap<>();
        map.put("username",phone);
        map.put("password",pwd);
            OkHttpUtils.postKeyValuePairAsync(url, map, new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                       handler.post(new Runnable() {
                           @Override
                           public void run() {
                               Toast.makeText(LoginActivity.this,"账号或者密码错误",Toast.LENGTH_SHORT).show();
                           }
                       });
                }

                @Override
                public void onResponse(final Response response) throws IOException {
                   handler.post(new Runnable() {
                       @Override
                       public void run() {
                           try {
                               Toast.makeText(LoginActivity.this,"iiiiii-----",Toast.LENGTH_SHORT).show();
                           } catch (Exception e) {
                               e.printStackTrace();
                           }
                       }
                   });
                }
            });
    }

    @Override
    public void onBackPressed() {
            // 点击两次退出
            if (isClick) {
                int secondTime = (int) System.currentTimeMillis();
                if ((secondTime - firstTime) < 2000) {
                    LoginActivity.this.finish();
                    isClick = false;
                } else {
                    firstTime = (int) System.currentTimeMillis();
                    Toast.makeText(LoginActivity.this, "再点一次退出", Toast.LENGTH_SHORT).show();
                    isClick = true;
                }
            } else {
                firstTime = (int) System.currentTimeMillis();
                Toast.makeText(LoginActivity.this, "再点一次退出", Toast.LENGTH_SHORT).show();
                isClick = true;
            }
        }


}

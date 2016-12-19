package com.taikun.jindouzi.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.taikun.jindouzi.R;
import com.taikun.jindouzi.utils.NetUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by A on 2016/12/15.
 * 找回密码界面
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.et_forg_userphone)
     EditText etPhone;
    @Bind(R.id.et_forg_erifynumber)
     EditText etErifynumber;
    @Bind(R.id.et_forg_password1)
     EditText etPassword1;
    @Bind(R.id.et_forg_password2)
     EditText etPassword2;
    @Bind(R.id.tv_forg_getCode)
     TextView getCode;
    @Bind(R.id.btn_savepassword)
     Button  btnSavepassword;
     Intent intent;

    private String phone;
    private String erifyNumber;
    private String pwd1;
    private String pwd2;
    @Override
    protected int getContentId() {
        return R.layout.activity_forgetpassword;
    }
    @Override
    public void initView() {
        ButterKnife.bind(this); //注入view和事件
        btnSavepassword.setOnClickListener(this);
    }
    @Override
    protected void initData() {
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_savepassword:
                phone =etPhone.getText().toString().trim();
                pwd1 = etPassword1.getText().toString().trim();
                pwd2 = etPassword2.getText().toString().trim();
                erifyNumber= etErifynumber.getText().toString().trim();
                if (TextUtils.isEmpty(pwd1)) {
                    Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
                } else if (pwd1.length() < 6) {
                    Toast.makeText(this, "密码应为6-20位数字或字母", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(erifyNumber)) {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.equals(pwd1, pwd2)) {
                    Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    savePassword(phone,pwd1,pwd2,erifyNumber);
                }
                break;
        }
    }
    private void savePassword(final String phone, final String pwd1, String pwd2, String erifyNumber) {
        String url = NetUtils.BASE_URL+"";
        Map<String, String> map = new HashMap<>();
        map.put("mobile",phone);
        map.put("pswd", pwd1);
        map.put("repswd", pwd2);
        map.put("validateCode", erifyNumber);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

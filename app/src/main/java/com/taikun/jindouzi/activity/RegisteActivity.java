package com.taikun.jindouzi.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taikun.jindouzi.R;
import com.taikun.jindouzi.utils.NetUtils;
import com.taikun.jindouzi.utils.PublicmethodUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by A on 2016/12/13.
 */
public class RegisteActivity extends BaseActivity  implements View.OnClickListener{
    @Bind(R.id.et__reg_userphone)
     EditText et__reg_userphone;
    @Bind(R.id.et_reg_erifynumber)
     EditText  et_reg_erifynumber;
    @Bind(R.id.et_reg_password1)
     EditText et_reg_password1;
    @Bind(R.id.et_reg_password2)
     EditText et_reg_password2;
    @Bind(R.id.tv_reg_address)
     TextView tv_reg_address;
    @Bind(R.id.tv_reg_getCode)
     TextView tv_reg_getCode;
    @Bind(R.id.btn_reg_registe)
      Button btn_reg_registe;
    private  String phone;
    private String psw1;
    private String psw2;
    private String number;
    private TimeCount count;
    private Intent intent;

    @Override
    protected int getContentId() {
        return R.layout.activity_registe;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this); //注入view和事件
        tv_reg_getCode.setOnClickListener(this);
        btn_reg_registe.setOnClickListener(this);
        /**
         * 验证手机号码
         * @param phoneNumber 手机号码
         * @return boolean
         */
//    public static boolean checkPhoneNumber(String phoneNumber){
//        Pattern pattern=Pattern.compile("^1[0-9]{10}$");
//        Matcher matcher=pattern.matcher(phoneNumber);
//        return matcher.matches();
    }

    @Override
    protected void initData() {

    }
    @Override
    public void onClick(View v) {
   switch (v.getId()){
       case R.id.tv_reg_getCode://获取验证码
           phone = et__reg_userphone.getText().toString().trim();
           if(!PublicmethodUtils.checkPhone(phone) || TextUtils.isEmpty(phone)){
               Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
           }else {
               getCheckCode(phone);
           }
           break;
       case R.id.tv_reg_address: //添加区域
          Toast.makeText(this,"功能待开放",Toast.LENGTH_SHORT).show();
           break;
       case R.id.btn_reg_registe: //注册
           phone = et__reg_userphone.getText().toString().trim();
           number = et_reg_erifynumber.getText().toString().trim();
           psw1 = et_reg_password1.getText().toString().trim();
           psw2 = et_reg_password2.getText().toString().trim();

           if (TextUtils.isEmpty(psw1)) {
               Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
           } else if (psw2.length() < 6) {
               Toast.makeText(this, "密码应为6-20位数字或字母", Toast.LENGTH_SHORT).show();
           } else if (TextUtils.isEmpty(number)) {
               Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
           }else {
               register(phone,psw1,psw2,number);
           }

           break;
      }
    }

    /**
     * 注册
     * @param phone
     * @param psw1
     * @param psw2
     * @param number
     */
    private void register(final String phone, final String psw1, String psw2, String number ) {
        String url = NetUtils.BASE_URL+"/doReister.action";
        Map<String, String> map = new HashMap<>();
        map.put("mobile",phone);
        map.put("pswd", psw1);
        map.put("repswd", psw2);
        map.put("validateCode", number);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 注册成功直接自动登陆
     * @param phone
     * @param pwd
     */
    private void login(final String phone, final String pwd) {
        String url = NetUtils.BASE_URL+"/登陆";
        Map<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("pswd", pwd);

    }
    public void getCheckCode(String phone) {
            String url = NetUtils.BASE_URL+"/获取验证码";
        Map<String, String> map = new HashMap<>();
        map.put("mobile",phone);
        map.put("codeType", String.valueOf(1));

    }
    public View makeView() {
        TextView t = new TextView(this);
        t.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        t.setGravity(Gravity.CENTER);
        t.setTextSize(14);
        t.setTextColor(getResources().getColor(R.color.tab_checked_color));
        t.setText("获取验证码");
        return t;
    }

    /*倒计时类 */
    public class TimeCount extends CountDownTimer {
        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onTick(long millisUntilFinished) {
            //计时过程中
            tv_reg_getCode.setClickable(false);
            tv_reg_getCode.setText(millisUntilFinished / 1000 + "秒");
        }
        @Override
        public void onFinish() {
            //计时完成
            tv_reg_getCode.setText("重新发送");
            tv_reg_getCode.setClickable(true);
        }
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

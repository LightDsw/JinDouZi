/**
 * 
 */
package com.taikun.jindouzi.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.EditText;

import com.taikun.jindouzi.activity.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author HAO NIE 2014-10-29上午11:36:03
 */
@SuppressLint("InflateParams")
public class PublicmethodUtils {
	private static long lastClickTime;

	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 3000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
//dp转换成pix
	public static int getPixel(int dp, Context context) {
		float density = context.getResources().getDisplayMetrics().density;
		int pixeldata = (int) (dp * density + 0.5);
		return pixeldata;
	}
	//sp转换成pix
	public static int sp2pix(int sp,Context context){
		float density = context.getResources().getDisplayMetrics().scaledDensity;
		int pixeldata = (int) (sp * density + 0.5);
		return pixeldata;
	}

	// 获取组件的高度
	public static int getCompentHeight(Context context, int height) {
		BaseActivity ba = (BaseActivity) context;
		return ba.dm.heightPixels * height / GlobalUtils.DESIGNHEIGHT;
	}

	// 获取组件的宽度
	public static int getCompentWidth(Context context, int width) {
		BaseActivity ba = (BaseActivity) context;
		return ba.dm.widthPixels * width / GlobalUtils.DESIGNWIDTH;
	}

	// 判断是否为手机号
	public static boolean checkPhone(String phone) {
		return phone.matches("^(13|15|18|14|17)\\d{9}$");
	}

	// 判断是否为emial
	public static boolean checkEmail(String emailstr) {
		Pattern p = Pattern
				.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.)+[a-z]{2,3}$");
		Matcher m = p.matcher(emailstr);
		boolean flag = m.matches();
		return flag;
	}

	public static void showDialog(final Context context, final String message) {
		Activity activity = (Activity) context;
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage(message).setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});

	}

	public static void showConfirmAndCancelDialog(final Context context,
			final String message, final DialogInterface.OnClickListener listener) {
		Activity activity = (Activity) context;
		activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage(message).setPositiveButton("确定", listener);
				builder.setMessage(message).setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});

	}

	// true表示为空
	public static boolean edittextContentWeiKong(EditText view) {
		boolean flag = false;
		if (view == null) {
			return true;
		}
		if (view.getText().toString() == null
				|| view.getText().toString().equals("")) {
			return true;
		}
		return flag;
	}

	public static String getJsongString(HashMap<String, Object> map) {
		if (map == null) {
			return null;
		}
		JSONObject jo = new JSONObject();
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			try {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it
						.next();
				String key = entry.getKey();
				// Object value = entry.getValue();
				jo.put(key, map.get(key));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}
		return jo.toString();

	}

	public static JSONObject getJson(HashMap<String, Object> map) {
		if (map == null) {
			return null;
		}
		JSONObject jo = new JSONObject();
		Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			try {
				Map.Entry<String, Object> entry = (Map.Entry<String, Object>) it
						.next();
				String key = entry.getKey();
				// Object value = entry.getValue();
				jo.put(key, map.get(key));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}
		return jo;

	}

	public static void showLogForI(String message) {
		Log.i(GlobalUtils.TAG, message);
	}


	// 进行对数计算，用于计算图片压缩比时计算2为底的对数
	static public double log(double value, double base) {
		// logx(y) =loge(y) / loge(x)，换底公式
		return Math.log(value) / Math.log(base);
	}

	// 根据得出的缩略值算出最接近的2的指数，只入不舍
	public static int getSuoFangZhi(double value) {
		double zhishu = Math.ceil(log(value, 2));
		int zhishuInt = (int) zhishu;
		return a(2, zhishuInt);
	}

	// 指数计算
	public static int a(int x, int n) {
		if (n == 0)
			return 1;
		else if (n == 1)
			return x;
		else if (isEven(n))
			return a(x * x, n / 2);
		else
			return a(x * x, (n - 1) / 2) * x;
	}

	public static boolean isEven(int n) {
		// TODO Auto-generated method stub
		if (n % 2 == 0)
			return true;
		else
			return false;
	}

	/*
	 * MD5加密 ,通用方法
	 */
	@SuppressLint("DefaultLocale")
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		// 16位加密，从第9位到25位
		// return md5StrBuff.toString().toUpperCase();
		return md5StrBuff.toString();
	}

	public static String getMyMD5Pwd(String phonenumber, String thepwd) {
		String md5_1 = getMD5Str(thepwd);
		int length = phonenumber.length();
		String newphone = phonenumber.substring(length - 6);
		PublicmethodUtils.showLogForI("newphone==" + newphone);
		String md5_2 = getMD5Str(md5_1 + newphone);
		PublicmethodUtils.showLogForI("md5_2==" + md5_2);
		return md5_2;
	}

	// 判断是否为数字
	public static boolean isShuZi(String str) {
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			if (ch < '0' || ch > '9') {
				return false;
			}
		}
		return true;
	}


	

}

package com.test.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * CommonUtils.java
 * 
 * @author zhazhaobao
 */
public class CommonUtils {

	public static String PATH = Environment.getExternalStorageDirectory()
			+ "/data/com.anju.smarthome/";

	public static CommonUtils commonUtils = null;
	/**
	 * 时间戳
	 */
	public String timestampStr = String.valueOf(System.currentTimeMillis());

	public CommonUtils() {
	}

	public static CommonUtils getInstance() {

		if (null == commonUtils) {
			commonUtils = new CommonUtils();
		}
		return commonUtils;

	}

	/**
	 * 判断是否能够取到imsi号，判断是否有sim卡
	 * 
	 * @return
	 */
	public static boolean isHaveImsi(Context c) {

		String imsi = null;
		TelephonyManager tm = null;
		try {
			tm = (TelephonyManager) c
					.getSystemService(Context.TELEPHONY_SERVICE);

			if (tm != null) {
				imsi = tm.getSubscriberId();

				int simState = tm.getSimState();

				switch (simState) {

				case TelephonyManager.SIM_STATE_ABSENT:
					// mString = "无卡";
					// do something
					imsi = null;
					break;

				case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
					// mString = "需要NetworkPIN解锁";
					// do something

					break;

				case TelephonyManager.SIM_STATE_PIN_REQUIRED:
					// mString = "需要PIN解锁";
					// do something
					break;

				case TelephonyManager.SIM_STATE_PUK_REQUIRED:
					// mString = "需要PUN解锁";
					// do something
					break;

				case TelephonyManager.SIM_STATE_READY:
					// mString = "良好";
					// do something
					break;

				case TelephonyManager.SIM_STATE_UNKNOWN:
					// mString = "未知状态";
					// do something
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (imsi == null) {
			return false;
		}
		return true;
	}

	/**
	 * 检测有没有网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetwork(Context context) {
		ConnectivityManager netManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = netManager.getActiveNetworkInfo();
		if (info != null)
			return info.isAvailable();
		else
			return false;
	}

	/**
	 * 获取存本地文件名方法
	 * 
	 * @param appName
	 * @param tail
	 *            文件名后缀 如“.mp3”
	 * @param flag
	 *            1图片 2 语音 3崩溃 默认是0
	 * @return
	 */
	@SuppressLint("SdCardPath")
    public static String getLocalFileSavePath(String appName, String tail,
                                              int flag) {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		if (!ExistSDCard()) {
			// path = Environment.getDataDirectory().getAbsolutePath() + "/";
			path = "/data/data/com.anju.smarthome/files/";
		} else {
			path += "/data/com/anju/smarthome";
		}
		if (tail.equals("jpb") || tail.endsWith("png")) {
			flag = 1;
		} else if (tail.equals("amr") || tail.endsWith("mp3")) {
			flag = 2;
		} else if (tail.equals("txt")) {
			flag = 0;
		}

		long name = System.currentTimeMillis();
		if (flag == 2) {
			path += appName + "/record";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			path += "/record-" + name + tail;
		} else if (flag == 1) {
			path += appName + "/image";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			path += "/img-" + name + tail;
		} else if (flag == 3) {
			path += appName + "/crush";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			path += "/crush-" + name + tail;
		} else {
			path += appName + "/temp";
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			path += "/temp-" + name + tail;
		}
		return path;
	}

	/**
	 * 获取下载文件默认保存路径
	 * 
	 * @return
	 */
	@SuppressLint("SdCardPath")
    public static String getDownloadFileSavePath() {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		if (!ExistSDCard()) {
			path = "/data/data/com.anju.smarthome/files/download/";
		} else {
			path += "/data/com/anju/smarthome/download/";
		}
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

		return path;
	}

	public static boolean ExistSDCard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}

	/**
	 * 获取文件默认保存路径
	 * 
	 * @return
	 */
	public static String getFileSavePath(String folderName) {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		if (TextUtils.isEmpty(path)) {
			path = Environment.getDataDirectory().getAbsolutePath() + "/";
		} else {
			path += "/smarthome/";
		}
		path += folderName + "/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}

		return path;
	}

	/**
	 * 打开键盘
	 */
	public static void changeKeyboardState(Context context) {

		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		// 得到InputMethodManager的实例
		if (imm.isActive()) {
			// 如果开启
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
					InputMethodManager.HIDE_NOT_ALWAYS);
			// 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
		}
	}

	public static void openKeyboard(Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

	}

	// 关闭软键盘
	public static void closeKeyboard(Context context, EditText editText) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		//InputMethodManager.SHOW_IMPLICIT
		imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	/**
	 * 将bitmap转换为byte[]
	 * 
	 * @param bitmap
	 * @return
	 */
	public static byte[] getBitmapByByte(Bitmap bitmap) {
		if (null == bitmap)
			return null;
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		// byte[] byteArray = baos.toByteArray();
		// return byteArray;
		return compressImage(bitmap);
	}

	/**
	 * 压缩图片
	 * 
	 * @param image
	 * @return
	 */
	public static byte[] compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		// ByteArrayInputStream isBm = new
		// ByteArrayInputStream(baos.toByteArray());//
		// 把压缩后的数据baos存放到ByteArrayInputStream中
		// Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
		// 把ByteArrayInputStream数据生成图片
		// return bitmap;
		byte[] byteArray = baos.toByteArray();
		return byteArray;
	}

	/**
	 * 经纬度度分秒转换为小数
	 * 
	 * @param du
	 * @param fen
	 * @param miao
	 * @return
	 */
	public static double convertToDecimal(double du, double fen, double miao) {
		if (du < 0)
			return -(Math.abs(du) + (Math.abs(fen) + (Math.abs(miao) / 60)) / 60);

		return Math.abs(du) + (Math.abs(fen) + (Math.abs(miao) / 60)) / 60;

	}

	/**
	 * 以字符串形式输入经纬度的转换 （37, 25, 19.222）
	 * 
	 * @param latlng
	 * @return
	 */
	public static double convertToDecimal(String latlng) {
		if (TextUtils.isEmpty(latlng)) {
			return 0;
		}
		double du = Double
				.parseDouble(latlng.substring(0, latlng.indexOf(",")));
		latlng = latlng.substring(latlng.indexOf(",") + 1, latlng.length());
		double fen = Double
				.parseDouble(latlng.substring(0, latlng.indexOf(",")));
		latlng = latlng.substring(latlng.indexOf(",") + 1, latlng.length());
		double miao = Double.parseDouble(latlng);
		if (du < 0)
			return -(Math.abs(du) + (Math.abs(fen) + (Math.abs(miao) / 60)) / 60);

		return Math.abs(du) + (Math.abs(fen) + (Math.abs(miao) / 60)) / 60;
	}

	/**
	 * 以字符串形式输入经纬度的转换（-37°25′19.222″）
	 * 
	 * @param latlng
	 * @return
	 */
	public static double convertToDecimalByString(String latlng) {

		double du = Double
				.parseDouble(latlng.substring(0, latlng.indexOf("°")));
		double fen = Double.parseDouble(latlng.substring(
				latlng.indexOf("°") + 1, latlng.indexOf("′")));
		double miao = Double.parseDouble(latlng.substring(
				latlng.indexOf("′") + 1, latlng.indexOf("″")));
		if (du < 0)
			return -(Math.abs(du) + (fen + (miao / 60)) / 60);
		return du + (fen + (miao / 60)) / 60;

	}

	/**
	 * 将小数转换为度分秒
	 * 
	 * @param num
	 * @return
	 */
	public static String convertToSexagesimal(double num) {

		int du = (int) Math.floor(Math.abs(num)); // 获取整数部分
		double temp = getdPoint(Math.abs(num)) * 60;
		int fen = (int) Math.floor(temp); // 获取整数部分
		double miao = getdPoint(temp) * 60;
		if (num < 0)
			return "-" + du + "°" + fen + "′" + miao + "″";

		return du + "°" + fen + "′" + miao + "″";

	}

	/**
	 * 获取小数部分
	 * 
	 * @param num
	 * @return
	 */
	public static double getdPoint(double num) {
		double d = num;
		int fInt = (int) d;
		BigDecimal b1 = new BigDecimal(Double.toString(d));
		BigDecimal b2 = new BigDecimal(Integer.toString(fInt));
		double dPoint = b1.subtract(b2).floatValue();
		return dPoint;
	}



	/**
	 * 获取版本名
	 * 
	 * @return 当前应用的版本号
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取版本号
	 * 
	 * @return 当前应用的版本号
	 */
	public static int getVersionCode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			int version = info.versionCode;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 判断是否有更新
	 * 
	 * @param oldPVersion
	 *            当前主版本号
	 * @param oldSVersion
	 *            当前次版本号
	 * @param newPVersion
	 *            最新主版本号
	 * @param newSVersion
	 *            最新此版本号
	 * @return
	 */
	public static boolean isNewVersion(int oldPVersion, int oldSVersion,
			int newPVersion, int newSVersion) {
		if (oldPVersion < newPVersion) {
			return true;
		} else if (oldPVersion == newPVersion) {
			if (oldSVersion <= newPVersion) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 判断是否有更新
	 * 
	 * @param oldVersion
	 *            当前版本号
	 * @param newVersion
	 *            最新版本号
	 * @return
	 */
	public static boolean isNewVersion(int oldVersion, int newVersion) {
		if (oldVersion < newVersion) {
			return true;
		}
		return false;
	}

	/**
	 * 安装软件
	 * 
	 */
	public static void inistallSoftware(Context context, String packageName) {
		String fileName = Environment.getExternalStorageDirectory() + "/"
				+ packageName;
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(fileName)),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 根据本地apk包安装应用
	 * 
	 * @param filePath
	 */
	public static void installSoftwareByAPK(Context context, String filePath) {
		Uri uri = Uri.fromFile(new File(filePath));
		Intent installIntent = new Intent(Intent.ACTION_VIEW);
		installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		installIntent.setDataAndType(uri,
				"application/vnd.android.package-archive");
		context.startActivity(installIntent);
	}

	public static String convertFileSize(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;

		if (size >= gb) {
			return String.format(Locale.getDefault(),"%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(Locale.getDefault(),f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(Locale.getDefault(),f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format(Locale.getDefault(),"%d B", size);
	}

	/**
	 * 播放ogg文件音乐
	 * 
	 * @param mContext
	 * @param soundPath
	 */
	public static void playSound(Context mContext, int soundPath) {
		final Uri soundUri = Uri.parse("android.resource://"
				+ mContext.getPackageName() + "/" + soundPath);
		if (soundUri != null) {
			final Ringtone sfx = RingtoneManager
					.getRingtone(mContext, soundUri);
			if (sfx != null) {
				sfx.setStreamType(AudioManager.STREAM_SYSTEM);
				sfx.play();
			} else {
				Log.d("playSounds", " failed to load ringtone from uri: "
						+ soundUri);
			}
		} else {
			Log.d("playSounds", " could not parse Uri: " + soundPath);
		}
	}

	/**
	 * 将0-9的数字改成01-09
	 * 
	 * @param num
	 */
	public static String getDoubleDigitNumber(int num) {
		String result = "";
		if (num >= 0 && num < 10) {
			result = "0" + String.valueOf(num);
		} else {
			result = String.valueOf(num);
		}
		return result;
	}


	/**
	 * 分享文本消息
	 * 
	 * @param activity
	 * @param content
	 */
	public static void shareApp(Activity activity, String content) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		// intent.setType("*/*");
		// intent.setType("image/*");
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
		intent.putExtra(Intent.EXTRA_TEXT, content);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		activity.startActivity(Intent.createChooser(intent, activity.getTitle()));
	}

	/**
	 * @brief 判断日期是否为同一天
	 * @details
	 */
	public static boolean isSameDate(Date date1, Date date2) {
		if(null == date1 || null == date2){
			return false;
		}
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);

		boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
				.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear
				&& cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth
				&& cal1.get(Calendar.DAY_OF_MONTH) == cal2
				.get(Calendar.DAY_OF_MONTH);
		return isSameDate;
	}

}

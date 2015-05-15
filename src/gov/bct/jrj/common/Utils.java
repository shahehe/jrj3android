package gov.bct.jrj.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;

@SuppressLint("SimpleDateFormat")
public class Utils {
	// 获取ApiKey
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
        	return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
            	apiKey = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {

        }
        return apiKey;
    }
    
    
    /**
     * 取得周几
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        String Week = "周";
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }
        return Week;
    }
    
	/**
	 * 获取当前时间点
	 * @param dateformat
	 * @return
	 */
	public static String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);
		return hehe;
	}
	
    /**
     * 格式化日期 yyyy-MM-dd kk:mm
     * @param date
     * @return
     */
    public static String formatDate(Date date){
    	if(date == null) return "None";
    	return android.text.format.DateFormat.format("yyyy-MM-dd kk:mm",date).toString();
    }
    
    /**
     * 格式化日期 yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formatDateShort(Date date){
    	if(date == null) return "None";
    	return android.text.format.DateFormat.format("yyyy-MM-dd",date).toString();
    }
    
    /**
     * 格式化日期 MM-dd
     * @param date
     * @return
     */
    public static String getMonthDate(Date date){
    	if(date == null) return "None";
    	return android.text.format.DateFormat.format("MM-dd",date).toString();
    }
    
    /**
     * 格式化日期 yyyy-MM-dd
     * @param date
     * @return
     */
    public static String getTime(Date date){
    	if(date == null) return "None";
    	if(formatDateShort(date).equals(getNowTime("yyyy-MM-dd"))){
    		return android.text.format.DateFormat.format("HH:mm",date).toString();
    	}else {
    		return android.text.format.DateFormat.format("yyyy/MM/dd",date).toString();
		}
    }
    
    /** 
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏 
     *  
     * @param v 
     * @param event 
     * @return 
     */  
    public static boolean isShouldHideInput(View v, MotionEvent event) {  
        if (v != null && (v instanceof EditText)) {  
            int[] l = { 0, 0 };  
            v.getLocationInWindow(l);  
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left  
                    + v.getWidth();  
            if (event.getX() > left && event.getX() < right  
                    && event.getY() > top && event.getY() < bottom) {  
                // 点击EditText的事件，忽略它。  
                return false;  
            } else {  
                return true;  
            }  
        }  
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点  
        return false;  
    } 
    
    /** 
     * 多种隐藏软件盘方法的其中一种 
     *  
     * @param token 
     */  
    public static void hideSoftInput(IBinder token,Context context){  
        if (token != null) {  
            InputMethodManager im = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);  
            im.hideSoftInputFromWindow(token,  
                    InputMethodManager.HIDE_NOT_ALWAYS);  
        }  
    } 
    
    /**
     * 判断系统是不是24小时制
    * @return boolean  True是24小时 
     */
	public static boolean is24(Context ctx) {
		ContentResolver cv = ctx.getContentResolver();
		String strTimeFormat = android.provider.Settings.System.getString(cv,android.provider.Settings.System.TIME_12_24);
		if (strTimeFormat != null && strTimeFormat.equals("24")) {// strTimeFormat某些rom12小时制时会返回null
			return true;
		} else{
			return false;
		}
	}

    /** 
     * 对网络连接进行判断 
     * @return  true, 网络已连接； false，未连接网络 
     */  
    public static boolean isNetworkConnected(Context context) {  
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
            if (mNetworkInfo != null) {  
                return mNetworkInfo.isAvailable();  
            }  
        }  
        return false;  
    }
    
    /** 
     * 对WIFI网络连接进行判断 
     * @return  true, WIFI已连接； false，WIFI未连接 
     */  
    public static boolean isWifiConnected(Context context) {  
        if (context != null) {  
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
            if (mWiFiNetworkInfo != null) {  
                return mWiFiNetworkInfo.isAvailable();  
            }  
        }  
        return false;
    }
    
    /** 
     * 对MOBILE网络连接进行判断 
     * @return  true, MOBILE已连接； false，MOBILE未连接 
     */ 
    public static boolean isMobileConnected(Context context) {  
        if (context != null) {  
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
            if (mMobileNetworkInfo != null) {  
                return mMobileNetworkInfo.isAvailable();  
            }  
        }  
        return false;  
    }
    
    /** 
     * 获取网络连接类型 
     * @return  
     */ 
    public static int getConnectedType(Context context) {  
        if (context != null) {  
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);  
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {  
                return mNetworkInfo.getType();
            }
        }
        return -1;  
    }
    
    /**
     * 创建数据适配器时需要转换的工具
    * @param @return MAP对象
     */
    public static Map<String, Object> createMap(String key,String label,String value){
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", key);
        map.put("label", label);
        map.put("value", value);
		return map;
	}
	
	/**
	 * 创建键值对工具
	 */
    public static Map<String, Object> createMap(String key,String value){
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", key);
        map.put("value", value);
		return map;
	}
    
	public static String dataPath = getSDCardPath()+"/hkcoupon_data/";
    
    /**
     * 截取手机屏幕
     */
    private static String SavePath;
    @SuppressWarnings("deprecation")
	public static String GetandSaveCurrentImage(WindowManager windowManager,
			View decorview) {
		// 1.构建Bitmap
		Display display = windowManager.getDefaultDisplay();
		int w = display.getWidth();
		int h = display.getHeight();
		Bitmap Bmp = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		// 2.获取屏幕
		decorview.setDrawingCacheEnabled(true);
		Bmp = decorview.getDrawingCache();
		SavePath = getSDCardPath() + "/ShareWX/ScreenImage";
		// 3.保存Bitmap
		try {
			File path = new File(SavePath);
			// 文件
			String filepath = SavePath + "/Scinan_Screen.png";
			File file = new File(filepath);
			if (!path.exists()) {
				path.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}

			FileOutputStream fos = null;
			fos = new FileOutputStream(file);
			if (null != fos) {
				Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
				fos.flush();
				fos.close();
			}
		} catch (Exception e) {
		}
		return SavePath;
	}
    
    /**
     *获取SD卡相关信息 
     * @return
     */
	private static String getSDCardPath() {
		File sdcardDir = null;
		// 判断SDCard是否存在
		boolean sdcardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		if (sdcardExist) {
			sdcardDir = Environment.getExternalStorageDirectory();
		}
		return sdcardDir.toString();
	}
	
	/**
	 * 生成MD5字符串
	 * @param plainText  需要生成MD5的字符串
	 * @return MD5字符串
	 */
	public static String Md5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
//			System.out.println("result: " + buf.toString());// 32位的加密
//			System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * String类型的集合转换为String 每个数据之间使用“,”间隔
	 * @param stringList
	 * @return
	 */
	 public static String listToString(List<String> stringList){
		 if (stringList==null) { 
			 return null;
			 }
		 StringBuilder result=new StringBuilder();
		 boolean flag=false;
		 for (String string : stringList) {
			 if (flag) {
				 result.append(",");
				 }else {
					 flag=true;
					 }
			 result.append(string);
			 }
		 return result.toString();
	}
	
	 
	/**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     * @param lng1 点1的经度
     * @param lat1 点1的纬度
     * @param lng2 点2的经度
     * @param lat2 点2的纬度
     * @return
     */
    public static String GetDistance(double lng1, double lat1, double lng2, double lat2)
    {
    	DecimalFormat df   = new DecimalFormat("######0.00");   
       double radLat1 = lat1 * Math.PI / 180.0;
       double radLat2 = lat2 * Math.PI / 180.0;
       double a = radLat1 - radLat2;
       double b = lng1 * Math.PI / 180.0 - lng2 * Math.PI / 180.0;;
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
        Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
       s = s * 6378137;
       s = Math.round(s * 10000) / 10000;
       return df.format(s/1000);
    }
	
	/**
	 * 获取WebView使用的HTML代码  没有其他任何修饰的样式
	 * @param content 内容
	 * @return
	 */
	public static String getHtml(String content){
		StringBuffer html = new StringBuffer();
		html.append("<!DOCTYPE HTML><html><body >");
		html.append(content);
		html.append("</body></html>");
		return html.toString();
	}
	
//	public static TextView getTagView(Context context,String tag){
//		TextView textView = new TextView(context);
//		textView.setText(tag);
//		textView.setTextSize(13);
//		textView.setTextColor(context.getResources().getColor(R.color.tag_color));
//        LinearLayout.LayoutParams layoutParams_txt = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams_txt.setMargins(20, 0, 0, 0);
//        textView.setLayoutParams(layoutParams_txt);
//        return textView;
//	}
	
	/**
	 * 修复图片出线内存溢出的情况
	 * @param url 图片的URL
	 * @param view 展示的控件
	 */
	public static void fixImage(String url,ImageView view){
		BitmapFactory.Options options = new BitmapFactory.Options();  
		options.inSampleSize = 2;
		Bitmap b = BitmapFactory.decodeFile(url, options);  
		if(getBitmapDegree(url)!=0){
			Bitmap bitmap = rotateBitmapByDegree(b, getBitmapDegree(url));
			view.setImageBitmap(bitmap);
		}else {
			view.setImageBitmap(b);
		}
	}
	
	/**
	 * 读取图片的旋转的角度
	 * @param path 图片绝对路径
	 * @return 图片的旋转角度
	 */
	public static int getBitmapDegree(String path) {
	    int degree = 0;
	    try {
	        // 从指定路径下读取图片，并获取其EXIF信息
	        ExifInterface exifInterface = new ExifInterface(path);
	        // 获取图片的旋转信息
	        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
	                ExifInterface.ORIENTATION_NORMAL);
	        switch (orientation) {
	        case ExifInterface.ORIENTATION_ROTATE_90:
	            degree = 90;
	            break;
	        case ExifInterface.ORIENTATION_ROTATE_180:
	            degree = 180;
	            break;
	        case ExifInterface.ORIENTATION_ROTATE_270:
	            degree = 270;
	            break;
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return degree;
	}
	
	/**
	 * 将图片按照某个角度进行旋转
	 *
	 * @param bm
	 *            需要旋转的图片
	 * @param degree
	 *            旋转角度
	 * @return 旋转后的图片
	 */
	public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
	    Bitmap returnBm = null;
	    // 根据旋转角度，生成旋转矩阵
	    Matrix matrix = new Matrix();
	    matrix.postRotate(degree);
	    try {
	        // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
	        returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
	    } catch (OutOfMemoryError e) {
	    }
	    if (returnBm == null) {
	        returnBm = bm;
	    }
	    if (bm != returnBm) {
	        bm.recycle();
	    }
	    return returnBm;
	}
	
	/**
	 * @param uri
	 * @return
	 */
    public static Bitmap decodeUriAsBitmap(Uri uri,Context context){
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
	
	/**
	 * 获取系统的语言
	 */
	public static String getLanguage(Context context){
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        String country = locale.getCountry();
//        Locale.getDefault().getLanguage()
//        Locale.getDefault().getCountry()
        if(language.equals("zh")){
        	if(country.equals("CN")){
        		return "简体";
        	}else if (country.equals("TW")){
				return "繁体";
			}
        }else if(language.equals("en")){
        	return "英语";
		}else {
			return language;
		}
        return "";
	}
	
    
	private static PopupWindow mPopupWindow;
	/**
	 * 创建侧边菜单栏
	 * @param ctx
	 */
    
	/**
	 * 显示侧边菜单栏
	 */
	public static void showMenuDialog(View parent,int gravity){
		if(mPopupWindow!=null&&mPopupWindow.isShowing()==false){
			mPopupWindow.showAtLocation(parent, gravity, 0, 0);
		}
	}
	
	/**
	 * 关闭侧边菜单栏
	 */
    public void closeProgressDialog() {
        if (mPopupWindow != null&&mPopupWindow.isShowing()==true){
        	mPopupWindow.dismiss();
//        	mPopupWindow = null;
        }
    }
    
 // session
	
 	public static synchronized boolean isSessionValiad(Context context) {
 		SharedPreferences prefs = context.getSharedPreferences(
 				Constants.KEY_SESSION_PREFS, 0);
 		return prefs.getBoolean(Constants.KEY_IS_LOGINED, false);
 	}

 	public static String getCurrentUser(Context context) {
 		SharedPreferences prefs = context.getSharedPreferences(
 				Constants.KEY_SESSION_PREFS, 0);
 		return prefs.getString(Constants.KEY_USER_NAME, "");
 	}

 	public static interface ILoginListener {
 		boolean onLoginSuccess(Context context,JSONObject data);
 		void onLoginFail();
 	}

 	public static interface ILogoutListener {
 		boolean onLogout(Context context);
 	}
 	
 	public static void sendMessage(Handler handler, int code) {
 		handler.sendEmptyMessage(code);
 	}
 	
 	 public static final String TAG = "PushDemoActivity";
     public static final String RESPONSE_METHOD = "method";
     public static final String RESPONSE_CONTENT = "content";
     public static final String RESPONSE_ERRCODE = "errcode";
     protected static final String ACTION_LOGIN = "com.baidu.pushdemo.action.LOGIN";
     public static final String ACTION_MESSAGE = "com.baiud.pushdemo.action.MESSAGE";
     public static final String ACTION_RESPONSE = "bccsclient.action.RESPONSE";
     public static final String ACTION_SHOW_MESSAGE = "bccsclient.action.SHOW_MESSAGE";
     protected static final String EXTRA_ACCESS_TOKEN = "access_token";
     public static final String EXTRA_MESSAGE = "message";

     public static String logStringCache = "";

    

     // 用share preference来实现是否绑定的开关。在ionBind且成功时设置true，unBind且成功时设置false
     public static boolean hasBind(Context context) {
         SharedPreferences sp = PreferenceManager
                 .getDefaultSharedPreferences(context);
         String flag = sp.getString("bind_flag", "");
         if ("ok".equalsIgnoreCase(flag)) {
             return true;
         }
         return false;
     }

     public static void setBind(Context context, boolean flag) {
         String flagStr = "not";
         if (flag) {
             flagStr = "ok";
         }
         SharedPreferences sp = PreferenceManager
                 .getDefaultSharedPreferences(context);
         Editor editor = sp.edit();
         editor.putString("bind_flag", flagStr);
         editor.commit();
     }

     public static List<String> getTagsList(String originalText) {
         if (originalText == null || originalText.equals("")) {
             return null;
         }
         List<String> tags = new ArrayList<String>();
         int indexOfComma = originalText.indexOf(',');
         String tag;
         while (indexOfComma != -1) {
             tag = originalText.substring(0, indexOfComma);
             tags.add(tag);

             originalText = originalText.substring(indexOfComma + 1);
             indexOfComma = originalText.indexOf(',');
         }

         tags.add(originalText);
         return tags;
     }

     public static String getLogText(Context context) {
         SharedPreferences sp = PreferenceManager
                 .getDefaultSharedPreferences(context);
         return sp.getString("log_text", "");
     }

     public static void setLogText(Context context, String text) {
         SharedPreferences sp = PreferenceManager
                 .getDefaultSharedPreferences(context);
         Editor editor = sp.edit();
         editor.putString("log_text", text);
         editor.commit();
     }
    
}

package gov.bct.jrj;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import gov.jrj.R;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.loopj.android.http.AsyncHttpResponseHandler;

import gov.bct.jrj.activity.BicycleActvity;
import gov.bct.jrj.activity.StartupDetailsActivity;
import gov.bct.jrj.common.BctidAPI;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.Constants;
import gov.bct.jrj.common.PushService;
import gov.bct.jrj.common.StartupDialog;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.common.WizardAlertDialog;
import gov.bct.jrj.fragment.TabFragment;
import gov.bct.jrj.library.http.AsyncHttpClient;
import gov.bct.jrj.library.http.JsonHttpResponseHandler;
import gov.bct.jrj.library.http.RequestParams;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;

public class MainActivity extends FragmentActivity {

	private StartupDialog dialog;
	private boolean nc = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY, Utils.getMetaValue(this, "com.baidu.lbsapi.API_KEY"));

		BctidAPI.getInstance().init(this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// 生成主页面下的四个小标签
		TabFragment tf = new TabFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.pf_main_bottom_fragment_layout, tf);
		transaction.addToBackStack(null);
		transaction.commit();

		// Push DeviceID
		String mDeviceID = Secure.getString(this.getContentResolver(),
				Secure.ANDROID_ID);
		Editor editor = getSharedPreferences(PushService.TAG, MODE_PRIVATE)
				.edit();
		editor.putString(PushService.PREF_DEVICE_ID, mDeviceID);
		editor.commit();
		nc = getIntent().getBooleanExtra("nc", true);
		boolean isAutoPush = getSharedPreferences(PushService.TAG, MODE_PRIVATE)
				.getBoolean("isAutoPush", false);
		int uid = getSharedPreferences(Constants.KEY_SESSION_PREFS,
				MODE_PRIVATE).getInt(Constants.KEY_UID, 0);
		if (isAutoPush && uid != 0) {
			boolean isStarted = getSharedPreferences(PushService.TAG,
					android.content.Context.MODE_PRIVATE).getBoolean(
					"isStarted", false);
			if (!isStarted) {
				if (!PushService.isServiceRunning(this)) {
					PushService.actionStart(getApplicationContext());
					System.out.print("start");
				}
			}
		} else {
			try {
				Log.d("push", "stop service");
				PushService.actionStop(getApplicationContext());
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.print("stop");
		}
		if (uid == 0 && !mDeviceID.equals("")) {
			if (!PushService.isServiceRunning(this)) {
				PushService.actionStart(getApplicationContext());
				System.out.print("start");
			}
		}

		// 检查服务器是否正常
		/*
		 * if(Config.isConnected(this)){ Toast.makeText(MainActivity.this, "h1",
		 * Toast.LENGTH_SHORT).show(); if
		 * (!Config.isHttpAvail("http://64.150.161.193") ) { if (
		 * !Config.isHttpAvail("http://64.150.161.193") ) { Config.httpOK =
		 * false; Toast.makeText(MainActivity.this, "h3",
		 * Toast.LENGTH_SHORT).show(); } else {
		 * Toast.makeText(MainActivity.this, "h4", Toast.LENGTH_SHORT).show();
		 * Config.URL = Config.URLBackup; } } else
		 * Toast.makeText(MainActivity.this, "h2", Toast.LENGTH_SHORT).show(); }
		 * else { Config.httpOK = false; Toast.makeText(MainActivity.this, "h5",
		 * Toast.LENGTH_SHORT).show(); }
		 */

		if (Config.isConnected(this) && nc) {
			AsyncHttpClient clientDevice = new AsyncHttpClient();
			RequestParams deviceIDPara = new RequestParams();
			deviceIDPara.put("deviceid", mDeviceID);
			clientDevice.post(Config.URL + "/device.php", deviceIDPara,
					new JsonHttpResponseHandler() {
					});

			// AsyncHttpClient client = new AsyncHttpClient();
			// client.get(Config.URL_new + "/rest/article/83",
			// new JsonHttpResponseHandler() {
			// @Override
			// public void onSuccess(JSONObject response) {
			// // try {
			// //
			// // if (response.getInt("status") == 1) {
			// // //
			// // PushService.actionStart(getApplicationContext());
			// // // Toast.makeText(MainActivity.this,
			// // // "消息推送服务器正常", Toast.LENGTH_SHORT).show();
			// // } else {
			// // // Toast.makeText(MainActivity.this,
			// // // "消息推送服务器无法访问",
			// // // Toast.LENGTH_SHORT).show();
			// // }
			// // if (response.getString("version")
			// // .equalsIgnoreCase(getVersionName())) {
			// // // Toast.makeText(MainActivity.this,
			// // // "no update needed " +
			// // // response.getString("url") + "\t" +
			// // // response.getString("version") ,
			// // // Toast.LENGTH_SHORT).show();
			// // } else {
			// // // Toast.makeText(MainActivity.this,
			// // // "update needed " +
			// // // response.getString("url") + "\t" +
			// // // response.getString("version") ,
			// // // Toast.LENGTH_SHORT).show();
			// // // showUpdataDialog(response.getString("url"));
			// //
			// // }
			// // } catch (Exception ex) {
			// // Toast.makeText(MainActivity.this,
			// // "消息推送服务器无法访问", Toast.LENGTH_SHORT).show();
			// // }
			// }
			//
			// @Override
			// public void onFailure(Throwable error) {
			// Toast.makeText(MainActivity.this, "消息推送服务器无法访问",
			// Toast.LENGTH_SHORT).show();
			// }
			// });

			// 飘窗数据:http://demo.bctid.com/jrj-web/public/rest/article/83
			AsyncHttpClient startMessage = new AsyncHttpClient();
			startMessage.get(Config.URL_new + "/rest/suspending",
					new JsonHttpResponseHandler() {

						@Override
						public void onSuccess(JSONArray response) {
							// TODO Auto-generated method stub
							super.onSuccess(response);
							try {
								// int id = response.getInt("id");
								// SharedPreferences sp = getSharedPreferences(
								// "startupImage", MODE_PRIVATE);
								// int currId = sp.getInt("id", -1);
								// boolean isShown = sp
								// .getBoolean("isShown", true);
								//
								// if (currId < id) {
								// startupMessage(response);
								// Config.clearCacheData(MainActivity.this,
								// response.getString("html"));
								// Editor editor = sp.edit();
								// editor.putBoolean("isShown", true);
								// editor.putInt("id", response.getInt("id"));
								// editor.commit();
								// } else if (isShown) {
								// startupMessage(response);
								// }

								SharedPreferences sp = getSharedPreferences(
										"startupImage", MODE_PRIVATE);
								boolean isShown = sp
										.getBoolean("isShown", true);
								if (isShown) {
									startupMessage(response);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
		}

		/** 版本监测 */
		BctidAPI.get("/rest/update",
				new com.loopj.android.http.JsonHttpResponseHandler(){

					@Override
					public void onSuccess(int statusCode, JSONObject response) {
						try {
							String version=response.getString("version");
							System.out.println("-----version------>"+version);
							if(version!=null&&!"null".equals(version)&&!"".equals(version)){
								System.out.println("--getVersionName-->"+getVersionName());
								System.out.println("--url-->"+response.getString("url"));
								if(!version.equalsIgnoreCase(getVersionName())){
									showUpdataDialog(response.getString("url"));
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					
			
		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			dialog();
			return false;
		}
		return false;
	}

	private void startupMessage(final JSONArray array) {
		try {
			JSONObject response = array.getJSONObject(0);
			StartupDialog.Builder customBuilder = new StartupDialog.Builder(
					MainActivity.this);
			final String content = response.getString("content");
			final String title = response.getString("title");
			final String imagePath = response.getString("image");
			customBuilder.setTitle(title).setMessage(
					response.getString("description"));

			// ImageView startImage = new ImageView(this);
			if (imagePath != null && !imagePath.equals("")
					&& !imagePath.equals("null")) {
				// StartImageLoader.setValue(dialog, customBuilder);
				// StartImageLoader.loadImage(this, startImage, url);
				customBuilder.setImageView(imagePath);

			}
			customBuilder.setPositiveButton("查看详情",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface arg0, int arg1) {

							Bundle bundle = new Bundle();
							Intent intent = new Intent();
							bundle.putString("content", content);
							bundle.putString("title", title);
							bundle.putString("image", imagePath);
							intent.putExtras(bundle);
							intent.setClass(MainActivity.this,
									StartupDetailsActivity.class);
							startActivity(intent);
						}

					});

			customBuilder.setNoMoreButton("不再显示",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							SharedPreferences sp = getSharedPreferences(
									"startupImage", MODE_PRIVATE);
							Editor editor = sp.edit();
							editor.putBoolean("isShown", false);
							editor.commit();
							dialog.dismiss();
						}

					});
			customBuilder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			customBuilder.create().show();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void dialog() {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setMessage("确定要退出吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						MainActivity.this.finish();
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	private String getVersionName() throws Exception {

		PackageManager packageManager = getPackageManager();

		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),
				0);
		return packInfo.versionName;
	}

	protected void showUpdataDialog(final String downloadUrl) {
		AlertDialog.Builder builer = new Builder(this);
		builer.setTitle("版本升级");
		builer.setMessage("更多惊喜");
		// 当点确定按钮时从服务器上下载 新的apk 然后安装
		builer.setPositiveButton("确定", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				downLoadApk(downloadUrl);
			}
		});
		// 当点取消按钮时进行登录
		builer.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				// LoginMain();
			}
		});
		AlertDialog dialog = builer.create();
		dialog.show();
	}

	protected void downLoadApk(final String downloadUrl) {
		final ProgressDialog pd;
		pd = new ProgressDialog(MainActivity.this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载更新");
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			Message msg = new Message();
		} else {
			pd.show();
			new Thread() {
				@Override
				public void run() {
					try {
						File file = getFileFromServer(downloadUrl, pd);
						sleep(1000);
						installApk(file);
						pd.dismiss();

					} catch (Exception e) {
						Message msg = new Message();
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

	protected void installApk(File file) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	public static File getFileFromServer(String path, ProgressDialog pd)
			throws Exception {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);

			pd.setMax(conn.getContentLength());
			InputStream is = conn.getInputStream();
			File file = new File(Environment.getExternalStorageDirectory(),
					"updata.apk");
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				pd.setProgress(total);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			return null;
		}
	}

}

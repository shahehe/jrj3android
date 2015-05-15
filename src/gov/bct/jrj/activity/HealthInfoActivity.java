package gov.bct.jrj.activity;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import gov.bct.jrj.R;
import gov.bct.jrj.common.Alerts;
import gov.bct.jrj.common.BctidAPI;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.MyWebViewClient;
import gov.bct.jrj.common.Session;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.common.WizardAlertDialog;
import gov.bct.jrj.pojo.HealthInfo;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HealthInfoActivity extends Activity {

	private Button btnBack;
	private TextView mTextTitle;
	private WebView webView;
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_activity);

		btnBack = (Button) findViewById(R.id.about_back_btn);
		
		webView = (WebView) findViewById(R.id.webView);
		mTextTitle = (TextView) findViewById(R.id.txt_title);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				HealthInfoActivity.this.finish();
			}
		});

		Bundle bundle = getIntent().getExtras();
		id = bundle.getInt("id");
		String title = bundle.getString("title");
		mTextTitle.setText(title);
		
		webView.setWebViewClient(new MyWebViewClient());

		/** 加载数据 */
		loadData();
	}

	/**
	 * 初始化数据 先从本地缓存读取数据，如果没有读取到再从网络上获取
	 */
	private void loadData() {
		String url = Utils.getMetaValue(HealthInfoActivity.this, "base_url")
				+ "/rest/article-list/" + id;

		// 读取缓存
		String data = Config.getCacheData(this, url);
		if (data == null) {
			// 读取本地
			// this.getData(url);
			// data = Config.readRawData(this,R.raw.list);
			data = Config.getCacheData(this, url);

			if (data == null) {
				// data = Config.getCacheData(this, url);
				getData();

			} else {
				try {
					this.processData(new JSONObject(data));
					if (!Session.getInstance().isCheckedImageUpdate()) {
						this.checkUpdate(url, data);
					}
				} catch (Exception ex) {
					this.getData();
				}
			}
		} else {
			try {
				this.processData(new JSONObject(data));
				if (!Session.getInstance().isCheckedUpdate()) {
					this.checkUpdate(url, data);
				}
			} catch (Exception ex) {
				this.getData();
			}

		}
	}

	public void processData(JSONObject json) {
		try {
			JSONArray array = json.getJSONArray("items");
			HealthInfo healthInfo = new HealthInfo(array.getJSONObject(array.length()-1));
			StringBuffer html = new StringBuffer();
			html.append("<!DOCTYPE HTML><html><body>");
			html.append(healthInfo.getContent()+"<br/>");
			html.append("</body></html>");
			webView.loadDataWithBaseURL(null, html.toString(), "text/html", "utf-8", "");
		} catch (JSONException e) {
			Alerts.show(e.getMessage(), HealthInfoActivity.this);
		}
	}

	/**
	 * 检查更新
	 * 
	 * @param url
	 * @param data
	 */
	public void checkUpdate(final String url, final String data) {
		if (Config.isConnected(this)) {
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(url, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray response) {
					try {
						JSONObject json = new JSONObject();
						json.put("items", response);
						String md5 = Config.getMD5(json.toString());
						String dataMd5 = Config.getMD5(data);
						if (md5.equals(dataMd5)) {
							Toast.makeText(HealthInfoActivity.this, "已经是最新了",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(HealthInfoActivity.this, "图册已经更新",
									Toast.LENGTH_SHORT).show();
							Config.setCacheData(HealthInfoActivity.this, url,
									json.toString());
							processData(json);
						}
						Session.getInstance().setCheckedImageUpdate(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				// @Override
				// public void onSuccess(JSONObject json) {
				// String md5 = Config.getMD5(json.toString());
				// String dataMd5 = Config.getMD5(data);
				// if(md5.equals(dataMd5)){
				// Toast.makeText(YuXiangActivity.this, "已经是最新了",
				// Toast.LENGTH_SHORT).show();
				// }else{
				// Toast.makeText(YuXiangActivity.this, "图册已经更新",
				// Toast.LENGTH_SHORT).show();
				// Config.setCacheData(YuXiangActivity.this, url,
				// json.toString());
				// processData(json);
				// }
				// Session.getInstance().setCheckedImageUpdate(true);
				// }
			});
		}
	}

	/**
	 * 获取事项列表
	 */
	private void getData() {
		BctidAPI.get("/rest/article-list/" + id, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				try {
					/*--------设置缓存数据--------*/
					JSONObject object = new JSONObject();
					object.put("items", response);
					Config.setCacheData(
							HealthInfoActivity.this,
							Utils.getMetaValue(HealthInfoActivity.this,
									"base_url") + "/rest/article-list/" + id,
							object.toString());
					processData(object);
				} catch (Exception ex) {
					WizardAlertDialog.showErrorDialog(HealthInfoActivity.this,
							ex.getMessage(), R.string.btn_ok);
				}
			}

			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				WizardAlertDialog.showErrorDialog(HealthInfoActivity.this,
						e.getMessage(), R.string.btn_ok);
			}

			@Override
			public void onFinish() {
				WizardAlertDialog.getInstance().closeProgressDialog();
			}

			@Override
			public void onStart() {
				WizardAlertDialog.getInstance().showProgressDialog(
						R.string.add_data, HealthInfoActivity.this);
			}
		});
	}
}

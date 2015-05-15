package gov.bct.jrj.food.fragment;


import org.json.JSONArray;
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
import gov.bct.jrj.pojo.FoodIntro;
import gov.bct.jrj.pojo.MessageList;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 
 * @author ouzehua 食药安全>简介
 *
 */
public class FoodIntroFragment extends Fragment {

	private WebView webView;
	private FoodIntro foodIntro;
	private Context mContext;
	private ImageView callBtn;//打电话
	View v;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getActivity();		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.intro_fragment, container, false);
		callBtn=(ImageView)v.findViewById(R.id.phoneBtn);
		webView = (WebView) v.findViewById(R.id.webView);
		webView.setWebViewClient(new MyWebViewClient());
		loadData();
		return v;
	}

	/**
	 * 初始化数据 先从本地缓存读取数据，如果没有读取到再从网络上获取
	 */
	private void loadData() {
		String url = Utils.getMetaValue(mContext, "base_url")+ "/rest/medicinal/90";
		// 读取缓存
		String data = Config.getCacheData(mContext, url);
		if (data == null) {
			data = Config.getCacheData(mContext, url);

			if (data == null) {
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
			foodIntro = new FoodIntro(json);
			StringBuffer html = new StringBuffer();
			html.append("<!DOCTYPE HTML><html><body>");
			html.append(foodIntro.getContent()+"<br/>");
			html.append("</body></html>");
			webView.loadDataWithBaseURL(null, html.toString(), "text/html", "utf-8", "");
			callBtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(foodIntro.getPhoneNum()!=null){
						 //用intent启动拨打电话  
		                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+foodIntro.getPhoneNum()));  
		                startActivity(intent);
					}
				}
			});
		} catch (Exception e) {
			Alerts.show(e.getMessage(), mContext);
		}
	}

	/**
	 * 检查更新
	 * 
	 * @param url
	 * @param data
	 */
	public void checkUpdate(final String url, final String data) {
		if (Config.isConnected(mContext)) {
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(url, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, JSONObject response) {
					try {
						String md5 = Config.getMD5(response.toString());
						String dataMd5 = Config.getMD5(data);
						if (md5.equals(dataMd5)) {
							Toast.makeText(mContext, "已经是最新了",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(mContext, "图册已经更新",
									Toast.LENGTH_SHORT).show();
							Config.setCacheData(mContext, url, response.toString());
							processData(response);
						}
						Session.getInstance().setCheckedImageUpdate(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			});
		}
	}

	/**
	 * 获取列表
	 */
	private void getData() {
		BctidAPI.get("/rest/medicinal/90", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				Config.setCacheData(mContext,Utils.getMetaValue(mContext, "base_url")+"/rest/medicinal/90", response.toString());
				processData(response);
				
			}

			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				WizardAlertDialog.showErrorDialog(mContext,e.getMessage(), R.string.btn_ok);
			}

			@Override
			public void onFinish() {
				WizardAlertDialog.getInstance().closeProgressDialog();
			}

			@Override
			public void onStart() {
				WizardAlertDialog.getInstance().showProgressDialog(
						R.string.add_data, mContext);
			}
		});
	}
}

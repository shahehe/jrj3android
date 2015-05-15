package gov.bct.jrj.medical.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import gov.bct.jrj.R;
import gov.bct.jrj.adapter.ConsultAdapter;
import gov.bct.jrj.common.Alerts;
import gov.bct.jrj.common.BctidAPI;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.Session;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.common.WizardAlertDialog;
import gov.bct.jrj.pojo.Consult;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
 * @author 欧泽华 社会服务>社区医疗 页面里的专家会诊页面
 */
public class ConsultFragment extends Fragment {

	private ListView lv;
	private List<Consult> mListItem;
	private ConsultAdapter mAdapter;
	private Context context;

	private WebView wv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		context = getActivity();	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.consultfragment, container, false);
//		lv = (ListView) view.findViewById(R.id.list);
//		loadData();
		wv=(WebView)view.findViewById(R.id.webview);
//		String url = Utils.getMetaValue(context, "base_url")+"/doctor";
		String url="http://demo.bctid.com/jrj-web/public/doctor";
		wv.loadUrl(url);
		wv.getSettings().setJavaScriptEnabled(true); 
		return view;
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {  
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {  
            wv.goBack(); //goBack()表示返回WebView的上一页面  
            return true;  
        }  
        return false;  
} 

	/**
	 * 初始化数据
	 * 先从本地缓存读取数据，如果没有读取到再从网络上获取
	 */
	private void loadData() {
		String url = Utils.getMetaValue(context, "base_url")+"/rest/specialist";
		//读取缓存
		String data = Config.getCacheData(context, url);
		if(data == null){
			//读取本地
//			this.getData(url);
//			data = Config.readRawData(this,R.raw.list);
			data = Config.getCacheData(context, url);
			
			if(data == null){
//				data = Config.getCacheData(this, url);
				getData();

			}else{
				try{
					this.processData(new JSONArray(data));
					if(!Session.getInstance().isCheckedImageUpdate()){
						this.checkUpdate(url, data);
					}
				}catch(Exception ex){
					this.getData();
				}
			}
		}else{
			try{
				this.processData(new JSONArray(data));
				if(!Session.getInstance().isCheckedUpdate()){
					this.checkUpdate(url, data);
				}
			}catch(Exception ex){
				this.getData();
			}
			
		}
	}

	public void processData(JSONArray json) {
		try {
			if (mAdapter == null) {
				if (mListItem == null) {
					mListItem = new ArrayList<Consult>();
					mListItem.clear();
					for (int i = 0; i < json.length(); i++) {
						Consult consult = new Consult(json.getJSONObject(i));
						mListItem.add(consult);
					}
				}
				mAdapter = new ConsultAdapter(context, mListItem);
				lv.setAdapter(mAdapter);
			}
		} catch (JSONException e) {
			Alerts.show(e.getMessage(), context);
		}
	}

	/**
	 * 获取宇翔图册列表
	 */
	private void getData() {
		BctidAPI.get("/rest/specialist", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				try {
				
//					for (int i = 0; i < response.length(); i++) {
//						Consult consult = new Consult(response.getJSONObject(i));
//						mListItem.add(consult);
//					}
					/*--------设置缓存数据--------*/
					Config.setCacheData(context,
							Utils.getMetaValue(context, "base_url")
									+ "/rest/specialist", response.toString());
					processData(response);
					/*-------------------*/
					// if(mListItem.size()==0){
					// Toast.makeText(YuXiangActivity.this, R.string.err_data,
					// Toast.LENGTH_SHORT).show();
					// }
					// mAdapter.notifyDataSetChanged();
				} catch (Exception ex) {
					WizardAlertDialog.showErrorDialog(context, ex.getMessage(),
							R.string.btn_ok);
				}
			}

			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				WizardAlertDialog.showErrorDialog(context, e.getMessage(),
						R.string.btn_ok);
			}

			@Override
			public void onFinish() {
				WizardAlertDialog.getInstance().closeProgressDialog();
			}

			@Override
			public void onStart() {
				WizardAlertDialog.getInstance().showProgressDialog(
						R.string.add_data, context);
			}
		});
	}

	/**
	 * 检查更新
	 * 
	 * @param url
	 * @param data
	 */
	public void checkUpdate(final String url, final String data) {
		if (Config.isConnected(context)) {
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
							Toast.makeText(context, "已经是最新了",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(context, "图册已经更新",
									Toast.LENGTH_SHORT).show();
							Config.setCacheData(context, url, json.toString());
							processData(response);
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
}

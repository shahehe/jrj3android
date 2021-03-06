package gov.bct.jrj.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import gov.jrj.R;
import gov.bct.jrj.adapter.ServiceInfoAdapter;
import gov.bct.jrj.common.Alerts;
import gov.bct.jrj.common.BctidAPI;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.Session;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.common.WizardAlertDialog;
import gov.bct.jrj.pojo.ServiceInfo;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author ouzehua
 * 服务事项页面
 *
 */
public class ServiceInfoActivity extends Activity {

	
	private Button backButton;
	private ListView listView;
	private List<ServiceInfo> mListItem;
	private ServiceInfoAdapter mAdapter;
	private TextView textTitle;
	
	private String title="服务信息>办事程序";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		listView = (ListView) findViewById(R.id.list);
		textTitle=(TextView)findViewById(R.id.txt_title);
		textTitle.setText(title);
		this.backButton = (Button) findViewById(R.id.backBtn);
		this.backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ServiceInfoActivity.this.finish();
			}
		});
		
		/**加载数据*/
		loadData();
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				String sub_title=mListItem.get(arg2).getName();
				String type=mListItem.get(arg2).getType();
				int id=mListItem.get(arg2).getId();
				
				if("article".equals(type)){
					Intent intent = new Intent(ServiceInfoActivity.this,
							HealthInfoActivity.class);
					intent.putExtra("sub_title", sub_title);
					intent.putExtra("id", id);
					startActivity(intent);
				}
				
				if("list".equals(type)){
					Intent intent = new Intent(ServiceInfoActivity.this,
							ServiceInfoActivity2.class);
					intent.putExtra("title", "服务事项>办事程序");
					intent.putExtra("id", id);
					startActivity(intent);
				}
			}
		});
	}
		

		/**
		 * 初始化数据
		 * 先从本地缓存读取数据，如果没有读取到再从网络上获取
		 */
		private void loadData() {
			String url = Utils.getMetaValue(ServiceInfoActivity.this, "base_url")+"/rest/category-list/6";
			//读取缓存
			String data = Config.getCacheData(this, url);
			if(data == null){
				//读取本地
//				this.getData(url);
//				data = Config.readRawData(this,R.raw.list);
				data = Config.getCacheData(this, url);
				
				if(data == null){
//					data = Config.getCacheData(this, url);
					getData();

				}else{
					try{
						this.processData(new JSONObject(data));
						if(!Session.getInstance().isCheckedImageUpdate()){
							this.checkUpdate(url, data);
						}
					}catch(Exception ex){
						this.getData();
					}
				}
			}else{
				try{
					this.processData(new JSONObject(data));
					if(!Session.getInstance().isCheckedUpdate()){
						this.checkUpdate(url, data);
					}
				}catch(Exception ex){
					this.getData();
				}
				
			}
		}
		
		public void processData(JSONObject json){
			try {
				JSONArray array = json.getJSONArray("items");
				if(mAdapter==null){
					if(mListItem==null){
						mListItem = new ArrayList<ServiceInfo>();
						mListItem.clear();
						for (int i = 0; i < array.length(); i++) {
							ServiceInfo serviceInfo = new ServiceInfo(array.getJSONObject(i));
							mListItem.add(serviceInfo);
						}
					}
					mAdapter = new ServiceInfoAdapter(ServiceInfoActivity.this, mListItem);
					listView.setAdapter(mAdapter);
				}
			} catch (JSONException e) {
				Alerts.show(e.getMessage(), ServiceInfoActivity.this);
			}
		}
		
		
		/**
		 * 检查更新
		 * @param url
		 * @param data
		 */
		public void checkUpdate(final String url,final String data){
			if(Config.isConnected(this)){
				AsyncHttpClient client = new AsyncHttpClient();
				client.get(url, new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray response) {
						try {
							JSONObject json = new JSONObject();
							json.put("items", response);
							String md5 = Config.getMD5(json.toString());
							String dataMd5 = Config.getMD5(data);
							if(md5.equals(dataMd5)){
								Toast.makeText(ServiceInfoActivity.this, "已经是最新了", Toast.LENGTH_SHORT).show();
							}else{
								Toast.makeText(ServiceInfoActivity.this, "图册已经更新", Toast.LENGTH_SHORT).show();
								Config.setCacheData(ServiceInfoActivity.this, url, json.toString());
								processData(json);
							}
							Session.getInstance().setCheckedImageUpdate(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
//					@Override
//					public void onSuccess(JSONObject json) {
//						String md5 = Config.getMD5(json.toString());
//						String dataMd5 = Config.getMD5(data);
//						if(md5.equals(dataMd5)){
//							Toast.makeText(YuXiangActivity.this, "已经是最新了", Toast.LENGTH_SHORT).show();
//						}else{
//							Toast.makeText(YuXiangActivity.this, "图册已经更新", Toast.LENGTH_SHORT).show();
//							Config.setCacheData(YuXiangActivity.this, url, json.toString());
//							processData(json);
//						}
//						Session.getInstance().setCheckedImageUpdate(true);
//					}
				});
			}
		}
		
		
		/**
		 * 获取服务事项列表
		 */
		private void getData() {
			BctidAPI.get("/rest/category-list/6",new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(int statusCode, JSONArray response) {
					try{
						/*--------设置缓存数据--------*/
						JSONObject object = new JSONObject();
						object.put("items", response);
						Config.setCacheData(ServiceInfoActivity.this,Utils.getMetaValue(ServiceInfoActivity.this, "base_url")+"/rest/category-list/6", object.toString());
						processData(object);
					}catch(Exception ex){
						WizardAlertDialog.showErrorDialog(ServiceInfoActivity.this, ex.getMessage(), R.string.btn_ok);
					}
				}
				@Override
				public void onFailure(Throwable e, JSONArray errorResponse) {
					WizardAlertDialog.showErrorDialog(ServiceInfoActivity.this, e.getMessage(), R.string.btn_ok);
				}
				@Override
				public void onFinish() {
					WizardAlertDialog.getInstance().closeProgressDialog();
				}
				@Override
				public void onStart() {
					WizardAlertDialog.getInstance().showProgressDialog(R.string.add_data, ServiceInfoActivity.this);
				}
			});
		}
}

package gov.bct.jrj.activity;

import gov.jrj.R;
import gov.bct.jrj.adapter.ServiceInfoAdapter2;
import gov.bct.jrj.common.Alerts;
import gov.bct.jrj.common.BctidAPI;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.Session;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.common.WizardAlertDialog;
import gov.bct.jrj.pojo.ServiceInfo2;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProcedureList extends Activity {

	private Button backButton;
	private TextView titleTxt;
	private ListView listView;
	private List<ServiceInfo2> mListItem;
	private ServiceInfoAdapter2 mAdapter;

	private int id;
	private String title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.title_list);

		backButton = (Button) findViewById(R.id.about_back_btn);
		listView = (ListView) findViewById(R.id.list);
		titleTxt = (TextView) findViewById(R.id.txt_title);
		id=getIntent().getExtras().getInt("id");
		title=getIntent().getExtras().getString("title");
		titleTxt.setText(title);
		
		loadData();
		
		initViews();
			
	}

	private void initViews() {
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent=new Intent(ProcedureList.this,Government_Introduction.class);
				intent.putExtra("title", title);
				intent.putExtra("id",mListItem.get(position).getId());
				intent.putExtra("description", mListItem.get(position).getDescription());
				intent.putExtra("content", mListItem.get(position).getContent());
				startActivity(intent);
			}
		});

		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ProcedureList.this.finish();
			}
		});
	}
	/**
	 * 初始化数据
	 * 先从本地缓存读取数据，如果没有读取到再从网络上获取
	 */
	private void loadData() {
		String url = Utils.getMetaValue(ProcedureList.this, "base_url")+"/rest/article?category="+id;
		//读取缓存
		String data = Config.getCacheData(this, url);
		if(data == null){
			//读取本地
//			this.getData(url);
//			data = Config.readRawData(this,R.raw.list);
			data = Config.getCacheData(this, url);
			
			if(data == null){
//				data = Config.getCacheData(this, url);
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
					mListItem = new ArrayList<ServiceInfo2>();
					mListItem.clear();
					for (int i = 0; i < array.length(); i++) {
						ServiceInfo2 serviceInfo = new ServiceInfo2(array.getJSONObject(i));
						mListItem.add(serviceInfo);
					}
				}
				mAdapter = new ServiceInfoAdapter2(ProcedureList.this, mListItem);
				listView.setAdapter(mAdapter);
			}
		} catch (JSONException e) {
			Alerts.show(e.getMessage(), ProcedureList.this);
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
							Toast.makeText(ProcedureList.this, "已经是最新了", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(ProcedureList.this, "图册已经更新", Toast.LENGTH_SHORT).show();
							Config.setCacheData(ProcedureList.this, url, json.toString());
							processData(json);
						}
						Session.getInstance().setCheckedImageUpdate(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
//				@Override
//				public void onSuccess(JSONObject json) {
//					String md5 = Config.getMD5(json.toString());
//					String dataMd5 = Config.getMD5(data);
//					if(md5.equals(dataMd5)){
//						Toast.makeText(ProcedureList.this, "已经是最新了", Toast.LENGTH_SHORT).show();
//					}else{
//						Toast.makeText(ProcedureList.this, "图册已经更新", Toast.LENGTH_SHORT).show();
//						Config.setCacheData(ProcedureList.this, url, json.toString());
//						processData(json);
//					}
//					Session.getInstance().setCheckedImageUpdate(true);
//				}
			});
		}
	}
	
	
	/**
	 * 获取宇翔图册列表
	 */
	private void getData() {
		BctidAPI.get("/rest/article?category="+id,new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				try{
//					mListItem.clear();
//					for (int i = 0; i < response.length(); i++) {
//						YuXiang yuXiang = new YuXiang(response.getJSONObject(i));
//						mListItem.add(yuXiang);
//					}
					/*--------设置缓存数据--------*/
					JSONObject object = new JSONObject();
					object.put("items", response);
					Config.setCacheData(ProcedureList.this,Utils.getMetaValue(ProcedureList.this, "base_url")+"/rest/article?category="+id, object.toString());
					processData(object);
					/*-------------------*/
//					if(mListItem.size()==0){
//						Toast.makeText(ProcedureList.this, R.string.err_data, Toast.LENGTH_SHORT).show();
//					}
//					mAdapter.notifyDataSetChanged();
				}catch(Exception ex){
					WizardAlertDialog.showErrorDialog(ProcedureList.this, ex.getMessage(), R.string.btn_ok);
				}
			}
			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				WizardAlertDialog.showErrorDialog(ProcedureList.this, e.getMessage(), R.string.btn_ok);
			}
			@Override
			public void onFinish() {
				WizardAlertDialog.getInstance().closeProgressDialog();
			}
			@Override
			public void onStart() {
				WizardAlertDialog.getInstance().showProgressDialog(R.string.add_data, ProcedureList.this);
			}
		});
	}
	
}

package gov.bct.jrj.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import gov.jrj.R;
import gov.bct.jrj.adapter.YuXiangAdapter;
import gov.bct.jrj.common.Alerts;
import gov.bct.jrj.common.BctidAPI;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.Session;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.common.WizardAlertDialog;
import gov.bct.jrj.pojo.YuXiang;
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

public class YuXiangActivity extends Activity {
	
	private Button backButton;
	private ListView listView;
	private List<YuXiang> mListItem;
	private YuXiangAdapter mAdapter;
	private TextView tx_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		backButton = (Button) findViewById(R.id.backBtn);
		listView = (ListView) findViewById(R.id.list);
		
		initViews();
		loadData();
		
//        if(mAdapter==null){
//       	 	mListItem = new ArrayList<YuXiang>();
//            mAdapter = new YuXiangAdapter(YuXiangActivity.this, mListItem);
//            getData();
//       }
//        
//       if(mAdapter!=null){
//    	   listView.setAdapter(mAdapter);
//    	   mAdapter.notifyDataSetChanged();
//       }
       
	}
	
	private void initViews() {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				Intent intent = new Intent();
				intent.setClass(YuXiangActivity.this, YuXiangDetailActivity.class);
				intent.putExtra("yuxiang", mListItem.get(position));
				tx_title = (TextView)arg1.findViewById(R.id.textView1);
				String str = tx_title.getText().toString();
				System.out.println("我的东西+"+str);
				intent.putExtra("titleg",str);
				YuXiangActivity.this.startActivity(intent);
				
			}
		});
		
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				YuXiangActivity.this.finish();
			}
		});
	}
	
	
	/**
	 * 初始化数据
	 * 先从本地缓存读取数据，如果没有读取到再从网络上获取
	 */
	private void loadData() {
		String url = Utils.getMetaValue(YuXiangActivity.this, "base_url")+"/rest/image";
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
					mListItem = new ArrayList<YuXiang>();
					mListItem.clear();
					for (int i = 0; i < array.length(); i++) {
						YuXiang yuXiang = new YuXiang(array.getJSONObject(i));
						mListItem.add(yuXiang);
					}
				}
				mAdapter = new YuXiangAdapter(YuXiangActivity.this, mListItem);
				listView.setAdapter(mAdapter);
			}
		} catch (JSONException e) {
			Alerts.show(e.getMessage(), YuXiangActivity.this);
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
							Toast.makeText(YuXiangActivity.this, "已经是最新了", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(YuXiangActivity.this, "图册已经更新", Toast.LENGTH_SHORT).show();
							Config.setCacheData(YuXiangActivity.this, url, json.toString());
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
//						Toast.makeText(YuXiangActivity.this, "已经是最新了", Toast.LENGTH_SHORT).show();
//					}else{
//						Toast.makeText(YuXiangActivity.this, "图册已经更新", Toast.LENGTH_SHORT).show();
//						Config.setCacheData(YuXiangActivity.this, url, json.toString());
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
		BctidAPI.get("/rest/image",new JsonHttpResponseHandler() {
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
					Config.setCacheData(YuXiangActivity.this,Utils.getMetaValue(YuXiangActivity.this, "base_url")+"/rest/image", object.toString());
					processData(object);
					/*-------------------*/
//					if(mListItem.size()==0){
//						Toast.makeText(YuXiangActivity.this, R.string.err_data, Toast.LENGTH_SHORT).show();
//					}
//					mAdapter.notifyDataSetChanged();
				}catch(Exception ex){
					WizardAlertDialog.showErrorDialog(YuXiangActivity.this, ex.getMessage(), R.string.btn_ok);
				}
			}
			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				WizardAlertDialog.showErrorDialog(YuXiangActivity.this, e.getMessage(), R.string.btn_ok);
			}
			@Override
			public void onFinish() {
				WizardAlertDialog.getInstance().closeProgressDialog();
			}
			@Override
			public void onStart() {
				WizardAlertDialog.getInstance().showProgressDialog(R.string.add_data, YuXiangActivity.this);
			}
		});
	}
	
}

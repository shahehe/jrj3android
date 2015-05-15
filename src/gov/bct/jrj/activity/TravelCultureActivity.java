package gov.bct.jrj.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import gov.jrj.R;
import gov.bct.jrj.adapter.TravelCultureAdapter;
import gov.bct.jrj.adapter.YuXiangAdapter;
import gov.bct.jrj.common.Alerts;
import gov.bct.jrj.common.BctidAPI;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.Session;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.common.WizardAlertDialog;
import gov.bct.jrj.pojo.TravelCulture;
import gov.bct.jrj.pojo.YuXiang;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TravelCultureActivity extends Activity {

	private Button backButton;
	private ListView listView;
	private List<TravelCulture> mListItem;
	private TravelCultureAdapter mAdapter;
	private TextView textTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		textTitle=(TextView)findViewById(R.id.txt_title);
		textTitle.setText("旅游文化");
		
		backButton = (Button) findViewById(R.id.backBtn);
		listView = (ListView) findViewById(R.id.list);
		
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TravelCultureActivity.this.finish();
			}
		});
		
		/**加载数据*/
		loadData();
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
				String title=mListItem.get(position).getName();
				String image=mListItem.get(position).getImage();
				String content=mListItem.get(position).getContent();
				Intent intent=new Intent(TravelCultureActivity.this,TravelListActivity.class);
				intent.putExtra("title", title);
				intent.putExtra("image", image);
				intent.putExtra("content", content);
				startActivity(intent);
				
			}
		});
       
	}

	
	/**
	 * 初始化数据
	 * 先从本地缓存读取数据，如果没有读取到再从网络上获取
	 */
	private void loadData() {
		String url = Utils.getMetaValue(TravelCultureActivity.this, "base_url")+"/rest/travel";
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
					mListItem = new ArrayList<TravelCulture>();
					mListItem.clear();
					for (int i = 0; i < array.length(); i++) {
						TravelCulture travelCulture = new TravelCulture(array.getJSONObject(i));
						mListItem.add(travelCulture);
					}
				}
				mAdapter = new TravelCultureAdapter(TravelCultureActivity.this, mListItem);
				listView.setAdapter(mAdapter);
			}
		} catch (JSONException e) {
			Alerts.show(e.getMessage(), TravelCultureActivity.this);
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
							Toast.makeText(TravelCultureActivity.this, "已经是最新了", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(TravelCultureActivity.this, "图册已经更新", Toast.LENGTH_SHORT).show();
							Config.setCacheData(TravelCultureActivity.this, url, json.toString());
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
	 * 获取旅游文化列表
	 */
	private void getData() {
		BctidAPI.get("/rest/travel",new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				try{
					/*--------设置缓存数据--------*/
					JSONObject object = new JSONObject();
					object.put("items", response);
					Config.setCacheData(TravelCultureActivity.this,Utils.getMetaValue(TravelCultureActivity.this, "base_url")+"/rest/travel", object.toString());
					processData(object);
				}catch(Exception ex){
					WizardAlertDialog.showErrorDialog(TravelCultureActivity.this, ex.getMessage(), R.string.btn_ok);
				}
			}
			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				WizardAlertDialog.showErrorDialog(TravelCultureActivity.this, e.getMessage(), R.string.btn_ok);
			}
			@Override
			public void onFinish() {
				WizardAlertDialog.getInstance().closeProgressDialog();
			}
			@Override
			public void onStart() {
				WizardAlertDialog.getInstance().showProgressDialog(R.string.add_data, TravelCultureActivity.this);
			}
		});
	}
}

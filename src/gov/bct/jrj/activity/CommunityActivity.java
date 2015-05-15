package gov.bct.jrj.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import gov.bct.jrj.R;
import gov.bct.jrj.adapter.CommunityAdpater;
import gov.bct.jrj.common.Alerts;
import gov.bct.jrj.common.BctidAPI;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.Session;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.common.WizardAlertDialog;
import gov.bct.jrj.pojo.Shop;
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
 * 社区活动
 */
public class CommunityActivity extends Activity {

	private Button backButton;
	private ListView listView;
	private List<Shop> mListItem;
	private CommunityAdpater mAdapter;
	private TextView textTitle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		textTitle=(TextView)findViewById(R.id.txt_title);
		textTitle.setText("社区活动");
		
		
		
		
		
		backButton = (Button) findViewById(R.id.backBtn);
		listView = (ListView) findViewById(R.id.list);
		
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				CommunityActivity.this.finish();
			}
		});
		
		/**加载数据*/
		loadData();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Shop shop =mListItem.get(position);
				Intent intent=new Intent(CommunityActivity.this,CommunityDetailsActivity.class);
				intent.putExtra("title", shop.getTitle());//标题
				intent.putExtra("image", shop.getImage());//图片
				intent.putExtra("address", shop.getSummary());//所属地址
				intent.putExtra("phone", shop.getAuthor());//电话号码
				intent.putExtra("longitude",shop.getLog());  //经度
				intent.putExtra("latitude", shop.getLat());  //纬度
//				intent.putExtra("content", shop.getContent());//详细内容
				startActivity(intent);
			}
			
		});
	}
	
	/**
	 * 初始化数据
	 * 先从本地缓存读取数据，如果没有读取到再从网络上获取
	 */
	private void loadData() {
		String url = Utils.getMetaValue(CommunityActivity.this, "base_url")+"/rest/active";
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
					mListItem = new ArrayList<Shop>();
					mListItem.clear();
					for (int i = 0; i < array.length(); i++) {
						Shop shop = new Shop(array.getJSONObject(i));
						mListItem.add(shop);
					}
				}
				mAdapter = new CommunityAdpater(CommunityActivity.this, mListItem);
				listView.setAdapter(mAdapter);
			}
		} catch (JSONException e) {
			Alerts.show(e.getMessage(), CommunityActivity.this);
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
							Toast.makeText(CommunityActivity.this, "已经是最新了", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(CommunityActivity.this, "图册已经更新", Toast.LENGTH_SHORT).show();
							Config.setCacheData(CommunityActivity.this, url, json.toString());
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
		BctidAPI.get("/rest/active",new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				try{
					/*--------设置缓存数据--------*/
					JSONObject object = new JSONObject();
					object.put("items", response);
					Config.setCacheData(CommunityActivity.this,Utils.getMetaValue(CommunityActivity.this, "base_url")+"/rest/active", object.toString());
					processData(object);
				}catch(Exception ex){
					WizardAlertDialog.showErrorDialog(CommunityActivity.this, ex.getMessage(), R.string.btn_ok);
				}
			}
			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				WizardAlertDialog.showErrorDialog(CommunityActivity.this, e.getMessage(), R.string.btn_ok);
			}
			@Override
			public void onFinish() {
				WizardAlertDialog.getInstance().closeProgressDialog();
			}
			@Override
			public void onStart() {
				WizardAlertDialog.getInstance().showProgressDialog(R.string.add_data, CommunityActivity.this);
			}
		});
	}
}

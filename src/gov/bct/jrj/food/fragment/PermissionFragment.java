package gov.bct.jrj.food.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import gov.jrj.R;
import gov.bct.jrj.activity.PermissionActivity;
import gov.bct.jrj.activity.PermissionInfoActivity;
import gov.bct.jrj.adapter.PermissionAdapter;
import gov.bct.jrj.adapter.PermissionFAdapter;
import gov.bct.jrj.common.Alerts;
import gov.bct.jrj.common.BctidAPI;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.Session;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.common.WizardAlertDialog;
import gov.bct.jrj.pojo.Permission;
import gov.bct.jrj.pojo.PermissionF;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
 * @author ouzehua
 * 食药安全>办事许可列表
 *
 */
public class PermissionFragment extends Fragment {

	private ListView listView;
	private List<PermissionF> mListItem;
	private PermissionFAdapter mAdapter;
	
	private Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=getActivity();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.fragment_list, container, false);
		listView=(ListView) v.findViewById(R.id.list);
		/**加载数据*/
		loadData();
		
		
		return v;
	}
	
	/**
	 * 初始化数据
	 * 先从本地缓存读取数据，如果没有读取到再从网络上获取
	 */
	private void loadData() {
		String url = Utils.getMetaValue(mContext, "base_url")+"/rest/category-list/2";
		//读取缓存
		String data = Config.getCacheData(mContext, url);
		if(data == null){
			//读取本地
//			this.getData(url);
//			data = Config.readRawData(this,R.raw.list);
			data = Config.getCacheData(mContext, url);
			
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
					mListItem = new ArrayList<PermissionF>();
					mListItem.clear();
					for (int i = 0; i < array.length(); i++) {
						PermissionF permissionF = new PermissionF(array.getJSONObject(i));
						mListItem.add(permissionF);
					}
				}
				mAdapter = new PermissionFAdapter(mContext, mListItem);
				listView.setAdapter(mAdapter);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
							long arg3) {
						String mTitle=mListItem.get(arg2).getName();
						String type=mListItem.get(arg2).getType();
						String id=mListItem.get(arg2).getId();
						if("article".equals(type)){
							Intent intent = new Intent(mContext,
									PermissionInfoActivity.class);
							intent.putExtra("title", mTitle);
							intent.putExtra("id", id);
							startActivity(intent);
						}
						
						if("list".equals(type)){
							Intent intent = new Intent(mContext,
									PermissionActivity.class);
							intent.putExtra("title", mTitle);
							intent.putExtra("id", id);
							startActivity(intent);
						}
					}
				});
			}
		} catch (JSONException e) {
			Alerts.show(e.getMessage(), mContext);
		}
	}
	
	
	/**
	 * 检查更新
	 * @param url
	 * @param data
	 */
	public void checkUpdate(final String url,final String data){
		if(Config.isConnected(mContext)){
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
							Toast.makeText(mContext, "已经是最新了", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(mContext, "图册已经更新", Toast.LENGTH_SHORT).show();
							Config.setCacheData(mContext, url, json.toString());
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
	 * 获取事项列表
	 */
	private void getData() {
		BctidAPI.get("/rest/category-list/2",new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				try{
					/*--------设置缓存数据--------*/
					JSONObject object = new JSONObject();
					object.put("items", response);
					Config.setCacheData(mContext,Utils.getMetaValue(mContext, "base_url")+"/rest/category-list/2", object.toString());
					processData(object);
				}catch(Exception ex){
					WizardAlertDialog.showErrorDialog(mContext, ex.getMessage(), R.string.btn_ok);
				}
			}
			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				WizardAlertDialog.showErrorDialog(mContext, e.getMessage(), R.string.btn_ok);
			}
			@Override
			public void onFinish() {
				WizardAlertDialog.getInstance().closeProgressDialog();
			}
			@Override
			public void onStart() {
				WizardAlertDialog.getInstance().showProgressDialog(R.string.add_data, mContext);
			}
		});
	}
	
}

package gov.bct.jrj.businessservice.fragment;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import gov.bct.jrj.R;
import gov.bct.jrj.activity.BusinessGuideActivity;
import gov.bct.jrj.adapter.GuideAdapter;
import gov.bct.jrj.common.Alerts;
import gov.bct.jrj.common.BctidAPI;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.Session;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.common.WizardAlertDialog;
import gov.bct.jrj.pojo.Guide;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author ouzehua 工商服务>办事指南
 *
 */
public class BusinessGuideFragment extends Fragment {

	private Context mContext;
	View v;

	private ListView listView;
	private List<Guide> mListItem;
	private GuideAdapter mAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getActivity();	
		
		/**加载数据*/
		loadData();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.fragment_list, container, false);
		listView = (ListView) v.findViewById(R.id.list);
		
		if(mListItem!=null){
			mAdapter = new GuideAdapter(mContext, mListItem);
			listView.setAdapter(mAdapter);
		}
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Bundle bundle = new Bundle();
				Intent intent = new Intent();
				Guide guide=mListItem.get(arg2);
				bundle.putString("title", guide.getTitle());
				bundle.putString("content", guide.getContent());
				bundle.putString("phone", guide.getPhone().trim());
				intent.putExtras(bundle);
				intent.setClass(mContext,BusinessGuideActivity.class);
				startActivity(intent);
			}
			
		});
		
		return v;
	}

	/**
	 * 初始化数据
	 * 先从本地缓存读取数据，如果没有读取到再从网络上获取
	 */
	private void loadData() {
		String url = Utils.getMetaValue(mContext, "base_url")+"/rest/administration_info";
		//读取缓存
		String data = Config.getCacheData(mContext, url);
		if(data == null){
			//读取本地
//			mContext.getData(url);
//			data = Config.readRawData(mContext,R.raw.list);
			data = Config.getCacheData(mContext, url);
			
			if(data == null){
//				data = Config.getCacheData(mContext, url);
				getData();

			}else{
				try{
					processData(new JSONObject(data));
					if(!Session.getInstance().isCheckedImageUpdate()){
						checkUpdate(url, data);
					}
				}catch(Exception ex){
					getData();
				}
			}
		}else{
			try{
				processData(new JSONObject(data));
				if(!Session.getInstance().isCheckedUpdate()){
					checkUpdate(url, data);
				}
			}catch(Exception ex){
				getData();
			}
			
		}
	}
	
	public void processData(JSONObject json){
		try {
			JSONArray array = json.getJSONArray("items");
			if(mAdapter==null){
				if(mListItem==null){
					mListItem = new ArrayList<Guide>();
					mListItem.clear();
					for (int i = 0; i < array.length(); i++) {
						Guide guide = new Guide(array.getJSONObject(i));
						mListItem.add(guide);
					}
				}
				mAdapter = new GuideAdapter(mContext, mListItem);
				listView.setAdapter(mAdapter);
				mAdapter.notifyDataSetChanged();
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

			});
		}
	}
	
	
	/**
	 * 获取列表
	 */
	private void getData() {
		BctidAPI.get("/rest/administration_info",new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				try{
					/*--------设置缓存数据--------*/
					JSONObject object = new JSONObject();
					object.put("items", response);
					Config.setCacheData(mContext,Utils.getMetaValue(mContext, "base_url")+"/rest/administration_info", object.toString());
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

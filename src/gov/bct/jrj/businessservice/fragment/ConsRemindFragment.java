package gov.bct.jrj.businessservice.fragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import gov.bct.jrj.R;
import gov.bct.jrj.activity.ProductInfoActivity;
import gov.bct.jrj.common.Alerts;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.Session;
import gov.bct.jrj.library.http.JSONRPCHandler;
import gov.bct.jrj.library.http.JSONRPCService;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 
 * @author ouzehua
 * 食药安全>下架商品
 *
 */
public class ConsRemindFragment extends Fragment {

	private ProgressDialog pd;
	private Activity mContext;
	
	private List<Object> listData = new ArrayList<Object>();
	private List<String> listTag = new ArrayList<String>();
	
	private ProgressBar progressBar;
	private ListView lstView;
	
	private View view;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=getActivity();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_goods_list, container, false);

		lstView = (ListView) view.findViewById(R.id.list);
		progressBar = (ProgressBar)view.findViewById(R.id.progressBar1);
		loadData();
		
		lstView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				try{
					JSONObject json = (JSONObject)listData.get(arg2);
					Intent intent = new Intent(mContext,ProductInfoActivity.class);
					intent.putExtra("data", json.toString());
					startActivity(intent);
				}catch(Exception ex){
					
				}
			}
		});
		return view;
	}
	
	private void loadData() {
		String url = "Commerce.getList";
		String data = Config.getCacheData(mContext, url);
		if(data == null){
			Session.getInstance().setCheckedProduceUpdate(false);
			this.getData(url);
		}else{
			try{
				this.processData(new JSONObject(data));
				Session.getInstance().setCheckedProduceUpdate(true);
				this.getData(url);
			}catch(Exception ex){
				this.getData(url);
			}
		}
	}
	
	private void getData(final String url){
		if(Session.getInstance().isProductUpdate()){
			this.progressBar.setVisibility(View.VISIBLE);
		}else{
			this.progressBar.setVisibility(View.GONE);
		}
		JSONRPCService.exec(url, null, new JSONRPCHandler(){

			@Override
			public void onSuccess(JSONObject json) {
				Config.setCacheData(mContext, url, json.toString());
				System.out.println(json);
				processData(json);
			}

			@Override
			public void onFailed(String message) {
				pd.dismiss();
				if(progressBar.VISIBLE==View.VISIBLE){
					progressBar.setVisibility(View.GONE);
				}
			}

			@Override
			public void onError(String message) {
				if(pd != null) pd.dismiss();
				Alerts.show(message,mContext);
				if(progressBar.VISIBLE==View.VISIBLE){
					progressBar.setVisibility(View.GONE);
				}
			}

			@Override
			public void onStart() {
				if(!Session.getInstance().isProductUpdate()){
					pd = ProgressDialog.show(mContext, null, getString(R.string.loading));
				}
			}

			@Override
			public void onFinish() {
				if(pd != null){
					pd.dismiss();
				}
				
				if(progressBar.VISIBLE==View.VISIBLE){
					progressBar.setVisibility(View.GONE);
				}
				
			}
			
		});
	}
	
	private void processData(JSONObject json){
		try {
			if(Session.getInstance().isProductUpdate()){
				listTag.clear();
				listData.clear();
				Session.getInstance().setCheckedProduceUpdate(false);
				this.progressBar.setVisibility(View.INVISIBLE);
			}
			JSONArray products = json.getJSONArray("commerce");
			for(int i=0;i<products.length();i++){
				JSONObject p = products.getJSONObject(i);
				listTag.add(p.getString("moth"));
				listData.add(p.getString("moth"));
				if(p.get("items").getClass().equals(JSONArray.class)){
					JSONArray items = p.getJSONArray("items");
					for(int x=0;x<items.length();x++){
						JSONObject item = items.getJSONObject(x);
						listData.add(item);
					}
				}else{
					listData.add(p.get("item"));
				}
			}
			lstView.setAdapter(new ListItemAdapter());
		} catch (JSONException e) {
			e.printStackTrace();
			//Alerts.show(e.getMessage(),TextNewsListActivity.this);
			
		}
	}
	
public class ListItemAdapter extends BaseAdapter{
		
		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int pos) {
			return listData.get(pos);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}
		@Override
		public boolean isEnabled(int position) {
			if(listTag.contains(getItem(position))){
				return false;
			}
			return super.isEnabled(position);
		}

		@Override
		public View getView(int pos, View v, ViewGroup vg) {
			View view = null;
			try {
				if(listTag.contains(getItem(pos))){
					view = ((Activity)mContext).getLayoutInflater().inflate(R.layout.product_tag_item, vg,false);
					view.setBackgroundResource(R.drawable.product_moth_bg);
					TextView title = (TextView)view.findViewById(R.id.textView1);
					title.setTextColor(Color.WHITE);
					title.setText(getItem(pos).toString());
				}else{
					view = mContext.getLayoutInflater().inflate(
							R.layout.product_list_item, vg, false);
					TextView title = (TextView) view
							.findViewById(R.id.textView1);
					TextView desc = (TextView) view
							.findViewById(R.id.textView2);
					JSONObject json = (JSONObject) getItem(pos);
					title.setText(json.getString("number") + ": "
							+ json.getString("title"));
					String descStr = json.getString("content");
					desc.setText(descStr);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return view;
		}
		
	}
}

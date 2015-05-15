package gov.bct.jrj.activity;



import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import gov.bct.jrj.R;
import gov.bct.jrj.common.Alerts;
import gov.bct.jrj.common.BctidAPI;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.Session;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.common.WizardAlertDialog;
import gov.bct.jrj.pojo.Police;
import gov.bct.jrj.police.fragment.BusisProcFragment;
import gov.bct.jrj.police.fragment.ChargeStandardFragment;
import gov.bct.jrj.police.fragment.ZhengjianFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 
 * @author ouzehua 公安服务页面
 *
 */
public class PoliceActivity extends FragmentActivity {

	private Button backBtn;

	private RelativeLayout processBtn;// 业务办理标签按钮
	private RelativeLayout zhengjianBtn;// 证件办理按钮
	private RelativeLayout chargeBtn;// 证件办理收费标准按钮

	private FragmentManager manager;
	private FragmentTransaction transaction;
	
	/**三个子标签的内容*/
	private ZhengjianFragment zhengjianFragment;//证件办理流程
	private BusisProcFragment processFragment;//业务办理
	private ChargeStandardFragment chargeStandardFragment;//消费提醒
	
	/**三个子标签的下划线*/
	private View view1;
	private View view2;
	private View view3;
	
	private List<Police> mList=new ArrayList<Police>();
	private ImageView phoneImage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.police_activity);
		view1=(View)findViewById(R.id.title_image0);
		view2=(View)findViewById(R.id.title_image1);
		view3=(View)findViewById(R.id.title_image2);
		
		phoneImage=(ImageView) findViewById(R.id.phoneImage);
		backBtn=(Button) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				PoliceActivity.this.finish();
			}
		});
		
		processBtn = (RelativeLayout) findViewById(R.id.bar_layout0);
		zhengjianBtn = (RelativeLayout) findViewById(R.id.bar_layout1);
		chargeBtn = (RelativeLayout) findViewById(R.id.bar_layout2);
		processBtn.setOnClickListener(listener);
		zhengjianBtn.setOnClickListener(listener);
		chargeBtn.setOnClickListener(listener);
		
		/**加载数据*/
		loadData();
		
	}
	
	/**
	 * 初始化数据
	 * 先从本地缓存读取数据，如果没有读取到再从网络上获取
	 */
	private void loadData() {
		String url = Utils.getMetaValue(PoliceActivity.this, "base_url")+"/rest/policeinfo";
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
			for(int i=0;i<array.length();i++){
				Police police=new Police(array.getJSONObject(i));
				mList.add(police);
			}
			if(!mList.get(0).getPhoneNum().equals(null)){
				phoneImage.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 传入服务， parse（）解析号码
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
								+ mList.get(0).getPhoneNum()));
						startActivity(intent);
					}
				});
			}
			
			/**展示被选择的fragment，默认第一次进入页面选择第一个fragment*/
			selectTab(0);
			
		} catch (JSONException e) {
			Alerts.show(e.getMessage(), PoliceActivity.this);
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
							Toast.makeText(PoliceActivity.this, "已经是最新了", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(PoliceActivity.this, "图册已经更新", Toast.LENGTH_SHORT).show();
							Config.setCacheData(PoliceActivity.this, url, json.toString());
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
	 * 获取服务事项列表
	 */
	private void getData() {
		BctidAPI.get("/rest/policeinfo",new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, JSONArray response) {
				try{
					/*--------设置缓存数据--------*/
					JSONObject object = new JSONObject();
					object.put("items", response);
					Config.setCacheData(PoliceActivity.this,Utils.getMetaValue(PoliceActivity.this, "base_url")+"/rest/policeinfo", object.toString());
					processData(object);
				}catch(Exception ex){
					WizardAlertDialog.showErrorDialog(PoliceActivity.this, ex.getMessage(), R.string.btn_ok);
				}
			}
			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				WizardAlertDialog.showErrorDialog(PoliceActivity.this, e.getMessage(), R.string.btn_ok);
			}
			@Override
			public void onFinish() {
				WizardAlertDialog.getInstance().closeProgressDialog();
			}
			@Override
			public void onStart() {
				WizardAlertDialog.getInstance().showProgressDialog(R.string.add_data, PoliceActivity.this);
			}
		});
	}
	
	private void selectTab(int index) {
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		
		switch (index) {
		case 0:
			if (zhengjianFragment == null) {
				zhengjianFragment = new ZhengjianFragment();
			}
			zhengjianFragment.setContent(mList.get(0).getContent()+"");
			transaction.replace(R.id.content, zhengjianFragment);
			break;

		case 1:
			
			if (processFragment == null) {
				processFragment = new BusisProcFragment();
			}
			processFragment.setContent(mList.get(1).getContent()+"");
			transaction.replace(R.id.content, processFragment);
			break;
		case 2:			
			if (chargeStandardFragment == null) {
				chargeStandardFragment = new ChargeStandardFragment();
			}
			chargeStandardFragment.setContent(mList.get(2).getContent()+"");
			transaction.replace(R.id.content, chargeStandardFragment);
			break;
		}
		transaction.addToBackStack(null);
		transaction.commit();
	}
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bar_layout0:
//				cleanfocus();
				view1.setVisibility(View.VISIBLE);
				view2.setVisibility(View.GONE);
				view3.setVisibility(View.GONE);
				selectTab(0);
				break;

			case R.id.bar_layout1:
//				cleanfocus();
				view2.setVisibility(View.VISIBLE);
				view1.setVisibility(View.GONE);
				view3.setVisibility(View.GONE);
				selectTab(1);
				break;
			case R.id.bar_layout2:
//				cleanfocus();
				view3.setVisibility(View.VISIBLE);
				view1.setVisibility(View.GONE);
				view2.setVisibility(View.GONE);
				selectTab(2);
				break;
			}
		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			PoliceActivity.this.finish();
			return true;
		}
		return false;
	}
}

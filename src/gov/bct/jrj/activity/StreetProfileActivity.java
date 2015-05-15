package gov.bct.jrj.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import gov.bct.jrj.R;
import gov.bct.jrj.adapter.MyViewPagerAdapter;
import gov.bct.jrj.common.BctidAPI;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.Session;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.common.WizardAlertDialog;
import gov.bct.jrj.pojo.StreetProfile;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StreetProfileActivity extends Activity {

	private Button backButton;
	private TextView titleView, subjectView, contentView;

	/** 实现街道图片的轮流播放 */
	private ViewPager viewPager;
	private MyViewPagerAdapter mAdapter;
	private List<String> imageViews; // 滑动的图片集合
	private int currentItem = 0; // 当前图片的索引号

	/**
	 * 与ViewPager下面小圆点相关的
	 */
	private List<ImageView> dotViews; // 小圆点的图片
	private LinearLayout dotLayout;// 小圆点的布局

	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;
	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};

	private StreetProfile profile = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_item_detail_di);
		backButton = (Button) findViewById(R.id.backBtn);
		titleView = (TextView) findViewById(R.id.txt_title);
		subjectView = (TextView) findViewById(R.id.detail_txt_subject);
		contentView = (TextView) findViewById(R.id.detail_txt_content);
		dotLayout = (LinearLayout) findViewById(R.id.viewPagerDot);
		viewPager = (ViewPager) findViewById(R.id.vp);
		dotViews = new ArrayList<ImageView>();
		if (profile == null) {
			loadData();
//			getData();
		}

		titleView.setText("金融街简介");
		subjectView.setText("西城区金融街街道办事处");

		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				StreetProfileActivity.this.finish();
			}
		});

	}

	@Override
	protected void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
				TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	protected void onStop() {
		scheduledExecutorService.shutdown();
		super.onStop();
	}

	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	private class MyPageChangeListener implements OnPageChangeListener {

		private int oldPosition = 0;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		/**
		 * 当页面被选择后，该方法被调用 position: 新页面的索引
		 */
		@Override
		public void onPageSelected(int position) {
			currentItem = position;
			 dotViews.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			 dotViews.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}
	}

	private void getData() {
		BctidAPI.get("/rest/page/about_jrj", new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				Config.setCacheData(StreetProfileActivity.this,Utils.getMetaValue(StreetProfileActivity.this, "base_url")+"/rest/page/about_jrj", response.toString());
				processData(response);
				
			}

			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				WizardAlertDialog.showErrorDialog(StreetProfileActivity.this,
						e.getMessage(), R.string.btn_ok);
			}

			@Override
			public void onFinish() {
				WizardAlertDialog.getInstance().closeProgressDialog();
			}

			@Override
			public void onStart() {
				WizardAlertDialog.getInstance().showProgressDialog(
						R.string.add_data, StreetProfileActivity.this);
			}
		});
	}

	public StreetProfileActivity(){  
		
	}
	
	/**
	 * 初始化数据
	 * 先从本地缓存读取数据，如果没有读取到再从网络上获取
	 */
	private void loadData() {
		String url = Utils.getMetaValue(StreetProfileActivity.this, "base_url")+"/rest/page/about_jrj";
		//读取缓存
		String data = Config.getCacheData(this, url);
		if(data == null){
			data = Config.getCacheData(this, url);
			
			if(data == null){
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
	public void processData(JSONObject response){
		try {
			profile = new StreetProfile(response);
			if (profile == null) {
				contentView.setText("读取不到数据...");
				Toast.makeText(StreetProfileActivity.this,
						R.string.err_data, Toast.LENGTH_SHORT).show();
			}
			if (profile != null) {
				contentView.setText(Html.fromHtml(profile.getContent()));
				imageViews = profile.getImagePath();
				viewPager.setAdapter(new MyViewPagerAdapter(imageViews,StreetProfileActivity.this));
				
				/**
				 * 添加小圆点,有几张图片就有几个小圆点
				 */
				for (int i = 0; i < imageViews.size(); i++) {
					ImageView imageView = new ImageView(
							StreetProfileActivity.this);
					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
							15, 15);
					params.setMargins(10, 0, 10, 0);
					imageView.setLayoutParams(params);
					if (currentItem == i) {
						imageView
								.setBackgroundResource(R.drawable.dot_focused);
					} else {
						imageView
								.setBackgroundResource(R.drawable.dot_normal);
					}
					dotLayout.addView(imageView);
					dotViews.add(imageView);
				}
				viewPager.setOnPageChangeListener(new MyPageChangeListener());
			}

		} catch (Exception ex) {
			WizardAlertDialog.showErrorDialog(
					StreetProfileActivity.this, ex.getMessage(),
					R.string.btn_ok);
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
				public void onSuccess(int statusCode, JSONObject response) {
					try {
						String md5 = Config.getMD5(response.toString());
						String dataMd5 = Config.getMD5(data);
						if(md5.equals(dataMd5)){
							Toast.makeText(StreetProfileActivity.this, "已经是最新了", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(StreetProfileActivity.this, "图册已经更新", Toast.LENGTH_SHORT).show();
							Config.setCacheData(StreetProfileActivity.this, url, response.toString());
							processData(response);
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
}

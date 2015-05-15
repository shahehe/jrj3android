package gov.bct.jrj.bank.fragment;

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
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author ouzehua 银行服务>简介
 *
 */
public class IntroFragment extends Fragment {

	private String tag;// 识别招商银行或广发银行
	private Context mContext;
	private String urlString;
	
	private TextView subjectView;// 标题
	private TextView contentView;//内容

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

	private View parentView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tag = getTag();
		mContext=getActivity();
		
		parentView = inflater.inflate(R.layout.fragment_bank_intro, container, false);
		subjectView = (TextView) parentView
				.findViewById(R.id.detail_txt_subject);
		contentView = (TextView)parentView. findViewById(R.id.detail_txt_content);
		dotLayout = (LinearLayout)parentView. findViewById(R.id.viewPagerDot);
		viewPager = (ViewPager)parentView. findViewById(R.id.vp);
		dotViews = new ArrayList<ImageView>();
		
		if(tag=="zhaoshang"){
			subjectView.setText("招商银行简介");
			urlString="/rest/bank/9/276";
		}else if(tag=="guangfa"){
			subjectView.setText("广发银行简介");
			urlString="/rest/bank/10/277";
		}
		loadData();
		return parentView;
	}
	
	@Override
	public void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
				TimeUnit.SECONDS);
		super.onStart();
	}
	@Override
	public void onStop() {
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
	/**
	 * 初始化数据
	 * 先从本地缓存读取数据，如果没有读取到再从网络上获取
	 */
	private void loadData() {
		
		String url = Utils.getMetaValue(mContext, "base_url")+urlString;
		//读取缓存
		String data = Config.getCacheData(mContext, url);
		if(data == null){
			data = Config.getCacheData(mContext, url);
			
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
				Toast.makeText(mContext,
						R.string.err_data, Toast.LENGTH_SHORT).show();
			}
			if (profile != null) {
				contentView.setText(Html.fromHtml(profile.getContent()));
				imageViews = profile.getImagePath();
				viewPager.setAdapter(new MyViewPagerAdapter(imageViews,mContext));
				
				/**
				 * 添加小圆点,有几张图片就有几个小圆点
				 */
				for (int i = 0; i < imageViews.size(); i++) {
					ImageView imageView = new ImageView(
							mContext);
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
					mContext, ex.getMessage(),
					R.string.btn_ok);
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
				public void onSuccess(int statusCode, JSONObject response) {
					try {
						String md5 = Config.getMD5(response.toString());
						String dataMd5 = Config.getMD5(data);
						if(md5.equals(dataMd5)){
							Toast.makeText(mContext, "已经是最新了", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(mContext, "图册已经更新", Toast.LENGTH_SHORT).show();
							Config.setCacheData(mContext, url, response.toString());
							processData(response);
						}
						Session.getInstance().setCheckedImageUpdate(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
	private void getData() {
		BctidAPI.get(urlString, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, JSONObject response) {
				Config.setCacheData(mContext,Utils.getMetaValue(mContext, "base_url")+urlString, response.toString());
				processData(response);
				
			}

			@Override
			public void onFailure(Throwable e, JSONArray errorResponse) {
				WizardAlertDialog.showErrorDialog(mContext,
						e.getMessage(), R.string.btn_ok);
			}

			@Override
			public void onFinish() {
				WizardAlertDialog.getInstance().closeProgressDialog();
			}

			@Override
			public void onStart() {
				WizardAlertDialog.getInstance().showProgressDialog(
						R.string.add_data, mContext);
			}
		});
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
}

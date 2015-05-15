package gov.bct.jrj.activity;

import gov.bct.jrj.R;
import gov.bct.jrj.businessservice.fragment.BusinessDynamicFragment;
import gov.bct.jrj.businessservice.fragment.BusinessGuideFragment;
import gov.bct.jrj.businessservice.fragment.ConsRemindFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * 
 * @author ouzehua
 * 工商服务页面
 *
 */
public class BusinessServiceActivity extends FragmentActivity {

	private Button backBtn;
	
	private RelativeLayout IntroBtn;// 简介标签按钮
	private RelativeLayout dynamicBtn;// 工商动态标签按钮
	private RelativeLayout remindBtn;// 消费提醒标签按钮
	
	private FragmentManager manager;
	private FragmentTransaction transaction;
	
	/**三个子标签的内容*/
	private BusinessGuideFragment IntroFragment;//简介
	private BusinessDynamicFragment dynamicFragment;//工商动态
	private ConsRemindFragment consRemindFragment;//消费提醒
	
	/**三个子标签的下划线*/
	private View view1;
	private View view2;
	private View view3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.business_service_activity);
		view1=(View)findViewById(R.id.title_image0);
		view2=(View)findViewById(R.id.title_image1);
		view3=(View)findViewById(R.id.title_image2);
		
		backBtn=(Button) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				BusinessServiceActivity.this.finish();
			}
		});
		
		IntroBtn = (RelativeLayout) findViewById(R.id.bar_layout0);
		dynamicBtn = (RelativeLayout) findViewById(R.id.bar_layout1);
		remindBtn = (RelativeLayout) findViewById(R.id.bar_layout2);
		IntroBtn.setOnClickListener(listener);
		dynamicBtn.setOnClickListener(listener);
		remindBtn.setOnClickListener(listener);
		
		/**展示被选择的fragment，默认第一次进入页面选择第一个fragment*/
		selectTab(0);
	}
	
	private void selectTab(int index) {
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		switch (index) {
		case 0:
			if (IntroFragment == null) {
				IntroFragment = new BusinessGuideFragment();
			}
			transaction.replace(R.id.content, IntroFragment);
			
			break;

		case 1:
			if (dynamicFragment == null) {
				dynamicFragment = new BusinessDynamicFragment();
			}
			transaction.replace(R.id.content, dynamicFragment);
			break;
		case 2:			
			if (consRemindFragment == null) {
				consRemindFragment = new ConsRemindFragment();
			}
			transaction.replace(R.id.content, consRemindFragment);
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
			case R.id.bar_layout3:
//				cleanfocus();
				view1.setVisibility(View.GONE);
				view3.setVisibility(View.GONE);
				view2.setVisibility(View.GONE);
				selectTab(3);
				break;
			}
		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			BusinessServiceActivity.this.finish();
			return true;
		}
		return false;
	}
}

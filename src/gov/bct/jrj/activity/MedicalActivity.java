package gov.bct.jrj.activity;

import com.baidu.mapapi.SDKInitializer;

import gov.bct.jrj.R;
import gov.bct.jrj.medical.fragment.ChangshiFragment;
import gov.bct.jrj.medical.fragment.ConsultFragment;
import gov.bct.jrj.medical.fragment.GaikuangFragment;
import gov.bct.jrj.medical.fragment.IntroFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

/**
 * 
 * @author 欧泽华
 *  社区医疗页面
 */
public class MedicalActivity extends FragmentActivity {

	private Button backBtn;
	private RelativeLayout gaikuangBtn;// 医疗概况标签
	private RelativeLayout IntroBtn;// 机构介绍标签
	private RelativeLayout consultBtn;// 名医会诊标签
	private RelativeLayout changshiBtn;// 健康常识标签

	private FragmentManager manager;
	private FragmentTransaction transaction;

	/** 四个子标签的内容 */
	private GaikuangFragment gaikuangFragment;// 医疗概况页面
	private IntroFragment introFragment;// 机构介绍页面
	private ConsultFragment consultFragment;// 名医会诊页面
	private ChangshiFragment changshiFragment;// 健康常识页面

	/**四个子标签的下划线*/
	private View view1;
	private View view2;
	private View view3;
	private View view4;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.medical_activity);
		
		view1=(View)findViewById(R.id.title_image0);
		view2=(View)findViewById(R.id.title_image1);
		view3=(View)findViewById(R.id.title_image2);
		view4=(View)findViewById(R.id.title_image3);
		

		backBtn = (Button) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				MedicalActivity.this.finish();
			}
		});

		gaikuangBtn = (RelativeLayout) findViewById(R.id.bar_layout0);
		IntroBtn = (RelativeLayout) findViewById(R.id.bar_layout1);
		consultBtn = (RelativeLayout) findViewById(R.id.bar_layout2);
		changshiBtn = (RelativeLayout) findViewById(R.id.bar_layout3);
		gaikuangBtn.setOnClickListener(listener);
		IntroBtn.setOnClickListener(listener);
		consultBtn.setOnClickListener(listener);
		changshiBtn.setOnClickListener(listener);
		
		/** 展示被选择的fragment，默认第一次进入页面选择第一个fragment */
		selectTab(0);
	}

	private void selectTab(int index) {
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		switch (index) {
		case 0:
			if (gaikuangFragment == null) {
				gaikuangFragment = new GaikuangFragment();
			}
			transaction.replace(R.id.content, gaikuangFragment);
			break;

		case 1:
			if (introFragment == null) {
				introFragment = new IntroFragment();
			}
			transaction.replace(R.id.content, introFragment);
//			cleanfocus();
//			view2.setVisibility(View.VISIBLE);
			break;
		case 2:			
			if (consultFragment == null) {
				consultFragment = new ConsultFragment();
			}
			transaction.replace(R.id.content, consultFragment);
//			cleanfocus();
			break;
		case 3:
			if (changshiFragment == null) {
				changshiFragment = new ChangshiFragment();
			}
			transaction.replace(R.id.content, changshiFragment);
//			cleanfocus();
//			view3.setVisibility(View.VISIBLE);
			break;
		}

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
				view4.setVisibility(View.GONE);
				selectTab(0);
				break;

			case R.id.bar_layout1:
//				cleanfocus();
				view2.setVisibility(View.VISIBLE);
				view1.setVisibility(View.GONE);
				view3.setVisibility(View.GONE);
				view4.setVisibility(View.GONE);
				selectTab(1);
				break;
			case R.id.bar_layout2:
//				cleanfocus();
				view3.setVisibility(View.VISIBLE);
				view1.setVisibility(View.GONE);
				view2.setVisibility(View.GONE);
				view4.setVisibility(View.GONE);
				selectTab(2);
				break;
			case R.id.bar_layout3:
//				cleanfocus();
				view4.setVisibility(View.VISIBLE);
				view1.setVisibility(View.GONE);
				view3.setVisibility(View.GONE);
				view2.setVisibility(View.GONE);
				selectTab(3);
				break;
			}
		}
	};

	private void cleanfocus(){
		view1.setVisibility(View.GONE);
		view2.setVisibility(View.GONE);
		view3.setVisibility(View.GONE);
		view4.setVisibility(View.GONE);
	}
}

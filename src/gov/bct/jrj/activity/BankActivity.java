package gov.bct.jrj.activity;

import gov.bct.jrj.R;
import gov.bct.jrj.bank.fragment.GuangFaFragment;
import gov.bct.jrj.bank.fragment.ZhaoShangFragment;
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
 * 银行服务
 *
 */
public class BankActivity extends FragmentActivity {

	private Button backBtn;
	private RelativeLayout zhaoShangBtn;// 招商银行标签
	private RelativeLayout GuangFaBtn;// 广发银行标签
	
	private FragmentManager manager;
	private FragmentTransaction transaction;
	
	/**两个子标签的Fragment*/
	private ZhaoShangFragment zhaoShangFragment;//招商银行
	private GuangFaFragment guangFaFragment;//广发银行
	
	/**两个子标签的下划线*/
	private View view1;
	private View view2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bank);
		//view1=(View)findViewById(R.id.title_image0);
		view2=(View)findViewById(R.id.title_image1);
		
		backBtn = (Button) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				BankActivity.this.finish();
			}
		});
	/**
	 * 为了只显示招商银行，去掉广发银行 注释
	 * 2015-4-1
	 */
		zhaoShangBtn=(RelativeLayout) findViewById(R.id.bar_layout0);
	//	GuangFaBtn=(RelativeLayout) findViewById(R.id.bar_layout1);
	//	zhaoShangBtn.setOnClickListener(listener);
	//	GuangFaBtn.setOnClickListener(listener);
		
		/**展示被选择的fragment，默认第一次进入页面选择第一个fragment*/
		selectTab(0);
	}
	
	private void selectTab(int index) {
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		switch (index) {
		case 0:
			if (zhaoShangFragment == null) {
				zhaoShangFragment = new ZhaoShangFragment();
			}
			transaction.replace(R.id.content, zhaoShangFragment);
			
			break;

		case 1:
			if (guangFaFragment == null) {
				guangFaFragment = new GuangFaFragment();
			}
			transaction.replace(R.id.content, guangFaFragment);
			break;
		}
		transaction.addToBackStack(null);
		transaction.commit();
	}
/**
 * 为了只显示招商银行，去掉广发银行 注释
 * 2015-4-1
 */
	/*private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bar_layout0:
				view1.setVisibility(View.VISIBLE);
				view2.setVisibility(View.GONE);
				selectTab(0);
				break;

			case R.id.bar_layout1:
				view2.setVisibility(View.VISIBLE);
				view1.setVisibility(View.GONE);
				selectTab(1);
				break;

			}
		}
	};*/
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			BankActivity.this.finish();
			return true;
		}
		return false;
	}
}

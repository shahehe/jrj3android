package gov.bct.jrj.activity;

import gov.jrj.R;
import gov.bct.jrj.food.fragment.FoodIntroFragment;
import gov.bct.jrj.food.fragment.GoodsFragment;
import gov.bct.jrj.food.fragment.MessageListFragment;
import gov.bct.jrj.food.fragment.PermissionFragment;
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
 * 食药安全页面
 *
 */
public class FoodSercurityActivity extends FragmentActivity {

	private Button backBtn;
	private RelativeLayout foodIntroBtn;// 简介标签
	private RelativeLayout messageListBtn;// 商品信息列表标签
	private RelativeLayout goodsBtn;// 下架商品标签
	private RelativeLayout permissionBtn;// 办事许可标签
	
	private FragmentManager manager;
	private FragmentTransaction transaction;

	/**三个子标签的Fragment*/
	private FoodIntroFragment foodIntroFragment;//简介
	private MessageListFragment messageListFragment;//动态宣传
	private GoodsFragment goodsFragment;//下架商品
	private PermissionFragment permissionFragment;//办事许可
	/**三个子标签的下划线*/
	private View view1;
	private View view2;
	private View view3;
	private View view4;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.food_security_activity);
		view1=(View)findViewById(R.id.title_image0);
		view2=(View)findViewById(R.id.title_image1);
		view3=(View)findViewById(R.id.title_image2);
		view4=(View)findViewById(R.id.title_image3);
		
		backBtn = (Button) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FoodSercurityActivity.this.finish();
			}
		});
		
		foodIntroBtn = (RelativeLayout) findViewById(R.id.bar_layout0);
		messageListBtn = (RelativeLayout) findViewById(R.id.bar_layout1);
		goodsBtn = (RelativeLayout) findViewById(R.id.bar_layout2);
		permissionBtn = (RelativeLayout) findViewById(R.id.bar_layout3);
		foodIntroBtn.setOnClickListener(listener);
		messageListBtn.setOnClickListener(listener);
		goodsBtn.setOnClickListener(listener);
		permissionBtn.setOnClickListener(listener);
		
		/**展示被选择的fragment，默认第一次进入页面选择第一个fragment*/
		selectTab(0);
	}
	
	
	private void selectTab(int index) {
		manager = getSupportFragmentManager();
		transaction = manager.beginTransaction();
		switch (index) {
		case 0:
			if (foodIntroFragment == null) {
				foodIntroFragment = new FoodIntroFragment();
			}
			transaction.replace(R.id.content, foodIntroFragment);
			
			break;

		case 1:
			if (messageListFragment == null) {
				messageListFragment = new MessageListFragment();
			}
			transaction.replace(R.id.content, messageListFragment);
			break;
		case 2:			
			if (goodsFragment == null) {
				goodsFragment = new GoodsFragment();
			}
			transaction.replace(R.id.content, goodsFragment);
			break;
		case 3:			
			if (permissionFragment == null) {
				permissionFragment = new PermissionFragment();
			}
			transaction.replace(R.id.content, permissionFragment);
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
				view2.setVisibility(View.GONE);
				view3.setVisibility(View.GONE);
				selectTab(3);
				break;
			}
		}
	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			FoodSercurityActivity.this.finish();
			return true;
		}
		return false;
	}
}

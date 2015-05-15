package gov.bct.jrj.bank.fragment;

import gov.bct.jrj.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

/**
 * 
 * @author ouzehua
 * 广发银行
 */
public class GuangFaFragment extends Fragment {

	private RelativeLayout introBtn;// 简介标签
	private RelativeLayout dynamicBtn;// 动态信息标签
	
	private FragmentManager manager;
	private FragmentTransaction transaction;
	
	/**两个子标签的Fragment*/
	private IntroFragment introFragment;//简介
	private DynamicFragment dynamicFragment;//动态信息
	
	/**两个子标签的下划线*/
	private View view1;
	private View view2;
	
	private View parentView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		parentView =inflater.inflate(R.layout.fragment_guangfa, container,false);
		
		view1=(View)parentView.findViewById(R.id.title_image0);
		view2=(View)parentView.findViewById(R.id.title_image1);
		
		introBtn=(RelativeLayout) parentView.findViewById(R.id.bar_layout0);
		dynamicBtn=(RelativeLayout) parentView.findViewById(R.id.bar_layout1);
		introBtn.setOnClickListener(listener);
		dynamicBtn.setOnClickListener(listener);
		
		/**展示被选择的fragment，默认第一次进入页面选择第一个fragment*/
		selectTab(0);
		return parentView;
	}
	
	private void selectTab(int index) {
		manager = getChildFragmentManager();
		transaction = manager.beginTransaction();
		switch (index) {
		case 0:
			if ( introFragment== null) {
				introFragment = new IntroFragment();
			}
			transaction.replace(R.id.fragment_content, introFragment,"guangfa");
			break;

		case 1:
			if (dynamicFragment == null) {
				dynamicFragment = new DynamicFragment();
			}
			transaction.replace(R.id.fragment_content, dynamicFragment,"guangfa");
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
	};
}

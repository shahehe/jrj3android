package gov.bct.jrj.fragment;

import gov.jrj.R;
import gov.bct.jrj.common.Constants;
import gov.bct.jrj.common.Utils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

/**
 * @author 欧 主页面下的四个标签页
 */
public class TabFragment extends Fragment {

	private RadioGroup mBottomRadioGroup;
	private Fragment mContentFragment;

	// 行政服务
	private GovInfoFragment mFragGoverInfo;
	// 生活服务
	private LifeFragment mLifeFragment;
	// 监督提交
	private FeedbackFragment mFragFeedback;
	// 个人中心
	private MyCenterFragment mMyCenterFragment;
	// 个人中心登陆页面
	private LoginFragment mLoginFragment;
	// 个人中心注册页面
	private RegisterFragment mRegisterFragment;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.main_tab_fragment, container, false);

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mBottomRadioGroup = (RadioGroup) view
				.findViewById(R.id.main_bottom_radiogroup);

		if (!Utils.isSessionValiad(getActivity())) {
			mLoginFragment = new LoginFragment(mHandler);
			mRegisterFragment = new RegisterFragment(mHandler);
		} else {
			mMyCenterFragment = new MyCenterFragment(mHandler);
		}
		mFragGoverInfo = new GovInfoFragment(false);
		mFragFeedback = new FeedbackFragment();

		// 新加的mtab2
		mLifeFragment = new LifeFragment();
		mContentFragment = getFragmentManager().findFragmentById(
				R.id.pf_main_content_fragment);
		int messageType = this.getActivity().getIntent().getIntExtra("type", 0);
		System.out.println("messageType=" + messageType);
		if (messageType == 1) {
			mBottomRadioGroup.check(R.id.tab4);
			if (!Utils.isSessionValiad(getActivity())) {
				mContentFragment = (mLoginFragment == null ? new LoginFragment(
						mHandler) : mLoginFragment);
			} else {
				mContentFragment = (mMyCenterFragment == null ? new MyCenterFragment(
						mHandler) : mMyCenterFragment);
			}
		} else {
			mBottomRadioGroup.check(R.id.tab1);
			mContentFragment = mFragGoverInfo;
		}
		mBottomRadioGroup.setOnCheckedChangeListener(onCheckedChangeListener);

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.pf_main_content_fragment, mContentFragment);
		ft.commit();
	}

	// 此方法在onClick之前触发
	RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.tab1:
				mContentFragment = (mFragGoverInfo == null ? new GovInfoFragment(
						false) : mFragGoverInfo);
				break;
			case R.id.tab2:
				// mContentFragment = mFragAnnonce;
				mContentFragment = mLifeFragment == null ? new LifeFragment()
						: mLifeFragment;
				break;
			case R.id.tab3:
				mContentFragment = mFragFeedback == null ? new FeedbackFragment()
						: mFragFeedback;
				break;
			case R.id.tab4:
				// 首先判断是否已经登录，没有登录跳转到登录的Fragment
				if (!Utils.isSessionValiad(getActivity())) {
					mContentFragment = (mLoginFragment == null ? new LoginFragment(
							mHandler) : mLoginFragment);
				} else {
					mContentFragment = (mMyCenterFragment == null ? new MyCenterFragment(
							mHandler) : mMyCenterFragment);
				}
				break;
			default:
				break;
			}
			changeContentFragment();

		}
	};

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case Constants.WHAT_LOGIN:
				mContentFragment = (mMyCenterFragment == null ? new MyCenterFragment(
						mHandler) : mMyCenterFragment);
				changeContentFragment();
				break;
			case Constants.WHAT_LOGOUT:
				mContentFragment = (mLoginFragment == null ? new LoginFragment(
						mHandler) : mLoginFragment);
				changeContentFragment();
				break;

			case Constants.WHAT_REGISTER:
				mContentFragment = (mRegisterFragment == null ? new RegisterFragment(
						mHandler) : mRegisterFragment);
				changeContentFragment();
				break;
			}
		}
	};

	/*
	 * 改变内容Fragment，切换不同的列表内容
	 */
	void changeContentFragment() {
		// if (!mContentFragment.isAdded()) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		try {
			// ft.remove(mContentFragment);
			Fragment fragment = getFragmentManager().findFragmentById(
					R.id.pf_main_content_fragment);
			ft.remove(fragment);
			ft.replace(R.id.pf_main_content_fragment, mContentFragment);
			ft.addToBackStack(null);
			// ft.commitAllowingStateLoss();
			ft.commit();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		// }
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	public interface ChangeRadioTab {
		public void changeTab();
	}

	public TabFragment() {
		super();
		// TODO Auto-generated constructor stub
	}

}

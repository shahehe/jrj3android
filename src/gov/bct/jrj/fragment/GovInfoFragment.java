package gov.bct.jrj.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gov.jrj.R;
import gov.bct.jrj.activity.AnnonceViewActivity;
import gov.bct.jrj.activity.BusinessServiceActivity;
import gov.bct.jrj.activity.FoodSercurityActivity;
import gov.bct.jrj.activity.PoliceActivity;
import gov.bct.jrj.activity.ServiceInfoActivity;
import gov.bct.jrj.activity.StreetProfileActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

/**
 * 行政服务页面
 * @author ouzehua
 *
 */
public class GovInfoFragment extends Fragment {
	
	private GridView gridView;
	private List<HashMap<String, Object>> data=new ArrayList<HashMap<String,Object>>();
	private HashMap<String, Object> map;
	
	private Context mContext;
	
	public GovInfoFragment(){
		super();
	}
	public GovInfoFragment(boolean b) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext=getActivity();
		getdata();//讲该页面各个控件的数据加载到list里
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v =inflater.inflate(R.layout.gov_info_fragment, container,false);
		gridView=(GridView)v.findViewById(R.id.gridView);
		
		GridviewAdapter mAdapter = new GridviewAdapter(getActivity(), data);
		gridView.setAdapter(mAdapter);
		gridView.setOnItemClickListener(listener);
		return v;
	}

	private List<HashMap<String, Object>> getdata() {
		
		map=new HashMap<String, Object>();
		map.put("itemImg",R.drawable.icon_street_profile);
//		map.put("itemName",getResources().getString(R.string.gov_info_title1));
		data.add(map);
		map=new HashMap<String, Object>();
		map.put("itemImg",R.drawable.icon_service);
//		map.put("itemName",getResources().getString(R.string.gov_info_title2));
		data.add(map);
		map=new HashMap<String, Object>();
		map.put("itemImg",R.drawable.icon_industry);
//		map.put("itemName",getResources().getString(R.string.gov_info_title3));
		data.add(map);
		map=new HashMap<String, Object>();
		map.put("itemImg",R.drawable.icon_security);
//		map.put("itemName",getResources().getString(R.string.gov_info_title4));
		data.add(map);
		map=new HashMap<String, Object>();
		map.put("itemImg",R.drawable.icon_police);
//		map.put("itemName",getResources().getString(R.string.gov_info_title5));
		data.add(map);
		map=new HashMap<String, Object>();
		map.put("itemImg",R.drawable.icon_weekly);
//		map.put("itemName",getResources().getString(R.string.gov_info_title6));
		data.add(map);
		return data;
	}
		
	private OnItemClickListener listener =new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
			switch (position) {
			case 0:
				Intent intent0 = new Intent(getActivity(), StreetProfileActivity.class);
				mContext.startActivity(intent0);
				break;
			case 1:
				Intent intent1 = new Intent(getActivity(), ServiceInfoActivity.class);
//				intent1.putExtra(Constants.KEY_SUBJECT_NAME, getString(R.string.tab1));
//				intent1.putExtra(Constants.KEY_ITEM_NAME, "ffdsf");
//				intent1.putExtra(Constants.KEY_ARRAY, R.array.gongshang_array);
				mContext.startActivity(intent1);
				break;
			case 2:
				Intent intent2 = new Intent(getActivity(), BusinessServiceActivity.class);
				mContext.startActivity(intent2);
				break;
			case 3:
				Intent intent3 = new Intent(getActivity(), FoodSercurityActivity.class);
				intent3.putExtra("title", "食药安全");
				mContext.startActivity(intent3);
				break;
			case 4:
				Intent intent4 = new Intent(getActivity(), PoliceActivity.class);
				intent4.putExtra("title", "公安服务");
				mContext.startActivity(intent4);
				break;
			case 5:
				Intent intent5 = new Intent();
				intent5.setClass(getActivity(), AnnonceViewActivity.class);
				startActivity(intent5);
				break;
			}
		}
			
	};
}
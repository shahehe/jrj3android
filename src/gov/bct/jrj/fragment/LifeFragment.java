package gov.bct.jrj.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gov.jrj.R;
import gov.bct.jrj.activity.BankActivity;
import gov.bct.jrj.activity.BicycleActvity;
import gov.bct.jrj.activity.CommunityActivity;
import gov.bct.jrj.activity.HealthServiceActivity;
import gov.bct.jrj.activity.MedicalActivity;
import gov.bct.jrj.activity.ShopActivity;
import gov.bct.jrj.activity.TravelCultureActivity;
import gov.bct.jrj.activity.WebViewActivity;
import gov.bct.jrj.activity.YuXiangActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


/** 
 * @author 欧泽华
 * 生活服务页面
 */
public class LifeFragment extends Fragment{

	private GridView gridView;
	private List<HashMap<String, Object>> data=new ArrayList<HashMap<String,Object>>();
	private HashMap<String, Object> map;
	
	int [] ImageId=new int []{
			R.drawable.book_icon,R.drawable.bicycle,R.drawable.travel_culture,
			R.drawable.community,R.drawable.community,R.drawable.community_business,
			R.drawable.health_service,R.drawable.shop,R.drawable.bank_service
			
		};
	
	private static Context context;
	int screenHeight;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		getdata();
		context=getActivity();
		DisplayMetrics dm = new DisplayMetrics();
		screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.life_fragment,container, false);
		gridView=(GridView)v.findViewById(R.id.gridView);
		
		GridviewAdapter mAdapter = new GridviewAdapter(getActivity(), data);
		gridView.setAdapter(mAdapter);
		gridView.setOnItemClickListener(listener);
		
//		SimpleAdapter adapter=new SimpleAdapter
//				(getActivity(),data, R.layout.fragment_gridview,
//						new String[]{"itemImg"}, new int[]{R.id.itemImg});
//		gridView.setAdapter(adapter);
//		gridView.setAdapter(new ImageAdapter());
		
		gridView.setOnItemClickListener(listener);
		return v;
	}
	
	private List<HashMap<String,Object> > getdata() {
		map=new HashMap<String, Object>();
		map.put("itemImg",R.drawable.book_icon1);
//		map.put("itemName",getResources().getString(R.string.life_title1));
		data.add(map);
		map=new HashMap<String, Object>();
		map.put("itemImg",R.drawable.bicycle1);
//		map.put("itemName",getResources().getString(R.string.life_title2));
		data.add(map);
		map=new HashMap<String, Object>();
		map.put("itemImg",R.drawable.travel_culture1);
//		map.put("itemName",getResources().getString(R.string.life_title3));
		data.add(map);
		map=new HashMap<String, Object>();
		map.put("itemImg",R.drawable.community1);
//		map.put("itemName",getResources().getString(R.string.life_title4));
		data.add(map);
		map=new HashMap<String, Object>();
		map.put("itemImg",R.drawable.community_business1);
//		map.put("itemName",getResources().getString(R.string.life_title5));
		data.add(map);
		map=new HashMap<String, Object>();
		map.put("itemImg",R.drawable.health_service1);
//		map.put("itemName",getResources().getString(R.string.life_title6));
		data.add(map);
		map=new HashMap<String, Object>();
		map.put("itemImg",R.drawable.business_activity1);
//		map.put("itemName",getResources().getString(R.string.life_title7));
		data.add(map);
		map=new HashMap<String, Object>();
		map.put("itemImg",R.drawable.shop1);
//		map.put("itemName",getResources().getString(R.string.life_title8));
		data.add(map);
		map=new HashMap<String, Object>();
		map.put("itemImg",R.drawable.bank_service1);
//		map.put("itemName",getResources().getString(R.string.life_title9));
		data.add(map);
		return data;
	}
	
	
	private OnItemClickListener  listener=new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int positon,long arg3) {
			switch (positon) {
			case 0:
				Intent intent0 = new Intent(getActivity(), YuXiangActivity.class);
				getActivity().startActivity(intent0);
				break;
			case 1:
				Intent intent1=new Intent(getActivity(), BicycleActvity.class);
				startActivity(intent1);
				break;
			case 2:
				Intent intent2 = new Intent(getActivity(), TravelCultureActivity.class);
				getActivity().startActivity(intent2);
				break;
			case 3:
				Intent intent3 = new Intent();
				intent3.setClass(LifeFragment.this.getActivity(), MedicalActivity.class);
				startActivity(intent3);
				break;
			case 4:
				Bundle bundle=new Bundle();
				bundle.putString("url", "http://jrjsq.chinaec.net/");
				bundle.putString("title", "社区商圈网");
				Intent intent = new Intent();
				intent.putExtras(bundle);
				intent.setClass(LifeFragment.this.getActivity(), WebViewActivity.class);
				startActivity(intent);
				break;
			case 5:
				Intent intent5 = new Intent();
				intent5.setClass(LifeFragment.this.getActivity(), HealthServiceActivity.class);
				startActivity(intent5);
				break;
			case 6:
				Intent intent6 = new Intent();
				intent6.setClass(LifeFragment.this.getActivity(), CommunityActivity.class);
				startActivity(intent6);
				break;
			case 7:
				Intent intent7=new Intent(LifeFragment.this.getActivity(),ShopActivity.class);
				startActivity(intent7);
				break;
			case 8:
				Intent intent8=new Intent(LifeFragment.this.getActivity(),BankActivity.class);
				startActivity(intent8);
				break;
			}
		}
	};
	
	private class ImageAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return ImageId.length;
		}

		@Override
		public Object getItem(int arg0) {
			return ImageId[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			 ImageView imageView;  
//	            if(convertView==null){  
	            	convertView = LayoutInflater.from(getActivity()).inflate(  
	                      R.layout.fragment_gridview, null);   
	            	convertView.setLayoutParams(new GridView.LayoutParams(LayoutParams.MATCH_PARENT, 85));
	            	imageView=(ImageView) convertView.findViewById(R.id.itemImg);
//	            }else{  
//	                imageView = (ImageView) convertView;  
//	            }  
	            imageView.setBackgroundResource(ImageId[position]);
	            return convertView;  
		}
		
	}
	
	public static int dip2px(float dipValue){
	    final float scale=context.getResources().getDisplayMetrics().density;
	    return (int) (dipValue * scale + 0.5f);
	}
}

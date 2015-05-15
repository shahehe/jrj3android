package gov.bct.jrj.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import gov.bct.jrj.common.RoundAngleImageView;
import gov.bct.jrj.pojo.YuXiang;
import gov.jrj.R;

import java.util.List;

public class YuXiangAdapter extends BaseAdapter {
	Context context;
	List<YuXiang> items;

//	/**关于定位和导航的字段*/
//	private String endPositionX=null;
//	private String endPositionY=null;
//	private String endName = null;
//	private double latitude = gov.bct.jrj.common.Constants.DEFAULT_LAT;//纬度
//	private double longitude = gov.bct.jrj.common.Constants.DEFAULT_LON;//经度
//	private boolean mIsEngineInitSuccess = false;
//	private LocationClient mLocClient;
//	private MyLocationListener listener = new MyLocationListener();
//	
//	private NaviEngineInitListener mNaviEngineInitListener = new NaviEngineInitListener() {  
//        public void engineInitSuccess() {
//            //导航初始化是异步的，需要一小段时间，以这个标志来识别引擎是否初始化成功，为true时候才能发起导航  
//            mIsEngineInitSuccess = true;  
//        }  
//        public void engineInitStart(){}
//        public void engineInitFail(){}  
//    };  
//	
//	private String getSdcardDir() {  
//	    if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {  
//	        return Environment.getExternalStorageDirectory().toString();  
//	    }  
//	    return null;  
//	 }
	
	public YuXiangAdapter(Context context, List<YuXiang> items) {
		this.context = context;
		this.items = items;
//		BaiduNaviManager.getInstance().initEngine((Activity)context, getSdcardDir(), mNaviEngineInitListener, "2vp9lUI08VpxCeHUu2GdS8vZ",null);
//		initLocation();

	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public YuXiang getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		final YuXiang item = getItem(position);
		ViewHolder holder=null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.yuxiang_list_item,
					null);
			holder=new ViewHolder();
			holder.imageView = (RoundAngleImageView) convertView
					.findViewById(R.id.imageView1);
			holder.textView1 = (TextView) convertView
					.findViewById(R.id.textView1);
			holder.textView2 = (TextView) convertView
					.findViewById(R.id.textView2);
//			holder.navigationButton = (ImageView) convertView
//					.findViewById(R.id.locationIV);
			convertView.setTag(holder);

		}else {
			holder=(ViewHolder)convertView.getTag();
		}
		
//		holder.navigationButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				//116.36677,39.914123
////				endPosition = "116.36677,39.914123";
////				String[] endPositionTemp = endPosition.split(",");
//				endName=getItem(position).getName();
//				endPositionX=getItem(position).getLatitude();
//				endPositionY=getItem(position).getLongitude();
//				if(endPositionX.length()!=0&&endPositionY.length()!=0){
//					try {
//						launchNavigator2(longitude,latitude,"",Double.parseDouble(endPositionY), Double.parseDouble(endPositionX), endName);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
////				launchNavigator();
//			}
//		});
		if (item.getImage() != null && !item.getImage().equals("")
				&& !item.getImage().equals("null")) {
			Picasso.with(context).load(item.getImage()).into(holder.imageView);
			holder.imageView.setRoundWidth(30);
			holder.imageView.setRoundHeight(30);
		}
		if (item.getName() != null && !item.getName().equals("")
				&& !item.getName().equals("null")) {
			holder.textView1.setText(item.getName());
		}
		if (item.getStorey() != null && !item.getStorey().equals("")
				&& !item.getStorey().equals("null")) {
			holder.textView2.setText(item.getStorey());
		}
		return convertView;
	}
	
	private class ViewHolder{
		public  RoundAngleImageView imageView;
//		public ImageView navigationButton;
		
		public TextView textView1;
		public TextView textView2;
	}
	
//	public void initLocation(){
//        mLocClient = new LocationClient(context);
//        mLocClient.registerLocationListener(listener);
//        LocationClientOption option = new LocationClientOption();
////	    option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
//        option.setOpenGps(true);
//	    option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值gcj02
//	    option.setScanSpan(1000);//设置发起定位请求的间隔时间为5000ms
////	    option.setIsNeedAddress(true);//返回的定位结果包含地址信息
////	    option.setProdName("bctid-android-standard");
////        option.disableCache(true);// 设置是否启用缓存设定说明  
//	    mLocClient.setLocOption(option);
//		mLocClient.start();
//		mLocClient.requestLocation();
//	}
//	/**
//     * 指定导航起终点启动GPS导航.起终点可为多种类型坐标系的地理坐标。
//     * 前置条件：导航引擎初始化成功
//     */
//    private void launchNavigator2(double startLatitude,double startLongitude,String startName,double endLatitude,double endLongitude,String endName){
//        //这里给出一个起终点示例，实际应用中可以通过POI检索、外部POI来源等方式获取起终点坐标
//        BNaviPoint startPoint = new BNaviPoint(startLatitude,startLongitude,startName, BNaviPoint.CoordinateType.BD09_MC);
//        BNaviPoint endPoint = new BNaviPoint(endLatitude,endLongitude,endName, BNaviPoint.CoordinateType.BD09_MC);
//        BaiduNaviManager.getInstance().launchNavigator((Activity)context,
//                startPoint,                                      //起点（可指定坐标系）
//                endPoint,                                        //终点（可指定坐标系）
//                NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME,       //算路方式
//                true,                                            //真实导航
//                BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, //在离线策略
//                new OnStartNavigationListener() {                //跳转监听
//                    @Override
//                    public void onJumpToNavigator(Bundle configParams) {
//                        Intent intent = new Intent(context, BNavigatorActivity.class);
//                        intent.putExtras(configParams);
//                        context.startActivity(intent);
//                    }
//                    
//                    @Override
//                    public void onJumpToDownloader() {
//                    }
//                });
//    }
    
	
//	public class MyLocationListener implements BDLocationListener {
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			if (location == null)
//				return;
//			latitude = location.getLatitude();
//			longitude = location.getLongitude();
//			mLocClient.stop();
//		}
//
//		public void onReceivePoi(BDLocation poiLocation) {
//		}
//	}
}

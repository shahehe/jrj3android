package gov.bct.jrj.activity;

import gov.bct.jrj.R;
import gov.bct.jrj.common.Constants;
import gov.bct.jrj.pojo.YuXiang;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationActivity extends Activity{
	private Button backButton;
	private YuXiang mXiang;
	
//	public LocationClient mLocationClient = null;
//	public BDLocationListener myListener = new MyLocationListener();
	
//	private double latitude = 39.963175;
//	private double longitude = 116.400244;
	
	private MapView mapView ;
	private BaiduMap mBaiduMap;
	OverlayOptions options;
	Marker marker;
	
//	private double latitude = Constants.DEFAULT_LAT;
//	private double longitude = Constants.DEFAULT_LON;
//	
//	private LocationClient mLocClient;
//	private MyLocationListener listener = new MyLocationListener();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());  
		setContentView(R.layout.activity_location);
//		initLocation();
//		System.out.println(latitude+";"+longitude);
		
		mXiang = (YuXiang)(getIntent().getSerializableExtra("yuxiang"));
		
//		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
//	    mLocationClient.registerLocationListener( myListener );    //注册监听函数
		
		backButton = (Button) findViewById(R.id.backBtn);
		mapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mapView.getMap();  
		//开启交通图   
//		mBaiduMap.setTrafficEnabled(true);
		
		//定义Maker坐标点  
		LatLng point = new LatLng(39.963175, 116.400244);  
		//构建Marker图标  
		BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.da_marker_red);  
//		//构建MarkerOption，用于在地图上添加Marker  
//		OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
//		//在地图上添加Marker，并显示  
//		mBaiduMap.addOverlay(option);
		options = new MarkerOptions().position(point).icon(bitmap).zIndex(9).draggable(true);  //设置手势拖拽
	//将marker添加到地图上
		marker = (Marker) (mBaiduMap.addOverlay(options));
		//调用BaiduMap对象的setOnMarkerDragListener方法设置marker拖拽的监听
		/*mBaiduMap.setOnMarkerDragListener(new OnMarkerDragListener() {
		    public void onMarkerDrag(Marker marker) {
		        //拖拽中
		    }
		    public void onMarkerDragEnd(Marker marker) {
		        //拖拽结束
		    }
		    public void onMarkerDragStart(Marker marker) {
		        //开始拖拽
		    }
		});*/
		
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}
			@Override
			public void onMapClick(LatLng latLng) {
				marker.remove();
				BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.da_marker_red);  
//				//构建MarkerOption，用于在地图上添加Marker  
				options = new MarkerOptions().position(latLng).icon(bitmap);
				marker = (Marker) (mBaiduMap.addOverlay(options));
//				//在地图上添加Marker，并显示  
//				mBaiduMap.addOverlay(options);
			}
		});
		
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				LocationActivity.this.finish();
			}
		});
	}
	
//	public void initLocation(){
//        mLocClient = new LocationClient(getApplicationContext());
//        mLocClient.registerLocationListener(listener);
//        LocationClientOption option = new LocationClientOption();
////	    option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
//        option.setOpenGps(true);
//	    option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值gcj02
//	    option.setScanSpan(1000);//设置发起定位请求的间隔时间为5000ms
////	    option.setIsNeedAddress(true);//返回的定位结果包含地址信息
////	    option.setProdName("bctid-android-standard");
////        option.disableCache(true);
//	    mLocClient.setLocOption(option);
//		mLocClient.start();
//		mLocClient.requestLocation();
//	}
	
//	public class MyLocationListener implements BDLocationListener {
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			if (location == null)
//				return;
////			StringBuffer sb = new StringBuffer(256);
////			sb.append("time : ");
////			sb.append(location.getTime());
////			sb.append("\nerror code : ");
////			sb.append(location.getLocType());
////			sb.append("\nlatitude : ");
////			sb.append(location.getLatitude());
////			sb.append("\nlontitude : ");
////			sb.append(location.getLongitude());
////			sb.append("\nradius : ");
////			sb.append(location.getRadius());
////			sb.append("\naddr : ");
////			sb.append(location.getAddrStr());
////			if(location.getAddrStr()==null){
//////				localAddress.setText("暂无位置信息");
////			}else {
//////				localAddress.setText(location.getAddrStr()+"");
//////				local.setText();
////				System.out.println(location.getLongitude()+","+location.getLatitude());
////				latitude = location.getLatitude();
////				longitude = location.getLongitude();
////				mLocClient.stop();
////			}
//			latitude = location.getLatitude();
//			longitude = location.getLongitude();
//			mLocClient.stop();
////			Log.i("=================", sb.toString());
//		}
//
//		public void onReceivePoi(BDLocation poiLocation) {
//		}
//	}
	
//	public class MyLocationListener implements BDLocationListener {
//		@Override
//		public void onReceiveLocation(BDLocation location) {
//			if (location == null)
//		            return ;
//			StringBuffer sb = new StringBuffer(256);
//			sb.append("time : ");
//			sb.append(location.getTime());
//			sb.append("\nerror code : ");
//			sb.append(location.getLocType());
//			sb.append("\nlatitude : ");
//			sb.append(location.getLatitude());
//			sb.append("\nlontitude : ");
//			sb.append(location.getLongitude());
//			sb.append("\nradius : ");
//			sb.append(location.getRadius());
//			if (location.getLocType() == BDLocation.TypeGpsLocation){
//				sb.append("\nspeed : ");
//				sb.append(location.getSpeed());
//				sb.append("\nsatellite : ");
//				sb.append(location.getSatelliteNumber());
//			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
//				sb.append("\naddr : ");
//				sb.append(location.getAddrStr());
//			} 
//			System.out.println(sb.toString());
//			
//			latitude = location.getLatitude();
//			longitude = location.getLongitude();
//		}
//	}
}

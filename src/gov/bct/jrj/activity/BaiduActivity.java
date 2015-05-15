package gov.bct.jrj.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.BNaviPoint;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BNaviEngineManager.NaviEngineInitListener;
import com.baidu.navisdk.BaiduNaviManager.OnStartNavigationListener;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import gov.bct.jrj.R;
import gov.bct.jrj.activity.BicycleActvity.MyLocationListener;
import gov.bct.jrj.common.Alerts;
import gov.bct.jrj.common.BctidAPI;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.Session;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.common.WizardAlertDialog;
import gov.bct.jrj.navigator.activity.BNavigatorActivity;
import gov.bct.jrj.pojo.Shop;
import gov.bct.jrj.pojo.YuXiang;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 新建的社区活动 百度位置
 * 
 * @author chova 2015-04-1
 */

public class BaiduActivity extends Activity {

	/**
	 * MapView 是地图主控件
	 */
	private MapView mMapView;
	private BaiduMap mBaiduMap;

	// 初始化全局 bitmap 信息，不用时及时 recycle
	BitmapDescriptor bdA = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_gcoding);
	private InfoWindow infoWindow;

	private List<Shop> mList;
	private Marker[] markers;

	private double latitude = gov.bct.jrj.common.Constants.DEFAULT_LAT;// 纬度
	private double longitude = gov.bct.jrj.common.Constants.DEFAULT_LON;// 经度

	private TextView titlea;
	private double lat;
	private double lng;
	private String shopNameTxt;// 商铺名称
	private String addressTxt;// 地址
	private String phoneNumTxt;// 联系号码

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.baidumap);

		titlea = (TextView) findViewById(R.id.txt_tit);

		final Bundle bundle = this.getIntent().getExtras();
		/** 社区活动名称 */
		titlea.setText(bundle.getString("title"));
		this.lng = Double.parseDouble(bundle.getString("longitude"));
		this.lat = Double.parseDouble(bundle.getString("latitude"));
		shopNameTxt=  bundle.getString("title");
		/** 所属地址 */
		addressTxt = bundle.getString("address");
		/** 联系电话 */
		phoneNumTxt = bundle.getString("phone");

		BaiduNaviManager.getInstance().initEngine(BaiduActivity.this,
				getSdcardDir(), mNaviEngineInitListener,
				Utils.getMetaValue(this, "com.baidu.lbsapi.API_KEY"), null);
		initLocation();

		Button backBtn = (Button) findViewById(R.id.backBtn);

		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				BaiduActivity.this.finish();
			}
		});

		// zuijin = (TextView) findViewById(R.id.textView1);

		mMapView = (MapView) findViewById(R.id.bmapVie);
		mBaiduMap = mMapView.getMap();
		loadData();
	}

	/**
	 * 初始化数据 先从本地缓存读取数据，如果没有读取到再从网络上获取
	 */
	private void loadData() {
		LatLng cenpot = new LatLng(lat,lng);
		 MapStatus mMapStatus = new MapStatus.Builder().target(cenpot) .zoom(15.0f).build();
		 MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus); mBaiduMap.setMapStatus(mMapStatusUpdate);

		LatLng latLng = new LatLng(this.lat,this.lng);
		OverlayOptions ooA = new MarkerOptions().position(latLng).icon(bdA)
				.zIndex(9).draggable(true);
		mBaiduMap.addOverlay(ooA);
		// new MapStatus.Builder().target(cenpot).build();
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				int i = arg0.getZIndex();
				final LatLng latlng = arg0.getPosition();
				//Shop b = list.get(i);
				View infoWindowView = LayoutInflater.from(BaiduActivity.this)
						.inflate(R.layout.infowindow_view, null);
				TextView message = (TextView) infoWindowView
						.findViewById(R.id.text1);

				message.setText(addressTxt);
				Button navigator = (Button) infoWindowView
						.findViewById(R.id.button1);
				navigator.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (latitude != 0f && longitude != 0) {
							launchNavigator2(latlng);
							mBaiduMap.hideInfoWindow();
						}
					}
				});
				infoWindow = new InfoWindow(infoWindowView, latlng, -50);
				mBaiduMap.showInfoWindow(infoWindow);
				return true;
			}
		});

//		String url = Utils.getMetaValue(BaiduActivity.this, "base_url")
//				+ "/rest/active";
//		// 读取缓存
//		String data = Config.getCacheData(BaiduActivity.this, url);
//		if (data == null) {
//			// 读取本地
//			// BicycleActvity.this.getData(url);
//			// data = Config.readRawData(BicycleActvity.this,R.raw.list);
//			data = Config.getCacheData(BaiduActivity.this, url);
//
//			if (data == null) {
//				// data = Config.getCacheData(BicycleActvity.this, url);
//				getData();
//
//			} else {
//				try {
//					processData(new JSONObject(data));
//					if (!Session.getInstance().isCheckedImageUpdate()) {
//						checkUpdate(url, data);
//					}
//				} catch (Exception ex) {
//					getData();
//				}
//			}
//		} else {
//			try {
//				processData(new JSONObject(data));
//				if (!Session.getInstance().isCheckedUpdate()) {
//					checkUpdate(url, data);
//				}
//			} catch (Exception ex) {
//				getData();
//			}
//
//		}
	}

	public void processData(JSONObject json) {
//		try {
//			JSONArray array = json.getJSONArray("items");
//
//			mList = new ArrayList<Shop>();
//			for (int i = 0; i < array.length(); i++) {
//				Shop bicycle = new Shop(array.getJSONObject(i));
//				mList.add(bicycle);
//			}
//
//			initOverlay(mList);
//			/*
//			 * zuijin.setOnClickListener(new OnClickListener() {
//			 *
//			 * @Override public void onClick(View v) { getNearbyPoint(mList); }
//			 * });
//			 */
//
//		} catch (JSONException e) {
//			Alerts.show(e.getMessage(), BaiduActivity.this);
//		}
	}

	/**
	 * 检查更新
	 * 
	 * @param url
	 * @param data
	 */
//	public void checkUpdate(final String url, final String data) {
//		if (Config.isConnected(BaiduActivity.this)) {
//			AsyncHttpClient client = new AsyncHttpClient();
//			client.get(url, new JsonHttpResponseHandler() {
//				@Override
//				public void onSuccess(JSONArray response) {
//					try {
//						JSONObject json = new JSONObject();
//						json.put("items", response);
//						String md5 = Config.getMD5(json.toString());
//						String dataMd5 = Config.getMD5(data);
//						if (md5.equals(dataMd5)) {
//							Toast.makeText(BaiduActivity.this, "已经是最新了",
//									Toast.LENGTH_SHORT).show();
//						} else {
//							Toast.makeText(BaiduActivity.this, "数据已经更新",
//									Toast.LENGTH_SHORT).show();
//							Config.setCacheData(BaiduActivity.this, url,
//									json.toString());
//							// processData(json);
//						}
//						Session.getInstance().setCheckedImageUpdate(true);
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//
//			});
//		}
//	}

	/**
	 * 获取列表
	 */
//	private void getData() {
//		BctidAPI.get("/rest/active", new JsonHttpResponseHandler() {
//			@Override
//			public void onSuccess(int statusCode, JSONArray response) {
//				try {
//					/*--------设置缓存数据--------*/
//					JSONObject object = new JSONObject();
//					object.put("items", response);
//					Config.setCacheData(BaiduActivity.this,
//							Utils.getMetaValue(BaiduActivity.this, "base_url")
//									+ "/rest/active", object.toString());
//					// processData(object);
//				} catch (Exception ex) {
//					WizardAlertDialog.showErrorDialog(BaiduActivity.this,
//							ex.getMessage(), R.string.btn_ok);
//				}
//			}
//
//			@Override
//			public void onFailure(Throwable e, JSONArray errorResponse) {
//				WizardAlertDialog.showErrorDialog(BaiduActivity.this,
//						e.getMessage(), R.string.btn_ok);
//			}
//
//			@Override
//			public void onFinish() {
//				WizardAlertDialog.getInstance().closeProgressDialog();
//			}
//
//			@Override
//			public void onStart() {
//				WizardAlertDialog.getInstance().showProgressDialog(
//						R.string.add_data, BaiduActivity.this);
//			}
//		});
//	}

	public void initOverlay(final List<Shop> list) {
		/**
		 * 消除地图的标点 04-04
		 */

		// ** 设置中心点 *//*
		/*
		 * LatLng cenpot = new LatLng(Double.parseDouble(list.get(0).getLat()),
		 * Double.parseDouble(list.get(0).getLog()));
		 *  MapStatus mMapStatus = new
		 * MapStatus.Builder().target(cenpot) .zoom(15.0f).build();
		 * MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
		 * .newMapStatus(mMapStatus); mBaiduMap.setMapStatus(mMapStatusUpdate);
		 * 
		 * markers = new Marker[list.size()];
		 */
		for (int i = 0; i < list.size(); i++) {


		}

	}

	/**
	 * 清除所有Overlay
	 * 
	 * @param view
	 */
	public void clearOverlay(View view) {
		mBaiduMap.clear();
	}

	/**
	 * 重新添加Overlay
	 * 
	 * @param view
	 */
	public void resetOverlay(View view) {
		clearOverlay(null);
		initOverlay(mList);
	}

	/**
	 * 指定导航起终点启动GPS导航.起终点可为多种类型坐标系的地理坐标。 前置条件：导航引擎初始化成功
	 */
	private void launchNavigator2(LatLng latLng) {
		// 这里给出一个起终点示例，实际应用中可以通过POI检索、外部POI来源等方式获取起终点坐标
		BNaviPoint startPoint = new BNaviPoint(longitude, latitude, "啦啦",
				BNaviPoint.CoordinateType.BD09_MC);
		BNaviPoint endPoint = new BNaviPoint(latLng.longitude, latLng.latitude,
				"公租自行车点", BNaviPoint.CoordinateType.BD09_MC);
		BaiduNaviManager.getInstance().launchNavigator(this, startPoint, // 起点（可指定坐标系）
				endPoint, // 终点（可指定坐标系）
				NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME, // 算路方式
				true, // 真实导航
				BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, // 在离线策略
				new OnStartNavigationListener() { // 跳转监听

					@Override
					public void onJumpToNavigator(Bundle configParams) {
						Intent intent = new Intent(BaiduActivity.this,
								BNavigatorActivity.class);
						intent.putExtras(configParams);
						startActivity(intent);
					}

					@Override
					public void onJumpToDownloader() {
					}
				});
	}

	private boolean mIsEngineInitSuccess = false;
	private LocationClient mLocClient;
	private MyLocationListener listener = new MyLocationListener();

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			mLocClient.stop();
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	private NaviEngineInitListener mNaviEngineInitListener = new NaviEngineInitListener() {
		public void engineInitSuccess() {
			// 导航初始化是异步的，需要一小段时间，以这个标志来识别引擎是否初始化成功，为true时候才能发起导航
			mIsEngineInitSuccess = true;
		}

		public void engineInitStart() {
		}

		public void engineInitFail() {
		}
	};

	private String getSdcardDir() {
		if (Environment.getExternalStorageState().equalsIgnoreCase(
				Environment.MEDIA_MOUNTED)) {
			return Environment.getExternalStorageDirectory().toString();
		}
		return null;
	}

	public void initLocation() {
		mLocClient = new LocationClient(BaiduActivity.this);
		mLocClient.registerLocationListener(listener);
		LocationClientOption option = new LocationClientOption();
		// option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
		option.setOpenGps(true);
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度，默认值gcj02
		option.setScanSpan(1000);// 设置发起定位请求的间隔时间为5000ms
		// option.setIsNeedAddress(true);//返回的定位结果包含地址信息
		// option.setProdName("bctid-android-standard");
		// option.disableCache(true);// 设置是否启用缓存设定说明
		mLocClient.setLocOption(option);
		mLocClient.start();
		mLocClient.requestLocation();
	}

	@Override
	protected void onPause() {
		// MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		// MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		mMapView.onResume();
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mMapView.onDestroy();
		super.onDestroy();
		// 回收 bitmap 资源
		bdA.recycle();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mBaiduMap.hideInfoWindow();
		return true;
	}

	/**
	 * 计算最近距离的5个点
	 */
	private void getNearbyPoint(List<Shop> mlist) {

		List<Shop> tempList = new ArrayList<Shop>();
		Collections.addAll(tempList, new Shop[mlist.size()]);
		Collections.copy(tempList, mlist);
		/**
		 * 冒泡法排序 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
		 * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。</li>
		 * 针对所有的元素重复以上的步骤，除了最后一个。 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
		 * 将tempList按照顺序排序
		 */
		double distant1 = 0;
		double distant2 = 0;
		int size = tempList.size();
		for (int i = 0; i < size - 1; i++) {

			for (int j = 0; j < size - i - 1; j++) {
				distant1 = Math
						.pow(Double.parseDouble(tempList.get(j).getLat())
								- latitude, 2)
						+ Math.pow(Double.parseDouble(tempList.get(j).getLog())
								- longitude, 2);
				distant2 = Math.pow(
						Double.parseDouble(tempList.get(j + 1).getLat())
								- latitude, 2)
						+ Math.pow(
								Double.parseDouble(tempList.get(j + 1).getLog())
										- longitude, 2);
				if (distant1 > distant2) {
					Shop tempBicycle = tempList.get(j);
					tempList.set(j, tempList.get(j + 1));
					tempList.set(j + 1, tempBicycle);
				}

			}
		}

		/**
		 * 判断tempList的元素有多少个 如果tempList的元素不只有5个，则截取前五个元素
		 */

		if (tempList.size() > 5) {
			System.out.println("--if-tempList.sizi-->" + tempList.size());
			List<Shop> mlist2 = new ArrayList<Shop>();
			for (int i = 0; i < 5; i++) {
				mlist2.add(tempList.get(i));
			}
			clearOverlay(null);
			initOverlay(mlist2);
		} else {
			clearOverlay(null);
			initOverlay(tempList);
		}

	}
}

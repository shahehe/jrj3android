package gov.bct.jrj.activity;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.navisdk.BNaviPoint;
import com.baidu.navisdk.BaiduNaviManager;
import com.baidu.navisdk.BNaviEngineManager.NaviEngineInitListener;
import com.baidu.navisdk.BaiduNaviManager.OnStartNavigationListener;
import com.baidu.navisdk.comapi.routeplan.RoutePlanParams.NE_RoutePlan_Mode;

import gov.bct.jrj.R;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.navigator.activity.BNavigatorActivity;
import gov.bct.jrj.pojo.YuXiang;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class YuXiangDetailActivity extends Activity {
	
	private Button backButton;
	private YuXiang mXiang;
	
	private WebView webView;
	
	private TextView navigationButton;//导航03-16
	
	private TextView title_yuxian; 
	
	
	/**关于定位和导航的字段*/
	private String endPositionX=null;
	private String endPositionY=null;
	private String endName = null;
	private double latitude = gov.bct.jrj.common.Constants.DEFAULT_LAT;//纬度
	private double longitude = gov.bct.jrj.common.Constants.DEFAULT_LON;//经度
	private boolean mIsEngineInitSuccess = false;
	private LocationClient mLocClient;
	private MyLocationListener listener = new MyLocationListener();
	
	private NaviEngineInitListener mNaviEngineInitListener = new NaviEngineInitListener() {  
        public void engineInitSuccess() {
            //导航初始化是异步的，需要一小段时间，以这个标志来识别引擎是否初始化成功，为true时候才能发起导航  
            mIsEngineInitSuccess = true;  
        }  
        public void engineInitStart(){}
        public void engineInitFail(){}  
    };  
	
	private String getSdcardDir() {  
	    if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {  
	        return Environment.getExternalStorageDirectory().toString();  
	    }  
	    return null;  
	 }
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_yuxiang_detail);
		BaiduNaviManager.getInstance().initEngine(YuXiangDetailActivity.this, getSdcardDir(), mNaviEngineInitListener, Utils.getMetaValue(this, "com.baidu.lbsapi.API_KEY"),null);
		initLocation();
		
		mXiang = (YuXiang)(getIntent().getSerializableExtra("yuxiang"));
		
		//修改标题栏   04-4
		title_yuxian = (TextView) findViewById(R.id.txt_title);
		final Bundle bundle = getIntent().getExtras();
		title_yuxian.setText(bundle.getString("titleg"));
		
		backButton = (Button) findViewById(R.id.backBtn);
		navigationButton=(TextView) findViewById(R.id.navigationBtn);
		webView = (WebView) findViewById(R.id.webView1);
		
		if(mXiang.getContent()!=null&&!mXiang.getContent().equals("")&&!mXiang.getContent().equals("null")){
//			webView.loadDataWithBaseURL(null, Utils.getHtml(mXiang.getContent()), "text/html", "utf-8", "");
			webView.loadDataWithBaseURL(null, mXiang.getContent(), "text/html", "utf-8", "");
		}
		
		
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				YuXiangDetailActivity.this.finish();
			}
		});
		
		navigationButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				endName=mXiang.getName();
				endPositionX=mXiang.getLatitude();
				endPositionY=mXiang.getLongitude();
				if(endPositionX.length()!=0&&endPositionY.length()!=0){
					try {
						//map
						Intent i = new Intent(YuXiangDetailActivity.this,BaiduActivity.class);
						i.putExtra("title",mXiang.getName());
						i.putExtra("address",mXiang.getAddress());
						i.putExtra("phone","");
						i.putExtra("latitude",mXiang.getLatitude());
						i.putExtra("longitude",mXiang.getLongitude());
						startActivity(i);
						//launchNavigator2(longitude,latitude,"",Double.parseDouble(endPositionY), Double.parseDouble(endPositionX), endName);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
	}
	
	
	public void initLocation(){
        mLocClient = new LocationClient(YuXiangDetailActivity.this);
        mLocClient.registerLocationListener(listener);
        LocationClientOption option = new LocationClientOption();
//	    option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
        option.setOpenGps(true);
	    option.setCoorType("bd09ll");//返回的定位结果是百度经纬度，默认值gcj02
	    option.setScanSpan(1000);//设置发起定位请求的间隔时间为5000ms
//	    option.setIsNeedAddress(true);//返回的定位结果包含地址信息
//	    option.setProdName("bctid-android-standard");
//        option.disableCache(true);// 设置是否启用缓存设定说明  
	    mLocClient.setLocOption(option);
		mLocClient.start();
		mLocClient.requestLocation();
	}
	/**
     * 指定导航起终点启动GPS导航.起终点可为多种类型坐标系的地理坐标。
     * 前置条件：导航引擎初始化成功
     */
    private void launchNavigator2(double startLatitude,double startLongitude,String startName,double endLatitude,double endLongitude,String endName){
        //这里给出一个起终点示例，实际应用中可以通过POI检索、外部POI来源等方式获取起终点坐标
        BNaviPoint startPoint = new BNaviPoint(startLatitude,startLongitude,startName, BNaviPoint.CoordinateType.BD09_MC);
        BNaviPoint endPoint = new BNaviPoint(endLatitude,endLongitude,endName, BNaviPoint.CoordinateType.BD09_MC);
        BaiduNaviManager.getInstance().launchNavigator(YuXiangDetailActivity.this,
                startPoint,                                      //起点（可指定坐标系）
                endPoint,                                        //终点（可指定坐标系）
                NE_RoutePlan_Mode.ROUTE_PLAN_MOD_MIN_TIME,       //算路方式
                true,                                            //真实导航
                BaiduNaviManager.STRATEGY_FORCE_ONLINE_PRIORITY, //在离线策略
                new OnStartNavigationListener() {                //跳转监听
                    @Override
                    public void onJumpToNavigator(Bundle configParams) {
                        Intent intent = new Intent(YuXiangDetailActivity.this, BNavigatorActivity.class);
                        intent.putExtras(configParams);
                        YuXiangDetailActivity.this.startActivity(intent);
                    }
                    
                    @Override
                    public void onJumpToDownloader() {
                    }
                });
    }
    
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
    
	
	
	
	
}

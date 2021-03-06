package gov.bct.jrj;

import com.baidu.frontia.FrontiaApplication;
import com.baidu.mapapi.SDKInitializer;

/*
 * 如果您的工程中实现了Application的继承类，那么，您需要将父类改为com.baidu.frontia.FrontiaApplication。
 * 如果您没有实现Application的继承类，那么，请在AndroidManifest.xml的Application标签中增加属性： 
 * <application android:name="com.baidu.frontia.FrontiaApplication"
 * 。。。
 */
public class MyApplication extends FrontiaApplication {

	@Override
	public void onCreate() {
		super.onCreate();

		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
	}

}

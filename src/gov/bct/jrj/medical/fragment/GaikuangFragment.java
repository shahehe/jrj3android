package gov.bct.jrj.medical.fragment;

import gov.bct.jrj.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

/**
 * @author 欧泽华 社会服务>社区医疗 页面里的医院概况页面
 */
public class GaikuangFragment extends Fragment {

	private WebView webView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.gaikuangfragment, container,
				false);
		webView = (WebView) view.findViewById(R.id.mWebView); 
		WebSettings settings=webView.getSettings();
		settings.setJavaScriptEnabled(true); 
		settings.setUseWideViewPort(true); 
        settings.setLoadWithOverviewMode(true); 
        settings.setBuiltInZoomControls(true);//会出现放大缩小的按钮
        settings.setSupportZoom(true);
		webView.loadUrl("http://demo.bctid.com/jrj-web/public/page/about_doctor");
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		return view;
	}
	
    public boolean onKeyDown(int keyCode, KeyEvent event) {       
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {       
        	webView.goBack();       
                   return true;       
        }       
        return true;       
    }     
}

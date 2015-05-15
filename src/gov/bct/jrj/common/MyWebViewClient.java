package gov.bct.jrj.common;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		view.loadUrl(url);
		return true;

	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		// TODO Auto-generated method stub
		 super.onPageStarted(view, url, favicon);  
	}

	public MyWebViewClient() {
		super();
		// TODO Auto-generated constructor stub
	}

}

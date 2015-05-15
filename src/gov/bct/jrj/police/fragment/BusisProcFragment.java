package gov.bct.jrj.police.fragment;

import gov.bct.jrj.R;
import gov.bct.jrj.common.MyWebViewClient;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * 
 * @author ouzehua 公安服务>业务办理
 *
 */
public class BusisProcFragment extends Fragment {

	private String content;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_web, container, false);
		WebView webView = (WebView) v.findViewById(R.id.webView);
		webView.setWebViewClient(new MyWebViewClient());
		StringBuffer html = new StringBuffer();
		html.append("<!DOCTYPE HTML><html><body>");
		html.append(content + "<br/>");
		html.append("</body></html>");
		webView.loadDataWithBaseURL(null, html.toString(), "text/html",
				"utf-8", "");
		return v;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

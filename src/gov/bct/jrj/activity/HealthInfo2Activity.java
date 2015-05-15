package gov.bct.jrj.activity;


import gov.bct.jrj.R;
import gov.bct.jrj.common.MyWebViewClient;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class HealthInfo2Activity extends Activity {

	private Button btnBack;
	private TextView mTextTitle;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_activity);

		btnBack = (Button) findViewById(R.id.about_back_btn);
		
		webView = (WebView) findViewById(R.id.webView);
		mTextTitle = (TextView) findViewById(R.id.txt_title);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				HealthInfo2Activity.this.finish();
			}
		});

		webView.setWebViewClient(new MyWebViewClient());
		Bundle bundle = getIntent().getExtras();
		String title = bundle.getString("title");
		String content=bundle.getString("content");
		mTextTitle.setText(title);
	
		StringBuffer html = new StringBuffer();
		html.append("<!DOCTYPE HTML><html><body>");
		html.append(content+"<br/>");
		html.append("</body></html>");
		webView.loadDataWithBaseURL(null, html.toString(), "text/html", "utf-8", "");
	}

}

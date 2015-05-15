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

/**
 * 
 * @author 欧泽华
 * 服务实现>办事程序 最后一级的页面（详情页面）
 */
public class ServiceInfoDetailsActivity extends Activity {

	private Button btnBack;
	private TextView mTextTitle;
	private WebView webView;
	
	private TextView sub_title_txt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_info_details);

		btnBack = (Button) findViewById(R.id.about_back_btn);
		sub_title_txt=(TextView) findViewById(R.id.sub_title);
		webView = (WebView) findViewById(R.id.webView);
		mTextTitle = (TextView) findViewById(R.id.txt_title);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				ServiceInfoDetailsActivity.this.finish();
			}
		});

		webView.setWebViewClient(new MyWebViewClient());
		Bundle bundle = getIntent().getExtras();
		String title = bundle.getString("title");
		String sub_title=bundle.getString("sub_title");
		String content=bundle.getString("content");	
		mTextTitle.setText(title);
		sub_title_txt.setText(sub_title);
				
		StringBuffer html = new StringBuffer();
		html.append("<!DOCTYPE HTML><html><body>");
		html.append(content+"<br/>");
		html.append("</body></html>");
		webView.loadDataWithBaseURL(null, html.toString(), "text/html", "utf-8", "");
	}

}

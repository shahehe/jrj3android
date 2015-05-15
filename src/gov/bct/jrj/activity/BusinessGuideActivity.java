package gov.bct.jrj.activity;

import gov.bct.jrj.R;
import gov.bct.jrj.common.MyWebViewClient;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class BusinessGuideActivity extends Activity {

	private ImageView phoneButton;//打电话
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_guide);
		
		Button backBtn = (Button) findViewById(R.id.about_back_btn);
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				BusinessGuideActivity.this.finish();
			}
		});
		
		Bundle bundle=getIntent().getExtras();
		
		
		/**如果传进来的值有电话号码，那么打电话的按钮需要显示*/
		final String phoneNume=bundle.getString("phone");
		System.out.println("phoneNume:"+phoneNume);
		if(!phoneNume.equals("")){
			phoneButton=(ImageView) findViewById(R.id.imageview);
			phoneButton.setVisibility(View.VISIBLE);
			phoneButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNume));
					startActivity(intent);  
				}
			});
		}
		String title=bundle.getString("title");
		String content=bundle.getString("content");
		TextView titleTxt=(TextView) findViewById(R.id.txt_title);
		WebView webView=(WebView) findViewById(R.id.webView);
		
		webView.setWebViewClient(new MyWebViewClient());
		titleTxt.setText(title);
		
		StringBuffer html = new StringBuffer();
		html.append("<!DOCTYPE HTML><html><body>");
		html.append(content+"<br/>");
		html.append("</body></html>");
		webView.loadDataWithBaseURL(null, html.toString(), "text/html", "utf-8", "");
	}
}

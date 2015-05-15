package gov.bct.jrj.activity;


import gov.bct.jrj.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author ouzehua
 * 工商服务页面> 简介
 *
 */
public class IntroActivity extends Activity {

	private TextView title;
	private Button backBtn;
	private TextView contentTxt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro_activity);
			
		title=(TextView)findViewById(R.id.txt_title);
		title.setText(getIntent().getStringExtra("title"));
		
		backBtn=(Button) findViewById(R.id.detail_back_btn);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				IntroActivity.this.finish();
			}
		});
		
		contentTxt=(TextView)findViewById(R.id.detail_txt_content);
		contentTxt.setText("简介啊简介啊简介啊简介啊简介啊简介啊简介啊简介啊简介啊简介啊");
		
	}
}

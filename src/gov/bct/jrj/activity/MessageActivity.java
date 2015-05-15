package gov.bct.jrj.activity;

import gov.bct.jrj.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MessageActivity extends Activity {

	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		TextView txtTitle=(TextView) findViewById(R.id.txt_title);
		txtTitle.setText("食药安全>消息推送");
		Button backBtn=(Button) findViewById(R.id.backBtn);
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MessageActivity.this.finish();
			}
		});
		
		listView=(ListView)findViewById(R.id.list);
	}
}

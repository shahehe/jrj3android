package gov.bct.jrj.activity;

import gov.bct.jrj.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class PushActivity extends Activity {

	private Button backBtn;
	private TextView titleTxt;
	private ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		initView();
	}
	private void initView() {
		backBtn=(Button) findViewById(R.id.backBtn);
		titleTxt=(TextView) findViewById(R.id.txt_title);
		lv=(ListView) findViewById(R.id.list);
	}
}

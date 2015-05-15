package gov.bct.jrj.activity;

import gov.bct.jrj.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

/**
 * 你问我答
 * @author 欧泽华
 *
 */
public class AnswerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_answer);
		initView();
	}

	private void initView() {
		TextView mTitle=(TextView) findViewById(R.id.txt_title);
		Button backBtn=(Button) findViewById(R.id.about_back_btn);
	}
}

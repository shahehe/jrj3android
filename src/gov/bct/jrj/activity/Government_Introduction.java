package gov.bct.jrj.activity;


import gov.bct.jrj.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Government_Introduction extends Activity{
	
//	private int id;
	
	private TextView mTextTitle;
	private TextView mSubjectTitle;
	private TextView mTextContent;
	private Button backButton;

	@Override  
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.text_item_detail_content);
	
		backButton = (Button) findViewById(R.id.detail_back_btn);
		mTextTitle=(TextView) findViewById(R.id.txt_title);
		mSubjectTitle=(TextView) findViewById(R.id.detail_txt_subject);
		mTextContent=(TextView) findViewById(R.id.detail_txt_content);
		
		Bundle bundle =getIntent().getExtras();
//		id=bundle.getInt("id");
		mTextTitle.setText(bundle.getString("title"));
		mSubjectTitle.setText(bundle.getString("description"));
		mTextContent.setText(Html.fromHtml(bundle.getString("content")));
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Government_Introduction.this.finish();
			}
		});
		
		
	}
			
}
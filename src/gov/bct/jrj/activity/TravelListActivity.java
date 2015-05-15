package gov.bct.jrj.activity;

import com.squareup.picasso.Picasso;

import gov.bct.jrj.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class TravelListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_travel_content);
		Button backbtn=(Button) findViewById(R.id.backBtn);
		backbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TravelListActivity.this.finish();
			}
		});
		
		TextView title=(TextView) findViewById(R.id.txt_title);
		TextView content=(TextView) findViewById(R.id.travelContent);
		ImageView image=(ImageView) findViewById(R.id.travelImage);
		
		Bundle bundle=getIntent().getExtras();
		title.setText(bundle.getString("title"));
		String imagePath=bundle.getString("image");
		if(imagePath!=null&&!imagePath.equals("")&&!imagePath.equals("null")){
			Picasso.with(TravelListActivity.this).load(imagePath).into(image);
		}
		
		content.setText(bundle.getString("content"));
		
	}
}

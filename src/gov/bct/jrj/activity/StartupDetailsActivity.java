package gov.bct.jrj.activity;

import com.squareup.picasso.Picasso;

import gov.bct.jrj.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StartupDetailsActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_startupdetails);
		Button backbtn=(Button) findViewById(R.id.backBtn);
		backbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				StartupDetailsActivity.this.finish();
			}
		});
		
		TextView title=(TextView) findViewById(R.id.txt_title);
		TextView content=(TextView) findViewById(R.id.travelContent);
		ImageView image=(ImageView) findViewById(R.id.travelImage);
		
		Bundle bundle=getIntent().getExtras();
		title.setText(bundle.getString("title"));
		String imagePath=bundle.getString("image");
		if(imagePath!=null&&!imagePath.equals("")&&!imagePath.equals("null")){
			Picasso.with(StartupDetailsActivity.this).load(imagePath).into(image);
		}else {
			image.setVisibility(View.GONE);
		}
		
		content.setText((Html.fromHtml(bundle.getString("content"))).toString());
	}
}

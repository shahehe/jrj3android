package gov.bct.jrj.activity;

import com.squareup.picasso.Picasso;

import gov.bct.jrj.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author ouzehua 社区活动子页面
 *
 */
public class CommunityDetailsActivity extends Activity {

	private TextView shopNameTxt;// 商铺名称
	private TextView addressTxt;// 地址
	private TextView phoneNumTxt;// 联系号码
//	private TextView contentDetailsTxt;// 详细内容
	private ImageView shopImage;// 图片
	private ImageView phoneImage;// 打电话
	
	private TextView txt_position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_community_details);
		Button backBtn = (Button) findViewById(R.id.backBtn);
		
		
		
		
		
		
		
		backBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommunityDetailsActivity.this.finish();
			}
		});

		shopNameTxt = (TextView) findViewById(R.id.shopName);
		addressTxt = (TextView) findViewById(R.id.address);
		phoneNumTxt = (TextView) findViewById(R.id.phoneNum);
//		contentDetailsTxt = (TextView) findViewById(R.id.content_details);
		shopImage = (ImageView) findViewById(R.id.shopImage);
		phoneImage = (ImageView) findViewById(R.id.phoneImage);

		final Bundle bundle = getIntent().getExtras();

		/** 商铺名称 */
		shopNameTxt.setText(bundle.getString("title"));
		/** 所属地址 */
		addressTxt.setText(bundle.getString("address"));
		/** 联系电话 */
		phoneNumTxt.setText(bundle.getString("phone"));
		/** 详细内容 */
//		contentDetailsTxt.setText(Html.fromHtml(bundle.getString("content")));
		final String loga=(String) bundle.get("longitude");
		final String latb=(String) bundle.get("latitude");
		
		
		

		//添加的位置信息            社区活动            2015-4-1
				txt_position = (TextView) findViewById(R.id.txt_position);
				
				txt_position.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(CommunityDetailsActivity.this, BaiduActivity.class);
						
						intent.putExtra("longitude",loga);  //经度
						intent.putExtra("latitude", latb);  //纬度
						intent.putExtra("title",shopNameTxt.getText().toString());
						intent.putExtra("address",addressTxt.getText().toString());
						intent.putExtra("phone",phoneNumTxt.getText().toString());
						intent.putExtras(bundle);  
						
			            startActivity(intent);
					}
				});

		String imagePath = bundle.getString("image");
		if (imagePath != null && !imagePath.equals("")
				&& !imagePath.equals("null")) {
			Picasso.with(CommunityDetailsActivity.this).load(imagePath)
					.into(shopImage);
		} else {
			shopImage.setVisibility(View.GONE);
		}

		phoneImage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 传入服务， parse（）解析号码
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ bundle.getString("phone")));
				startActivity(intent);
			}
		});
	}
}

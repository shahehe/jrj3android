package gov.bct.jrj.adapter;

import gov.jrj.R;
import gov.bct.jrj.common.RoundAngleImageView;
import gov.bct.jrj.pojo.TravelCulture;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TravelCultureAdapter extends BaseAdapter {
	Context context;
	List<TravelCulture> items;
	
	public TravelCultureAdapter(Context context,List<TravelCulture> items){
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public TravelCulture getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		TravelCulture item = getItem(position);
		if(convertView==null){
			convertView = View.inflate(context, R.layout.yuxiang_list_item,null);
//			imageView2.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					
//				}
//			});
		}
		RoundAngleImageView imageView = (RoundAngleImageView) convertView.findViewById(R.id.imageView1);
		TextView textView1 = (TextView) convertView.findViewById(R.id.textView1);
		TextView textView2 = (TextView) convertView.findViewById(R.id.textView2);
		ImageView imageView2 = (ImageView) convertView.findViewById(R.id.locationIV);
		imageView2.setVisibility(View.GONE);
		if(item.getImage()!=null&&!item.getImage().equals("")&&!item.getImage().equals("null")){
			Picasso.with(context).load(item.getImage()).into(imageView);
			imageView.setRoundHeight(30);
			imageView.setRoundHeight(30);
		}
		if(item.getName()!=null&&!item.getName().equals("")&&!item.getName().equals("null")){
			textView1.setText(item.getName());
		}
		if(item.getStorey()!=null&&!item.getStorey().equals("")&&!item.getStorey().equals("null")){
			textView2.setText(item.getStorey());
		}
		return convertView;
	}
}

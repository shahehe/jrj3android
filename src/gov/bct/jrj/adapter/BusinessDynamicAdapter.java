package gov.bct.jrj.adapter;

import gov.bct.jrj.R;
import gov.bct.jrj.common.RoundAngleImageView;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.pojo.BusinessDynamic;
import gov.bct.jrj.pojo.HealthService;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author ouzehua 
 * 食药安全>信息发布列表的Adapter
 */
public class BusinessDynamicAdapter extends BaseAdapter {
	Context context;
	List<BusinessDynamic> items;

	public BusinessDynamicAdapter(Context context, List<BusinessDynamic> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public BusinessDynamic getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		BusinessDynamic item = getItem(position);
		
		ViewHolder viewHolder=null;
		
		if (convertView == null) {
			viewHolder=new ViewHolder();
			convertView = View.inflate(context, R.layout.message_list_item,null);	
//			viewHolder.imageView= (RoundAngleImageView) convertView
//					.findViewById(R.id.imageView1);
			viewHolder.textView= (TextView) convertView
					.findViewById(R.id.textView1);
			viewHolder.contentTxt= (TextView) convertView
					.findViewById(R.id.textView2);
			viewHolder.timeText= (TextView) convertView
					.findViewById(R.id.textView3);
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
//		if (item.getImage() != null && !item.getImage().equals("")
//				&& !item.getImage().equals("null")) {
//			Picasso.with(context).load(item.getImage()).into(viewHolder.imageView);
//			viewHolder.imageView.setRoundHeight(30);
//			viewHolder.imageView.setRoundHeight(30);
//		}

		viewHolder.textView.setText(item.getTitle());
		viewHolder.timeText.setText(Utils.formatDateShort(item.getDate()));
		viewHolder.contentTxt.setText(item.getDesc());
		return convertView;
	}
	
	private class ViewHolder{
		TextView textView;	
		TextView contentTxt;
		TextView timeText;
	}
}

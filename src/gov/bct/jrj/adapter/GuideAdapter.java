package gov.bct.jrj.adapter;

import gov.bct.jrj.R;
import gov.bct.jrj.pojo.Guide;

import java.util.List;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author ouzehua 
 * 工商服务>服务指南列表的Adapter
 */
public class GuideAdapter extends BaseAdapter {
	Context context;
	List<Guide> items;

	public GuideAdapter(Context context, List<Guide> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Guide getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		Guide item = getItem(position);
		
		ViewHolder viewHolder=null;
		
		if (convertView == null) {
			viewHolder=new ViewHolder();
			convertView = View.inflate(context, R.layout.guide_list_item,
					null);	
//			viewHolder.imageView= (RoundAngleImageView) convertView
//					.findViewById(R.id.imageView1);
			viewHolder.title= (TextView) convertView
					.findViewById(R.id.textView1);
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

		viewHolder.title.setText(item.getTitle());
		return convertView;
	}
	
	private class ViewHolder{
		TextView title;	
	}
}

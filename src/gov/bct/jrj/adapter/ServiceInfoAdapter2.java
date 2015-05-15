package gov.bct.jrj.adapter;

import gov.jrj.R;
import gov.bct.jrj.common.RoundAngleImageView;
import gov.bct.jrj.pojo.ServiceInfo2;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
 * @author ouzehua 服务事项第二级页面的Adapter
 */
public class ServiceInfoAdapter2 extends BaseAdapter {
	Context context;
	List<ServiceInfo2> items;

	public ServiceInfoAdapter2(Context context, List<ServiceInfo2> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public ServiceInfo2 getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		ServiceInfo2 item = getItem(position);
		ViewHolder viewHolder=null;
		if (convertView == null) {
			viewHolder=new ViewHolder();
			convertView = View.inflate(context, R.layout.activity_service_list,
					null);
			viewHolder.imageView = (RoundAngleImageView) convertView
					.findViewById(R.id.imageView1);
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.textView1);
		}
		
		// if(item.getImage()!=null&&!item.getImage().equals("")&&!item.getImage().equals("null")){
		// Picasso.with(context).load(item.getImage()).into(imageView);
		// imageView.setRoundHeight(30);
		// imageView.setRoundHeight(30);
		// }
		// if(item.getName()!=null&&!item.getName().equals("")&&!item.getName().equals("null")){
		// textView1.setText(item.getName());
		// }
		// if(item.getStorey()!=null&&!item.getStorey().equals("")&&!item.getStorey().equals("null")){
		// textView2.setText(item.getStorey());
		// }

		// imageView.setImageResource((Integer) item.get("ic_operate"));

		viewHolder.textView.setText(item.getDescription());
		return convertView;
	}
	
	private class ViewHolder{
		RoundAngleImageView imageView;
		TextView textView;	
	}
}

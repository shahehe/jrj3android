package gov.bct.jrj.adapter;

import gov.jrj.R;
import gov.bct.jrj.common.RoundAngleImageView;
import gov.bct.jrj.pojo.PermissionF;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * @author ouzehua 
 * 许可办理的Fragment的Adapter
 */
public class PermissionFAdapter extends BaseAdapter {
	Context context;
	List<PermissionF> items;

	public PermissionFAdapter(Context context, List<PermissionF> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public PermissionF getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		PermissionF item = getItem(position);
		
		ViewHolder viewHolder=null;
		
		if (convertView == null) {
			viewHolder=new ViewHolder();
			convertView = View.inflate(context, R.layout.activity_service_list,
					null);	
			viewHolder.imageView= (RoundAngleImageView) convertView
					.findViewById(R.id.imageView1);
			viewHolder.textView= (TextView) convertView
					.findViewById(R.id.textView1);
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		if (item.getImage() != null && !item.getImage().equals("")
				&& !item.getImage().equals("null")) {
			Picasso.with(context).load(item.getImage()).into(viewHolder.imageView);
			viewHolder.imageView.setRoundHeight(30);
			viewHolder.imageView.setRoundHeight(30);
		}

		viewHolder.textView.setText(item.getName());
		return convertView;
	}
	
	private class ViewHolder{
		RoundAngleImageView imageView;
		TextView textView;	
	}
}

package gov.bct.jrj.fragment;

import gov.bct.jrj.R;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridviewAdapter extends BaseAdapter {
	Context context;
	List<HashMap<String, Object>> items;
	public GridviewAdapter(Context context,List<HashMap<String, Object>> items){
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public HashMap<String, Object> getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		HashMap<String, Object> item = getItem(position);
		if(convertView==null){
			convertView = View.inflate(context, R.layout.gridview_item,null);
			ImageView imageView = (ImageView) convertView.findViewById(R.id.itemImg);
			TextView textView = (TextView) convertView.findViewById(R.id.itemName);
			imageView.setBackgroundResource(Integer.parseInt(item.get("itemImg").toString()));
//			textView.setText(item.get("itemName").toString());
		}
		return convertView;
	}

}

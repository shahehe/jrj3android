package gov.bct.jrj.adapter;

import gov.bct.jrj.R;
import gov.bct.jrj.pojo.Shop;

import java.util.List;

import com.squareup.picasso.Picasso;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author ouzehua
 * 便民商铺Adapter
 */
public class ShopAdapter extends BaseAdapter {
	Context context;
	List<Shop> items;
	
	public ShopAdapter(Context context,List<Shop> items){
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Shop getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		Shop item = getItem(position);
		ViewHolder viewHolder=null;
		if(convertView==null){
			convertView = View.inflate(context, R.layout.shop_list_item,null);
			viewHolder=new ViewHolder();
			viewHolder.imageView= (ImageView) convertView.findViewById(R.id.imageView1);
			viewHolder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
			viewHolder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
			viewHolder.textView3 = (TextView) convertView.findViewById(R.id.textView3);
			viewHolder.textView4 = (TextView) convertView.findViewById(R.id.textView4);
			convertView.setTag(viewHolder);
			
		}
		else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		
		if(item.getImage()!=null&&!item.getImage().equals("")&&!item.getImage().equals("null")){
			Picasso.with(context).load(item.getImage()).into(viewHolder.imageView);
		}
		if(item.getTitle()!=null&&!item.getTitle().equals("")&&!item.getTitle().equals("null")){
			viewHolder.textView1.setText(item.getTitle());
		}
		if(item.getCopyright()!=null&&!item.getCopyright().equals("")&&!item.getCopyright().equals("null")){
			viewHolder.textView2.setText(item.getCopyright());
		}
		if(item.getSummary()!=null&&!item.getSummary().equals("")&&!item.getSummary().equals("null")){
			viewHolder.textView3.setText(item.getSummary());
		}
		if(item.getAuthor()!=null&&!item.getAuthor().equals("")&&!item.getAuthor().equals("null")){
			viewHolder.textView4.setText(item.getAuthor());
		}
		return convertView;
	}
	
	private class ViewHolder{
		ImageView imageView;
		TextView textView1;
		TextView textView2;
		TextView textView3;
		TextView textView4;
	}
}

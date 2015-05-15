package gov.bct.jrj.adapter;

import gov.bct.jrj.R;
import gov.bct.jrj.pojo.Consult;

import java.util.List;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 专家门诊数据的适配器
 */
public class ConsultAdapter extends BaseAdapter {
	Context context;
	List<Consult> items;
	
	public ConsultAdapter(Context context,List<Consult> items){
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Consult getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		Consult item = getItem(position);
		ViewHolder holder=null;
		if(convertView==null){
			convertView = View.inflate(context, R.layout.consultfragment_list_item,null);
			holder=new ViewHolder();
			holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
			holder.textView2 = (TextView) convertView.findViewById(R.id.textView2);
			holder.textView3 = (TextView) convertView.findViewById(R.id.textView3);
			holder.textView4 = (TextView) convertView.findViewById(R.id.textView4);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		
		if(item.getName()!=null&&!item.getName().equals("")&&!item.getName().equals("null")){
			holder.textView1.setText(item.getName());
		}
		if(item.getOffice()!=null&&!item.getOffice().equals("")&&!item.getOffice().equals("null")){
			holder.textView3.setText(item.getOffice());
		}
		if(item.getSpeciality()!=null&&!item.getSpeciality().equals("")&&!item.getSpeciality().equals("null")){
			holder.textView2.setText(item.getSpeciality());
		}
		if(item.getOut_call_time()!=null&&!item.getOut_call_time().equals("")&&!item.getOut_call_time().equals("null")){
			holder.textView4.setText(item.getOut_call_time());
		}
		return convertView;
	}
	
	private class ViewHolder{
		TextView textView1;
		TextView textView2;
		TextView textView3;
		TextView textView4;
		
	}
}

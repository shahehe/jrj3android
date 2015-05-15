package gov.bct.jrj.adapter;


import gov.bct.jrj.R;

import java.util.ArrayList;
import java.util.List;

import com.baidu.navisdk.ui.routeguide.subview.r;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * @author ouzeha
 *Viewpager的适配器
 */
public class MyViewPagerAdapter extends PagerAdapter{

	private List<ImageView>image;
	private List<String> imageViews;
	private Context context;
	
	public MyViewPagerAdapter(List<String> imageViews,Context context) {
		this.imageViews=imageViews;
		this.context=context;
	}

	@Override
	public int getCount() {
		return imageViews.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView imageView=new ImageView(context);
		if(!imageViews.get(position).equals("")){		
			Picasso.with(context).load(imageViews.get(position)).into(imageView);
	        ((ViewPager)container).addView(imageView);	       
		}else {
			imageView.setImageResource(R.drawable.no_image);
			((ViewPager)container).addView(imageView);	
		}
		imageView.setScaleType(ScaleType.CENTER_CROP);
		return imageView;   
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((View) object);
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}

}

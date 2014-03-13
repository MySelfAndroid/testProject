package com.easy.apps.easyopenvpn;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LockItemAdapter extends BaseAdapter{
	Context context;
	List<Map> listData;
	public final static String ICON = "ICON";
	public final static String APPNAME = "APPNAME";
	
	public LockItemAdapter(Context context,List<Map> listData) {  
		this.context = context;  
		this.listData = listData;
	}  
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(listData!=null)
			return listData.size(); 
		else
			return 0;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		if(listData!=null)
			return listData.get(arg0);
		else
			return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		if(listData!=null)
			return arg0; 
		else
			return 0;
	}
	static class ContactsViewHolder {
		ImageView icon;
		TextView appName;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		
		final Map item = listData.get(arg0);
		
		LayoutInflater mInflater = LayoutInflater.from(context);
		convertView = mInflater.inflate(R.layout.items,null);
		
		ImageView icon = (ImageView) convertView
				.findViewById(R.id.app_icon);
		TextView appName = (TextView) convertView
				.findViewById(R.id.app_name);
		
		
		icon.setImageDrawable((Drawable)item.get(ICON));	
		appName.setText((String)item.get(APPNAME));
		
		return convertView;
	}

}

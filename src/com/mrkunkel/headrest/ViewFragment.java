package com.mrkunkel.headrest;

import com.mrkunkel.headrest.adapter.ServiceItemAdapter;
import com.mrkunkel.headrest.model.ServiceItem;
import java.util.ArrayList;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewFragment extends ListFragment {

	private ArrayList<ServiceItem> mServiceItems;
	private Context mContext;

	public ViewFragment(Context context, ArrayList<ServiceItem> serviceItems) {
		mServiceItems = serviceItems;
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_view, container,
				false);
		setListAdapter(new ServiceItemAdapter(mContext, mServiceItems));
		setRetainInstance(true);
		return rootView;
	}

}
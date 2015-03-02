package com.mrkunkel.headrest.adapter;

import com.mrkunkel.headrest.R;
import com.mrkunkel.headrest.model.ActionItem;
import com.mrkunkel.headrest.model.SelectItem;
import com.mrkunkel.headrest.model.ServiceItem;
import com.mrkunkel.headrest.model.SliderItem;
import com.mrkunkel.headrest.model.StatusItem;
import com.mrkunkel.headrest.model.SwitchItem;
import com.mrkunkel.headrest.model.WeburlItem;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class ServiceItemAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<ServiceItem> serviceItems;

	public ServiceItemAdapter(Context context,
			ArrayList<ServiceItem> serviceItems) {
		this.mContext = context;
		this.serviceItems = serviceItems;
	}

	@Override
	public int getCount() {
		return serviceItems.size();
	}

	@Override
	public Object getItem(int position) {
		return serviceItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) mContext
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(serviceItems.get(position)
					.getLayout(), null);
		}

		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
		TextView txtName = (TextView) convertView
				.findViewById(R.id.service_name);

		imgIcon.setImageResource(serviceItems.get(position).getIcon());
		txtName.setText(serviceItems.get(position).getName());

		if (serviceItems.get(position).getClass().equals(SwitchItem.class)) {

			Switch mSwitch;
			mSwitch = (Switch) convertView.findViewById(R.id.item_switch);
			mSwitch.setChecked(((SwitchItem) serviceItems.get(position))
					.getState());

			mSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {

					if (isChecked) {
						// switchStatus.setText("Switch is currently ON");
					} else {
						// switchStatus.setText("Switch is currently OFF");
					}
				}
			});

		} else if (serviceItems.get(position).getClass()
				.equals(SliderItem.class)) {

			SeekBar seekBar = (SeekBar) convertView.findViewById(R.id.slider);
			seekBar.setMax(((SliderItem) serviceItems.get(position)).getMax());
			seekBar.setProgress(80);

		} else if (serviceItems.get(position).getClass()
				.equals(SelectItem.class)) {

			Spinner spinner = (Spinner) convertView.findViewById(R.id.spinner);
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
					mContext, android.R.layout.simple_spinner_item,
					((SelectItem) serviceItems.get(position)).getOptions());
			dataAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(dataAdapter);

		} else if (serviceItems.get(position).getClass()
				.equals(StatusItem.class)) {

			TextView txtStatus = (TextView) convertView
					.findViewById(R.id.status);
			txtStatus.setText(((StatusItem) serviceItems.get(position))
					.getStatus());

		} else if (serviceItems.get(position).getClass()
				.equals(ActionItem.class)) {

			Button mButton;
			mButton = (Button) convertView.findViewById(R.id.item_button);
			mButton.setText("Run");
			mButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// Do something in response to button click
				}
			});

		} else if (serviceItems.get(position).getClass()
				.equals(WeburlItem.class)) {

			final Uri weburlPath = Uri.parse(serviceItems.get(position)
					.getPath());
			RelativeLayout rl = (RelativeLayout) convertView
					.findViewById(R.id.weburlItemLayout);
			rl.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(weburlPath);
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					mContext.startActivity(i);
				}
			});
		}

		return convertView;
	}
}

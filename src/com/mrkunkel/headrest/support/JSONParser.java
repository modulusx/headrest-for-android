package com.mrkunkel.headrest.support;

import android.util.Log;

import com.mrkunkel.headrest.model.ActionItem;
import com.mrkunkel.headrest.model.SelectItem;
import com.mrkunkel.headrest.model.ServiceItem;
import com.mrkunkel.headrest.model.SliderItem;
import com.mrkunkel.headrest.model.StatusItem;
import com.mrkunkel.headrest.model.SwitchItem;
import com.mrkunkel.headrest.model.WeburlItem;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParser {
	private static final boolean DEBUG = true;
	private JSONObject jsonObject;
	private String type;
	private String version;
	private ArrayList<ServiceItem> mServiceList;
	private ArrayList<ServiceItem> mServiceItems;

	private static final String HR_TYPE = "type";
	private static final String HR_VERSION = "version";
	private static final String HR_TYPE_LIST = "service_list";
	private static final String HR_SERVICES = "services";

	private static final String HR_TYPE_DESC = "service_description";
	private static final String HR_NAME = "service_name";
	private static final String HR_STATE = "service_state";

	private static final String HR_SERVICE_NAME = "name";
	private static final String HR_SERVICE_PATH = "path";
	private static final String HR_SERVICE_STATUS = "status";
	private static final String HR_SERVICE_VALUES = "values";
	private static final String HR_SERVICE_RANGE = "range";
	private static final String HR_SERVICE_MIN = "min";
	private static final String HR_SERVICE_MAX = "max";

	private static final String HR_SERVICE_VIEWS = "views";
	private static final String HR_DEFAULT_VIEW = "default";

	private static final String HR_SI_SWITCH = "SwitchItem";
	private static final String HR_SI_SLIDER = "SliderItem";
	private static final String HR_SI_SELECT = "SelectItem";
	private static final String HR_SI_STATUS = "StatusItem";
	private static final String HR_SI_ACTION = "ActionItem";
	private static final String HR_SI_WEBURL = "WeburlItem";

	public JSONParser(String jsonString) {
		try {
			jsonObject = new JSONObject(jsonString);
			type = jsonObject.getString(HR_TYPE);
			if (DEBUG)
				Log.d("type: ", "> " + type);

			version = jsonObject.getString(HR_VERSION);
			if (DEBUG)
				Log.d("version: ", "> " + version);

			if (type.equals(HR_TYPE_LIST)) {
				this.parseServiceList(jsonObject.getJSONArray(HR_SERVICES));
			} else if (type.equals(HR_TYPE_DESC)) {

				String name = jsonObject.getString(HR_NAME);
				String state = jsonObject.getString(HR_STATE);
				JSONObject views = jsonObject.getJSONObject(HR_SERVICE_VIEWS);

				this.parseServiceDesc(name, state, views);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseServiceList(JSONArray services) {
		try {
			mServiceList = new ArrayList<ServiceItem>();
			ServiceItem serviceInfo;
			JSONObject service;
			for (int i = 0; i < services.length(); i++) {
				service = services.getJSONObject(i);
				serviceInfo = new ServiceItem(
						service.getString(HR_SERVICE_NAME),
						service.getString(HR_SERVICE_PATH));

				mServiceList.add(serviceInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseServiceDesc(String serviceName, String state,
			JSONObject views) {
		if (!state.equals("Connected"))
			return;
		try {
			mServiceItems = new ArrayList<ServiceItem>();

			JSONArray defaultView = views.getJSONArray(HR_DEFAULT_VIEW);
			JSONObject serviceItem;
			String serviceItemType;

			for (int i = 0; i < defaultView.length(); i++) {
				serviceItem = defaultView.getJSONObject(i);
				serviceItemType = serviceItem.getString(HR_TYPE);

				if (serviceItemType.equals(HR_SI_SWITCH)) {
					mServiceItems.add(new SwitchItem(serviceItem
							.getString(HR_SERVICE_NAME), serviceItem
							.getString(HR_SERVICE_PATH), Boolean
							.parseBoolean(serviceItem
									.getString(HR_SERVICE_STATUS))));

				} else if (serviceItemType.equals(HR_SI_SLIDER)) {

					int value = Integer.parseInt(serviceItem
							.getString(HR_SERVICE_STATUS));
					JSONArray values = serviceItem
							.getJSONArray(HR_SERVICE_VALUES);
					JSONObject range = values.getJSONObject(3).getJSONObject(
							HR_SERVICE_RANGE);

					int minValue = Integer.parseInt(range
							.getString(HR_SERVICE_MIN));
					int maxValue = Integer.parseInt(range
							.getString(HR_SERVICE_MAX));

					mServiceItems.add(new SliderItem(serviceItem
							.getString(HR_SERVICE_NAME), serviceItem
							.getString(HR_SERVICE_PATH), minValue, maxValue,
							value));

				} else if (serviceItemType.equals(HR_SI_SELECT)) {

					List<String> options = new ArrayList<String>();

					String value = serviceItem.getString(HR_SERVICE_STATUS);

					JSONArray values = serviceItem
							.getJSONArray(HR_SERVICE_VALUES);

					options.add(value);

					for (int j = 0; j < values.length(); j++)
						if (!value.equals(values.getString(j)))
							options.add(values.getString(j));

					mServiceItems.add(new SelectItem(serviceItem
							.getString(HR_SERVICE_NAME), serviceItem
							.getString(HR_SERVICE_PATH), options));

				} else if (serviceItemType.equals(HR_SI_STATUS)) {
					mServiceItems.add(new StatusItem(serviceItem
							.getString(HR_SERVICE_NAME), serviceItem
							.getString(HR_SERVICE_PATH), serviceItem
							.getString(HR_SERVICE_STATUS)));

				} else if (serviceItemType.equals(HR_SI_ACTION)) {
					mServiceItems.add(new ActionItem(serviceItem
							.getString(HR_SERVICE_NAME), serviceItem
							.getString(HR_SERVICE_PATH)));

				} else if (serviceItemType.equals(HR_SI_WEBURL)) {
					mServiceItems.add(new WeburlItem(serviceItem
							.getString(HR_SERVICE_NAME), serviceItem
							.getString(HR_SERVICE_PATH)));

				} else {
					mServiceItems.add(new ServiceItem(serviceItem
							.getString(HR_SERVICE_NAME), serviceItem
							.getString(HR_SERVICE_PATH)));
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getVersion() {
		return version;
	}

	public ArrayList<ServiceItem> getServiceList() {
		return mServiceList;
	}

	public ArrayList<ServiceItem> getServiceItems() {
		return mServiceItems;
	}

}

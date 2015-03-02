package com.mrkunkel.headrest;

import com.mrkunkel.headrest.adapter.ServiceItemAdapter;
import com.mrkunkel.headrest.model.ServiceItem;
import com.mrkunkel.headrest.support.JSONParser;
import com.mrkunkel.headrest.support.ServiceHandler;
import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {
	private Context mContext;

	private ProgressDialog pDialog;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private int mItemPosition;
	private ServiceItem mServiceItem;
	private ArrayList<ServiceItem> mServiceItems;
	private ArrayList<ServiceItem> mSelectedServiceItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mContext = getApplicationContext();

		mTitle = mDrawerTitle = getTitle();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		if (mServiceItems == null)
			new GetServiceList().execute();

		mDrawerList.setOnItemClickListener(new NavDrawerClickListener());

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.app_name, R.string.app_name) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// mSelectedServiceItems = mServiceItems;
			// displayView();
		}
	}

	/**
	 * Navigation Drawer click listener
	 * */
	private class NavDrawerClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			mItemPosition = position;
			mServiceItem = mServiceItems.get(mItemPosition);

			new GetServiceDesc().execute();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:

			startActivity(new Intent(this, PreferencesActivity.class));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Displaying fragment view for selected nav drawer list item
	 * */
	private void displayView() {
		// update the main content by replacing fragments

		(getSupportFragmentManager())
				.beginTransaction()
				.replace(R.id.frame_container,
						new ViewFragment(mContext, mSelectedServiceItems))
				.commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(mItemPosition, true);
		mDrawerList.setSelection(mItemPosition);
		setTitle(mServiceItem.getName());
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	/**
	 * Async task class to get json SERVICE LIST by making HTTP call
	 * */
	private class GetServiceList extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Loading ServiceList...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			SharedPreferences asp = PreferenceManager
					.getDefaultSharedPreferences(mContext);

			String headrestServer = "http://"
					+ asp.getString("serverurl", "headrest.mrkunkel.com")
					+ "/headrest/";

			String jsonString = (new ServiceHandler()).makeServiceCall(
					headrestServer, asp.getString("username", "headrest"),
					asp.getString("password", "password"), ServiceHandler.GET);

			mServiceItems = (new JSONParser(jsonString)).getServiceList();

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();

			mDrawerList.setAdapter(new ServiceItemAdapter(mContext,
					mServiceItems));

		}

	}

	/**
	 * Async task class to get json SERVICE DESCRIPTION by making HTTP call
	 * */
	private class GetServiceDesc extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Loading ServiceItems...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {

			SharedPreferences asp = PreferenceManager
					.getDefaultSharedPreferences(mContext);

			String jsonString = (new ServiceHandler()).makeServiceCall(
					mServiceItem.getPath(),
					asp.getString("username", "headrest"),
					asp.getString("password", "password"), ServiceHandler.GET);

			mSelectedServiceItems = (new JSONParser(jsonString))
					.getServiceItems();

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			displayView();
		}
	}

}
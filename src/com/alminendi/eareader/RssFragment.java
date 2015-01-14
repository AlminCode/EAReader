package com.alminendi.eareader;

import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class RssFragment extends android.support.v4.app.Fragment implements OnItemClickListener {

	private ProgressBar progressBar;
	private ListView listView;
	private View view;
	public String urlStranice;
   //public static final String NEKI_STRING="s";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	Log.i("Nešta", urlStranice);
		setRetainInstance(true);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.fragment_layout, container, false);
			progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
			listView = (ListView) view.findViewById(R.id.listView);
			listView.setOnItemClickListener(this);
			startService();
		} else {
		
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
		}
		return view;
	}

	private void startService() {
		urlStranice=getArguments().getString("url-stranice");
		Log.i("Url Stranice:", urlStranice);
		Intent intent = new Intent(getActivity(), RssServis.class);
		intent.putExtra(RssServis.RECEIVER, resultReceiver);
		intent.putExtra(RssServis.rssurl, urlStranice);
		getActivity().startService(intent);
	}


	private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
		@SuppressWarnings("unchecked")
		@Override
		protected void onReceiveResult(int resultCode, Bundle resultData) {
			progressBar.setVisibility(View.GONE);
			List<RssItem> items = (List<RssItem>) resultData.getSerializable(RssServis.ITEMS);
			if (items != null) {
				RssAdapter adapter = new RssAdapter(getActivity(), items);
				listView.setAdapter(adapter);
			} else {
				Toast.makeText(getActivity(), "An error occured while downloading the rss feed.",
						Toast.LENGTH_LONG).show();
			}
		};
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		RssAdapter adapter = (RssAdapter) parent.getAdapter();
		RssItem item = (RssItem) adapter.getItem(position);
		Uri uri = Uri.parse(item.getLink());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

}

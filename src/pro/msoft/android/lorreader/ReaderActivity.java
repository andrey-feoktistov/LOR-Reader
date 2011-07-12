package pro.msoft.android.lorreader;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.xml.sax.SAXException;
import pro.msoft.android.lorreader.rss.Channel;
import pro.msoft.android.lorreader.rss.FeedLoader;
import pro.msoft.android.lorreader.rss.Item;
import pro.msoft.android.lorreader.utils.FancyLog;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;



public class ReaderActivity extends Activity
	implements AdapterView.OnItemClickListener
{
	private static final FancyLog log = new FancyLog(ReaderActivity.class);
	private static final String rssURL = "http://feeds.feedburner.com/org/LOR?format=xml";

	public static class ItemsAdapter extends BaseAdapter
	{
		private final ArrayList<Item> items;
		private final Context context;
		private final LayoutInflater layoutInflater;

		public ItemsAdapter(Context context, ArrayList<Item> items)
		{
			this.context = context;
			this.items = items;
			layoutInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount()
		{
			return items.size();
		}

		@Override
		public Item getItem(int i)
		{
			return items.get(i);
		}

		@Override
		public long getItemId(int i)
		{
			return i;
		}

		@Override
		public View getView(int i, View view, ViewGroup viewGroup)
		{
			if (view == null)
				view = layoutInflater.inflate(R.layout.rss_item, null);

			TextView titleView = (TextView) view.findViewById(R.id.ItemTitle);
			TextView dateView = (TextView) view.findViewById(R.id.ItemDate);
			TextView authorView = (TextView) view.findViewById(R.id.ItemAuthor);
			Item item = getItem(i);
			titleView.setText(Html.fromHtml(item.getTitle()).toString());
			dateView.setText(DateFormat.getDateFormat(context).format(item.getDate()));
			authorView.setText(item.getAuthor());

			return view;
		}
	}

	private final FeedLoader feedLoader = new FeedLoader();
	private ProgressDialog progressDialog;
	ListView itemsListView;
	ItemsAdapter itemsAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		log.v("onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reader_activity);

		itemsListView = (ListView) findViewById(R.id.RSSItemsListView);
		itemsListView.setOnItemClickListener(this);

		if (itemsAdapter == null) {
			LoadTask loadTask = new LoadTask();
			loadTask.execute(rssURL);
		} else {
			itemsListView.setAdapter(itemsAdapter);
		}
	}

//	@Override
//	protected void onSaveInstanceState(Bundle outState)
//	{
//		log.v("onSaveInstanceState");
//		super.onSaveInstanceState(outState);
//	}
//
//	@Override
//	protected void onRestoreInstanceState(Bundle savedInstanceState)
//	{
//		log.v("onRestoreInstanceState");
//		super.onRestoreInstanceState(savedInstanceState);
//	}

	public ProgressDialog getProgressDialog()
	{
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
//			progressDialog.setTitle(R.string.Msg_please_wait);
			progressDialog.setMessage(getString(R.string.Msg_loading_rss));
			progressDialog.setCancelable(false);
		}

		return progressDialog;
	}


	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
	{
		Item item = itemsAdapter.getItem(i);
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
		startActivity(intent);
	}

	private class LoadTask extends AsyncTask<String, Void, Channel>
	{
		@Override
		protected Channel doInBackground(String... strings)
		{
			try {
				return feedLoader.loadFeedChannel(strings[0]);
			}
			catch (Exception e) {
				log.e(e);
				throw new RuntimeException(e.getMessage(), e);
			}
		}

		@Override
		protected void onPreExecute()
		{
			getProgressDialog().show();
		}

		@Override
		protected void onPostExecute(Channel channel)
		{
			if (channel != null) {
				itemsAdapter = new ItemsAdapter(ReaderActivity.this, channel.getItems());
				itemsListView.setAdapter(itemsAdapter);
			}
			
			getProgressDialog().hide();
		}
	}
}

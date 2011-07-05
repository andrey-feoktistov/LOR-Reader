package pro.msoft.android.lorreader;


import android.test.AndroidTestCase;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.xml.sax.SAXException;
import pro.msoft.android.lorreader.rss.Channel;
import pro.msoft.android.lorreader.rss.FeedLoader;
import pro.msoft.android.lorreader.rss.Item;
import pro.msoft.android.lorreader.utils.FancyLog;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.text.*;
import java.util.Date;
import java.util.Locale;



public class RSSLoaderTest extends AndroidTestCase
{
	private static final FancyLog log = new FancyLog(RSSLoaderTest.class);

	public void testLoading() throws Exception
	{
		FeedLoader loader = new FeedLoader();
		Channel channel = loader.loadFeedChannel("http://feeds.feedburner.com/org/LOR?format=xml");
		for (Item item : channel.getItems()) {
			log.v("item title = " + item.getTitle());
		}
	}

	public void testDateParser() throws Exception
	{
		final DateFormat dateFormat = new SimpleDateFormat("EE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
		log.v(dateFormat.format(new Date()));
		dateFormat.parse("Thu, 30 Jun 2011 10:53:53 +0400");
	}
}

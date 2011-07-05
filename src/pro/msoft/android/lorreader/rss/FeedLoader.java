package pro.msoft.android.lorreader.rss;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import pro.msoft.android.lorreader.utils.FancyLog;
import pro.msoft.android.lorreader.utils.xml.XPathAccessor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;



public class FeedLoader
{
//	private static final FancyLog log = new FancyLog(FeedLoader.class);

	private static final DateFormat dateFormat =
			new SimpleDateFormat("EE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
	private final HttpClient httpClient = new DefaultHttpClient();
	private DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

	public Channel loadFeedChannel(String url)
			throws IOException, ParserConfigurationException, SAXException
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response = httpClient.execute(request);
		InputStream content = response.getEntity().getContent();
		return parseRSS(content);
	}

	public Channel parseRSS(InputStream istream)
			throws ParserConfigurationException, IOException, SAXException
	{
		final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		return parseRSS(documentBuilder.parse(istream));
	}

	public Channel parseRSS(Document document)
	{
		final XPathAccessor accessor = new XPathAccessor(document);
		final Channel channel = new Channel();
		final ArrayList<Item> items = new ArrayList<Item>();

		accessor.appendObjectsToList(
				"/rss/channel/item", items,
				new XPathAccessor.ObjectFactory<Item>(){
					@Override
					public Item create(Node node)
					{
						final XPathAccessor accessor = new XPathAccessor(node);
						final Item item = new Item();
						item.setAuthor(accessor.getText("author"));
						item.setDate(accessor.getDateTime("pubDate", dateFormat));
						item.setTitle(accessor.getText("title"));
						item.setDescription(accessor.getText("description"));
						item.setLink(accessor.getText("link"));
						return item;
					}
				});
		channel.setItems(items);
		return channel;
	}
}

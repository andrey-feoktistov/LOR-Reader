package pro.msoft.android.lorreader;

import android.net.http.AndroidHttpClient;
import android.test.ActivityInstrumentationTestCase2;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xml.sax.SAXException;
import pro.msoft.android.lorreader.rss.Channel;
import pro.msoft.android.lorreader.rss.FeedLoader;
import pro.msoft.android.lorreader.rss.Item;
import pro.msoft.android.lorreader.utils.FancyLog;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;



/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class pro.msoft.android.lorreader.ReaderActivityTest \
 * pro.msoft.android.lorreader.tests/android.test.InstrumentationTestRunner
 */
public class ReaderActivityTest extends ActivityInstrumentationTestCase2<ReaderActivity>
{
	private static final FancyLog log = new FancyLog(ReaderActivityTest.class);

	public ReaderActivityTest()
	{
		super("pro.msoft.android.lorreader", ReaderActivity.class);
	}
}

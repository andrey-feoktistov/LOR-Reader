/**
 * @author Andrey Feoktistov <cattum@gmail.com>
 * @copyright (c) 2011, Andrey Feoktistov.
 */
package pro.msoft.android.lorreader.utils;


import android.util.Log;



/**
 * This class is a simple wraper for the android.util.Log,
 * which adds the default tag, for all messages.
 */
public class FancyLog
{
	private final String tag;

	// class FancyLog
	// constructors
	//

	public FancyLog(String tag)
	{
		this.tag = tag;
	}


	public FancyLog(Class tagClass)
	{
		this(tagClass.getName());
	}


	// class FancyLog
	// methods
	//

	public int d(String msg)
	{
		return Log.d(tag, msg);
	}


	public int d(String msg, Throwable tr)
	{
		return Log.d(tag, msg, tr);
	}


	public int v(String msg)
	{
		return Log.v(tag, msg);
	}


	public int v(String msg, Throwable tr)
	{
		return Log.v(tag, msg, tr);
	}


	public int i(String msg)
	{
		return Log.i(tag, msg);
	}


	public int i(String msg, Throwable tr)
	{
		return Log.i(tag, msg, tr);
	}

	public int e(Throwable tr)
	{
		return e(tr.getMessage(), tr);
	}


	public int e(String msg)
	{
		return Log.e(tag, msg);
	}


	public int e(String msg, Throwable tr)
	{
		return Log.e(tag, msg, tr);
	}


	public int w(String msg)
	{
		return Log.w(tag, msg);
	}


	public int w(Throwable tr)
	{
		return Log.w(tag, tr);
	}


	public int w(String msg, Throwable tr)
	{
		return Log.w(tag, msg, tr);
	}

// methods Log.wtf doesn't available in API 7
//	public int wtf(String msg)
//	{
//		return Log.wtf(tag, msg);
//	}
//
//
//	public int wtf(Throwable tr)
//	{
//		return Log.wtf(tag, tr);
//	}
//
//
//	public int wtf(String msg, Throwable tr)
//	{
//		return Log.wtf(tag, msg, tr);
//	}


	public int println(int priority, String msg)
	{
		return Log.println(priority, tag, msg);
	}


	public String getTag()
	{
		return tag;
	}


	public boolean isLogable(int priority)
	{
		return Log.isLoggable(tag, priority);
	}
}

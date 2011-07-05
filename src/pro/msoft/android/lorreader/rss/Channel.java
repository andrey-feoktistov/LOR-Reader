/**
 * @author Andrey Feoktistov <cattum@gmail.com>
 * @copyright (c) 2011, Andrey Feoktistov.
 */
package pro.msoft.android.lorreader.rss;


import java.util.ArrayList;



public class Channel
{
//	private String link;
//	private String language;
//	private String title;
//	private String description;

	private ArrayList<Item> items;

	public ArrayList<Item> getItems()
	{
		return items;
	}

	public void setItems(ArrayList<Item> items)
	{
		this.items = items;
	}
}

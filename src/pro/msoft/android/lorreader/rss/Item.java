/**
 * @author Andrey Feoktistov <cattum@gmail.com>
 * @copyright (c) 2011, Andrey Feoktistov.
 */
package pro.msoft.android.lorreader.rss;


import java.util.Date;



public class Item
{
	private String author;
	private String link;
	private String title;
	private String description;
	private Date date;
	
	public String getAuthor()
	{
		return author;
	}

	public void setAuthor(String author)
	{
		this.author = author;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getLink()
	{
		return link;
	}

	public void setLink(String link)
	{
		this.link = link;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
}

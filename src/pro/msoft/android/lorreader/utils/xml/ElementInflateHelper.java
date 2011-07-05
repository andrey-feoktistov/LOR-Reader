package pro.msoft.android.lorreader.utils.xml;


import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public abstract class ElementInflateHelper<DataType>
{
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private final Document document;

	public ElementInflateHelper(Document document)
	{
		this.document = document;
	}

	public Document getDocument()
	{
		return document;
	}

	public abstract void inflate(Element element, DataType data);

	public void inflate(String name, DataType data)
	{
		inflate(document.createElement(name), data);
	}

	public final Element appendElement(Element parent, String name, String text)
	{
		return appendElement(document, parent, name, text);
	}

	public final Element appendElement(Element parent, String name, Long data)
	{
		return appendElement(document, parent, name, data);
	}

	public final Element appendElement(Element parent, String name, Date date)
	{
		return appendElement(document, parent, name, date);
	}

	public final Element appendElement(Element parent, String name, Date date, DateFormat format)
	{
		return appendElement(document, parent, name, date, format);
	}

	public final <T extends Enum> Element appendElement(Element parent, String name, T v)
	{
		return appendElement(document, parent, name, v);
	}

	public static Element appendElement(Document document, Element parent, String name, String text)
	{
		if (text == null)
			return null;

		Element el = document.createElement(name);
		el.setTextContent(text);
		parent.appendChild(el);
		return el;
	}

	public static Element appendElement(Document document, Element parent, String name, Long data)
	{
		if (data == null)
			return null;

		return appendElement(document, parent, name, data.toString());
	}

	public static Element appendElement(Document document, Element parent, String name, Date date)
	{
		return appendElement(document, parent, name, date, dateTimeFormat);
	}

	public static Element appendElement(
			Document document, Element parent, String name,
			Date date, DateFormat format)
	{
		if (date == null)
			return null;

		return appendElement(document, parent, name, format.format(date));
	}

	public static <T extends Enum> Element appendElement(
			Document document, Element parent, String name, T v)
	{
		if (v == null)
			return null;

		return appendElement(document, parent, name, v.toString().toLowerCase());
	}

}


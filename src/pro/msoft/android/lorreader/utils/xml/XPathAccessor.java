package pro.msoft.android.lorreader.utils.xml;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class XPathAccessor
{
	public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private XPathFactory xpathFactory;
	private Node rootNode;

	public interface ObjectFactory<T>
	{
		public T create(Node node);
	}

	// class XPathAccessor
	// constructors
	//

	public XPathAccessor(Node rootNode)
	{
		this(rootNode, XPathFactory.newInstance());
	}


	public XPathAccessor(Node rootNode, XPathFactory factory)
	{
		if (rootNode == null)
			throw new NullPointerException();
		this.rootNode = rootNode;
		this.xpathFactory = factory;
	}


	// class XPathAccessor
	// methods
	//

	public XPathFactory getXPathFactory()
	{
		return xpathFactory;
	}

	public XPath newXPath()
	{
		return xpathFactory.newXPath();
	}

	public Node getRootNode()
	{
		return rootNode;
	}

	public Node getNode(Node rootNode, String query)
	{
		try {
			return (Node) newXPath().evaluate(query, rootNode, XPathConstants.NODE);
		}
		catch (XPathExpressionException e) {
			throw new ValueAccessingException(
					"Error while accessing node \"" + query + "\": " + e.getMessage(), e);
		}
	}

	public Node getNode(String query)
	{
		return getNode(getRootNode(), query);
	}
	
	public NodeList getNodeSet(Node rootNode, String query)
	{
		try {
			return (NodeList) newXPath().evaluate(query, rootNode, XPathConstants.NODESET);
		}
		catch (XPathExpressionException e) {
			throw new ValueAccessingException(
					"Error while accessing node \"" + query + "\": " + e.getMessage(), e);
		}
	}

	public NodeList getNodeSet(String query)
	{
		return getNodeSet(rootNode, query);
	}

	public <T> T getObject(String query, ObjectFactory<T> factory)
	{
		final Node node = getNode(query);
		if (node != null)
			return factory.create(node);
		return null;
	}

	public String getText(String query)
	{
		return getText(rootNode, query);
	}

	public String getText(Node rootNode, String query)
	{
		final Node node = getNode(rootNode, query);
		return node != null ? node.getTextContent().trim() : null;
	}

	public Long getLong(String query)
	{
		return Long.parseLong(getText(query));
	}

	public Integer getInteger(String query)
	{
		return Integer.parseInt(getText(query));
	}

	public Byte getByte(String query)
	{
		return Byte.parseByte(getText(query));
	}

	public <T extends Enum<T>> T getEnum(String query, Class<T> aClass)
	{
		return T.valueOf(aClass, getText(query).toUpperCase());
	}

	public Date getDateTime(Node node, String query, DateFormat format)
	{
		try {
			return format.parse(getText(node, query).trim());
		} catch (ParseException e) {
			throw new ValueAccessingException(e.getMessage(), e);
		}
	}

	public Date getDateTime(String query, DateFormat format)
	{
		return getDateTime(rootNode, query, format);
	}

	public Date getDateTime(Node node, String query)
	{
		return getDateTime(node, query, dateTimeFormat);
	}

	public Date getDateTime(String query)
	{
		return getDateTime(rootNode, query);
	}

	public Date getDate(Node node, String query)
	{
		return getDateTime(node, query, dateFormat);
	}

	public Date getDate(String query)
	{
		return getDate(rootNode, query);
	}

	public <T> void appendObjectsToList(String query, List<T> list, ObjectFactory<T> factory)
	{
		final NodeList nl = getNodeSet(query);
		if (nl.getLength() > 0)
		{
			if (list instanceof ArrayList)
				((ArrayList) list).ensureCapacity(list.size() + nl.getLength());
			for (int i = 0; i < nl.getLength(); ++i)
				list.add(factory.create(nl.item(i)));
		}
	}

	public <T> List<T> getList(String query, ObjectFactory<T> factory)
	{
		final ArrayList<T> list = new ArrayList<T>();
		appendObjectsToList(query, list, factory);
		return list;
	}
}

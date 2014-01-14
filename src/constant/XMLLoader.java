package constant;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

class XMLLoader {

	private String constFilePath = "res\\Const.xml";
	
	private Element rootElem;
	
	XMLLoader() {
		SAXBuilder builder = new SAXBuilder();
		Document doc = null;
		try {
			doc = (Document) builder.build(new File(constFilePath));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		rootElem = (Element) doc.getRootElement();
	}
	
	Object getConst(ConstType type, String name) {
		List<Element> strings = rootElem.getChildren(type.toString());
		Iterator<Element> i = strings.iterator();
		
		while(i.hasNext()) {
			Element item = i.next();
			if(item.getAttributeValue("name").equals(name)) {
				switch (type) {
				case Integer:
					return getInteger(name, item);
				case String:
					return item.getAttributeValue("value");
				case StringArray:
					return getString(item);
				case Font:
					return getFont(name, item);
				case Color:
					return getColor(item);
				default:
					return null;
				}
			}
		}
		
		return null;
	}

	private Object getColor(Element item) {
		String strRed = item.getAttribute("red").getValue();
		String strGreen = item.getAttribute("green").getValue();
		String strBlue = item.getAttribute("blue").getValue();
		
		Integer intRed = strToInt(strRed);
		Integer intGreen = strToInt(strGreen);
		Integer intBlue = strToInt(strBlue);
		
		if(intRed == null || intGreen == null || intBlue == null) {
			return null;
		} else {
			return new Color(intRed, intGreen, intBlue);
		}
	}

	private Object getFont(String name, Element item) {
		String fontName = item.getAttribute("font").getValue();
		String fontStyle = item.getAttribute("style").getValue();
		String fontSize = item.getAttribute("size").getValue();
		
		Font font = strToFont(fontName, fontStyle, fontSize);
		if(font == null) {
			System.err.println("Font reading has fell. Problem "
					+ "constant: " + name + ", font: " + fontName
					+ ", style: " + fontStyle + ", size: "
					+ fontSize);
		}
		
		return font;
	}

	private Object getString(Element item) {
		List<Element> arrayStrings = item.getChildren();
		String[] array = new String[arrayStrings.size()];
		Iterator<Element> arrayIter = arrayStrings.iterator();
		
		int index = 0;
		while(arrayIter.hasNext()) {
			array[index++] = arrayIter.next().getAttributeValue("value");
		}
		
		return array;
	}

	private Object getInteger(String name, Element item) {
		String strVal = item.getAttributeValue("value");
		Integer intVal = strToInt(strVal);
		if(intVal == null) {
			System.err.println("String to Integer convertation has fell. "
					+ "Problem constant: " + name + ", value: " + strVal);
		}
		return intVal;
	}
	
	private Integer strToInt(String strVal) {
		try {
			Integer intVal = Integer.parseInt(strVal);
			return intVal;
		} catch (NumberFormatException ex) {
			return null;
		}
	}
	
	private Font strToFont(String fontName, String fontStyle, String fontSize) {
		Integer intSize = null;
		try {
			intSize = Integer.parseInt(fontSize);
		} catch (NumberFormatException ex) {
			return null;
		}
		
		int style = 0;
		if (fontStyle.equalsIgnoreCase("bold")) {
			style = Font.BOLD;
		} else if (fontStyle.equalsIgnoreCase("italic")) {
			style = Font.ITALIC;
		} else if(fontStyle.equalsIgnoreCase("plain")) {
			style = Font.PLAIN;
		} else {
			return null;
		}
		
		return new Font(fontName, style, intSize);
	}
}

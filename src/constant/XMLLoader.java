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
	
	/**
	 * <p>
	 * Load *.xml file, read it and prepares for parsing.
	 * </p>
	 * 
	 * @author Vlad
	 */
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
	
	/**
	 * <p>
	 * Find in xml-file element with specified type and name and returns it's
	 * value. In case of any invalid data returns <b>null</b>.
	 * </p>
	 * 
	 * @param type
	 *            of the constant
	 * @param name
	 *            of the constant
	 * 
	 * @return value of the constant with such type and name. If constant not
	 *         exists or invalid returns <b>null</b>
	 * 
	 * @author Vlad
	 */
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
					return getStringArray(item);
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

	/**
	 * <p>
	 * Parses item and returns Color object or <b>null</b> if item is not correct.
	 * </p>
	 * 
	 * @param item Color item
	 * 
	 * @return <b>color</b> or <b>null</b> if any color value is invalid.
	 * 
	 * @author Vlad
	 */
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

	/**
	 * <p>
	 * Parses item and returns Font object or <b>null</b> if item is not correct.
	 * </p>
	 * 
	 * @param item Font item
	 * 
	 * @return <b>font</b> or <b>null</b> if any parameter value is invalid.
	 * 
	 * @author Vlad
	 */
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

	/**
	 * <p>
	 * Parses item and returns array with strings.
	 * </p>
	 * 
	 * @param item StringsArray item
	 * 
	 * @return <b>array of strings</b>
	 * 
	 * @author Vlad
	 */
	private Object getStringArray(Element item) {
		List<Element> arrayStrings = item.getChildren();
		String[] array = new String[arrayStrings.size()];
		Iterator<Element> arrayIter = arrayStrings.iterator();
		
		int index = 0;
		while(arrayIter.hasNext()) {
			array[index++] = arrayIter.next().getAttributeValue("value");
		}
		
		return array;
	}

	/**
	 * <p>
	 * Parses item and returns Integer object or <b>null</b> if item is not correct.
	 * </p>
	 * 
	 * @param item Integer item
	 * 
	 * @return <b>Integer</b> or <b>null</b> if value is invalid.
	 * 
	 * @author Vlad
	 */
	private Object getInteger(String name, Element item) {
		String strVal = item.getAttributeValue("value");
		Integer intVal = strToInt(strVal);
		if(intVal == null) {
			System.err.println("String to Integer convertation has fell. "
					+ "Problem constant: " + name + ", value: " + strVal);
		}
		return intVal;
	}
	
	/**
	 * <p>
	 * Parses string and convert it to Integer. If it can't be converted, return null.
	 * </p>
	 * 
	 * @param strVal string with number
	 * @return <b>Integer</b> or <b>null</b> if strVal is not number
	 * 
	 * @author Vlad
	 */
	private Integer strToInt(String strVal) {
		try {
			Integer intVal = Integer.parseInt(strVal);
			return intVal;
		} catch (NumberFormatException ex) {
			return null;
		}
	}
	
	/**
	 * <p>
	 * Initiates new Font object from String parameters. If any parameter isn't
	 * valid return null.
	 * </p>
	 * 
	 * @param fontName
	 * @param fontStyle
	 * @param fontSize
	 * 
	 * @return font with specified parameters or null if parameters is invalid.
	 * 
	 * @author Vlad
	 */
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

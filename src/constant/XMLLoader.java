package constant;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.File;

class XMLLoader {

    private final Element rootElem;

    /**
     * <p>
     * Load *.xml file, read it and prepares for parsing.
     * </p>
     *
     * @author Vlad
     */
    XMLLoader() {
        String constFilePath = "res\\Const.xml";
        File file = new File(constFilePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if(doc != null) {
            rootElem = doc.getDocumentElement();
        } else {
            rootElem = null;
        }
    }

    /**
     * <p>
     * Find in xml-file element with specified type and name and returns it's
     * value. In case of any invalid data returns <b>null</b>.
     * </p>
     *
     * @param type of the constant
     * @param name of the constant
     * @return value of the constant with such type and name. If constant not
     *         exists or invalid returns <b>null</b>
     * @author Vlad
     */
    Object getConst(ConstType type, String name) {
        NodeList strings = rootElem.getChildNodes();

        for (int i = 0; i < strings.getLength(); ++i) {
            Node child = strings.item(i);
            if (child instanceof Element) {
                Element item = (Element) child;
                if (item.getAttribute("name").equals(name)) {
                    switch (type) {
                        case Integer:
                            return getInteger(name, item);
                        case String:
                            return item.getAttribute("value");
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
        }

        return null;
    }

    /**
     * <p>
     * Parses item and returns Color object or <b>null</b> if item is not correct.
     * </p>
     *
     * @param item Color item
     * @return <b>color</b> or <b>null</b> if any color value is invalid.
     * @author Vlad
     */
    private Object getColor(Element item) {
        String strRed = item.getAttribute("red");
        String strGreen = item.getAttribute("green");
        String strBlue = item.getAttribute("blue");

        Integer intRed = strToInt(strRed);
        Integer intGreen = strToInt(strGreen);
        Integer intBlue = strToInt(strBlue);

        if (intRed == null || intGreen == null || intBlue == null) {
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
     * @return <b>font</b> or <b>null</b> if any parameter value is invalid.
     * @author Vlad
     */
    private Object getFont(String name, Element item) {
        String fontName = item.getAttribute("font");
        String fontStyle = item.getAttribute("style");
        String fontSize = item.getAttribute("size");

        Font font = strToFont(fontName, fontStyle, fontSize);
        if (font == null) {
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
     * @return <b>array of strings</b>
     * @author Vlad
     */
    private Object getStringArray(Element item) {
        NodeList arrayStrings = item.getChildNodes();
        String[] array = new String[arrayStrings.getLength()];

        for (int i = 0; i < arrayStrings.getLength(); ++i) {
            Node node = arrayStrings.item(i);
            if (node instanceof Element) {
                Element child = (Element) node;
                array[i] = child.getAttribute("value");
            }
        }

        return array;
    }

    /**
     * <p>
     * Parses item and returns Integer object or <b>null</b> if item is not correct.
     * </p>
     *
     * @param item Integer item
     * @return <b>Integer</b> or <b>null</b> if value is invalid.
     * @author Vlad
     */
    private Object getInteger(String name, Element item) {
        String strVal = item.getAttribute("value");
        Integer intVal = strToInt(strVal);
        if (intVal == null) {
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
     * @return font with specified parameters or null if parameters is invalid.
     * @author Vlad
     */
    private Font strToFont(String fontName, String fontStyle, String fontSize) {
        Integer intSize = null;
        try {
            intSize = Integer.parseInt(fontSize);
        } catch (NumberFormatException ex) {
            return null;
        }

        int style;
        if (fontStyle.equalsIgnoreCase("bold")) {
            style = Font.BOLD;
        } else if (fontStyle.equalsIgnoreCase("italic")) {
            style = Font.ITALIC;
        } else if (fontStyle.equalsIgnoreCase("plain")) {
            style = Font.PLAIN;
        } else {
            return null;
        }

        return new Font(fontName, style, intSize);
    }
}

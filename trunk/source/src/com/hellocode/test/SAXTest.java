package com.hellocode.test;

import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;
import org.xml.sax.SAXException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.helpers.DefaultHandler;

class SAXTest extends DefaultHandler {
	private StringBuffer buffer = new StringBuffer();

	public void parseURI(String uri) {
		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			sp.parse(uri, this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Start document. */
	public void startDocument() {
		buffer.append("< ?xml version=\"1.0\"?>");
	}

	/** Start element. */
	public void startElement(String namespaceURI, String localName,
			String rawName, Attributes attrs) {
		buffer.append("SSS< ");
		buffer.append(rawName);
		if (attrs != null) {
			int len = attrs.getLength();
			for (int i = 0; i < len; i++) {
				buffer.append(" ");
				buffer.append(attrs.getQName(i));
				buffer.append("=\"");
				buffer.append(attrs.getValue(i));
				buffer.append("\"");
			}
		}
		buffer.append(">");
	}

	/** Characters. */
	public void characters(char ch[], int start, int length) {
		buffer.append(new String(ch, start, length));
	}

	/** Ignorable whitespace. */
	public void ignorableWhitespace(char ch[], int start, int length) {
		characters(ch, start, length);
	}

	/** End element. */
	public void endElement(String namespaceURI, String localName, String rawName) {
		buffer.append("");
	} // endElement(String)

	/** End document. */
	public void endDocument() {
	}

	/** Processing instruction. */
	public void processingInstruction(String target, String data) {
		buffer.append("< ?");
		buffer.append(target);
		if (data != null && data.length() > 0) {
			buffer.append(' ');
			buffer.append(data);
		}
		buffer.append("?>");
	}

	/** Returns a string of the location. */
	private String getLocationString(SAXParseException ex) {
		StringBuffer str = new StringBuffer();
		String systemId = ex.getSystemId();
		if (systemId != null) {
			int index = systemId.lastIndexOf('/');
			if (index != -1)
				systemId = systemId.substring(index + 1);
			str.append(systemId);
		}
		str.append(':');
		str.append(ex.getLineNumber());
		str.append(':');
		str.append(ex.getColumnNumber());
		return str.toString();
	}

	public static void main(String args[]) {
		SAXTest st = new SAXTest();
		st.parseURI("game.xml");
		System.out.println(st.buffer.toString());
	}
}
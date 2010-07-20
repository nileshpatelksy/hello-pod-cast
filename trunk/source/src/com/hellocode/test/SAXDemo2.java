package com.hellocode.test;

/*
<PHONEBOOK>
<PERSON>
 <NAME>Joe Wang</NAME>
 <EMAIL>joe@yourserver.com</EMAIL>
 <TELEPHONE>202-999-9999</TELEPHONE>
 <WEB>www.java2s.com</WEB>
</PERSON>
<PERSON>
 <NAME>Karol</name>
 <EMAIL>karol@yourserver.com</EMAIL>
 <TELEPHONE>306-999-9999</TELEPHONE>
 <WEB>www.java2s.com</WEB>
</PERSON>
<PERSON>
 <NAME>Green</NAME>
 <EMAIL>green@yourserver.com</EMAIL>
 <TELEPHONE>202-414-9999</TELEPHONE>
 <WEB>www.java2s.com</WEB>
</PERSON>
</PHONEBOOK>

*/

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXDemo2 {

  public static void main(String args[]) {

//    if (args.length != 1) {
//      System.err.println("Usage: java NameLister xmlfile.xml");
//      System.exit(-1);
//    }

    try {

      SAXParserFactory factory = SAXParserFactory.newInstance();
      SAXParser saxParser = factory.newSAXParser();

      DefaultHandler handler = new DefaultHandler() {
        boolean name = false;

        public void startElement(String uri, String localName,
            String qName, Attributes attributes)
            throws SAXException {
          if (qName.equalsIgnoreCase("title")) {
            name = true;
          }
        }

        public void characters(char ch[], int start, int length)
            throws SAXException {
          if (name) {
            System.out.println("Name: "
                + new String(ch, start, length));
            name = false;
          }
        }
      };

      saxParser.parse("http://www.voanews.com/podcast/videocastxml_local.cfm?id=677", handler);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

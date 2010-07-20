package com.hellocode.test;

import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.hellocode.model.MediaItem;
import com.hellocode.model.XMLPodCastURL;

public class CopyOfPodCastURL extends XMLPodCastURL {

	private ArrayList<String> labels = new ArrayList<String>();
	private ArrayList<MediaItem> medias = new ArrayList<MediaItem>();

	private String URL = null;
	private int newCount = 0;
	private boolean hasNew = false;
	boolean name = false;

	public CopyOfPodCastURL(String url) {
		this.URL = url;
	}

	/**
	 * urlconnection, get MediaItems
	 * 
	 * @return
	 */
	public int reFreshURL() {
		if (this.URL == null) {
			return -1;
		}
		try {

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new DefaultHandler() {

				final StringBuffer value = new StringBuffer();

				@Override
				public void startElement(String uri, String localName,
						String tag, Attributes attributes) throws SAXException {
					if (tag.equalsIgnoreCase("itunes:image")) {
						CopyOfPodCastURL.this
								.setImageURL(attributes.getValue("href"));
					}
					if (tag.equalsIgnoreCase("itunes:category")) {
						CopyOfPodCastURL.this
								.setImageURL(attributes.getValue("text"));
					}
				}

				@Override
				public void characters(char[] chars, int start, int length)
						throws SAXException {
					value.setLength(0);
					value.append(chars, start, length);
				}

				@Override
				public void endElement(String uri, String localName, String tag)
						throws SAXException {
					System.out.println(uri+"#"+localName+"#"+tag);
					if (tag.equalsIgnoreCase("title")) {
						CopyOfPodCastURL.this.setTitle(this.value.toString().trim());
					}
					if (tag.equalsIgnoreCase("itunes:author")) {
						CopyOfPodCastURL.this.setAuthor(this.value.toString().trim());
					}
					if (tag.equalsIgnoreCase("link")) {
						CopyOfPodCastURL.this.setLink(this.value.toString().trim());
					}
					if (tag.equalsIgnoreCase("itunes:subtitle")) {
						CopyOfPodCastURL.this.setSubtitle(this.value.toString()
								.trim());
					}
					if (tag.equalsIgnoreCase("itunes:summary")) {
						CopyOfPodCastURL.this
								.setSummary(this.value.toString().trim());
					}
					if (tag.equalsIgnoreCase("copyright")) {
						CopyOfPodCastURL.this.setCopyright(this.value.toString()
								.trim());
					}
					if (tag.equalsIgnoreCase("language")) {
						CopyOfPodCastURL.this.setLanguage(this.value.toString()
								.trim());
					}
					if (tag.equalsIgnoreCase("itunes:name")) {
						CopyOfPodCastURL.this.setOwnerName(this.value.toString()
								.trim());
					}
					if (tag.equalsIgnoreCase("itunes:email")) {
						CopyOfPodCastURL.this.setOwnerEmail(this.value.toString()
								.trim());
					}
					this.value.setLength(0);
				}

				@Override
				public void warning(SAXParseException e) throws SAXException {
					System.out.println(e.toString());
				}

			};

			saxParser
					.parse(
							"http://www.voanews.com/podcast/videocastxml_local.cfm?id=677",
							handler);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public void addLabel(String label) {
		this.labels.add(label);
	}

	public void delLabel(String label) {
		this.labels.remove(label);
	}

	public void print() {
		System.out.println(this.getTitle());
		System.out.println("Author:"+this.getAuthor());
		System.out.println(this.getLink());
		System.out.println(this.getSubtitle());
		System.out.println("Summary:"+this.getSummary());
		
		System.out.println("copyright:"+this.getCopyright());
		
		System.out.println(this.getLanguage());
		System.out.println(this.getOwnerName());
		System.out.println(this.getOwnerEmail());
		System.out.println("URL:"+this.getImageURL());
		System.out.println("Cat:"+this.getCategory());
		
	}

	public static void main(String[] args) {
		CopyOfPodCastURL url = new CopyOfPodCastURL("Test");
		url.reFreshURL();
		url.print();

	}
}

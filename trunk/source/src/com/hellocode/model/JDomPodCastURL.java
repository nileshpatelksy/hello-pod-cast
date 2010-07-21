package com.hellocode.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class JDomPodCastURL extends XMLPodCastURL {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String label;
	private ArrayList<MediaItem> medias = new ArrayList<MediaItem>();
	private String URL = null;
	private String commnet;
	private int newCount = 0;
	private boolean hasNew = false;

	public String getCommnet() {
		return commnet;
	}

	public void setCommnet(String commnet) {
		this.commnet = commnet;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ArrayList<MediaItem> getMedias() {
		return medias;
	}

	public String getURL() {
		return URL;
	}

	public int getNewCount() {
		return newCount;
	}

	public boolean isHasNew() {
		return hasNew;
	}

	public JDomPodCastURL(String url) {
		this.URL = url;
	}

	public void clearMedia(){
		this.medias.clear();
	}
	/**
	 * urlconnection, get MediaItems
	 * 
	 * @return
	 */
	public int reFreshURL() {
		this.clearMedia();
		
		if (this.URL == null) {
			System.out.println("over");
			return -1;
		}
		
		try {
			URL url = new URL(this.URL);
			SAXBuilder builder = new SAXBuilder();
			Document doc = builder.build(url);
			Element root = doc.getRootElement();

			Element channel = root.getChild("channel");
			// XML path=channel.*; its the top elements
			this.title = channel.getChildText("title");
			this.author = channel.getChildText("itunes:author");
			this.link = channel.getChildText("link");
			this.setSubtitle(channel.getChildText("itunes:subtitle"));
			this.setSummary(channel.getChildText("itunes:summary"));
			this.setCopyright(channel.getChildText("copyright"));
			this.setLanguage(channel.getChildText("language"));

			System.out.println("OKOKOK&&&&&&&&&&   " + title);

			// XML path=channel.item[]; List of item
			List items = channel.getChildren("item");
			Iterator i = items.iterator();
			MediaItem media = new MediaItem();
			
			while (i.hasNext()) {
				media = new MediaItem();
				Element item = (Element) i.next();
				media.setTitle(item.getChildText("title"));
				media.setAuthor(item.getChildText("itunes:author"));
				media.setSubtitle(item.getChildText("itunes:subtitle"));
				media.setSummary(item.getChildText("itunes:summary"));
				media.setGUID(item.getChildText("guid"));
				media.setPubData(item.getChildText("pubDate"));
				media.setKeywords(item.getChildText("itunes:keywords"));
				media.setExplicit(item.getChildText("itunes:explicit"));
				media.setDuration(item.getChildText("itunes:duration"));
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$itunes:duration =="+item.getChildText("itunes:duration"));
				Element enclosure = item.getChild("enclosure");
				media
						.setENCLOSURE_Length(enclosure
								.getAttributeValue("length"));
				media.setENCLOSURE_URL(enclosure.getAttributeValue("url"));
				System.out.println("$$$$$$$$$$$$$$$$$$$$$$$URL =="+enclosure.getAttributeValue("url"));
				media.setENCLOSURE_TYPE(enclosure.getAttributeValue("type"));
				// add media
				this.medias.add(media);
				String ititle = item.getChildText("title");
				//System.out.println(ititle + "########################");
			}
			this.print();
		} catch (Exception e) {

		}

		return 2;

	}

	public void print() {
		System.out.println(this.getTitle());
		System.out.println("Author:" + this.getAuthor());
		System.out.println(this.getLink());
		System.out.println(this.getSubtitle());
		System.out.println("Summary:" + this.getSummary());

		System.out.println("copyright:" + this.getCopyright());

		System.out.println(this.getLanguage());
		System.out.println(this.getOwnerName());
		System.out.println(this.getOwnerEmail());
		System.out.println("URL:" + this.getImageURL());
		System.out.println("Cat:" + this.getCategory());

		System.out.println("All itmes size=" + this.medias.size());

	}

	public static void main(String[] args) {
		String voa = "http://downloads.bbc.co.uk/podcasts/worldservice/how2/rss.xml";
		JDomPodCastURL url = new JDomPodCastURL(voa);
		url.reFreshURL();
		url.print();

	}

	@Override
	public int hashCode() {
		int result = this.getURL().length() + this.getLabel().length();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass()) {
			System.out.println("sorry");
			return false;
		}
		JDomPodCastURL other = (JDomPodCastURL) obj;
		if (label == null || other.getLabel() == null) {
			return false;
		}
		if (URL == null || other.getURL() == null) {
			return false;
		}
		if (label.equals(other.getLabel())
				&& this.URL.equalsIgnoreCase(other.getURL())) {
			return true;
		} else {
			return false;
		}
	}
}

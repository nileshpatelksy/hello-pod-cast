package com.hellocode.model;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class PodCastURL extends XMLPodCastURL {

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

	public PodCastURL(String url) {
		this.URL = url;
	}

	/**
	 * urlconnection, get MediaItems
	 * 
	 * @return
	 */
	public int reFreshURL() {
		if (this.URL == null) {
			System.out.println("over");
			return -1;
		}
		try {
			// XMLInputFactory factory = XMLInputFactory.newInstance();
			URL url = new URL(this.URL);
			InputStream stream = url.openStream();
			BufferedInputStream bis = new BufferedInputStream(stream);
			Reader fileReader = new InputStreamReader(bis);

			DocumentBuilder db = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(fileReader);

			Document doc = db.parse(is);
			String value = doc.getElementsByTagName("itunes:name").item(0)
					.getTextContent();
			this.setOwnerName(value);
			value = doc.getElementsByTagName("itunes:email").item(0)
					.getTextContent();
			this.setOwnerEmail(value);

			NodeList nodes = doc.getElementsByTagName("channel");
			nodes = nodes.item(0).getChildNodes();
			Node element = null;
			String tag;
			// get top value;
			for (int i = 0; i < nodes.getLength(); i++) {
				element = nodes.item(i);
				tag = element.getNodeName();
				// System.out.println(""+tag+"="+element.getNodeType()+element.getTextContent());
				if (tag.equalsIgnoreCase("title")) {
					this.title = element.getTextContent();
				}
				if (tag.equalsIgnoreCase("itunes:author")) {
					this.author = element.getTextContent();
				}
				if (tag.equalsIgnoreCase("link")) {
					this.link = element.getTextContent();
				}
				if (tag.equalsIgnoreCase("itunes:subtitle")) {
					this.setSubtitle(element.getTextContent());
				}
				if (tag.equalsIgnoreCase("itunes:summary")) {
					this.setSummary(element.getTextContent());
				}
				if (tag.equalsIgnoreCase("copyright")) {
					this.setCopyright(element.getTextContent());
				}
				if (tag.equalsIgnoreCase("language")) {
					this.setLanguage(element.getTextContent());
				}
				if (tag.equalsIgnoreCase("itunes:image")) {
					NamedNodeMap attri = element.getAttributes();
					this.setImageURL(attri.item(0).getNodeValue());
				}
				if (tag.equalsIgnoreCase("itunes:category")) {
					NamedNodeMap attri = element.getAttributes();
					this.setCategory(attri.item(0).getNodeValue());
				}
			}

			// get item List
			nodes = doc.getElementsByTagName("item");
			NodeList list = null;
			MediaItem media = new MediaItem();
			this.medias.clear();
			for (int i = 0; i < nodes.getLength(); i++) {
				// new media
				media = new MediaItem();
				list = nodes.item(i).getChildNodes();
				for (int kcount = 0; kcount < list.getLength(); kcount++) {
					element = list.item(kcount);
					tag = element.getNodeName();
					// System.out.println("ITEM=" + tag + "="
					// + element.getNodeType() + "   value=   "
					// + element.getTextContent());
					if (tag.equalsIgnoreCase("title")) {
						media.setTitle(element.getTextContent());
					}
					if (tag.equalsIgnoreCase("itunes:author")) {
						media.setAuthor(element.getTextContent());
					}
					if (tag.equalsIgnoreCase("itunes:subtitle")) {
						media.setSubtitle(element.getTextContent());
					}
					if (tag.equalsIgnoreCase("itunes:summary")) {
						media.setSummary(element.getTextContent());
					}
					if (tag.equalsIgnoreCase("guid")) {
						media.setGUID(element.getTextContent());
					}
					if (tag.equalsIgnoreCase("pubDate")) {
						media.setPubData(element.getTextContent());
					}
					if (tag.equalsIgnoreCase("itunes:keywords")) {
						media.setKeywords(element.getTextContent());
					}
					if (tag.equalsIgnoreCase("itunes:explicit")) {
						media.setExplicit(element.getTextContent());
					}
					if (tag.equalsIgnoreCase("itunes:duration")) {
						media.setDuration(element.getTextContent());
					}
					if (tag.equalsIgnoreCase("enclosure")) {
						// System.out.println("UUUUUUUUU");
						NamedNodeMap attri = element.getAttributes();
						Node inNode = attri.getNamedItem("length");
						media.setENCLOSURE_Length(inNode.getNodeValue());
						inNode = attri.getNamedItem("url");
						media.setENCLOSURE_URL(inNode.getNodeValue());
						inNode = attri.getNamedItem("type");
						media.setENCLOSURE_TYPE(inNode.getNodeValue());
					}
					//
				}
				// add media
				this.medias.add(media);
			}

		} catch (Exception e) {

		}
		this.print();
		return 0;
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
		String voa = "http://www.voanews.com/podcast/videocastxml_local.cfm?id=677";
		PodCastURL url = new PodCastURL(voa);
		url.reFreshURL();
		url.print();

	}

	@Override
	public int hashCode() {
		int result = 0;
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
		PodCastURL other = (PodCastURL) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		}
		if (label.equals(other.label)) {
			return true;
		} else {
			return false;
		}
	}
}

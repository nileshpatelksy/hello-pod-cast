package com.hellocode.model;

public class XMLMediaItem {
	 String title;
	 String author;
	 String subtitle;
	 String summary;
	 String ENCLOSURE_Length;
	 String ENCLOSURE_URL;
	 String ENCLOSURE_TYPE;
	 String GUID;
	 String pubData;
	 String keywords;
	 String explicit;
	 public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setENCLOSURE_Length(String eNCLOSURELength) {
		ENCLOSURE_Length = eNCLOSURELength;
	}

	public void setENCLOSURE_URL(String eNCLOSUREURL) {
		ENCLOSURE_URL = eNCLOSUREURL;
	}

	public void setENCLOSURE_TYPE(String eNCLOSURETYPE) {
		ENCLOSURE_TYPE = eNCLOSURETYPE;
	}

	public void setGUID(String gUID) {
		GUID = gUID;
	}

	public void setPubData(String pubData) {
		this.pubData = pubData;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setExplicit(String explicit) {
		this.explicit = explicit;
	}

	String duration;
	
	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public String getSummary() {
		return summary;
	}

	public String getENCLOSURE_Length() {
		return ENCLOSURE_Length;
	}

	public String getENCLOSURE_URL() {
		return ENCLOSURE_URL;
	}

	public String getENCLOSURE_TYPE() {
		return ENCLOSURE_TYPE;
	}

	public String getGUID() {
		return GUID;
	}

	public String getPubData() {
		return pubData;
	}

	public String getKeywords() {
		return keywords;
	}

	public String getExplicit() {
		return explicit;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	
}

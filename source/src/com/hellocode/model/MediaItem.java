package com.hellocode.model;

public class MediaItem extends XMLMediaItem {
	private String httpURL;
	private String filePath;
	private String label;
	private boolean download;
	public String getHttpURL() {
		return httpURL;
	}
	public void setHttpURL(String httpURL) {
		this.httpURL = httpURL;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public boolean isDownload() {
		return download;
	}
	public void setDownload(boolean download) {
		this.download = download;
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
		if (getClass() != obj.getClass())
			return false;
		MediaItem other = (MediaItem) obj;
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

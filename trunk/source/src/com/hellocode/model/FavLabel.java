package com.hellocode.model;

import java.util.ArrayList;

public class FavLabel {
	private String nickname;
	private String label;
	private ArrayList<FavLabel> childs;
	private ArrayList<MediaItem> medias;

	public FavLabel(String lb){
		this.label = lb;
	}
	
	public void addMedia(MediaItem item){
		this.medias.add(item);
	}
	public void delMedia(MediaItem item){
		this.medias.remove(item);
	}
	public void delMedia(String item){
		//TODO----
	}
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLabel() {
		return label;
	}
	

	public void addFavLabel(FavLabel label) {
		this.childs.add(label);
	}

	public void delFavLabel(FavLabel label) {
		this.childs.remove(label);
	}

	public void delFavLabel(String label) {
		this.childs.remove(new FavLabel(label));
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
		FavLabel other = (FavLabel) obj;
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

package gov.bct.jrj.pojo;

import java.io.Serializable;

import org.json.JSONObject;

public class Thumbnail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6115159278808639541L;
	private String small;
	private String medium;
	private String large;
	public Thumbnail(){};
	public Thumbnail(JSONObject json){
		try {
			if(json.has("medium")) small = json.getString("medium");
			if(json.has("medium")) medium = json.getString("medium");
			if(json.has("large")) large = json.getString("large");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getSmall() {
		return small;
	}
	public void setSmall(String small) {
		this.small = small;
	}
	public String getMedium() {
		return medium;
	}
	public void setMedium(String medium) {
		this.medium = medium;
	}
	public String getLarge() {
		return large;
	}
	public void setLarge(String large) {
		this.large = large;
	}
	
}

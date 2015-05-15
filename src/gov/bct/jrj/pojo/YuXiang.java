package gov.bct.jrj.pojo;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 宇翔数据实体
 * @author huangfei
 *
 */
public class YuXiang implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1837169953736836527L;
	/**
	 * 
    {
       "id": "1",
       "name": "移动大厦",
       "address": "啊师傅问啊发",
       "storey": null,
       "area": null,
       "green_area": null,
       "play_area": null,
       "manage": null,
       "height": null,
       "power": null,
       "content": null,
       "image": null,
       "longitude": null,
       "latitude": null,
       "thumbnail":
       {
           "small": "http://localhost/jrj-web/public/data/cache/images/no_image.jpg",
           "medium": "http://localhost/jrj-web/public/data/cache/images/no_image.jpg",
           "large": "http://localhost/jrj-web/public/data/cache/images/no_image.jpg"
       }
    }
	 */
	private int id;
	private String name;
	private String address;
	private String storey;
	private String area;
	private String greenArea;
	private String playArea;
	private String manage;
	private String height;
	private String power;
	private String content;
	private String image;
	private String longitude;
	private String latitude;
	private Thumbnail thumbnail = new Thumbnail();
	public YuXiang(){};
	public YuXiang(JSONObject json){
		try {
			if(json.has("id")) id = json.getInt("id");
			if(json.has("name")) name = json.getString("name");
			if(json.has("address")) address = json.getString("address");
			if(json.has("intro")) storey = json.getString("intro");
			if(json.has("area")) area = json.getString("area");
			if(json.has("green_area")) greenArea = json.getString("green_area");
			if(json.has("play_area")) playArea = json.getString("play_area");
			if(json.has("manage")) manage = json.getString("manage");
			if(json.has("height")) height = json.getString("height");
			if(json.has("power")) power = json.getString("power");
			if(json.has("content")) content = json.getString("content");
			if(json.has("image")) image = json.getString("image");
			if(json.has("longitude")) longitude = json.getString("longitude");
			if(json.has("latitude")) latitude = json.getString("latitude");
			if(json.has("thumbnail")){
				thumbnail = new Thumbnail(json.getJSONObject("thumbnail"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStorey() {
		return storey;
	}
	public void setStorey(String storey) {
		this.storey = storey;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getGreenArea() {
		return greenArea;
	}
	public void setGreenArea(String greenArea) {
		this.greenArea = greenArea;
	}
	public String getPlayArea() {
		return playArea;
	}
	public void setPlayArea(String playArea) {
		this.playArea = playArea;
	}
	public String getManage() {
		return manage;
	}
	public void setManage(String manage) {
		this.manage = manage;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public Thumbnail getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	
}

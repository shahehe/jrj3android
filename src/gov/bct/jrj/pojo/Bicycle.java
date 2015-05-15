package gov.bct.jrj.pojo;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

/**
 * 
 * @author ouzehua 公租自行车 实体
 *
 */
public class Bicycle implements Serializable {

	/***
	 * [ 
	 * { 
	 * "id": "8", 
	 * "name": "租赁1", 
	 * "address": "地址1",
	 * "storey": null, 
	 * "area": null, 
	 * "green_area": null, 
	 * "play_area": null, 
	 * "manage": null, 
	 * "height":null, 
	 * "power": null, 
	 * "content": "", 
	 * "image": "", 
	 * "longitude":
	 * "116.365106", 
	 * "latitude": "39.911052", 
	 * "create_time": "1422862980",
	 * "intro": "", 
	 * "url": null 
	 * },
	 * ]
	 */
	private static final long serialVersionUID = 1837169953736836527L;

	private int id;
	private String name;
	private String address;
	private String storey;
	private String content;
	private String image;
	private Date date;
	private String log;// 经度
	private String lat;// 纬度

	public Bicycle() {

	}

	public Bicycle(JSONObject json) {
		try {
			if (json.has("id"))
				id = json.getInt("id");
			if (json.has("name"))
				name = json.getString("name");
			if (json.has("address"))
				address = json.getString("address");
			if (json.has("storey"))
				storey = json.getString("storey");
			if (json.has("content"))
				content = json.getString("content");
			if (json.has("image"))
				image = json.getString("image");
			if (json.has("longitude")) {
				if (null == json.getString("longitude")||"null".equals(json.getString("longitude"))||"".equals(json.getString("longitude"))) {
					log = "000.000000";
				} else {
					log = json.getString("longitude");
				}
			}
			if (json.has("latitude")) {
				if (null == json.getString("latitude")||"null".equals(json.getString("latitude"))||"".equals(json.getString("latitude"))) {
					lat = "00.000000";
				} else {
					lat = json.getString("latitude");
				}
			}
			if (json.has("create_time"))
				date = new Date(json.getLong("create_time") * 1000);
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
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

	@Override
	public String toString() {
		return "Bicycle [id=" + id + ", name=" + name + ", address=" + address
				+ ", storey=" + storey + ", content=" + content + ", image="
				+ image + ", date=" + date + ", log=" + log + ", lat=" + lat
				+ "]";
	}

}

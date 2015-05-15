package gov.bct.jrj.pojo;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 健康服务事项实体
 * 
 * @author ouzehua
 *
 */
public class HealthService2 implements Serializable {

	/**
	 * 
	 [ 
	 { "id": "81",
	  "title": "上交叉综合征", 
	  "content": "上交叉综合征上交叉综合征上交叉综合征上交叉综合征 ", 
	  "keywords": null, 
	  "description": "上交叉综合征",
	  "author": null, 
	  "copyright": null,
	  "category_id": "42",
	  "create_time":
	  "1419559493", 
	  "update_time": "1419559493", 
	  "status": "0", 
	  "image": "",
	  "code": "", 
	  "summary": null 
	  },
	   ]
	 */
	private static final long serialVersionUID = 1837169953736836527L;

	private int id;
	private String name;
	private String title;
	private String type;
	private String desc;
	private String content;
	private String image;
	private Thumbnail thumbnail = new Thumbnail();

	public HealthService2() {

	};

	public HealthService2(JSONObject json) {
		try {
			if (json.has("id"))
				id = json.getInt("id");
			if (json.has("name"))
				name = json.getString("name");
			if (json.has("title"))
				title = json.getString("title");
			if (json.has("type"))
				type = json.getString("type");
			if (json.has("desc"))
				desc = json.getString("desc");
			if (json.has("content"))
				content = json.getString("content");
			if (json.has("image"))
				image = json.getString("image");
			if (json.has("thumbnail")) {
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public Thumbnail getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}

}

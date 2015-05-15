package gov.bct.jrj.pojo;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

/**
 * 
 * @author ouzehua
 * 工商服务>工商动态 实体
 *
 */
public class BusinessDynamic implements Serializable {

	/**
	 *
	[
   	 {
       "category_id": "53",
       "category_name": "工商服务",
       "id": "275",
       "title": "工商管理",
       "content": "5626+5941565",
       "keywords": null,
       "description": "阿范德萨发",
       "author": null,
       "copyright": null,
       "create_time": "1421733593",
       "update_time": "1421733878",
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
	private String desc;
	private String title;
	private String content;
	private String image;
	private Date date;
	
	public BusinessDynamic() {

	}

	public BusinessDynamic(JSONObject json) {
		try{
			if(json.has("id")) id = json.getInt("id");
			if(json.has("category_name")) name = json.getString("category_name");
			if(json.has("title")) title = json.getString("title");
			if(json.has("description")) desc = json.getString("description");
			if(json.has("content")) content = json.getString("content");
			if(json.has("image")) image = json.getString("image");
			if(json.has("create_time")) date = new Date(json.getLong("create_time")*1000);
		}catch(Exception e){
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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
	
	
}

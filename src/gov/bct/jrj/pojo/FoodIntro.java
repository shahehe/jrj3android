package gov.bct.jrj.pojo;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

/**
 * 健康服务事项实体
 * @author ouzehua
 *
 */
public class FoodIntro implements Serializable{
	
	/**
	 *   
    [
   {
       "id": "90",
       "title": "食药11",
       "content": "",
       "keywords": null,
       "description": "111",
       "author": null,--电话
       "copyright": null,
       "category_id": "52",
       "create_time": "1420361921",
       "update_time": "1420361921",
       "status": "0",
       "image": "",
       "code": "11",
       "summary": null
   },   
	 */
	private static final long serialVersionUID = 1837169953736836527L;
	
	private int id;
	private String name;
	private String title;
	private String type;
	private String desc;
	private String content;
	private String image;
	private String phoneNum;
	public FoodIntro(JSONObject json){
		try {
			if(json.has("id")) id = json.getInt("id");
			if(json.has("name")) name = json.getString("name");
			if(json.has("title")) title = json.getString("title");
			if(json.has("type")) type = json.getString("type");
			if(json.has("description")) desc = json.getString("description");
			if(json.has("content")) content = json.getString("content");
			if(json.has("image")) image = json.getString("image");
			if(json.has("author")) phoneNum = json.getString("author");
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
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	
}

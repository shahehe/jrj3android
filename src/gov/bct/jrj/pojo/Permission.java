package gov.bct.jrj.pojo;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 办事许可实体
 * @author ouzehua
 *
 */
public class Permission implements Serializable{
	
	/**
	 * [
   {
       "category_id": "93",
       "category_name": "食品流通许可",
       "id": "300",
       "title": "如何办理食品流通许可",
       "content": "dfkldfadslfjlasdfkl",
       "keywords": null,
       "description": "23",
       "author": null,
       "copyright": null,
       "create_time": "1425363993",
       "update_time": "1425363993",
       "status": "0",
       "image": "",
       "code": "",
       "summary": null,
       "type": "article"
   },
	 */
	private static final long serialVersionUID = 1837169953736836527L;
	
	private int category_id;
	private String category_name;
	private String id;
	private String title;
	private String content;
	private String keywords;
	private String description;
	private int author;
	private String copyright;
	private String create_time;
	private String update_time;
	private String status;
	private String image;
	private String code;
	private String summary;
	private String type;
	public Permission(){
		
	};
	public Permission(JSONObject json){
		try {
			if(json.has("category_id")) category_id = json.getInt("category_id");
			if(json.has("category_name")) category_name = json.getString("category_name");
			if(json.has("id")) id = json.getString("id");
			if(json.has("title")) title = json.getString("title");
			if(json.has("content")) content = json.getString("content");
			if(json.has("keywords")) keywords = json.getString("keywords");
			if(json.has("description")) description = json.getString("description");
			if(json.has("author")) author = json.getInt("author");
			if(json.has("copyright")) copyright = json.getString("copyright");
			if(json.has("create_time")) create_time = json.getString("create_time");
			if(json.has("update_time")) update_time = json.getString("update_time");
			if(json.has("status")) status = json.getString("status");
			if(json.has("image")) image = json.getString("image");
			if(json.has("code")) code = json.getString("code");
			if(json.has("summary")) summary = json.getString("summary");
			if(json.has("type")) type = json.getString("type");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAuthor() {
		return author;
	}
	public void setAuthor(int author) {
		this.author = author;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}

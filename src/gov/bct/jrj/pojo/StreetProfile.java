package gov.bct.jrj.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 街道简介实体
 * @author ouzehua
 *
 */
public class StreetProfile implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1837169953736836527L;
	/**
	 * 
   {
       "id": "37",
       "title": "金融街简介",
       "content": "金融街简介金融街简介",
       "keywords": null,
       "description": "金融街简介",
       "author": null,
       "copyright": null,
       "category_id": "18",
       "create_time": "1417157869",
       "update_time": "1417157869",
       "status": "0",
       "image": "",
       "code": "about_jrj",
       "summary": null,
       "thumbnail":
       [
       {
           "id": "132",
           "article_id": "37",
           "image":
           {
               "small": "http://192.168.1.200/jrj-web/public/data/cache/image/8fecf51a8bc70abac4a5861ac4ba120c-small.jpg",
               "medium": "http://192.168.1.200/jrj-web/public/data/cache/image/8fecf51a8bc70abac4a5861ac4ba120c-medium.jpg",
               "large": "http://192.168.1.200/jrj-web/public/data/cache/image/8fecf51a8bc70abac4a5861ac4ba120c-large.jpg"
           },
           "desc": ""
       },
       ]
    }
	 */
	private int id;
	private String title;
	private String content;
	private String keywords;
	private String description;
	private String author;
	private String copyright;
	private String category_id;
	private String create_time;
	private String update_time;
	private String status;
	private String image;
	private String code;
	private String summary;
	private Thumbnail thumbnail = new Thumbnail();
	private List<String> imagePath = new ArrayList<String>();
	public StreetProfile(){};
	public StreetProfile(JSONObject json){
		try {
			if(json.has("id")) id = json.getInt("id");
			if(json.has("title")) title = json.getString("title");
			if(json.has("keywords")) keywords = json.getString("keywords");
			if(json.has("author")) author = json.getString("author");
			if(json.has("description")) description = json.getString("description");
			if(json.has("copyright")) copyright = json.getString("copyright");
			if(json.has("category_id")) category_id = json.getString("category_id");
			if(json.has("create_time")) create_time = json.getString("create_time");
			if(json.has("update_time")) update_time = json.getString("update_time");
			if(json.has("status")) status = json.getString("status");
			if(json.has("content")) content = json.getString("content");
			if(json.has("image")) image = json.getString("image");
			if(json.has("code")) code = json.getString("code");
			if(json.has("summary")) summary = json.getString("summary");
			if(json.has("thumbnail")){
				JSONArray array = json.getJSONArray("thumbnail");
				for(int i= 0;i<array.length();i++){
					String object = array.getJSONObject(i).getJSONObject("image").getString("medium");
					imagePath.add(object);
				}
//				thumbnail = new Thumbnail(json.getJSONObject("thumbnail"));
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
	public String getTitle () {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setAddress(String keywords) {
		this.keywords = keywords;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setGreenArea(String copyright) {
		this.copyright = copyright;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setManage(String create_time) {
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
	public void setSower(String status) {
		this.status = status;
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
	public String getCode() {
		return code;
	}
	public void setCode(String latitude) {
		this.code = latitude;
	}
	public Thumbnail getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getSummary() {
		return summary;
	}
	public void setsummary(String summary) {
		this.summary = summary;
	}
	public List<String> getImagePath() {
		return imagePath;
	}
	public void setImagePath(List<String> imagePath) {
		this.imagePath = imagePath;
	}
	
	
}

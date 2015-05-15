package gov.bct.jrj.pojo;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 便民商品实体
 * @author 欧泽华
 */
public class Shop implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1837169953736836527L;
	/**
	 * 
    {[
   {
       "category_id": "56",
       "category_name": "生活",
       "id": "98",
       "title": "生活2",--商品名称
       "content": "地方撒个",--内容
       "keywords": null,
       "description": "地方撒个",
       "author": "98765423", --联系电话
       "copyright": "湘潭",  --所属街道
       "create_time": "1420452729",
       "update_time": "1420452729",
       "status": "0",
       "image": "http://demo.bctid.com/jrj-web/public/data/cache/image/1357d2e6d634e40405a07ad110a5bd67-large.jpg",--图片
       "code": "干撒",
       "summary": "湖南" --地址
   },
   ]
	 */
	private int id;
	private String title;
	private String description;
	private String author;
	private String copyright;
	private String summary;
	private String content;
	private String image;
	private String log;// 经度
	private String lat;// 纬度
	private Thumbnail thumbnail = new Thumbnail();
	public Shop(){};
	public Shop(JSONObject json){
		try {
			if(json.has("id")) id = json.getInt("id");
			if(json.has("title")) title = json.getString("title");
			if(json.has("description")) description = json.getString("description");
			if(json.has("author")) author = json.getString("author");
			if(json.has("copyright")) copyright = json.getString("copyright");
			if(json.has("summary")) summary = json.getString("summary");
			if(json.has("content")) content = json.getString("content");
			if(json.has("image")) image = json.getString("image");
			
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
			if(json.has("thumbnail")){
				thumbnail = new Thumbnail(json.getJSONObject("thumbnail"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCopyright() {
		return copyright;
	}
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
}

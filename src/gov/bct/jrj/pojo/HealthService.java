package gov.bct.jrj.pojo;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 健康服务事项实体
 * @author ouzehua
 *
 */
public class HealthService implements Serializable{
	
	/**
	 * 
    [
   {
       "id": "57",
       "name": "健康服务",--标题
       "code": "jkfw",
       "desc": "jkfw", 
       "image": "",
       "pid": "0",
       "type": "article"
   },
    
	 */
	private static final long serialVersionUID = 1837169953736836527L;
	
	private int id;
	private String name;
	private String title;
	private String type;
	private String desc;
	private String pid;
	private String image;
	private Thumbnail thumbnail = new Thumbnail();
	public HealthService(){
		
	};
	public HealthService(JSONObject json){
		try {
			if(json.has("id")) id = json.getInt("id");
			if(json.has("name")) name = json.getString("name");
			if(json.has("title")) title = json.getString("title");
			if(json.has("type")) type = json.getString("type");
			if(json.has("desc")) desc = json.getString("desc");
			if(json.has("pid")) pid = json.getString("pid");
			if(json.has("image")) image = json.getString("image");
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
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
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

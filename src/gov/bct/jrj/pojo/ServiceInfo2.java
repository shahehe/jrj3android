package gov.bct.jrj.pojo;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 服务事项第二级的实体
 * @author ouzehua
 *
 */
public class ServiceInfo2 implements Serializable{
	
	/**
	 * 
    {
   "data":
   [
       {
           "id": "42",
           "title": "居民社保",
           "content": "北京市城市居民低收入待遇申请审批办理规范",
           "keywords": null,
           "contentription": "北京市城市居民低收入待遇申请审批办理规范",
           "author": null,
           "copyright": null,
           "category_id": "36",
           "create_time": "1418608089",
           "update_time": "1418608248",
           "status": "0",
           "image": "",
           "code": "shebao1",
           "summary": null
       }
       ]
       }
	 */
	private static final long serialVersionUID = 1837169953736836527L;
	
	private int id;
	private String title;
	private String code;
	private String content;
	private String pid;
	private String description;
	private Thumbnail thumbnail = new Thumbnail();
	public ServiceInfo2(){
		
	};
	public ServiceInfo2(JSONObject json){
		try {
			if(json.has("id")) id = json.getInt("id");
			if(json.has("title")) title = json.getString("title");
			if(json.has("code")) code = json.getString("code");
			if(json.has("content")) content = json.getString("content");
			if(json.has("pid")) pid = json.getString("pid");
			if(json.has("description")) description = json.getString("description");
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Thumbnail getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(Thumbnail thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}

package gov.bct.jrj.pojo;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 服务事项实体
 * @author ouzehua
 *
 */
public class ServiceInfo implements Serializable{
	
	/**
	 * 
    

    [
       {
           "id": "61",
           "name": "办事程序",
           "code": "",
           "desc": "",
           "image": "",
           "pid": "0",
           "type": "list"
       },
       
    ]


	 */
	private static final long serialVersionUID = 1837169953736836527L;
	
	private int id;
	private String name;
	private String code;
	private String desc;
	private String pid;
	private String image;
	private String type;
	private Thumbnail thumbnail = new Thumbnail();
	public ServiceInfo(){
		
	};
	public ServiceInfo(JSONObject json){
		try {
			if(json.has("id")) id = json.getInt("id");
			if(json.has("name")) name = json.getString("name");
			if(json.has("code")) code = json.getString("code");
			if(json.has("desc")) desc = json.getString("desc");
			if(json.has("pid")) pid = json.getString("pid");
			if(json.has("image")) image = json.getString("image");
			if(json.has("type")) type = json.getString("type");
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}

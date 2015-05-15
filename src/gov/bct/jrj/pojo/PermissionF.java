package gov.bct.jrj.pojo;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 办事许可Fragment数据实体
 * @author ouzehua
 *
 */
public class PermissionF implements Serializable{
	
	/**
	 * [
   {
       "id": "52",
       "name": "食药管理",
       "code": "",
       "desc": "",
       "image": "",
       "pid": "0",
       "type": "list"
   },
   ]
	 */
	private static final long serialVersionUID = 1837169953736836527L;
	
	private String id;
	private String name;
	private String code;
	private String desc;
	private String image;
	private String pid;
	private String type;
	public PermissionF(){
		
	};
	public PermissionF(JSONObject json){
		try {
			if(json.has("id")) id = json.getString("id");
			if(json.has("name")) name = json.getString("name");
			if(json.has("code")) code = json.getString("code");
			if(json.has("desc")) desc = json.getString("desc");
			if(json.has("image")) image = json.getString("image");
			if(json.has("pid")) pid = json.getString("pid");
			if(json.has("type")) type = json.getString("type");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}

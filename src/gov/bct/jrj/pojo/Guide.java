package gov.bct.jrj.pojo;

import java.io.Serializable;
import java.util.Date;

import org.json.JSONObject;

/**
 * 
 * @author ouzehua
 * 工商服务>服务指南 实体
 *
 */
public class Guide implements Serializable {

	/**
	 * [
   {
       "category_id": "53",
       "category_name": "工商服务",
       "id": "293",
       "title": "个体工商户注销登记",
       "content": "1、所需材料：照主本人带身份证，填写《个体工商户注销登记申请书》、《北京市工商行政管理局西城分局准许设立（变更、注销）登记通知书》、《个体工商户注销登记审核表》，填写备注（债权债务已结清，职工工资已结清，各项税款已结清）。缴回营业执照正副本。",
       "keywords": null,
       "description": "zx",
       "author": null,
       "copyright": null,
       "create_time": "1422841562",
       "update_time": "1422845839",
       "status": "0",
       "image": "",
       "code": "base",
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
	private String phone;//电话
	public Guide() {

	}

	public Guide(JSONObject json) {
		try{
			if(json.has("id")) id = json.getInt("id");
			if(json.has("category_name")) name = json.getString("category_name");
			if(json.has("title")) title = json.getString("title");
			if(json.has("description")) desc = json.getString("description");
			if(json.has("content")) content = json.getString("content");
			if(json.has("author")) phone = json.getString("author");
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}

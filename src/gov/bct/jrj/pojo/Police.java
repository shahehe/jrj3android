package gov.bct.jrj.pojo;

import org.json.JSONObject;

public class Police {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1837169953736836527L;
	/**
	 * 
    

    {
       "id": "268",
       "title": "公安服务简介",
       "content": "

    公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介

    公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介

    公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介公安服务简介
    ",
       "keywords": null,
       "description": "公安服务简介",
       "author": "10086",--电话号码
       "copyright": null,
       "category_id": "83",
       "create_time": "1421481336",
       "update_time": "1421482377",
       "status": "0",
       "image": "",
       "code": "公安服务简介",
       "summary": null
    }


	 */
	private int id;
	private String content;
	private String image;
	private String phoneNum;
	public Police(){};
	public Police(JSONObject json){
		try {
			if(json.has("id")) id = json.getInt("id");
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

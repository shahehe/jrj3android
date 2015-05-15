package gov.bct.jrj.pojo;

import java.io.Serializable;

import org.json.JSONObject;

/**
 * 专家坐诊实体
 * @author 欧泽华
 *
 */
public class Consult implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1837169953736836527L;
	/**
	 * 
    [
       {
       "id": "2",
       "office": "骨伤科门诊 ",
       "name": "陈福林 ",
       "job": "主任医师 知名专家 ",
       "speciality": "中西医结合诊治骨伤科疑难疾病，擅长正骨、筋伤疾病的诊疗。 ",
       "out_call_time": "周二全天 ",
       "re_fee": "13元",
       "message": " "
   		},
    ]
	 */
	private int id;
	private String name;
	private String address;
	private String office;
	private String job;
	private String speciality;
	private String out_call_time;
	private String re_fee;
	private String message;
//	private Thumbnail thumbnail = new Thumbnail();
//	public consult(){};
	public Consult(JSONObject json){
		try {
			if(json.has("id")) id = json.getInt("id");
			if(json.has("name")) name = json.getString("name");
			if(json.has("address")) address = json.getString("address");
			if(json.has("office")) office = json.getString("office");
			if(json.has("job")) job = json.getString("job");
			if(json.has("speciality")) speciality = json.getString("speciality");
			if(json.has("out_call_time")) out_call_time = json.getString("out_call_time");
			if(json.has("re_fee")) re_fee = json.getString("re_fee");
			if(json.has("message")) message = json.getString("message");
//			if(json.has("thumbnail")){
//				thumbnail = new Thumbnail(json.getJSONObject("thumbnail"));
//			}
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getOffice() {
		return office;
	}
	public void setOffice(String office) {
		this.office = office;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getSpeciality() {
		return speciality;
	}
	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}
	public String getOut_call_time() {
		return out_call_time;
	}
	public void setOut_call_time(String out_call_time) {
		this.out_call_time = out_call_time;
	}
	public String getRe_fee() {
		return re_fee;
	}
	public void setManage(String re_fee) {
		this.re_fee = re_fee;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
//	public String getImage() {
//		return image;
//	}
//	public void setImage(String image) {
//		this.image = image;
//	}
//	public Thumbnail getThumbnail() {
//		return thumbnail;
//	}
//	public void setThumbnail(Thumbnail thumbnail) {
//		this.thumbnail = thumbnail;
//	}
//	
	
}

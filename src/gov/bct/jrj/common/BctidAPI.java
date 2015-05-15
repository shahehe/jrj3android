package gov.bct.jrj.common;

import android.content.Context;
import android.util.Log;

import org.apache.http.conn.ssl.SSLSocketFactory;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;

import java.security.KeyStore;
import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class BctidAPI {

    public static String TAG = "BCT-API";

    public static void get(String url,AsyncHttpResponseHandler responseHandler) {
        BctidAPI.getInstance().restGet(url,null,responseHandler);
    }

    public static void get(String url,RequestParams params,AsyncHttpResponseHandler responseHandler) {
        BctidAPI.getInstance().restGet(url,params,responseHandler);
    }

	public static void post(String url, RequestParams params,AsyncHttpResponseHandler responseHandler) {
		BctidAPI.getInstance().restPost(url, params, responseHandler);
	}
	
	public static void put(String url, RequestParams params,AsyncHttpResponseHandler responseHandler) {
        BctidAPI.getInstance().restPut(url, params, responseHandler);
	}
	
	public static void delete(String url, AsyncHttpResponseHandler responseHandler) {
        BctidAPI.getInstance().restDelete(url, responseHandler);
	}
	
    /*下载文件*/
    public static void downloadFile(String url,FileAsyncHttpResponseHandler responseHandler){
    	BctidAPI.getInstance().restDownloadFile(url, responseHandler);
    }

//    public static void downloadImage(String url,ImageView imageView){
//        BctidAPI.getInstance().restDownloadImage(url,imageView);
//    }
//
//    public static void clearCache(String url){
//        BctidAPI.getInstance().clearCacheFile(url);
//    }
    
    /*下载图片到本地,url是取得服务器图片的地址，filename是32位的字符串*/
//    public static void downloadImage(String url,String fileName){
//        BctidAPI.getInstance().restDownloadImage(url,fileName);
//    }
	
	private static BctidAPI instance = null;
	private AsyncHttpClient client = null;
	private String BaseUrl = null;
//	private String ImageUrl = null;
	private String clientSecret = null;
	private String clientId = null;
//	private File cacheDir;
	private int uid = 0;
	
	public synchronized static BctidAPI getInstance() {   
		if (instance == null) {   
			instance = new BctidAPI();   
		}   
		synchronized (instance) {
			return instance;   
		}
	}
	
	public void init(Context context){
		this.BaseUrl = Utils.getMetaValue(context, "base_url");
//		if(this.BaseUrl == null) this.BaseUrl = "https://app.bctid.com";
//		this.ImageUrl = Utils.getMetaValue(context, "image_url");
//		if(this.ImageUrl == null) this.ImageUrl = "http://static.bctid.com/s3/";
		this.clientId = Utils.getMetaValue(context, "client_id");
		this.clientSecret = Utils.getMetaValue(context, "client_secret");
	}
	
	
	public String getClientId() {
		return clientId;
	}

	public AsyncHttpClient getClient(){
		if(this.client == null){
			this.client = new AsyncHttpClient();
			if(this.BaseUrl.indexOf("https") > -1){
				try{
					KeyStore trusted = KeyStore.getInstance("BKS");
					trusted.load(null,null);
		            SSLSocketFactory sf = new MySSLSocketFactory(trusted);
		            sf.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
					this.client.setSSLSocketFactory(sf);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
		return this.client;
	}
	
//	/**/
//	public void restDownloadImage(String url,String fileName){
////        final String link = this.getUrl(url);
////        Log.d(TAG,link);
////        File file = this.getCacheFile(link);
////        if(file == null){
////            this.createSignature(url);
//	        final String ALBUM_PATH  = Environment.getExternalStorageDirectory() + "/download_image/";  
//	        final String mFileName = fileName +".jpg";
//            this.getClient().get(url, new BinaryHttpResponseHandler() {
//                @Override
//                public void onStart() {
////                    Log.d("Album Adapter", "Getting the image data...");
//                }
//
//                @Override
//                public void onSuccess(byte[] fileData) {
////                    Log.d("Album Adapter", "Got the image data...");
//                    Bitmap mBitmap = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
//                    File dirFile = new File(ALBUM_PATH);
//                    if(!dirFile.exists()){
//                        dirFile.mkdir();  
//                    } 
//                    File myCaptureFile = new File(ALBUM_PATH + mFileName);
//                    BufferedOutputStream bos;
//					try {
//						bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
//	                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);  
//	                    bos.flush();  
//	                    bos.close();  
//					} catch (Exception e) {
//						e.printStackTrace();
//					}  
//                }
//
//                @Override
//                public void onFailure(Throwable e) {
//                    Log.e("Album Adapter", "Can't fetch the image");
//                }
//            });
//        }//else{
        //    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
       // }
   //}

	
//    public void restDownloadImage(String url,final ImageView imageView){
//        final String link = this.getUrl(url);
//        Log.d(TAG,link);
//        File file = this.getCacheFile(link);
//        if(file == null){
//            this.createSignature(url);
//            this.getClient().get(link, new BinaryHttpResponseHandler() {
//                @Override
//                public void onStart() {
//                    imageView.setImageResource(R.drawable.loading);
//                    Log.d("Album Adapter", "Getting the image data...");
//                }
//
//                @Override
//                public void onSuccess(byte[] fileData) {
//                    Log.d("Album Adapter", "Got the image data...");
//                    Bitmap imageThumb = BitmapFactory.decodeByteArray(fileData, 0, fileData.length);
//                    setCacheFile(link, fileData);
//                    imageView.setImageBitmap(imageThumb);
//                }
//
//                @Override
//                public void onFailure(Throwable e) {
//                    imageView.setImageResource(R.drawable.not_found);
//                    Log.e("Album Adapter", "Can't fetch the image");
//                }
//
//            });
//        }else{
//            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
//            imageView.setImageBitmap(bitmap);
//        }
//    }

	public void restPost(String url,RequestParams params,AsyncHttpResponseHandler responseHandler){
        if(params != null) Log.i(TAG,params.toString());
		this.createSignature(url);
		this.getClient().post(this.getUrl(url), params,responseHandler);
	}

    public void restGet(String url,RequestParams params,AsyncHttpResponseHandler responseHandler){
        String link = this.getUrl(url);
        Log.i(TAG,link);
        if(params != null) Log.i(TAG,params.toString());
        this.createSignature(url);
        this.getClient().get(link, params,responseHandler);
    }

    public void restPut(String url,RequestParams params,AsyncHttpResponseHandler responseHandler){
        String link = this.getUrl(url);
        Log.i(TAG,link);
        Log.i(TAG,params.toString());
        this.createSignature(url);
        this.getClient().put(link, params,responseHandler);
    }

    public void restDelete(String url,AsyncHttpResponseHandler responseHandler){
        String link = this.getUrl(url);
        Log.i(TAG,link);
        this.createSignature(url);
        this.getClient().delete(link,responseHandler);
    }
    
	/**
	 * 下载文件
	 */
	public void restDownloadFile(String url,FileAsyncHttpResponseHandler responseHandler){
        String link = this.getUrl(url);
        Log.i(TAG,link);
        this.createSignature(url);
        this.getClient().get(link,responseHandler);
    }

	private String getUrl(String url){
        Log.v(TAG,"REQUEST URL:"+this.BaseUrl + url);
		return this.BaseUrl+url;
	}

    private void createSignature(String url){
        long timestamp = System.currentTimeMillis();
        this.getClient().addHeader("BCTUID",this.getUid()+"");
//        this.getClient().addHeader("bct_domain",this.getDomain());
        this.getClient().addHeader("BCTCLIENT",this.clientId);
        this.getClient().addHeader("BCTTIMESTAMP",timestamp+"");
//        String string = this.getDomain()+this.getUid()+timestamp;
        String string = this.getUid()+""+timestamp;
        this.getClient().addHeader("BCTSIGNATURE",this.getSignature(string));
        
    }
	
	private String getSignature(String str){
		//Token = hmac-sha1( Hash(Pasword + Salt) + RequestUrl + UserName )
        try {
        	//String str = url+"-"+Session.getInstance().getDomain() + "-" + method +"-"+ Session.getInstance().getUid();
        	Log.v(TAG, str);
            Log.v(TAG, "PRIVATE SECRET:"+clientSecret);
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secret = new SecretKeySpec(this.clientSecret.getBytes(), mac.getAlgorithm());
            mac.init(secret);
            byte[] digest = mac.doFinal(str.getBytes());
            byte[] hash = MessageDigest.getInstance("MD5").digest(digest);
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 0xFF) < 0x10) hex.append("0");
                hex.append(Integer.toHexString(b & 0xFF));
            }
			return hex.toString();
			
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
	}

//    private File getCacheFile(String url){
//        File file = new File(this.getCacheDir(), HashEncrypt.encode(CryptType.MD5, url));
//        if(file.exists()){
//            return file;
//        }else{
//            return null;
//        }
//    }

//    private void setCacheFile(String url,byte[] data){
//        try {
//            File file = new File(this.getCacheDir(), HashEncrypt.encode(CryptType.MD5, url));
//            FileOutputStream out = new FileOutputStream(file);
//            out.write(data);
//            out.close();
//        } catch (Exception e) {
//            Log.e(TAG,e.getMessage());
//        }
//    }

//    private void clearCacheFile(String url){
//        File file = new File(this.getCacheDir(), HashEncrypt.encode(CryptType.MD5, url));
//        if(file.exists()){
//            file.delete();
//        }
//    }
    
//    public File getCacheDir() {
//		return cacheDir;
//	}
//
//	public void setCacheDir(File cacheDir) {
//		this.cacheDir = cacheDir;
//	}

//	public String getDomain() {
//		return domain;
//	}
//
//	public void setDomain(String domain) {
//		this.domain = domain;
//	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	/**
     * 取得图片的url
     * @param image
     * @param width
     * @param height
     * @return
     */
    public static String getImageUrl(String image){
    	if(image == null || image.length() == 0){
    		return BctidAPI.getInstance().BaseUrl+"no_image.jpg";
    	}
    	String url = BctidAPI.getInstance().BaseUrl+"/files/Application/thumb/"+image+"/0,0?.jpg";
    	return url;
    }
    
	/**
     * 取得下载文件的url
     * @return
     */
    public static String getFileUrl(String file){
    	if(file == null || file.length() == 0){
    		return null;
    	}
    	String url = BctidAPI.getInstance().BaseUrl+"/files/Application/download/"+file;
    	return url;
    }
    
    /**
     * 取得图片的url
     * @param image
     * @return
     */
//    public static String getImageUrl(String image){
//    	return BctidAPI.getImageUrl(image, 0, 0);
//    }
//    
    
}

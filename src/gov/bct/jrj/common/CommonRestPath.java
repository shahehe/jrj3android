package gov.bct.jrj.common;

public class CommonRestPath {
	
	/**
	 * 用户登录
	 */
	public static String login() {
		return "/rest/login";
	}
	
	/**
	 * 用户注册
	 */
	public static String register() {
		return "/rest/register";
	}
	
	/**
	 * 用户相关
	 */
	public static String putUser(){
		return "/rest/user";
	}
	
	/**
	 * 获取商家列表
	 */
	public static String getShopList() {
		return "/rest/supplier";
	}
	
	/**
	 * 获取商家详情
	 * @param sid 商家ID
	 */
	public static String getShopDetail(int sid) {
		return "/rest/supplier/"+sid;
	}
	
	/**
	 * 获取商家点评
	 * @param sid 商家ID
	 */
	public static String getShopComment(int sid) {
		return "/rest/supplier/"+sid+"/supplier-comment";
	}
	
	/**
	 * 发布商家点评
	 * @param sid 商家ID
	 */
	public static String postShopComment(int sid) {
		return "/rest/comment/supplier/"+sid;
	}
	
	/**
	 * 获取商家相片
	 * @param sid 商家ID
	 */
	public static String getShopPhoto(int sid) {
		return "/rest/supplier/"+sid+"/supplier-image";
	}
	
	/**
	 * 获取点评相片
	 * @param sid 商家ID
	 */
	public static String getShopCommentPhoto(int sid) {
		return "/rest/supplier/"+sid+"/supplier-comment-image";
	}
	
	/**
	 * 获取商家优惠
	 * @param sid 商家ID
	 */
	public static String getShopCoupon(int sid) {
		return "/rest/supplier/"+sid+"/supplier-coupon";
	}
	
	/**
	 * 获取商家商品
	 * @param sid 商家ID
	 */
	public static String getShopProduct(int sid) {
		return "/rest/supplier/"+sid+"/supplier-product";
	}
	
	/**
	 * 获取商品列表
	 */
	public static String getProductList() {
		return "/rest/product";
	}
	
	/**
	 * 获取商品详情
	 * @param pid 商品ID
	 */
	public static String getProductDetail(int pid) {
		return "/rest/product/"+pid;
	}
	
	/**
	 * 获取优惠券列表
	 */
	public static String getCouponList() {
		return "/rest/coupon";
	}
	
	/**
	 * 获取优惠券详情
	 * @param cid 优惠券ID
	 */
	public static String getCouponDetail(int cid) {
		return "/rest/coupon/"+cid;
	}
	
	/**
	 * 获取用户收藏的商家列表
	 */
	public static String getMyShopList() {
		return "/rest/my-collect/supplier";
	}
	
	/**
	 * 获取用户收藏的商品列表
	 */
	public static String getMyProductList() {
		return "/rest/my-collect/product";
	}
	
	/**
	 * 获取用户收藏的优惠券列表
	 */
	public static String getMyCouponList() {
		return "/rest/my-collect/coupon";
	}
	
	/**
	 * 获取用户点评
	 */
	public static String getMyCommentList() {
		return "/rest/my-comment";
	}
	
	/**
	 * 获取用户粉丝
	 */
	public static String getMyFansList() {
		return "/rest/my-fans";
	}
	
	/**
	 * 获取用户关注
	 */
	public static String getMyFocusList() {
		return "/rest/my-focus";
	}
	
	/**
	 * 关注用户
	 */
	public static String focusUser() {
		return "/rest/focus";
	}
	
	/**
	 * 获取用户收藏的优惠券列表
	 */
	public static String getMyLikeList() {
		return "/rest/my-like";
	}
	
	/**
	 * 获取用户收藏的优惠券列表
	 */
	public static String getMyDynamicList() {
		return "/rest/my-dynamic";
	}
	
	/**
	 * 获取分类、地区、主题列表
	 */
	public static String getConditionList() {
		return "/rest/condition";
	}
	
	
	/**
	 * 获取达人秀列表
	 */
	public static String getShowList() {
		return "/rest/show";
	}
	
	/**
	 * 获取达人秀详情
	 * @param sid
	 */
	public static String getShowDetail(int sid) {
		return "/rest/show/"+sid;
	}
	
	/**
	 * 回复达人秀
	 * @param sid 达人秀ID
	 */
	public static String postShowReply(int sid){
		return "/rest/reply/show/"+sid;
	}
	
	/**
	 * 上传图片地址
	 */
	public static String postFile(){
		return "/files/application/file";
	}
	
	/**
	 *加入收藏
	 *@param key (supplier,coupon,product)
	 *@param id 
	 */
	public static String getCollect(String key,int id){
		return "/rest/collect/"+key+"/"+id;
	}
	
	/**
	 *加入赞赏
	 *@param key (coupon,product,comment)
	 *@param id 
	 */
	public static String getLike(String key,int id,int to_id){
		if(to_id==0){
			return "/rest/like/"+key+"/"+id;
		}else {
			return "/rest/like/"+key+"/"+id+"/"+to_id;
		}
		
	}
	
	/**
	 * 筛选的公用接口
	 */
	public static String getSearch(){
		return "/rest/search";
	}
	
	/**
	 * 获取论坛文章列表
	 */
	public static String getArticleList(){
		return "/rest/bbs";
	}
	
	/**
	 * 获取论坛文章详情
	 * @param aid 文章ID
	 */
	public static String getArticle(int aid){
		return "/rest/bbs/"+aid;
	}
	
	/**
	 * 删除论坛文章
	 * @param aid 文章ID
	 */
	public static String deleteArticle(int aid){
		return "/rest/bbs/"+aid;
	}
	
	/**
	 * 回复论坛帖子
	 * @param aid 帖子ID
	 */
	public static String postArticleReply(int aid){
		return "/rest/reply/bbs/"+aid;
	}
	
	/**
	 * 获取子分类
	 */
	public static String getChildCategory(String pid){
		return "/rest/category/"+pid;
	}
	
	/**
	 * 获取首页的图片
	 * @return
	 */
	public static String getIndexImage(){
		return "/rest/ad";
	}
	
	/**
	 * 第三方登陆
	 * @return
	 */
	public static String postLoad(){
		return "/rest/load";
	}
}

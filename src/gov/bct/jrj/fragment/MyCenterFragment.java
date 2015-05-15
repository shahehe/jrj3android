package gov.bct.jrj.fragment;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import gov.jrj.R;
import gov.bct.jrj.activity.TextNewsListActivity;
import gov.bct.jrj.common.Alerts;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.Constants;
import gov.bct.jrj.common.PushService;
import gov.bct.jrj.common.Session;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.common.Utils.ILogoutListener;
import gov.bct.jrj.library.http.AsyncHttpClient;
import gov.bct.jrj.library.http.JsonHttpResponseHandler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 个人中心设置页面
 * 当用户已经登陆，点击个人中心的标签将会到这个页面
 * @author 欧泽华
 *
 */
public class MyCenterFragment extends Fragment {

	private List<Object> listData = new ArrayList<Object>();

	private ListView messageListView;
	private JSONArray jsons = new JSONArray();
	private Activity mContext;
	private TextView username;
	private Button logout;
	private Button receiveMsg;
	private Button userTimes;
	private ProgressDialog pd;
	private Button autoPush;
	private Boolean isPush = false;
	private Button clear;
	private TextView userTimesTV;
	private TextView userRank;
	private TextView creditTV;
	int reportTimes;
	String[] totalClass;
	
	Handler mHandler;

	private Handler mListHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				messageListView.setVisibility(View.VISIBLE);
				messageListView.setAdapter(new ListItemAdapter());
				// new
				// Utility().setListViewHeightBasedOnChildren(messageListView);
				// Resources rs = mContext.getResources();
				// Drawable dr =
				// rs.getDrawable(R.drawable.centerbackground_down);
				// receiveMsg.setBackgroundDrawable(dr);
				// dr = rs.getDrawable(R.drawable.messagebox);
				// messageListView.setBackgroundColor(Color.parseColor("#22c8b0"));
				// messageListView.setBackgroundDrawable(dr);
				break;
			case 1:
				Toast.makeText(mContext,
						mContext.getString(R.string.no_message),
						Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			// 这里是两个控制是否还能加在的函数
		}

	};

	
	public MyCenterFragment(Handler mHandler) {
		this.mHandler=mHandler;
	}
	public MyCenterFragment() {
		super();
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Auto-generated method stub
				super.onCreate(savedInstanceState);
				totalClass = getResources().getStringArray(R.array.total_class);
				AsyncHttpClient client = new AsyncHttpClient();
				int uid = getActivity().getSharedPreferences(
						Constants.KEY_SESSION_PREFS, 0).getInt(Constants.KEY_UID, 0);
				client.get(Config.URL + "/credit.php?uid=" + uid,
						new JsonHttpResponseHandler() {
							@Override
							public void onSuccess(JSONObject json) {
								try {
									String credit = json.getString("credit");
									creditTV.setText(credit);
									pd.dismiss();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							@Override
							public void onStart() {
								pd = ProgressDialog.show(getActivity(), null,
										getString(R.string.loading_credit));
							}

							@Override
							public void onFinish() {
								pd.dismiss();
							}

							@Override
							public void onFailure(Throwable e) {
								pd.dismiss();
								Alerts.show("网络错误,服务器无法访问", getActivity());
							}

						});
				TelephonyManager tm = (TelephonyManager) getActivity()
						.getSystemService(Context.TELEPHONY_SERVICE);
				// 获取手机号码
				String phoneId = tm.getLine1Number();
				// phoneId = "18600318399";
				boolean isInner = true;
				try {
					if (phoneId.length() >= 11)
						phoneId = phoneId.substring(phoneId.length() - 11,
								phoneId.length());
					else
						phoneId = "0";
					BigInteger bi = new BigInteger(phoneId);
					// 186 0031 8001-8399
					BigInteger start = new BigInteger("18600318001");
					BigInteger end = new BigInteger("18600318399");
					if (!(bi.compareTo(start) >= 0 && bi.compareTo(end) <= 0)) {
						client.get(Config.URL + "/getRank.php?uid=" + uid,
								new JsonHttpResponseHandler() {

									@Override
									public void onSuccess(JSONObject response) {
										// TODO Auto-generated method stub
										super.onSuccess(response);
										System.out.println(response+"我和军分区");
										System.out.println(totalClass[0]+"我2");
										System.out.println(totalClass[1]+"爱妃");
										System.out.println(totalClass[2]+"萨芬");
										System.out.println(totalClass[3]+"撒广发");
										
										try {
											SharedPreferences prefs = getActivity()
													.getSharedPreferences(
															Constants.KEY_UID, 0);
											int count = prefs
													.getInt(Constants.COUNT, 0);
											String userClass;
											int score = response.getInt("credit");
											if (count >= 10 || score >= 500)
												userClass = totalClass[3];
											else if (count > 3 || score > 150)
												userClass = totalClass[2];
											else if (count >= 1 || score > 50)
												userClass = totalClass[1];
											else
												userClass = totalClass[0];

											userRank.setVisibility(TextView.VISIBLE);
											userRank.setText("等级:" + userClass
													+ " 排名:第"
													+ response.getString("Rank") + "名");
											System.out.println(count);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}

								});
						isInner = false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}
		View view = inflater.inflate(R.layout.person_center_logined_fragment,
				container, false);
		// ImageButton btn1 = (ImageButton)view.findViewById(R.id.imageButton1);
		// btn1.setOnClickListener(this);
		// ImageButton btn2 = (ImageButton)view.findViewById(R.id.imageButton2);
		// btn2.setOnClickListener(this);
		mContext = this.getActivity();
		// scrollViewInner = (ScrollView) view
		// .findViewById(R.id.messagelistScrollInner);
		// scrollViewOutter = (ScrollView) mContext
		// .findViewById(R.id.centerscroll);

		SharedPreferences prefs = mContext.getSharedPreferences(
				Constants.KEY_SESSION_PREFS, 0);
		creditTV = (TextView) view.findViewById(R.id.usercredit);
		username = (TextView) view.findViewById(R.id.uesrname);
		username.setText(mContext.getString(R.string.text1) + " "
				+ prefs.getString(Constants.KEY_USER_NAME, ""));
		userRank = (TextView) view.findViewById(R.id.rank);

		logout = (Button) view.findViewById(R.id.logout);
		receiveMsg = (Button) view.findViewById(R.id.receive_messages);
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				logoutListener.onLogout(getActivity());
			}
		});

		receiveMsg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				messageListView = (ListView) mContext
						.findViewById(R.id.messageList);
				if (messageListView.getVisibility() == View.GONE) {
					loadData();
				} else {
					messageListView.setVisibility(View.GONE);
					Resources rs = mContext.getResources();
					Drawable dr = rs.getDrawable(R.drawable.centerbackground);
					v.setBackgroundDrawable(dr);
				}
			}

		});

		clear = (Button) view.findViewById(R.id.clear);
		clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Config.delAllFile(mContext.getCacheDir().toString());
				Session.getInstance().setCheckedUpdate(false);
				Config.clearCacheData(mContext, "Product.getList");
				Session.getInstance().setCheckedProduceUpdate(false);

				SharedPreferences prefs = mContext.getSharedPreferences(
						TextNewsListActivity.PREFS_NAME,
						android.content.Context.MODE_WORLD_WRITEABLE);
				prefs.edit().clear().commit();
				Toast.makeText(mContext,
						mContext.getString(R.string.clear_success),
						Toast.LENGTH_SHORT).show();

			}

		});

		autoPush = (Button) view.findViewById(R.id.autopush);
		boolean isAutoPush = mContext.getSharedPreferences(PushService.TAG,
				android.content.Context.MODE_PRIVATE).getBoolean("isAutoPush",
				false);
		if (!isAutoPush) {
			Resources rs = mContext.getResources();
			Drawable dr = rs.getDrawable(R.drawable.auto_push_off);
			autoPush.setBackgroundDrawable(dr);
			Log.d("push", "stop service");
			PushService.actionStop(mContext.getApplicationContext());
			isPush = false;
		} else {
			isPush = true;
			Resources rs = mContext.getResources();
			Drawable dr = rs.getDrawable(R.drawable.auto_push_on);
			autoPush.setBackgroundDrawable(dr);
			// boolean isStarted =
			// mContext.getSharedPreferences(PushService.TAG,
			// android.content.Context.MODE_PRIVATE).getBoolean(
			// "isStarted", false);
			// if (!isStarted)
			if (!PushService.isServiceRunning(mContext)) {
				PushService.actionStart(mContext.getApplicationContext());
			}
		}
		autoPush.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Resources rs = mContext.getResources();
				if (isPush == true) {
					Drawable dr = rs.getDrawable(R.drawable.auto_push_off);
					v.setBackgroundDrawable(dr);
					Log.d("push", "stop service");
					PushService.actionStop(mContext.getApplicationContext());
					isPush = false;
					SharedPreferences prefs = mContext.getSharedPreferences(
							PushService.TAG,
							android.content.Context.MODE_PRIVATE);
					Editor editor = prefs.edit();
					editor.putBoolean("isAutoPush", false);
					editor.commit();
				} else {
					isPush = true;
					Drawable dr = rs.getDrawable(R.drawable.auto_push_on);
					v.setBackgroundDrawable(dr);
					if (!PushService.isServiceRunning(mContext)) {
						PushService.actionStart(mContext
								.getApplicationContext());
					}
					SharedPreferences prefs = mContext.getSharedPreferences(
							PushService.TAG,
							android.content.Context.MODE_PRIVATE);
					Editor editor = prefs.edit();
					editor.putBoolean("isAutoPush", true);
					editor.commit();
				}
			}

		});
		userTimesTV = (TextView) view.findViewById(R.id.usertimesTV);
		userTimes = (Button) view.findViewById(R.id.usertimes);
		userTimes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Resources rs = mContext.getResources();
				if (userTimesTV.getVisibility() == View.GONE) {
					Drawable dr = rs
							.getDrawable(R.drawable.centerbackground_down);
					v.setBackgroundDrawable(dr);
					userTimesTV.setVisibility(View.VISIBLE);
					// SharedPreferences prefs = getActivity()
					// .getSharedPreferences(Constants.KEY_UID, 0);
					// reportTimes = prefs.getInt(Constants.COUNT, 0);
					// userTimesTV.setText("您已经提交了" + reportTimes + "次");
					AsyncHttpClient client = new AsyncHttpClient();
					int uid = getActivity().getSharedPreferences(
							Constants.KEY_SESSION_PREFS, 0).getInt(
							Constants.KEY_UID, 0);
					client.get(Config.URL + "/getReport.php?uid=" + uid,
							new JsonHttpResponseHandler() {
								@Override
								public void onSuccess(JSONObject json) {
									try {
										String credit = json
												.getString("report_time");
										userTimesTV.setText(credit);
										pd.dismiss();
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}

								@Override
								public void onStart() {
									pd = ProgressDialog.show(getActivity(),
											null,
											getString(R.string.loading_times));
								}

								@Override
								public void onFinish() {
									pd.dismiss();
								}

								@Override
								public void onFailure(Throwable e) {
									pd.dismiss();
									Alerts.show("网络错误,服务器无法访问", getActivity());
								}

							});
					// dr = rs.getDrawable(R.drawable.messagebox);
					// userTimesTV.setBackgroundDrawable(dr);
				} else {
					Drawable dr = rs
							.getDrawable(R.drawable.centerbackground_up);
					v.setBackgroundDrawable(dr);
					userTimesTV.setVisibility(View.GONE);
				}
			}

		});

		return view;
	}
	
	private void loadData() {
		listData.clear();
		SharedPreferences prefs = mContext.getSharedPreferences(
				Constants.KEY_SESSION_PREFS, 0);
		String url = Config.URL + "/message.php?uid="
				+ prefs.getInt(Constants.KEY_UID, 0);
		String data = null;
		// Session.getInstance().setNewMessage(true);
		// if (Session.getInstance().isNewMessage()) {
		// data = Config.getCacheData(this.getActivity(), url);
		// } else {
		// Session.getInstance().setNewMessage(false);
		// }
		// if (data == null) {
		// this.getData(url);
		// } else {
		// try {
		// this.processData(new JSONArray(data));
		// } catch (Exception ex) {
		// this.getData(url);
		// }
		// }
		this.getData(url);

	}
	
	public void getData(final String url) {
		if (Config.isConnected(MyCenterFragment.this.getActivity())) {
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(url, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONObject json) {
					Log.v("submit", json.toString());
					try {
						if (json.getInt("code") == 1) {
							processData(json.getJSONArray("data"));
							// Config.setCacheData(
							// MyCenterFragment.this.getActivity(), url,
							// json.getJSONArray("data").toString());
						}
					} catch (JSONException e) {
						Alerts.show(e.getMessage(), mContext);
					}
				}

				@Override
				public void onStart() {
					pd = ProgressDialog.show(mContext, null,
							getString(R.string.loading));
				}

				@Override
				public void onFinish() {
					pd.dismiss();
				}

				@Override
				public void onFailure(Throwable e) {
					Alerts.show(e.getMessage(), mContext);
				}
			});
		}
	}

	public void processData(JSONArray json) {
		try {
			jsons = json;
			this.listData.clear();
			// // this.listTag.clear();
			//
			// // this.listTag.add("user");
			// this.listData.add("user");
			// this.listData.add("user-item");
			// this.listData.add("message-update");
			//
			// // this.listTag.add("message");
			// this.listData.add("message");

			if (json.length() > 0) {
				for (int x = 0; x < json.length(); x++) {
					JSONObject item = json.getJSONObject(x);
					listData.add(item);
				}
				mListHandler.sendEmptyMessage(0);
			} else {
				mListHandler.sendEmptyMessage(1);
			}
			// else{
			// listData.add("none");
			// }
			// listTag.add("push");
			// listData.add("push");
			// listData.add("push-item");
			// listView.setAdapter(new ListItemAdapter());
		} catch (Exception ex) {

		}

	}
	
	public class ListItemAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int pos) {
			return listData.get(pos);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int pos, View v, ViewGroup vg) {
			View view = null;
			try {
				view = mContext.getLayoutInflater().inflate(
						R.layout.message_item, vg, false);
				TextView title = (TextView) view.findViewById(R.id.textView1);
				TextView desc = (TextView) view.findViewById(R.id.textView2);
				// JSONObject json = jsons.getJSONObject(pos);
				JSONObject json = (JSONObject) listData.get(pos);
				if (!json.getString("message").equals("")) {
					title.setText(json.getString("message"));
					desc.setText(json.getString("create_time"));
				} else {
					title.setText(mContext.getString(R.string.no_message));
					desc.setText("");
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return view;
		}
	}
	private OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.button1: // logout
				logoutListener.onLogout(getActivity());
				break;
			case R.id.autopush: // submitted feedback
				CheckBox cb = (CheckBox) arg0;
				if (cb.isChecked()) {
					// System.out.println("sss");
					// PushService.actionStart(getActivity().getApplicationContext());
				} else {
					// System.out.println("sss");
					// PushService.actionStop(getActivity().getApplicationContext());
				}
				break;
			default:
				break;
			}
		}
	};
	private OnClickListener updateMessageClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			SharedPreferences prefs = mContext.getSharedPreferences(
					Constants.KEY_SESSION_PREFS, 0);
			String url = Config.URL + "/message.php?uid="
					+ prefs.getInt(Constants.KEY_UID, 0);
			getData(url);
		}
	};
	private ILogoutListener logoutListener = new ILogoutListener() {

		@Override
		public boolean onLogout(Context context) {
			SharedPreferences prefs = context.getSharedPreferences(
					Constants.KEY_SESSION_PREFS, 0);
			int uid = prefs.getInt(Constants.KEY_UID, 0);
			// System.out.print(uid);
			Editor editor = prefs.edit();
			editor.putString(Constants.KEY_USER_NAME, "");
			editor.putBoolean(Constants.KEY_IS_LOGINED, false);
			editor.putInt(Constants.KEY_UID, 0);
			editor.commit();
			Log.v("LOGOUT uid", String.valueOf(uid));
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(Config.URL + "/login.php?logout=" + uid,
					new JsonHttpResponseHandler() {
						@Override
						public void onSuccess(JSONObject response) {
							Log.v("LOGOUT", response.toString());
						}
					});
			Utils.sendMessage(mHandler, Constants.WHAT_LOGOUT);
			PushService.actionStop(getActivity());
			return true;
		}
	};
}

class Utility {
	public void setListViewHeightBasedOnChildren(ListView listView) {
		// 获取ListView对应的Adapter
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		// listView.getDividerHeight()获取子项间分隔符占用的高度
		// params.height最后得到整个ListView完整显示需要的高度
		listView.setLayoutParams(params);
	}
}

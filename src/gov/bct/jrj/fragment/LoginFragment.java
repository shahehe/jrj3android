package gov.bct.jrj.fragment;

import gov.bct.jrj.R;
import gov.bct.jrj.common.Alerts;
import gov.bct.jrj.common.Config;
import gov.bct.jrj.common.Constants;
import gov.bct.jrj.common.PushService;
import gov.bct.jrj.common.Session;
import gov.bct.jrj.common.Utils;
import gov.bct.jrj.common.Utils.ILoginListener;
import gov.bct.jrj.library.http.AsyncHttpClient;
import gov.bct.jrj.library.http.JsonHttpResponseHandler;
import gov.bct.jrj.library.http.RequestParams;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 登陆页面
 * @author 欧泽华
 *
 */
@SuppressLint("ValidFragment")
public class LoginFragment extends Fragment {

	Button loginBtn;
	Button registerBtn;
	EditText userEdit;
	EditText pwdEdit;
	Context mContext;
	String userName;
	String password;
	Handler mHandler;
	private ProgressDialog pd;
	ILoginListener loginListener = new ILoginListener() {

		@Override
		public boolean onLoginSuccess(Context context,JSONObject data) {
			try{
				//Start Push Service 
				//start service
				//PushService.actionStart(getActivity().getApplicationContext());
				
				SharedPreferences prefs =mContext.getSharedPreferences(Constants.KEY_SESSION_PREFS, 0);
				Editor editor = prefs.edit();
				editor.putString(Constants.KEY_USER_NAME, userName);
				editor.putBoolean(Constants.KEY_IS_LOGINED, true);
				editor.putInt(Constants.KEY_UID, data.getInt("uid"));
//				System.out.println(data.getInt("uid"));
				editor.commit();
			}catch(Exception ex){
				ex.printStackTrace();
			}
			Utils.sendMessage(mHandler, Constants.WHAT_LOGIN);
			return true;
		}

		@Override
		public void onLoginFail() {

		}

	};
	
	
	public LoginFragment(Handler handler) {
		mHandler = handler;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) {
			return null;
		}

		//View view = inflater.inflate(R.layout.person_center_fragment,
		View view = inflater.inflate(R.layout.login,
				container, false);
		loginBtn = (Button) view.findViewById(R.id.button2);
		loginBtn.setOnClickListener(new LoginOnClickListener());
		registerBtn = (Button) view.findViewById(R.id.button1);
		registerBtn.setOnClickListener(new RegisterOnClickListener());
		userEdit = (EditText) view.findViewById(R.id.edit_text);
		pwdEdit = (EditText) view.findViewById(R.id.edit_text1);
		mContext = getActivity();
		return view;
	}

	class LoginOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			userName = userEdit.getText().toString();
			password = pwdEdit.getText().toString();
			if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)){
				Toast.makeText(mContext, R.string.login_null, Toast.LENGTH_SHORT).show();
				return;
			}else{
				//hide keybard
				//InputMethodManager imm = (InputMethodManager)LoginFragment.this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			    //imm.hideSoftInputFromWindow(LoginFragment.this.getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);  
				
			    SharedPreferences mPrefs = getActivity().getSharedPreferences(PushService.TAG, Context.MODE_PRIVATE);
				String deviceID = mPrefs.getString(PushService.PREF_DEVICE_ID, null);
				RequestParams params = new RequestParams();
				params.put("name", userName);
				params.put("password", password);
				params.put("deviceid",deviceID);
				if(Config.isConnected(LoginFragment.this.getActivity())){
				AsyncHttpClient client = new AsyncHttpClient();
				client.post(Config.URL+"/login.php?first=true",params, new JsonHttpResponseHandler(){

					@Override
					public void onSuccess(JSONObject response) {
						try{
							System.out.println(response.toString());
							if(response.getInt("code") == 1){
								loginListener.onLoginSuccess(mContext,response.getJSONObject("data"));
							}else{
								Alerts.show(response.getString("message"), LoginFragment.this.getActivity());
							}
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}

					@Override
					public void onStart() {
						pd = ProgressDialog.show(LoginFragment.this.getActivity(), null,"正在登录中...");
					}

					@Override
					public void onFinish() {
						pd.dismiss();
					}

					@Override
					public void onFailure(Throwable error) {
						Alerts.show(error.getMessage(), LoginFragment.this.getActivity());
					}
					
				});}
			}
			
		}
	}

	class RegisterOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			Session.getInstance().setHandler(mHandler);
			Utils.sendMessage(mHandler, Constants.WHAT_REGISTER);
			/*
			Intent i = new Intent();
			i.setClass(LoginFragment.this.getActivity(), RegisterActivity.class);
			LoginFragment.this.startActivity(i);
			PushService.actionStart(LoginFragment.this.getActivity().getApplicationContext());*/
		}
	}

	public LoginFragment() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

}

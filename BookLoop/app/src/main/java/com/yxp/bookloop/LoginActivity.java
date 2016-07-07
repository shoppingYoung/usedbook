package com.yxp.bookloop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yxp.bookloop.Utils.YxpUtils.sendDataByPost;

public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView huang;
    private EditText userName;
    private EditText password;
    private Button login;
    private Button register;
    private static final String LOGIN_URL = "http://182.254.136.170/usedbook/login.php";
    public static String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        huang = (TextView) findViewById(R.id.tv_huang);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        huang.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            //点击“随便看看”
            case R.id.tv_huang:
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
                break;

            //点击“登录”按钮
            case R.id.login:
                doLogin();
                break;

            //点击“注册”按钮
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    //登录逻辑
    private void doLogin() {
        //得到用户名和密码
        final String getUserName = userName.getText().toString();
        String getPassword = password.getText().toString();
        //判断用户输入的内容是否是电话号码
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(getUserName);
        if (!m.matches()) {
            Toast.makeText(getApplicationContext(), "请输入正确的手机号码！",
                    Toast.LENGTH_SHORT).show();
        } else if (getPassword.equals("")) {
            Toast.makeText(getApplicationContext(), "您还没有输入密码！",
                    Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setMessage("正在登录。。。");
            progressDialog.show();
            //将用户填写的信息上传到服务器
            new AsyncTask<String, Void, String>() {
                @Override
                protected String doInBackground(String... params) {
                    return getDataFromWeb(params[0], params[1], params[2]);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    HandleMsgForLogin(getUserName, progressDialog, s);
                }
            }.execute(LOGIN_URL, getUserName, getPassword);
        }
    }

    private String getDataFromWeb(String url, String mUserName, String mPassword) {
        String userName = "userName";
        String password = "password";
        String a = "";
        String result = sendDataByPost(url, userName, mUserName, password, mPassword, a, a);
        return result;
    }

    private void HandleMsgForLogin(String userName, ProgressDialog progressDialog, String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("msg");
            if (code == 200) {

                //用户唯一识别码
                userId = userName;
                progressDialog.dismiss();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

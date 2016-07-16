package com.yxp.bookloop;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yxp.bookloop.Utils.YxpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yxp.bookloop.Utils.YxpUtils.sendDataByPost;


public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText userName;
    private EditText verify;
    private EditText password;
    private Button sendMsg;
    private Button registerComplete;
    private Button back_button;
    private static final String GET_VERIFY_CODE = "http://182.254.136.170/usedbook/sendmessage.php";
    private static final String REG_URL = "http://182.254.136.170/usedbook/register.php";
    public static String userId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = (EditText) findViewById(R.id.userName);
        verify = (EditText) findViewById(R.id.verify);
        password = (EditText) findViewById(R.id.password);
        sendMsg = (Button) findViewById(R.id.sendMsg);
        registerComplete = (Button) findViewById(R.id.registerComplete);
        back_button = (Button) findViewById(R.id.back_button);

        sendMsg.setOnClickListener(this);
        registerComplete.setOnClickListener(this);

        //点击“返回”按钮
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //响应用户单击事件
    @Override
    public void onClick(View v) {
        final String getUserName = userName.getText().toString();
        String getVerify = verify.getText().toString();
        String getPassword = password.getText().toString();
        switch (v.getId()) {

            //点击“发送验证码”
            case R.id.sendMsg:
                //如果手机号码是有效的，则发送短信验证码
                if (clickVerify(getUserName)) {
                    if (!YxpUtils.isNetworkAvailable(getApplicationContext())) {
                        Toast.makeText(this, "当前网络不可用!", Toast.LENGTH_SHORT).show();
                    } else {
                        sendMsg.setEnabled(false);
                        userName.setEnabled(false);
                        sendMsg.setText("验证码已发送");
                        sendMsg.setTextSize(16);
                        new AsyncTask<String, Void, String>() {
                            @Override
                            protected String doInBackground(String... params) {
                                return getMessageStatusCode(params[0], params[1]);
                            }

                            @Override
                            protected void onPostExecute(String s) {
                                super.onPostExecute(s);
                                HandleMsgForVerify(s);
                            }
                        }.execute(GET_VERIFY_CODE, getUserName);
                    }
                }
                break;

            //点击“注册”按钮
            case R.id.registerComplete:
                if (clickRegister(getUserName, getVerify, getPassword)) {
                    if (!YxpUtils.isNetworkAvailable(getApplicationContext())) {
                        Toast.makeText(this, "当前网络不可用!", Toast.LENGTH_SHORT).show();
                    }
                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected String doInBackground(String... params) {
                            return getDataFromWeb(params[0], params[1], params[2], params[3]);
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                            HandleMsgForReg(getUserName, s);
                        }
                    }.execute(REG_URL, getUserName, getVerify, getPassword);
                }
                break;
            default:
        }
    }

    //判断用户是否填写了手机号
    private boolean clickVerify(String user) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(user);
        if (!m.matches()) {
            Toast.makeText(getApplicationContext(), "请输入正确的手机号码！", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    //判断用户信息是否填写完整
    private boolean clickRegister(String user, String verify, String password) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
        Matcher m = p.matcher(user);
        if (!m.matches()) {
            Toast.makeText(getApplicationContext(), "您还没有填写手机号码！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (verify.length() < 6) {
            Toast.makeText(getApplicationContext(), "你还没有输入验证码！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "密码不能少于6位！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //请求发送验证码
    private String getMessageStatusCode(String url, String phone) {
        String userName = "userName";
        String a = "";
        String result = sendDataByPost(url, userName, phone, a, a, a, a);
        return result;
    }


    //判断验证码是否发送成功
    private void HandleMsgForVerify(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            int code = jsonObject.getInt("code");
            if (code == 200) {
                //让手机号码输入框进入“不可编辑”状态，防止用户修改手机号码
                userName.setEnabled(false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //将用户注册信息提交到服务器
    private String getDataFromWeb(String url, String mUserName, String mVerify, String mPassword) {
        String userName = "userName";
        String verify = "verify";
        String password = "password";
        String result = sendDataByPost(url, userName, mUserName, verify, mVerify, password, mPassword);
        return result;
    }

    //判断用户是否注册成功
    public void HandleMsgForReg(String userName, String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            int code = jsonObject.getInt("code");
            String msg = jsonObject.getString("msg");
            if (code == 200) {

                //用户唯一识别码
                userId = userName;
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
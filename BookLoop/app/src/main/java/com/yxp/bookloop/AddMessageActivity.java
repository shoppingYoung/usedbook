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

public class AddMessageActivity extends Activity implements View.OnClickListener {

    private Button btn_back;
    private Button btn_submit;
    private EditText content;
    private static String ADD_URL = "http://182.254.136.170/usedbook/addmessage.php";
    private static final String USER = LoginActivity.userId.equals("") ? RegisterActivity.userId : LoginActivity.userId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmessage);

        btn_back = (Button) findViewById(R.id.btn_back);
        content = (EditText) findViewById(R.id.et_content);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            //点击“返回”
            case R.id.btn_back:
                onBackPressed();
                break;

            //点击“留言”
            case R.id.btn_submit:
                checkContent(content,USER);

                break;
        }

    }

    //留言
    private void checkContent(EditText view, String user) {
        String msgContent = view.getText().toString();
        if (msgContent.equals("")) {
            Toast.makeText(AddMessageActivity.this, "请输入留言内容！", Toast.LENGTH_SHORT).show();
        } else {
            new AddMessageAsyncTask().execute(ADD_URL, user, msgContent);
        }
    }

    // 异步添加留言
    class AddMessageAsyncTask extends AsyncTask< String, Void, String > {
        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String user = "user";
            String mUer = params[1];
            String content = "content";
            String mContent = params[2];
            String a = "";

            String result = YxpUtils.sendDataByPost(url, user, mUer,content, mContent,a,a);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                String jsonString = s;
                JSONObject jsonObject = new JSONObject(jsonString);
                int code = jsonObject.getInt("code");
                String msg = jsonObject.getString("msg");
                if (code == 200) {
                    Intent intent = new Intent(AddMessageActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddMessageActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

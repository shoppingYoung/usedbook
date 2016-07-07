package com.yxp.bookloop;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShowMessageFragment extends Fragment {
    private TextView tv_say;
    private ListView mListView;
    private static final String SHOW_MESSAGE_URL = "http://182.254.136.170/usedbook/showmessage.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_showmessage, container, false);
        tv_say = (TextView) view.findViewById(R.id.tv_say);

        //点击“我要留言"
        loadMessage();

        mListView = (ListView) view.findViewById(R.id.lv_message);
        new ShowMessageAsyncTask().execute(SHOW_MESSAGE_URL);
        return view;
    }

    private void loadMessage() {
        tv_say.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //判断用户是否登录
                if (MainActivity.isLogin()) {
                    Intent intent = new Intent(getActivity(), AddMessageActivity.class);
                    intent.putExtra("user", MainActivity.USER);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "游客不能留言！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //异步加载留言
    class ShowMessageAsyncTask extends AsyncTask<String, Void, List<MessageBoard>> {

        @Override
        protected List<MessageBoard> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<MessageBoard> messageBoard) {
            super.onPostExecute(messageBoard);
            MessageAdapter adapter = new MessageAdapter(getActivity(), messageBoard);
            mListView.setAdapter(adapter);
        }
    }

    //得到具体的留言内容
    private List<MessageBoard> getJsonData(String url) {
        List<MessageBoard> messageBoardList = new ArrayList<>();
        try {
            String jsonString = readStream(new URL(url).openStream());
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray arr = jsonObject.getJSONArray("info");
            for (int i = 0; i < arr.length(); i++) {
                jsonObject = arr.getJSONObject(i);
                MessageBoard messageBoard = new MessageBoard();
                messageBoard.userFace = jsonObject.getString("userFace");
                messageBoard.user = jsonObject.getString("nickname");
                messageBoard.content = jsonObject.getString("content");
                messageBoard.msgTime = jsonObject.getString("msgTime");
                messageBoardList.add(messageBoard);
            }
            return messageBoardList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取服务器返回的内容
    private String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";
        try {
            isr = new InputStreamReader(is, "UTF-8");
            String str;
            BufferedReader br = new BufferedReader(isr);
            while ((str = br.readLine()) != null) {
                result = result + str;
            }
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

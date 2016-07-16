package com.yxp.bookloop;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OnSaleFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView mListView;
    private static final String ON_SALE_URL = "http://182.254.136.170/usedbook/booklist.php";
    private String[] option = {"打电话", "发短信"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_on_sale, container, false);
        mListView = (ListView) view.findViewById(R.id.lv_book);
        mListView.setOnItemClickListener(this);
        getJSONByVolley(ON_SALE_URL);
        return view;
    }

    private void display(List<Book> bookList) {
        BookAdapter bookAdapter = new BookAdapter(getActivity(), bookList);
        mListView.setAdapter(bookAdapter);
    }

    public void getJSONByVolley(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        display(addBookData(jsonObject));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "当前网络不可用", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private List<Book> addBookData(JSONObject jsonObject) {
        List<Book> bookList = new ArrayList<>();
        JSONObject bookJsonObject;
        try {
            JSONArray arr = jsonObject.getJSONArray("info");
            for (int i = 0; i < arr.length(); i++) {
                bookJsonObject = arr.getJSONObject(i);
                Book book = new Book();
                book.userFace = bookJsonObject.getString("userface");
                book.userName = bookJsonObject.getString("nickname");
                book.school = bookJsonObject.getString("school");
                book.bookFace = bookJsonObject.getString("bookface");
                book.bookName = bookJsonObject.getString("bookname");
                book.author = bookJsonObject.getString("author");
                book.publish = bookJsonObject.getString("publish");
                book.price = bookJsonObject.getString("price");
                book.pubTime = bookJsonObject.getString("pub_time");
                bookList.add(book);
            }
            return bookList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    //设置点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if (!MainActivity.isLogin()) {
            Toast.makeText(getActivity(), "你需要登录才能与卖家联系！", Toast.LENGTH_SHORT).show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("请选择联系方式");
            builder.setItems(option, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent;
                    if (which == 0) {
                        intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:13006336086"));
                    } else {
                        int first = mListView.getFirstVisiblePosition();
                        RelativeLayout layout = (RelativeLayout) mListView.getChildAt(position - first);
                        TextView tv = (TextView) layout.findViewById(R.id.tv_bookName);
                        String bookName = tv.getText().toString();
                        String body = "童鞋你好，我在“书环”这款APP上面看到你出售《" + bookName + "》，请问这本书还有吗？";
                        intent = new Intent(Intent.ACTION_SENDTO);
                        intent.setData(Uri.parse("smsto:13006336086"));
                        intent.putExtra("sms_body", body);
                    }
                    startActivity(intent);
                }
            });
            builder.show();
        }
    }

}
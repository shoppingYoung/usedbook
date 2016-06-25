package com.yxp.bookloop;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

public class OnSaleFragment extends Fragment {
    private ListView mListView;
    private static final String ON_SALE_URL = "http://182.254.136.170/usedbook/booklist.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vie = inflater.inflate(R.layout.fragment_on_sale, container, false);
        mListView = (ListView) vie.findViewById(R.id.lv_book);
        getJSONByVolley(ON_SALE_URL);
        return vie;
    }

    private void display(List<Book> bookList) {
        BookAdapter bookAdapter = new BookAdapter(getActivity(),bookList);
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
                System.out.print("错误");
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
                book.msgTime = bookJsonObject.getString("pub_time");
                bookList.add(book);
            }
            return bookList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
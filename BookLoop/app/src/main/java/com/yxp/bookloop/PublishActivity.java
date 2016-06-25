package com.yxp.bookloop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class PublishActivity extends Activity {
    private String path;
    private Uri mURi;
    private ImageView iv_book;
    private Button btn_publish;
    private Spinner sp_category;
    private Spinner sp_newOrOld;
    private List<String> categoryList;
    private ArrayAdapter<String> categoryAdapter;
    private List<String> newOrOldList;
    private ArrayAdapter<String> newOrOldAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        iv_book = (ImageView) findViewById(R.id.iv_book);
        btn_publish = (Button) findViewById(R.id.btn_publish);
        sp_category = (Spinner) findViewById(R.id.sp_category);
        sp_newOrOld = (Spinner) findViewById(R.id.sp_newOrOld);

        setCategory();
        setNewOrOld();

        iv_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBookPicture();
            }
        });

        //点击“发布”按钮
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPressPublish();
            }
        });
    }

    //重写“返回键”逻辑
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    //添加书的封面
    private void addBookPicture() {
        String fileName = System.currentTimeMillis() + ".jpg";
        path = Environment.getExternalStorageDirectory().getPath() + "/" + fileName;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mURi = Uri.fromFile(new File(path));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mURi);
        startActivityForResult(intent, 1);
    }

    //设置“类别”数据
    private void setCategory() {
        categoryList = new ArrayList<>();
        categoryList.add("文学艺术");
        categoryList.add("人文社科");
        categoryList.add("经济管理");
        categoryList.add("生活休闲");
        categoryList.add("外语学习");
        categoryList.add("自然科学");
        categoryList.add("考试教育");
        categoryList.add("计算机");
        categoryList.add("生物医学");
        categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp_category.setAdapter(categoryAdapter);
    }

    //设置“新旧”数据
    private void setNewOrOld() {
        newOrOldList = new ArrayList<>();
        newOrOldList.add("全新");
        newOrOldList.add("9成新");
        newOrOldList.add("8成新");
        newOrOldList.add("7成新");
        newOrOldList.add("6成新及以下");
        newOrOldAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, newOrOldList);
        newOrOldAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp_newOrOld.setAdapter(newOrOldAdapter);
    }

    //“发布”按钮的逻辑
    private void onPressPublish() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("再来一本？");
        builder.setPositiveButton("好", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PublishActivity.this, PublishActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("不了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(PublishActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(path));
                iv_book.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

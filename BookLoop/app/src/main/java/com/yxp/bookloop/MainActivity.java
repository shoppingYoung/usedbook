package com.yxp.bookloop;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup bottomMenu;
    private RadioButton button_onSale, button_pub, button_message, button_my;
    Drawable unpress = null;
    Drawable press = null;
//    public static boolean IS_LOGIN = (LoginActivity.userId.equals("") && RegisterActivity.userId.equals("")) ? false : true;
    public static String USER = LoginActivity.userId.equals("") ?RegisterActivity.userId:LoginActivity.userId;

    public static boolean isLogin() {
        if (LoginActivity.userId.equals("") && RegisterActivity.userId.equals(""))
        {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLogin();

//        boolean a = IS_LOGIN;
        //设置按钮按下去之后和之前的背景
        press = ContextCompat.getDrawable(this, R.drawable.img_press);
        press.setBounds(0, 0, 100, 100);
        unpress = ContextCompat.getDrawable(this, R.drawable.img_unpress);
        unpress.setBounds(0, 0, 100, 100);

        button_onSale = (RadioButton) findViewById(R.id.button_onSale);
        button_pub = (RadioButton) findViewById(R.id.button_pub);
        button_message = (RadioButton) findViewById(R.id.button_message);
        button_my = (RadioButton) findViewById(R.id.button_my);

        setMenuButtonUnpressStatus(unpress, "#555555");
        button_onSale.setCompoundDrawables(null, press, null, null);
        button_onSale.setTextColor(Color.parseColor("#3fca3a"));

        setDefaultContent();

        bottomMenu = (RadioGroup) findViewById(R.id.bottomMenu);
        bottomMenu.setOnCheckedChangeListener(this);
    }

    private void setDefaultContent() {
        OnSaleFragment onSaleFragment = new OnSaleFragment();
        FragmentManager onSaleFragmentManager = getFragmentManager();
        FragmentTransaction onSaleFragmentBeginTraction = onSaleFragmentManager.beginTransaction();
        onSaleFragmentBeginTraction.replace(R.id.content_layout, onSaleFragment);
        onSaleFragmentBeginTraction.commit();
    }

    //给每个按钮添加初始化图片
    private void setMenuButtonUnpressStatus(Drawable img, String RGB) {
        button_onSale.setCompoundDrawables(null, img, null, null);
        button_onSale.setTextColor(Color.parseColor(RGB));
        button_pub.setCompoundDrawables(null, img, null, null);
        button_pub.setTextColor(Color.parseColor(RGB));
        button_message.setCompoundDrawables(null, img, null, null);
        button_message.setTextColor(Color.parseColor(RGB));
        button_my.setCompoundDrawables(null, img, null, null);
        button_my.setTextColor(Color.parseColor(RGB));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {

            //点击“淘一淘”
            case R.id.button_onSale:
                setMenuButtonUnpressStatus(unpress, "#555555");
                button_onSale.setCompoundDrawables(null, press, null, null);
                button_onSale.setTextColor(Color.parseColor("#3fca3a"));
                setDefaultContent();
                break;

            //点击“我要卖”
            case R.id.button_pub:
                setMenuButtonUnpressStatus(unpress, "#555555");
//                button_pub.setCompoundDrawables(null, press, null, null);
//                button_pub.setTextColor(Color.parseColor("#3fca3a"));
                Intent intent = new Intent(MainActivity.this, PublishActivity.class);
                startActivity(intent);
                finish();
                break;

            //点击“留言板”
            case R.id.button_message:
                setMenuButtonUnpressStatus(unpress, "#555555");
                button_message.setCompoundDrawables(null, press, null, null);
                button_message.setTextColor(Color.parseColor("#3fca3a"));

                ShowMessageFragment showMessageFragment = new ShowMessageFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction showMessageFragmentBeginTraction = fragmentManager.beginTransaction();
                showMessageFragmentBeginTraction.replace(R.id.content_layout, showMessageFragment);
                showMessageFragmentBeginTraction.commit();
                break;

            //点击“我自己”
            case R.id.button_my:
                setMenuButtonUnpressStatus(unpress, "#555555");
                button_my.setCompoundDrawables(null, press, null, null);
                button_my.setTextColor(Color.parseColor("#3fca3a"));

                MyFragment myFragment = new MyFragment();
                FragmentManager myFragmentManager = getFragmentManager();
                FragmentTransaction myFragmentBeginTraction = myFragmentManager.beginTransaction();
                myFragmentBeginTraction.replace(R.id.content_layout, myFragment);
                myFragmentBeginTraction.commit();
                break;
        }
    }
}

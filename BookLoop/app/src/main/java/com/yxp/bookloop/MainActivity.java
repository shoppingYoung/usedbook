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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup bottomMenu;
    private RadioButton button_onSale, button_pub, button_message, button_my;
    private List<Drawable> unpress = new ArrayList<>();
    private List<Drawable> press = new ArrayList<>();

    public static boolean isLogin() {
        if (LoginActivity.userId.equals("") && RegisterActivity.userId.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUnpress();
        initPress();

        button_onSale = (RadioButton) findViewById(R.id.button_onSale);
        button_pub = (RadioButton) findViewById(R.id.button_pub);
        button_message = (RadioButton) findViewById(R.id.button_message);
        button_my = (RadioButton) findViewById(R.id.button_my);

        setMenuButtonUnpressState(unpress, "#555555");

        setDefaultShow();

        bottomMenu = (RadioGroup) findViewById(R.id.bottomMenu);
        bottomMenu.setOnCheckedChangeListener(this);
    }

    //添加“未点击”图片
    private void initUnpress() {
        Drawable buttonOnSaleUnpress = ContextCompat.getDrawable(this, R.drawable.onsale_unpress);
        buttonOnSaleUnpress.setBounds(0, 0, 70, 70);
        Drawable buttonPubUnpress = ContextCompat.getDrawable(this, R.drawable.onpub_unpress);
        buttonPubUnpress.setBounds(0, 0, 70, 70);
        Drawable buttonMessageUnpress = ContextCompat.getDrawable(this, R.drawable.onmessage_unpress);
        buttonMessageUnpress.setBounds(0, 0, 70, 70);
        Drawable buttonMyUnpress = ContextCompat.getDrawable(this, R.drawable.onmy_unpress);
        buttonMyUnpress.setBounds(0, 0, 70, 70);
        unpress.add(buttonOnSaleUnpress);
        unpress.add(buttonPubUnpress);
        unpress.add(buttonMessageUnpress);
        unpress.add(buttonMyUnpress);
    }

    //添加“点击”图片
    private void initPress() {
        Drawable buttonOnSalePress = ContextCompat.getDrawable(this, R.drawable.onsale_press);
        buttonOnSalePress.setBounds(0, 0, 70, 70);
        Drawable buttonPubPress = ContextCompat.getDrawable(this, R.drawable.onpub_press);
        buttonPubPress.setBounds(0, 0, 70, 70);
        Drawable buttonMessagePress = ContextCompat.getDrawable(this, R.drawable.onmessage_press);
        buttonMessagePress.setBounds(0, 0, 70, 70);
        Drawable buttonMyPress = ContextCompat.getDrawable(this, R.drawable.onmy_press);
        buttonMyPress.setBounds(0, 0, 70, 70);
        press.add(buttonOnSalePress);
        press.add(buttonPubPress);
        press.add(buttonMessagePress);
        press.add(buttonMyPress);
    }

    //给每个按钮添加未被按下去的图片
    private void setMenuButtonUnpressState(List<Drawable> unpress, String RGB) {
        button_onSale.setCompoundDrawables(null, unpress.get(0), null, null);
        button_onSale.setTextColor(Color.parseColor(RGB));
        button_pub.setCompoundDrawables(null, unpress.get(1), null, null);
        button_pub.setTextColor(Color.parseColor(RGB));
        button_message.setCompoundDrawables(null, unpress.get(2), null, null);
        button_message.setTextColor(Color.parseColor(RGB));
        button_my.setCompoundDrawables(null, unpress.get(3), null, null);
        button_my.setTextColor(Color.parseColor(RGB));
    }

    //默认显示“淘一淘”里面的内容
    private void setDefaultShow() {
        OnSaleFragment onSaleFragment = new OnSaleFragment();
        FragmentManager onSaleFragmentManager = getFragmentManager();
        FragmentTransaction onSaleFragmentBeginTraction = onSaleFragmentManager.beginTransaction();
        onSaleFragmentBeginTraction.replace(R.id.content_layout, onSaleFragment);
        onSaleFragmentBeginTraction.commit();
        button_onSale.setChecked(true);
        button_onSale.setCompoundDrawables(null, press.get(0), null, null);
        button_onSale.setTextColor(Color.parseColor("#3fca3a"));
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {

            //点击“淘一淘”
            case R.id.button_onSale:
                setMenuButtonUnpressState(unpress, "#555555");
                button_onSale.setCompoundDrawables(null, press.get(0), null, null);
                button_onSale.setTextColor(Color.parseColor("#3fca3a"));
                setDefaultShow();
                break;

            //点击“我要卖”
            case R.id.button_pub:
                if (!isLogin()) {
                    Toast.makeText(MainActivity.this, "你需要登录才能进行此操作！", Toast.LENGTH_SHORT).show();
                } else {
                    setMenuButtonUnpressState(unpress, "#555555");
                    Intent intent = new Intent(MainActivity.this, PublishActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;

            //点击“留言板”
            case R.id.button_message:
                setMenuButtonUnpressState(unpress, "#555555");
                button_message.setCompoundDrawables(null, press.get(2), null, null);
                button_message.setTextColor(Color.parseColor("#3fca3a"));

                ShowMessageFragment showMessageFragment = new ShowMessageFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction showMessageFragmentBeginTraction = fragmentManager.beginTransaction();
                showMessageFragmentBeginTraction.replace(R.id.content_layout, showMessageFragment);
                showMessageFragmentBeginTraction.commit();
                break;

            //点击“我自己”
            case R.id.button_my:
                setMenuButtonUnpressState(unpress, "#555555");
                button_my.setCompoundDrawables(null, press.get(3), null, null);
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

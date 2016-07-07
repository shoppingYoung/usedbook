package com.yxp.bookloop;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragment extends Fragment implements View.OnClickListener {
    private ImageView iv_userFace;
    private TextView tv_get_nickname;
    private TextView tv_chang_password;
    private Button btn_login;
    private Button btn_register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (MainActivity.isLogin()) {
            view = inflater.inflate(R.layout.fragment_my_is_login, container, false);
            isLoginInit(view);
        } else {
            view = inflater.inflate(R.layout.fragment_my_not_login, container, false);
            notLoginInit(view);
        }
        return view;
    }

    private void isLoginInit(View view) {
        iv_userFace = (ImageView) view.findViewById(R.id.iv_userFace);
        tv_get_nickname = (TextView) view.findViewById(R.id.tv_get_nickname);
        tv_chang_password = (TextView) view.findViewById(R.id.tv_chang_password);
        iv_userFace.setOnClickListener(this);
        tv_get_nickname.setOnClickListener(this);
        tv_chang_password.setOnClickListener(this);
    }

    private void notLoginInit(View view) {
        btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_register = (Button) view.findViewById(R.id.btn_register);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {

            //点击“登录”按钮
            case R.id.btn_login:
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;

            //点击“注册”按钮
            case R.id.btn_register:
                intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;

            //点击“头像”
            case R.id.iv_userFace:
                break;

            //点击“昵称”
            case R.id.tv_get_nickname:
                break;

            //点击“修改密码”
            case R.id.tv_chang_password:
                Toast.makeText(getActivity(), "你点击了“修改密码”", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

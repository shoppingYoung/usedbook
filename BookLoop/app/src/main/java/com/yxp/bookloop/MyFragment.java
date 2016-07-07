package com.yxp.bookloop;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyFragment extends Fragment implements View.OnClickListener {
    private Button btn_login;
    private Button btn_register;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if (MainActivity.isLogin()) {
            view = inflater.inflate(R.layout.fragment_my_is_login, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_my_not_login, container, false);
            init(view);
        }
        return view;
    }

    private void init(View view) {
        btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_register = (Button) view.findViewById(R.id.btn_register);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (v.getId() == R.id.btn_login) {
            intent = new Intent(getActivity(), LoginActivity.class);
        } else {
            intent = new Intent(getActivity(), RegisterActivity.class);
        }
        startActivity(intent);
        getActivity().finish();
    }
}

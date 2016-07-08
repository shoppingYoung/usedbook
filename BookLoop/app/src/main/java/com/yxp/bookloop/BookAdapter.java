package com.yxp.bookloop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class BookAdapter extends BaseAdapter {

    private List<Book> mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public BookAdapter(Context context, List<Book> book) {
        mContext = context;
        mList = book;
        mInflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_book, null);
            viewHolder.iv_user = (ImageView) convertView.findViewById(R.id.iv_user);
            viewHolder.tv_user = (TextView) convertView.findViewById(R.id.tv_user);
            viewHolder.iv_school = (TextView) convertView.findViewById(R.id.iv_school);
            viewHolder.iv_bookFace = (ImageView) convertView.findViewById(R.id.iv_bookItem);
            viewHolder.tv_bookName = (TextView) convertView.findViewById(R.id.tv_bookName);
            viewHolder.tv_author = (TextView) convertView.findViewById(R.id.tv_author);
            viewHolder.tv_publish = (TextView) convertView.findViewById(R.id.tv_publish);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String userFaceUrl = mList.get(position).userFace;
        String bookFaceUrl = mList.get(position).bookFace;
        Glide.with(getContext()).load(userFaceUrl).into(viewHolder.iv_user);
        Glide.with(getContext()).load(bookFaceUrl).into(viewHolder.iv_bookFace);
        viewHolder.tv_user.setText(mList.get(position).userName);
        viewHolder.iv_school.setText(mList.get(position).school);
        viewHolder.tv_bookName.setText(mList.get(position).bookName);
        viewHolder.tv_author.setText(mList.get(position).author);
        viewHolder.tv_publish.setText(mList.get(position).publish);
        viewHolder.tv_price.setText(mList.get(position).price);
        viewHolder.tv_time.setText(mList.get(position).pubTime);
        return convertView;
    }

    class ViewHolder {
        public ImageView iv_user;
        public TextView tv_user;
        public TextView iv_school;
        public ImageView iv_bookFace;
        public TextView tv_bookName;
        public TextView tv_author;
        public TextView tv_publish;
        public TextView tv_price;
        public TextView tv_time;
    }
}

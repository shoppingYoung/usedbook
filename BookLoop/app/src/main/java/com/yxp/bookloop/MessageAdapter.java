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

public class MessageAdapter extends BaseAdapter {

    private List<MessageBoard> mList;
    private LayoutInflater mInflater;
    private ImageLoad mImageLoad;
    private Context mContext;

    public MessageAdapter(Context context, List<MessageBoard> data) {
        mList = data;
        mInflater = LayoutInflater.from(context);
        mContext = context;
//        mImageLoad = new ImageLoad();
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
            convertView = mInflater.inflate(R.layout.item_message, null);
            viewHolder.tv_user = (TextView) convertView.findViewById(R.id.user);
            viewHolder.im_userFace = (ImageView) convertView.findViewById(R.id.userFace);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.date);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String url = mList.get(position).userFace;
        Glide.with(getContext()).load(url).into(viewHolder.im_userFace);
//        viewHolder.im_userFace.setTag(url);
//        mImageLoad.showImageByAsyncTask(viewHolder.im_userFace, url);
        viewHolder.tv_user.setText(mList.get(position).user);
        viewHolder.tv_time.setText(mList.get(position).msgTime);
        viewHolder.tv_content.setText(mList.get(position).content);
        return convertView;
    }

    class ViewHolder {
        public TextView tv_user;
        public ImageView im_userFace;
        public TextView tv_time;
        public TextView tv_content;
    }
}

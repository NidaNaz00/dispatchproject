package com.example.dispatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class profileAdapter extends ArrayAdapter<profile> {
    Context context;
    public profileAdapter(Context context, ArrayList<profile>object)
    {
        super(context,0,object);
        this.context=context;
    }
    public profileAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_profile_listview_design, parent, false);
        }
        ImageView lv_image;
        TextView image_id;
        TextView profile_title;
        ImageView expand;
        lv_image = v.findViewById(R.id.lv_image);
        image_id = v.findViewById(R.id.image_id);
        expand = v.findViewById(R.id.expand);
        profile_title = v.findViewById(R.id.profile_title);
        profile p = getItem(position);
        if (p != null) {
            String title = p.getTitle();
            profile_title.setText(p.getTitle());
            switch (title) {
                case "Edit Profile":
                    lv_image.setImageResource(R.drawable.ic_edit);
                    break;
                case "Setting":
                    lv_image.setImageResource(R.drawable.ic_setting);
                    break;
                case "Notification":
                    lv_image.setImageResource(R.drawable.ic_notify);
                    break;
                case "Delivery Orders":
                    lv_image.setImageResource(R.drawable.ic_orders);
                    break;
                case "Help & Support":
                    lv_image.setImageResource(R.drawable.ic_help);
                    break;
                case "Logout":
                    lv_image.setImageResource(R.drawable.ic_logout);
                    break;
                default:
                    lv_image.setImageResource(R.drawable.ic_edit);
                    break;
            }
        }
        return v;
    }
    }

package com.master.design.therapist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.master.design.therapist.Models.DrawerMenu;
import com.master.design.therapist.R;

import java.util.ArrayList;


public class Adapter_Menu  extends BaseAdapter {

    private Context context;
    private ArrayList<DrawerMenu> menus;

    public Adapter_Menu(Context context, ArrayList<DrawerMenu> menus) {
        this.context = context;
        this.menus = menus;
    }

    @Override
    public int getCount() {
        return menus.size();
    }

    @Override
    public Object getItem(int position) {
        return menus.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_item_for_menu, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setDetails(viewHolder, position);
        return convertView;
    }

    private void setDetails(ViewHolder viewHolder, int position) {
        DrawerMenu DrawerMenu = menus.get(position);
        viewHolder.txt_title.setText(DrawerMenu.getName());
        viewHolder.img.setImageResource(DrawerMenu.getIcon());
    }

    private static class ViewHolder {
        private TextView txt_title;
        ImageView img;

        private ViewHolder(View view) {
            txt_title = (TextView) view.findViewById(R.id.txt_title);
            img=(ImageView) view.findViewById(R.id.changerImage);
        }
    }
}

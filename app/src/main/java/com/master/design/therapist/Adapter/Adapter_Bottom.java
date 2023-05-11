package com.master.design.therapist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.master.design.therapist.Helper.DataChangeDM;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;

import java.util.ArrayList;

public class Adapter_Bottom extends BaseAdapter {

    private Context context;
    private ArrayList<DataChangeDM> arrayList;
    private DataChangeDM selected;
    private int position;
    User user;

    int selectedPosition;
    boolean correct = false;

    public Adapter_Bottom(Context context, ArrayList<DataChangeDM> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        user = new User(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
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
            convertView = inflater.inflate(R.layout.bottom_for_all_adapter_item, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        setDetails(viewHolder, position);
        return convertView;
    }


    private void setDetails(ViewHolder viewHolder, int position) {
        DataChangeDM data = arrayList.get(position);
//        viewHolder.size.setText(data.getSize());
//        viewHolder.price.setText(data.getUnitPriceKWD());

        if (user.getLanguageCode().equalsIgnoreCase("en")) {

            viewHolder.size.setText(data.getName());
//            viewHolder.price.setText(data.getUnitPriceKWD());

        } else {
            viewHolder.size.setText(data.getName());
//            viewHolder.price.setText(data.getUnitPriceKWDAr());
        }


    }

    public DataChangeDM getSelected() {
        return selected;
    }

    public void setSelected(DataChangeDM selected) {
        this.selected = selected;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private static class ViewHolder {
        private TextView price, size;
        ImageView image;
        LinearLayout ll;


        private ViewHolder(View view) {
            size = view.findViewById(R.id.sizeTV);
//            price = view.findViewById(R.id.prizeTV);
            image = view.findViewById(R.id.iv_selected);
            ll = view.findViewById(R.id.linearLayout);
        }
    }
}

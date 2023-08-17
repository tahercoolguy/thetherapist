package com.master.design.therapist.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.master.design.therapist.Helper.DataChangeDM;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;

import java.util.ArrayList;

public class CountrySuggetsionAdapter extends ArrayAdapter<DataChangeDM> {

    User user;

    public CountrySuggetsionAdapter(Context context, ArrayList<DataChangeDM> items) {
        super(context, 0, items);
        user = new User(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custum_country_suggestion, parent, false);
        }

        DataChangeDM item = getItem(position);

        TextView countryNameTxt = convertView.findViewById(R.id.countryNameTxt);
        TextView countryIDTxt = convertView.findViewById(R.id.countryIDTxt);

        if (user.getLanguageCode().equalsIgnoreCase("en")) {

            countryNameTxt.setText(item.getName());

        } else {

            countryNameTxt.setText(item.getNameAr());

        }
        countryIDTxt.setText(item.getId());

        return convertView;
    }
}
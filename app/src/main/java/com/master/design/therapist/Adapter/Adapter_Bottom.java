package com.master.design.therapist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.master.design.therapist.Helper.DataChangeDM;
import com.master.design.therapist.Helper.User;
import com.master.design.therapist.R;

import java.util.ArrayList;

public class Adapter_Bottom extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<DataChangeDM> arrayList;
    private ArrayList<DataChangeDM> filteredList;
    public DataChangeDM selected;
    private int position;
    //    private String selected;
    Boolean checck = true;
    private ItemFilter filter = new ItemFilter();
    User user;
    Adapter_Bottom.OnSearchItemClickListener onItemClickListener;

    public Adapter_Bottom(Context context, ArrayList<DataChangeDM> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.filteredList = arrayList;
        user = new User(context);
    }

    public void setOnItemClickListener(Adapter_Bottom.OnSearchItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
            viewHolder.size.setText(data.getNameAr());
//            viewHolder.price.setText(data.getUnitPriceKWDAr());
        }

//        viewHolder.ll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(checck)
//                {
//                    viewHolder.image.setVisibility(View.VISIBLE);
//                    checck=false;
//                }
//            }
//        });


        viewHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onSearchItemClick(arrayList.get(position));
                }
            }
        });

//
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = arrayList;
                results.count = arrayList.size();
            } else {
                ArrayList<DataChangeDM> filtered = new ArrayList<>();
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (DataChangeDM item : arrayList) {
                    if (user.getLanguageCode().equalsIgnoreCase("en")) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filtered.add(item);
                        }
                    } else {
                        if (item.getNameAr().toLowerCase().contains(filterPattern)) {
                            filtered.add(item);
                        }
                    }
                }

                results.values = filtered;
                results.count = filtered.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<DataChangeDM>) results.values;
            notifyDataSetChanged();
        }
    }


    // method for filtering our recyclerview items.
    public void filterList(ArrayList<DataChangeDM> filterlist) {
        // below line is to add our filtered
        // list in our course array list.
        arrayList = filterlist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
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

    public interface OnSearchItemClickListener {

        void onSearchItemClick(DataChangeDM arrayList);
    }
}

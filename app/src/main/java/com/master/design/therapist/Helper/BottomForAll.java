package com.master.design.therapist.Helper;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.master.design.therapist.Adapter.Adapter_Bottom;
import com.master.design.therapist.R;

import java.util.ArrayList;

import io.reactivex.annotations.NonNull;

public class BottomForAll extends BottomSheetDialogFragment implements View.OnClickListener, View.OnTouchListener {
    private Context context;
    private ListView listview;
    public EditText search;
    private TextView btn_cancel, btn_done, txt_error_message;
    private ResponseListener responseListener;
    private BottomSheetBehavior bottomSheetBehavior;
    private ProgressBar progressBar;
    private RelativeLayout layout_top;
    private SearchView searchView;

    private boolean isTopScroll = false;
    private DataChangeDM selected;
    public ArrayList<DataChangeDM> arrayList = new ArrayList<>();
    private Adapter_Bottom adapter;

    User user;
    public boolean isSort = false;

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            } else if (newState == BottomSheetBehavior.STATE_DRAGGING && Util.canScrollUp(listview)) {
                if (!isTopScroll) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        this.context = getContext();

        View contentView = View.inflate(context, R.layout.bottom_for_all, null);
        // assert dialog.getWindow() != null;
        //dialog.getWindow().setDimAmount(0f);
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(contentView);

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((View) contentView.getParent()).setBackgroundColor(getResources().getColor(android.R.color.transparent));

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            bottomSheetBehavior = (BottomSheetBehavior) behavior;
            bottomSheetBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }
        mapping(contentView);
        user = new User(context);
        if (arrayList != null && arrayList.size() > 0) {
            setData(arrayList);
        } else {
            getList();
        }
        listview.setAdapter(adapter);
        listview.setVisibility(View.VISIBLE);

        adapter.setOnItemClickListener(new Adapter_Bottom.OnSearchItemClickListener() {
            @Override
            public void onSearchItemClick(DataChangeDM arrayList) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                if (responseListener != null && adapter != null) {
                    responseListener.response(arrayList);
                }
            }
        });


    }

    private void getList() {
//        arrayList = new ArrayList<>();
//        arrayList = new DummyData(context).getStringList();
        setData(arrayList);
    }


    public void setData(ArrayList<DataChangeDM> arrayList) {
        if (arrayList != null && arrayList.size() > 0) {
            if (adapter == null) {
                adapter = new Adapter_Bottom(context, arrayList);
                selected = arrayList.get(0);
            }
        }
        this.arrayList = arrayList;
    }


    private void mapping(View view) {

        btn_done = view.findViewById(R.id.btn_done);
        btn_cancel = view.findViewById(R.id.btn_cancel);

        listview = view.findViewById(R.id.list_view);
//        search = view.findViewById(R.id.searchET);
        if (isSort)
            search.setVisibility(View.GONE);
        listview.setVisibility(View.VISIBLE);
        progressBar = view.findViewById(R.id.progressBar);
        layout_top = view.findViewById(R.id.layout_top);
        txt_error_message = view.findViewById(R.id.txt_error_message);
        searchView = view.findViewById(R.id.searchView);

        btn_done.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        layout_top.setOnTouchListener(this);
        searchView.setIconified(false);
        searchView.setOnSearchClickListener(this);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //                              selected = adapter.searchResults.get(i);
                adapter.setSelected(selected);
                adapter.setPosition(i);
                adapter.notifyDataSetChanged();
                if (responseListener != null && adapter != null) {
                    responseListener.response(arrayList.get(adapter.getPosition()));

                }
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        // Request focus and show keyboard when SearchView is enabled
        searchView.setOnSearchClickListener(new SearchView.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });


    }

    //    private void filterSuggestions(String query) {
//        adapter.getFilter().filter(query);
//    }
    public void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<DataChangeDM> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (DataChangeDM item : arrayList) {

            // checking if the entered string matched with any item of our recycler view.


//            if (SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.Language, "").equalsIgnoreCase("ar")) {
//                if (item.getArabicName().contains(text.toLowerCase())) {
//                    // if the item is matched we are
//                    // adding it to our filtered list.
//                    filteredlist.add(item);
//                }
//            }
//
//            if (SharedPreferencesUtils.getInstance(getActivity()).getValue(Constants.Language, "").equalsIgnoreCase("en")) {
//                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
//                    // if the item is matched we are
//                    // adding it to our filtered list.
//                    filteredlist.add(item);
//                }
//            }

            if (user.getLanguageCode().equalsIgnoreCase("en")) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())) {
//                    // if the item is matched we are
//                    // adding it to our filtered list.
                    filteredlist.add(item);
//                }


                }
            } else {
                if (item.getNameAr().contains(text)) {
//                    // if the item is matched we are
//                    // adding it to our filtered list.
                    filteredlist.add(item);
//                }


                }
            }

            if (filteredlist.isEmpty()) {
                // if no item is added in filtered list we are
                // displaying a toast message as no data found.
//            Toast.makeText(getContext(), getString(R.string.not_found), Toast.LENGTH_SHORT).show();
            } else {
                // at last we are passing that filtered
                // list to our adapter class.
                adapter.filterList(filteredlist);
            }
        }

        adapter.setOnItemClickListener(new Adapter_Bottom.OnSearchItemClickListener() {
            @Override
            public void onSearchItemClick(DataChangeDM arrayList) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                if (responseListener != null && adapter != null) {
                    responseListener.response(arrayList);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;
            case R.id.btn_done:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                if (responseListener != null && adapter != null) {
                    responseListener.response(arrayList.get(adapter.getPosition()));
                }
                break;
        }
    }


    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isTopScroll = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            isTopScroll = false;
        }
        return true;
    }

}

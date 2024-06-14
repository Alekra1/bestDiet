package com.example.bestdiet;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.view.WindowManager;


import java.util.ArrayList;
import java.util.List;

public class SearchDialog extends Dialog {

    private EditText searchEditText;
    private ListView searchResultsListView;
    private SearchResultsAdapter adapter;
    private List<String> allItems;

    String clientId,meal_name;

    public SearchDialog(Context context, List<String> items,String meal_name,String clientID) {
        super(context);
        this.allItems = items;
        this.clientId = clientID;
        this.meal_name = meal_name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_search);

        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.copyFrom(window.getAttributes());
            params.width = WindowManager.LayoutParams.MATCH_PARENT;  // Set the width to match parent
            params.height = WindowManager.LayoutParams.WRAP_CONTENT; // Set the height to wrap content
            window.setAttributes(params);
        }


        searchEditText = findViewById(R.id.search_edit_text);
        searchResultsListView = findViewById(R.id.search_results_list_view);

        adapter = new SearchResultsAdapter(getContext(), new ArrayList<>(),meal_name,clientId);
        searchResultsListView.setAdapter(adapter);

        adapter.clear();
        adapter.addAll(allItems);
        adapter.notifyDataSetChanged();

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                filterResults(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterResults(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void filterResults(String query) {
        List<String> filteredResults = new ArrayList<>();
        for (String item : allItems) {
            if (item.toLowerCase().contains(query.toLowerCase())) {
                filteredResults.add(item);
            }
        }
        if(filteredResults.size() == 0)
            filteredResults.add("Пусто");
        adapter.clear();
        adapter.addAll(filteredResults);
        adapter.notifyDataSetChanged();
    }
}


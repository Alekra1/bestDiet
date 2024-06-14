package com.example.bestdiet;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bestdiet.database.AppDatabase;
import com.example.bestdiet.database.ProductDao;
import com.example.bestdiet.database.client_in_client_list;
import com.example.bestdiet.database.clients;
import com.example.bestdiet.database.product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class SearchResultsAdapter extends ArrayAdapter<String> {

    private ExecutorService executor = Executors.newSingleThreadExecutor();


    private product product;

    private AppDatabase database;

    private ProductDao productDao;

    Context context = null;

    product product_info = new product();

    String clientId,meal_name;




    public SearchResultsAdapter(Context context, List<String> results,String meal_name,String clientID) {
        super(context, 0, results);
        this.context = context;
        this.clientId = clientID;
        this.meal_name = meal_name;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        database = AppDatabase.getAppDatabase(context);
        productDao = database.productDao();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_search_result, parent, false);
        }

        String result = getItem(position);

        ImageView productImage = convertView.findViewById(R.id.product_image);
        TextView Name = convertView.findViewById(R.id.name);
        Button addButton = convertView.findViewById(R.id.add_button);

        if (result != null) {
            Name.setText(result);

            ExecutorService executor = Executors.newSingleThreadExecutor();

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    product product_info = productDao.getproductbyname(result);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (product_info != null) {
                                Glide.with(productImage.getContext())
                                        .load(product_info.getPhoto_product())
                                        .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(30, 0)))
                                        .into(productImage);
                            }
                        }
                    });
                }
            });
        }

        final String productName = result; // Создаем финальную локальную переменную

        addButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        product = productDao.getproductbyname(productName); // Используем финальную переменную

                        // Выполните код, связанный с отображением диалогового окна, в основном потоке UI
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                if(product != null) {
                                    Product_info_Dialog productInfoDialog = new Product_info_Dialog(getContext(), product,meal_name,clientId);
                                    productInfoDialog.show();
                                }
                            }
                        });
                    }
                });
            }
        });

        return convertView;
    }
}


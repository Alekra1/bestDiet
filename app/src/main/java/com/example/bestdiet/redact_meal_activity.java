package com.example.bestdiet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestdiet.database.AppDatabase;
import com.example.bestdiet.database.ProductDao;
import com.example.bestdiet.database.product;
import com.example.bestdiet.database.product_info_meal_Dao;
import com.example.bestdiet.database.user_message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class redact_meal_activity  extends AppCompatActivity implements OnItemClickListener {


    private RecyclerView meal_of_plan_recyclerview;
    private redact_meal_Adapter adapter;
    private MessagesAdapter adapter2;
    List<Item> items = new ArrayList<>();

    ProductDao productDao;

    AppDatabase appDatabase;

    redact_meal_element meal_element;

    product_info_meal_Dao Product_info_meal_Dao;

    private ImageButton searchButton;

    String clientId,meal_name;


    private ExecutorService executor = Executors.newSingleThreadExecutor();

    List<TelegramMessage> messages = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
        productDao = appDatabase.productDao();
        setContentView(R.layout.redact_plan_menu);

        Intent intent = getIntent();
        clientId = intent.getStringExtra("CLIENT_ID");
        meal_name = intent.getStringExtra("Meal_name");


        TextView name_meal;
        name_meal = findViewById(R.id.name_meal);

        name_meal.setText(meal_name);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<ProductWithAmount> productList = productDao.getProductByClientId(clientId,meal_name);

                for(ProductWithAmount product : productList)
                {
                     meal_element = new redact_meal_element(product.getProductname(),"Продукт",product.getAmount(), product.getPhoto_product());
                     items.add(new Item(0,meal_element ));
                }
            }
        });

        meal_of_plan_recyclerview = findViewById(R.id.RecyclerView_meal_of_plan);

        searchButton = findViewById(R.id.imageButton10);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new list here
                List<String> items_prod = new ArrayList<>();

                if (!isFinishing()) {  // Проверяем, что активность не завершена
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            List<product> prod_list = productDao.getAllproducts();
                            for (product product : prod_list) {
                                if (product.getProductname() != null)
                                    items_prod.add(product.getProductname());
                                else
                                    Log.e("fail", "product.getProductname() is null");
                            }

                            // After adding products, switch back to the UI thread to show the dialog
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (items_prod.size() > 0) {
                                        SearchDialog searchDialog = new SearchDialog(redact_meal_activity.this, items_prod, meal_name, clientId);
                                        searchDialog.show();
                                    } else {
                                        Log.e("fail", "items_prod is null");
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

        meal_of_plan_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new redact_meal_Adapter(items, this);
        meal_of_plan_recyclerview.setAdapter(adapter);

    }
    @Override
    public void onHeaderButtonClick(int position) {
        String text = adapter.getTextAtPosition(position);
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userFullName = sharedPreferences.getString("user_full_name", "Default Name");
        Toast.makeText(this, "Text: " + userFullName, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Header button clicked at position: " + position, Toast.LENGTH_SHORT).show();
    }
}

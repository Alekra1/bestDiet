package com.example.bestdiet;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bestdiet.database.AppDatabase;
import com.example.bestdiet.database.MealDao;
import com.example.bestdiet.database.meal;
import com.example.bestdiet.database.product;
import com.example.bestdiet.database.product_info_meal_Dao;
import com.example.bestdiet.database.productinfoodmeal;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class Product_info_Dialog extends Dialog {

    private product productinfo;

    String clientId,meal_name;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private AppDatabase database;

    private product_info_meal_Dao Product_info_meal;

    private MealDao mealDao;


    public Product_info_Dialog(Context context, product productinfo,String meal_name,String clientID) {
        super(context);
        database = AppDatabase.getAppDatabase(context);
        Product_info_meal = database.product_info_meal_Dao();
        mealDao = database.mealDao();
        this.clientId = clientID;
        this.meal_name = meal_name;
        this.productinfo = new product(productinfo.getProductname(), productinfo.getKkal(), productinfo.getFats(), productinfo.getUglivod(), productinfo.getProtein(), productinfo.getPhoto_product());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Set the content view
        setContentView(R.layout.product_info_windw);

        // Initialize the TextView after setting the content view
        ImageView img_product = findViewById(R.id.photo_prod);
        TextView nameProduct = findViewById(R.id.name_product);
        TextView kkal = findViewById(R.id.kkal);
        TextView amount_bilok = findViewById(R.id.amount_bilok);
        TextView amount_uglivod = findViewById(R.id.amount_uglivod);
        TextView amount_fats = findViewById(R.id.amount_fats);
        TextInputEditText inputAmount = findViewById(R.id.input_amount);
        Button add_button = findViewById(R.id.add_product);

        // Set window layout parameters
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.copyFrom(window.getAttributes());
            params.width = WindowManager.LayoutParams.MATCH_PARENT;  // Set the width to match parent
            params.height = WindowManager.LayoutParams.WRAP_CONTENT; // Set the height to wrap content
            window.setAttributes(params);
        }

        if (img_product != null && productinfo.getPhoto_product() != null) {
            Log.e("Img_url ", productinfo.getPhoto_product());
            Glide.with(img_product.getContext())
                    .load(productinfo.getPhoto_product())
                    .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(30, 0)))
                    .into(img_product);
        } else Log.e("Img_url ", "Img_url null");
        // Ensure the TextView is updated
        if (productinfo != null && nameProduct != null) {
            nameProduct.setText(productinfo.getProductname());
        } else {
            Log.e("null", "productinfo or nameProduct is null");
        }

        if (productinfo != null && kkal != null) {
            kkal.setText(String.valueOf(productinfo.getKkal()) + " ккал");
        } else {
            Log.e("null", "productinfo or nameProduct is null");
        }

        if (productinfo != null && amount_bilok != null) {
            amount_bilok.setText(String.valueOf(productinfo.getProtein()) + " г");
        } else {
            Log.e("null", "productinfo or nameProduct is null");
        }

        if (productinfo != null && amount_uglivod != null) {
            amount_uglivod.setText(String.valueOf(productinfo.getUglivod()) + " г");
        } else {
            Log.e("null", "productinfo or nameProduct is null");
        }
        if (productinfo != null && amount_fats != null) {
            amount_fats.setText(String.valueOf(productinfo.getFats()) + " г");
        } else {
            Log.e("null", "productinfo or nameProduct is null");
        }

        inputAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                float inputAmountFloat = 0.0f;
                try {
                    inputAmountFloat = Float.parseFloat(s.toString());
                } catch (NumberFormatException e) {
                    Log.e("NumberFormatException", "Invalid input for conversion to float");
                }

// Your method to calculate nutrition based on the input amount
                calculate_nutr(inputAmountFloat);

                if (productinfo != null && kkal != null) {
                    kkal.setText(String.format("%.2f ккал", productinfo.getKkal() * inputAmountFloat / 100));
                } else {
                    Log.e("null", "productinfo or kkal is null");
                }

                if (productinfo != null && amount_bilok != null) {
                    amount_bilok.setText(String.format("%.2f г", productinfo.getProtein() * inputAmountFloat / 100));
                } else {
                    Log.e("null", "productinfo or amount_bilok is null");
                }

                if (productinfo != null && amount_uglivod != null) {
                    amount_uglivod.setText(String.format("%.2f г", productinfo.getUglivod() * inputAmountFloat / 100));
                } else {
                    Log.e("null", "productinfo or amount_uglivod is null");
                }

                if (productinfo != null && amount_fats != null) {
                    amount_fats.setText(String.format("%.2f г", productinfo.getFats() * inputAmountFloat / 100));
                } else {
                    Log.e("null", "productinfo or amount_fats is null");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DecimalFormat decimalFormat = new DecimalFormat("#.##");

                float inputAmountFloat = 0.0f;
                try {
                    inputAmountFloat = Float.parseFloat(s.toString());
                } catch (NumberFormatException e) {
                    Log.e("NumberFormatException", "Invalid input for conversion to float");
                }

// Your method to calculate nutrition based on the input amount
                calculate_nutr(inputAmountFloat);

                if (productinfo != null && kkal != null) {
                    kkal.setText(decimalFormat.format(productinfo.getKkal() * inputAmountFloat / 100) + " ккал");
                } else {
                    Log.e("null", "productinfo or kkal is null");
                }

                if (productinfo != null && amount_bilok != null) {
                    amount_bilok.setText(decimalFormat.format(productinfo.getProtein() * inputAmountFloat / 100) + " г");
                } else {
                    Log.e("null", "productinfo or amount_bilok is null");
                }

                if (productinfo != null && amount_uglivod != null) {
                    amount_uglivod.setText(decimalFormat.format(productinfo.getUglivod() * inputAmountFloat / 100) + " г");
                } else {
                    Log.e("null", "productinfo or amount_uglivod is null");
                }

                if (productinfo != null && amount_fats != null) {
                    amount_fats.setText(decimalFormat.format(productinfo.getFats() * inputAmountFloat / 100) + " г");
                } else {
                    Log.e("null", "productinfo or amount_fats is null");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("end change", "is end change");
                String newText = s.toString();
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        // Получаем объект meal
                        meal meal = mealDao.getidmeal(clientId, meal_name);

                        if (meal != null && meal.meal_id != 0) {
                            // Создаем объект productinfoodmeal
                            productinfoodmeal productinfoodmeal = new productinfoodmeal(meal.meal_id, nameProduct.getText().toString(), Float.parseFloat(inputAmount.getText().toString()));

                            // Вставляем объект в базу данных
                            long result = Product_info_meal.insertproduct(productinfoodmeal);

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    if (result > 0) {
                                        // Запись успешно добавлена
                                        Log.d("Database", "Record successfully inserted with ID: " + result);
                                    } else {
                                        // Произошла ошибка при добавлении записи
                                        Log.e("Database", "Error inserting record");
                                    }
                                }
                            });
                        } else {
                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("null", "meal.meal_id is null or meal is null");
                                }
                            });
                        }
                    }
                });
                dismiss();
            }
        });
    }

    private product calculate_nutr(float amount) {
        if (amount > 0) {
            product newproduct = new product("", productinfo.getKkal() * (amount / 100), productinfo.getFats() * (amount / 100), productinfo.getUglivod() * (amount / 100), productinfo.getProtein() * (amount / 100), " ");
            return newproduct;
        }
        return null;
    }
}


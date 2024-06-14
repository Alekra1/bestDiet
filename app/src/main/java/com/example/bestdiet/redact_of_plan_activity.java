package com.example.bestdiet;

import static com.example.bestdiet.PdfHelper.generatePdf;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestdiet.database.AppDatabase;
import com.example.bestdiet.database.DishDao;
import com.example.bestdiet.database.MealDao;
import com.example.bestdiet.database.ProductDao;
import com.example.bestdiet.database.clients;
import com.example.bestdiet.database.meal;
import com.example.bestdiet.database.product;
import com.example.bestdiet.database.product_info_meal_Dao;
import com.example.bestdiet.database.productinfoodmeal;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class redact_of_plan_activity extends AppCompatActivity implements OnItemClickListener {

    private static final String token = "7144334653:AAEAStoToGf7_P78Ep7FWLAzHWQDYW4sOOc";
    private static final String CHAT_ID = "751112696";
    private TelegramBotClient telegramBotClient;

    private ExecutorService executor = Executors.newSingleThreadExecutor();


    AppDatabase database;
    MealDao mealDao;

    DishDao dishDao;

    ProductDao productDao;
    List<Item> items = new ArrayList<>();

    String clientId;

    product_info_meal_Dao Product_info_meal_Dao;


    private RecyclerView meal_of_plan_recyclerview;
    private meal_of_plan_Adapter adapter;

    float Protein_amount = 0, Fats_amount = 0, Uglivos_amount = 0,Kalories_amount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_plan_menu);
        database = AppDatabase.getAppDatabase(getApplicationContext());
        mealDao = database.mealDao();
        dishDao = database.dishDao();
        productDao = database.productDao();
        Product_info_meal_Dao = database.product_info_meal_Dao();

        Intent intent = getIntent();
        clientId = intent.getStringExtra("CLIENT_ID");

        ImageButton create_plan = findViewById(R.id.create_plan);
        TextView protein_amount = findViewById(R.id.proteins);
        TextView fats_amount = findViewById(R.id.fats);
        TextView uglivods_amount = findViewById(R.id.uglivods);
        TextView kkalories_amount = findViewById(R.id.kkalories);

        create_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] mealTitles = {
                        "Сніданок",
                        "Перекус 1",
                        "Обід",
                        "Перекус 2",
                        "Вечеря"
                };

                String[] meals = new String[5];
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        int i =0;
                        for(String meal : mealTitles) {
                            List<ProductWithAmount> prod_list = productDao.getProductByClientId(clientId, meal);
                            if (prod_list != null || !prod_list.isEmpty()) {
                                meals[i] = mealTitles[i] + ": ";
                                for (ProductWithAmount product : prod_list) {
                                    meals[i] = meals[i] + product.getProductname() + " " + String.valueOf(product.getAmount()) + " г, ";
                                }
                                i++;
                            }
                            else meals[i] = null;
                        }
                    }
                });
                generatePdf(v.getContext(),meals);
            }
        });

        executor.execute(new Runnable() {
            @Override
            public void run() {
//                mealDao.deleteclientfoodplan();

                // Преобразование clientId в int, если требуется
                List<meal> clientMeals = mealDao.getclientmealfromclient(Integer.parseInt(clientId));

                if (clientId != null && clientMeals == null || clientMeals.isEmpty()) {
                    int clientUid = Integer.parseInt(clientId);
                    meal meal = new meal("Сніданок",clientUid) ;
                    mealDao.insert(meal);
                    header_plan_menu header2 = new header_plan_menu("Сніданок",7);
                    items.add(new Item(0,header2));
                    meal = new meal("Перекус 1",clientUid) ;
                    mealDao.insert(meal);
                    header2 = new header_plan_menu("Перекус 1",10);
                    items.add(new Item(0,header2));
                    meal = new meal("Обід",clientUid) ;
                    mealDao.insert(meal);
                    header2 = new header_plan_menu("Обід",13);
                    items.add(new Item(0,header2));
                    meal = new meal("Перекус 2",clientUid) ;
                    mealDao.insert(meal);
                    header2 = new header_plan_menu("Перекус 2",17);
                    items.add(new Item(0,header2));
                    meal = new meal("Вечеря",clientUid) ;
                    mealDao.insert(meal);
                    header2 = new header_plan_menu("Вечеря",19);
                    items.add(new Item(0,header2));
                } else if(clientId == null){
                    Log.e("NextActivity", "CLIENT_ID is null");
                }
                else {
                    List<productinfoodmeal> productinfoodmeal = Product_info_meal_Dao.getproductbymeal_nameandclient("Сніданок",clientId);
                    header_plan_menu header2 = new header_plan_menu("Сніданок", 7);
                    items.add(new Item(0, header2));
                    for(productinfoodmeal prodinfmeal : productinfoodmeal) {
                        product prod_info = productDao.getproductbyname(prodinfmeal.getProductname());

                        if (prodinfmeal != null) {
                            product product = productDao.getproductbyname(prodinfmeal.getProductname());
                            body_plan_menu body2 = new body_plan_menu(product, null, prodinfmeal.getAmount());
                            items.add(new Item(1, body2));
                        }
                        Protein_amount = Protein_amount + ((prod_info.getProtein() * prodinfmeal.getAmount()) / 100);
                        Fats_amount = Fats_amount + ((prod_info.getFats() * prodinfmeal.getAmount()) / 100);
                        Uglivos_amount = Uglivos_amount + ((prod_info.getUglivod() * prodinfmeal.getAmount()) / 100);
                        Kalories_amount = Kalories_amount + ((prod_info.getKkal() * prodinfmeal.getAmount()) / 100);

                    }
                    productinfoodmeal = Product_info_meal_Dao.getproductbymeal_nameandclient("Перекус 1",clientId);
                    header2 = new header_plan_menu("Перекус 1",10);
                    items.add(new Item(0,header2));
                    for(productinfoodmeal prodinfmeal : productinfoodmeal) {
                        product prod_info = productDao.getproductbyname(prodinfmeal.getProductname());
                        if (prodinfmeal != null) {
                            product product = productDao.getproductbyname(prodinfmeal.getProductname());
                            body_plan_menu body2 = new body_plan_menu(product, null, prodinfmeal.getAmount());
                            items.add(new Item(1, body2));
                        }
                        Protein_amount = Protein_amount + ((prod_info.getProtein() * prodinfmeal.getAmount()) / 100);
                        Fats_amount = Fats_amount + ((prod_info.getFats() * prodinfmeal.getAmount()) / 100);
                        Uglivos_amount = Uglivos_amount + ((prod_info.getUglivod() * prodinfmeal.getAmount()) / 100);
                    }
                    productinfoodmeal = Product_info_meal_Dao.getproductbymeal_nameandclient("Обід",clientId);
                    header2 = new header_plan_menu("Обід",13);
                    items.add(new Item(0,header2));
                    for(productinfoodmeal prodinfmeal : productinfoodmeal) {
                        product prod_info = productDao.getproductbyname(prodinfmeal.getProductname());
                        if (prodinfmeal != null) {
                            product product = productDao.getproductbyname(prodinfmeal.getProductname());
                            body_plan_menu body2 = new body_plan_menu(product, null, prodinfmeal.getAmount());
                            items.add(new Item(1, body2));
                        }
                        Protein_amount = Protein_amount + ((prod_info.getProtein() * prodinfmeal.getAmount()) / 100);
                        Fats_amount = Fats_amount + ((prod_info.getFats() * prodinfmeal.getAmount()) / 100);
                        Uglivos_amount = Uglivos_amount + ((prod_info.getUglivod() * prodinfmeal.getAmount()) / 100);
                        Kalories_amount = Kalories_amount + ((prod_info.getKkal() * prodinfmeal.getAmount()) / 100);
                    }
                    productinfoodmeal = Product_info_meal_Dao.getproductbymeal_nameandclient("Перекус 2",clientId);
                    header2 = new header_plan_menu("Перекус 2",17);
                    items.add(new Item(0,header2));
                    for(productinfoodmeal prodinfmeal : productinfoodmeal) {
                        product prod_info = productDao.getproductbyname(prodinfmeal.getProductname());
                        if (prodinfmeal != null) {
                            product product = productDao.getproductbyname(prodinfmeal.getProductname());
                            body_plan_menu body2 = new body_plan_menu(product, null, prodinfmeal.getAmount());
                            items.add(new Item(1, body2));
                        }
                        Protein_amount = Protein_amount + ((prod_info.getProtein() * prodinfmeal.getAmount()) / 100);
                        Fats_amount = Fats_amount + ((prod_info.getFats() * prodinfmeal.getAmount()) / 100);
                        Uglivos_amount = Uglivos_amount + ((prod_info.getUglivod() * prodinfmeal.getAmount()) / 100);
                        Kalories_amount = Kalories_amount + ((prod_info.getKkal() * prodinfmeal.getAmount()) / 100);
                    }
                    productinfoodmeal = Product_info_meal_Dao.getproductbymeal_nameandclient("Вечеря",clientId);
                    header2 = new header_plan_menu("Вечеря",19);
                    items.add(new Item(0,header2));
                    for(productinfoodmeal prodinfmeal : productinfoodmeal) {
                        product prod_info = productDao.getproductbyname(prodinfmeal.getProductname());
                        if (prodinfmeal != null) {
                            product product = productDao.getproductbyname(prodinfmeal.getProductname());
                            body_plan_menu body2 = new body_plan_menu(product, null, prodinfmeal.getAmount());
                            items.add(new Item(1, body2));
                        }
                        Protein_amount = Protein_amount + ((prod_info.getProtein() * prodinfmeal.getAmount()) / 100);
                        Fats_amount = Fats_amount + ((prod_info.getFats() * prodinfmeal.getAmount()) / 100);
                        Uglivos_amount = Uglivos_amount + ((prod_info.getUglivod() * prodinfmeal.getAmount()) / 100);
                        Kalories_amount = Kalories_amount + ((prod_info.getKkal() * prodinfmeal.getAmount()) / 100);
                    }
                }

                protein_amount.setText(String.format("%.2f г", Protein_amount));
                fats_amount.setText(String.format("%.2f г", Fats_amount));
                uglivods_amount.setText(String.format("%.2f г", Uglivos_amount));
                kkalories_amount.setText(String.format("%.2f ккал", Kalories_amount));




//                dishDao.insertDish(new dish("Apple Pie", 250.0f, 10.0f, 30.0f, 2.0f));
//                dishDao.insertDish(new dish("Caesar Salad", 180.0f, 15.0f, 10.0f, 5.0f));
//                dishDao.insertDish(new dish("Grilled Chicken", 300.0f, 20.0f, 0.0f, 30.0f));
//                productDao.insertproduct(new product("Apple", 250.0f, 10.0f, 30.0f, 2.0f));
//                productDao.insertproduct(new product("Lemon", 180.0f, 15.0f, 10.0f, 5.0f));
//                productDao.insertproduct(new product("Pineapple", 300.0f, 20.0f, 0.0f, 30.0f));

//                List<meal> meal_list = mealDao.getclientmeal();
//                List<dish> meal_dish = dishDao.getAllDishes();
//                for(meal meal_et : meal_list)
//                {
//                    header_plan_menu header = new header_plan_menu(meal_et.meal_name,7);
//                    items.add(new Item(0,header));
//                    for(dish dish : meal_dish)
//                    {
//                        body_plan_menu body = new body_plan_menu(dish.getDishname(),dish.getKkal());
//                        items.add(new Item(1,body));
//                    }
//                }

//                body_plan_menu body2 = new body_plan_menu("Цезарь","Страва",120);
//                items.add(new Item(1,body2));
//                body2 = new body_plan_menu("Полуниця","Продукт",100);
//                items.add(new Item(1,body2));
//                body2 = new body_plan_menu("Банан","Продукт",100);
//                items.add(new Item(1,body2));
                 //body2 = new body_plan_menu("Полуниця","Продукт",100);
               // items.add(new Item(1,body2));

               //  body2 = new body_plan_menu("Полуниця","Продукт",100);
               // items.add(new Item(1,body2));
                // body2 = new body_plan_menu("Полуниця","Продукт",100);
                //items.add(new Item(1,body2));
            }
        });

//        TelegramMessage test = new TelegramMessage("erererer.","Maxik", "weewew");
//            messages.add(test);

        meal_of_plan_recyclerview = findViewById(R.id.RecyclerViewseeallplan);
        meal_of_plan_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new meal_of_plan_Adapter(items, this,clientId);
        meal_of_plan_recyclerview.setAdapter(adapter);



    }
    @Override
    public void onHeaderButtonClick(int position) {
        String text = adapter.getTextAtPosition(position);
        SharedPreferences sharedPreferences = getSharedPreferences("client_meal", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("client_meal", text);
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        // Ваш код для обработки нажатия кнопки "Назад"
        // Например, показать диалог для подтверждения выхода:
        new AlertDialog.Builder(this)
                .setMessage("Вы действительно хотите выйти?")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Выйти из активности
                        redact_of_plan_activity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Нет", null)
                .show();
    }

}

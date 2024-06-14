package com.example.bestdiet;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.bestdiet.*;
import com.example.bestdiet.database.AppDatabase;
import com.example.bestdiet.database.ClientDao;
import com.example.bestdiet.database.ProductDao;
import com.example.bestdiet.database.clients;
import com.example.bestdiet.database.product;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String BASEURL = "https://www.tablycjakalorijnosti.com.ua/tablytsya-yizhyi";

    private reloadData reload;

    private Get_SpoonacularAPI Spoonacular;

    private TelegramBotClient telegramBotClient;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null);
        if (email == null) {
            setContentView(R.layout.activity_main);
            Button myButton = findViewById(R.id.starting_button);

//            TextView gradient_name = findViewById(R.id.name_user);
//
//            // Задаем градиент
//            LinearGradient linearGradient = new LinearGradient(
//                    0, 0, 0, gradient_name.getTextSize(),
//                    getResources().getColor(R.color.start_color),
//                    getResources().getColor(R.color.end_color),
//                    Shader.TileMode.CLAMP);
//
//            gradient_name.getPaint().setShader(linearGradient);
            myButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, registr_activity.class);
                    startActivity(intent);
                    telegramBotClient.getUpdates(new UpdatesCallback() {
                        @Override
                        public void onUpdatesReceived(List<Message> messages) {
                            // Обработка полученных сообщений
                            for (Message message : messages) {
                                Log.e("Telegramchatid", String.valueOf(message.getChat().getId()));
                                Log.e("Telegramchatmessage", String.valueOf(message.getText()));
                                Log.e("Telegramchatdate", String.valueOf(message.getDate()));
                            }
                        }

                        @Override
                        public void onError(Throwable t) {
                            // Обработка ошибки
                            Log.e("TelegramBotClient", "Error fetching updates", t);
                        }
                    });
                    //Spoonacular = new Get_SpoonacularAPI(getApplicationContext());
                }
            });
        } else {
            setContentView(R.layout.main_menu);
            //new FetchData(getApplicationContext()).execute(BASEURL);


//            telegramBotClient = new TelegramBotClient();
//            telegramBotClient.getUpdates(new UpdatesCallback() {
//                @Override
//                public void onUpdatesReceived(List<Message> messages) {
//                    // Обработка полученных сообщений
//                    for (Message message : messages) {
//                        Log.e("Telegramchatid", String.valueOf(message.getChat().getId()));
//                        Log.e("Telegramchatmessage", String.valueOf(message.getText()));
//                        Log.e("Telegramchatdate", String.valueOf(message.getDate()));                            }
//                }
//
//                @Override
//                public void onError(Throwable t) {
//                    // Обработка ошибки
//                    Log.e("TelegramBotClient", "Error fetching updates", t);
//                }
//            });
//            telegramBotClient.getUpdatesByUserId(new UpdatesCallback() {
//                @Override
//                public void onUpdatesReceived(List<Message> messages) {
//                    // Обработка полученных сообщений из чата с указанным идентификатором
//                    for (Message message : messages) {
//
//                        Log.d("ID", "По айди ");                        // Ваш код обработки сообщений
//                        Log.d("TelegramBotClient", "Received message from chat " + message.getFrom().getId() + ": " + message.getText());
//                    }
//                }
//
//                @Override
//                public void onError(Throwable t) {
//                    // Обработка ошибок
//                    t.printStackTrace();
//                    Log.e("Error", "Error occurred: " + t.getMessage());
//                }
//            }, 751112696);
            reload = new reloadData(getApplicationContext());
            //Spoonacular = new Get_SpoonacularAPI(getApplicationContext());
            Intent intent = new Intent(MainActivity.this, main_menu.class);
            startActivity(intent);

//        }

//        Button showDialogButton = findViewById(R.id.starting_button);
//        showDialogButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showAlertDialog();
//            }
//        });
        }

    }
    private static class FetchData extends AsyncTask<String, Void, Void> {
        private static final String TAG = "FetchData";

        Map<String, String> ProductValues = new HashMap<>();

        private ExecutorService executor = Executors.newSingleThreadExecutor();

        AppDatabase database;

        ProductDao productDao = null;

        public FetchData(Context applicationContext) {
            database = AppDatabase.getAppDatabase(applicationContext);
            productDao = database.productDao();
        }


        @Override
        protected Void doInBackground(String... urls) {
            for (int i = 1; i <= 101; i++) {
                String url = urls[0];
                if (i > 1) {
                    url = url + "?page=" + i;
                }
                try {
                    // Загружаем HTML-документ по URL
                    Document doc = Jsoup.connect(url).get();

                    Elements links = doc.select("a.p-link");
                    int j = 1;
                    for (Element link : links) {
                        if (j == 10) {
                            break;
                        }
                        String href = link.attr("href");
                        String text = link.text();
                        Log.d(TAG, "Link: " + href + ", Text: " + text);
                        if (href != null) {
                            String newUrl = "https://www.tablycjakalorijnosti.com.ua" + href;
                            Document newDoc = Jsoup.connect(newUrl).get();
                            Elements newLinks = newDoc.select("a.crumb-link");
                            for (Element newLink : newLinks) {
                                String newHref = newLink.attr("href");
                                String newText = newLink.text();
                            }

                            // Извлекаем элемент с id "foodstuff-title"
                            Element foodstuffTitle = newDoc.getElementById("foodstuff-title");
                            if (foodstuffTitle != null) {
                                String title = foodstuffTitle.text();
                                title = title.split(" - к")[0];
                                ProductValues.put("Назва", title);
                            }

                            // Извлекаем изображение с классом "image-foodstuff-lg"
                            Elements images = newDoc.select("img.image-foodstuff-lg");
                            for (Element img : images) {
                                String src = img.attr("src");
                                String alt = img.attr("alt");
                                ProductValues.put("Фото", "https://www.tablycjakalorijnosti.com.ua" + src);
                                break;
                            }

                            Elements nutrientElements = newDoc.select("div.text-subtitle");
                            for (Element nutrientElement : nutrientElements) {
                                String nutrientName = nutrientElement.text().trim();
                                if (nutrientElement.text().contains(" Вуглеводи")) {
                                    Element parentElement = nutrientElement.parent();
                                    Elements siblingElements = parentElement.select("div > span");

                                    for (Element siblingElement : siblingElements) {
                                        String value = siblingElement.text().trim();
                                        // Проверяем, что значение не является шаблоном ({{data.foodstuff....}})
                                        if (!value.startsWith("{{") && !value.isEmpty()) {
                                            // Используем регулярное выражение для извлечения числовых значений
                                            Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
                                            Matcher matcher = pattern.matcher(nutrientName);

                                            if (matcher.find()) {
                                                String numericValue = matcher.group();
                                                ProductValues.put("Вугливоди", numericValue);
                                            }
                                            break;
                                        }
                                    }
                                }
                                else if(nutrientElement.text().contains(" Білки")) {
                                    Element parentElement = nutrientElement.parent();
                                    Elements siblingElements = parentElement.select("div > span");

                                    for (Element siblingElement : siblingElements) {
                                        String value = siblingElement.text().trim();
                                        // Проверяем, что значение не является шаблоном ({{data.foodstuff....}})
                                        if (!value.startsWith("{{") && !value.isEmpty()) {
                                            // Используем регулярное выражение для извлечения числовых значений
                                            Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
                                            Matcher matcher = pattern.matcher(nutrientName);

                                            if (matcher.find()) {
                                                String numericValue = matcher.group();
                                                ProductValues.put("Білки", numericValue);
                                                ProductValues.put("Калорійність", value);
                                            }
                                            break;
                                        }
                                    }
                                }
                                else if(nutrientElement.text().contains(" Жири"))
                                {
                                    Element parentElement = nutrientElement.parent();
                                    Elements siblingElements = parentElement.select("div > span");

                                    for (Element siblingElement : siblingElements) {
                                        String value = siblingElement.text().trim();
                                        // Проверяем, что значение не является шаблоном ({{data.foodstuff....}})
                                        if (!value.startsWith("{{") && !value.isEmpty()) {
                                            // Используем регулярное выражение для извлечения числовых значений
                                            Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
                                            Matcher matcher = pattern.matcher(nutrientName);

                                            if (matcher.find()) {
                                                String numericValue = matcher.group();
                                                ProductValues.put("Жири", numericValue);
                                            }
                                            break;
                                        }
                                    }
                                    break;
                                }
                            }

                            executor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    product product = new product(
                                            String.valueOf(ProductValues.get("Назва")),
                                            Float.valueOf(ProductValues.get("Калорійність")),
                                            Float.valueOf(ProductValues.get("Жири")),
                                            Float.valueOf(ProductValues.get("Вугливоди")),
                                            Float.valueOf(ProductValues.get("Білки")),
                                            String.valueOf(ProductValues.get("Фото"))
                                    );

                                    List<product> allProducts = productDao.getAllproducts();
                                    for (product p : allProducts) {
                                        if (p.getProductname().equals(product.getProductname())) {
                                            Log.d(TAG, "Product successfully added: " + p.getProductname());
                                            return;
                                        }
                                    }
                                    productDao.insertproduct(product);
                                }
                            });
                            // Вывод значений нутриентов в лог
                            for (Map.Entry<String, String> entry : ProductValues.entrySet()) {
                                Log.d("NutrientValues", entry.getKey() + ": " + entry.getValue());
                            }

                            j++;
                        }
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Error fetching URL", e);
                }
            }
            return null;
        }
    }
//    private void showAlertDialog() {
//        // Создание кастомного макета для диалога
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
//
//        // Создание AlertDialog с кастомным макетом
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setView(dialogView);
//
//        // Получение ссылок на виджеты в кастомном макете
//        TextView dialogTitle = dialogView.findViewById(R.id.dialog_title);
//        TextView dialogMessage = dialogView.findViewById(R.id.dialog_message);
//        Button positiveButton = dialogView.findViewById(R.id.positive_button);
//        Button negativeButton = dialogView.findViewById(R.id.negative_button);
//
//        // Установка текста для виджетов (можно изменить при необходимости)
//        dialogTitle.setText("Custom Dialog Title");
//        dialogMessage.setText("This is a custom dialog message.");
//
//        // Настройка обработчиков кликов для кнопок
//        AlertDialog dialog = builder.create();
//        positiveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Действие при нажатии кнопки "OK"
//                dialog.dismiss();
//            }
//        });
//
//        negativeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Действие при нажатии кнопки "Cancel"
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }
}



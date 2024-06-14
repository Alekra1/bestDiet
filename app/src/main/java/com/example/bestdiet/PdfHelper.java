package com.example.bestdiet;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.example.bestdiet.database.AppDatabase;
import com.example.bestdiet.database.ProductDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PdfHelper {

    static ExecutorService executor = Executors.newSingleThreadExecutor();

    static AppDatabase database;

    static ProductDao productDao;

    String client_id;

    public static void generatePdf(Context context,String[] meals) {
        database = AppDatabase.getAppDatabase(context);
        productDao = database.productDao();
        // Set up the document and page size
        PdfDocument document = new PdfDocument();
        int pageWidth = 595; // A4 width in points
        int pageHeight = 842; // A4 height in points
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        // Set up canvas and paint
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(12f); // Smaller text size to fit more content
        paint.setTextAlign(Paint.Align.LEFT);

        // Load and scale the background image
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ramka);
        Bitmap scaledBitmap = getScaledBitmap(bitmap, pageWidth, pageHeight);
        canvas.drawBitmap(scaledBitmap, 0, 0, paint);

        // Add custom elements to the PDF
        addCustomElements(canvas, paint, pageWidth, pageHeight,meals);

        document.finishPage(page);

        // Save the document
        File pdfDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "pdfs");
        if (!pdfDir.exists()) {
            pdfDir.mkdirs();
        }

        File pdfFile = new File(pdfDir, "meal_plan.pdf");
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream fos = new FileOutputStream(pdfFile);
                    document.writeTo(fos);
                    Toast.makeText(context, "PDF saved: " + pdfFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    document.close();
                }
            }
        });

        // Optionally, open the PDF after creation
        openPdf(context, pdfFile);
    }

    private static Bitmap getScaledBitmap(Bitmap bitmap, int width, int height) {
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width) / bitmap.getWidth();
        float scaleHeight = ((float) height) / bitmap.getHeight();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private static void addCustomElements(Canvas canvas, Paint paint, int pageWidth, int pageHeight,String[] meals) {

        float y = 100;
        float padding = 10; // Padding between text lines
        float maxWidth = pageWidth - 100; // Maximum text width considering margins

        // Style for section headers
        Paint headerPaint = new Paint();
        headerPaint.setTextSize(18f); // Header text size
        headerPaint.setTextAlign(Paint.Align.CENTER);
        headerPaint.setColor(Color.BLACK);

        // Style for main text
        paint.setTextSize(14f); // Main text size
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.DKGRAY);

        // Style for rectangles
        Paint rectPaint = new Paint();
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setColor(Color.BLACK);
        rectPaint.setStrokeWidth(1); // Thinner stroke width

        String[] nutritionInfo = {
                "Калорійність: 2000 ккал",
                "Білки: 75 г",
                "Жири: 70 г",
                "Вуглеводи: 250 г"
        };

        for (String info : nutritionInfo) {
            canvas.drawText(info, 60, y, paint);
            y += paint.descent() - paint.ascent() + padding;
        }

        y += 20;

        // Section 2: План харчування
        String[] mealTitles = {
                "Сніданок",
                "Перекус 1",
                "Обід",
                "Перекус 2",
                "Вечеря"
        };

        // Начинаем отрисовку таблицы
        for (int i = 0; i < mealTitles.length; i++) {
            String title = mealTitles[i];
            String meal = meals[i];
            if (meals[i].isEmpty() || meals[i] == null){
                Log.e("meals[i]","meals[i] is null");
                continue;
            }
            // Разбиваем текст на строки
            List<String> lines = breakTextIntoLines(meal, paint, maxWidth);

            // Вычисляем высоту текста и размеры рамки
            float textHeight = (paint.descent() - paint.ascent()) * lines.size();
            float rectTop = y + paint.ascent() - padding;
            float rectBottom = y + textHeight + padding * (lines.size() + 1);

            // Рисуем заголовок
            canvas.drawText(title, pageWidth / 2, y, headerPaint);
            y += 20; // Добавляем пространство между заголовком и содержимым

            // Рисуем рамку вокруг текста
            canvas.drawRect(50, rectTop, pageWidth - 50, rectBottom, rectPaint);

            if(lines == null) {
                Log.e("lines","lines is null");
                continue;
            }
            else {
                for (String line : lines) {
                    canvas.drawText(line, 60, y, paint);
                    y += paint.descent() - paint.ascent() + padding; // Переходим к следующей строке
                }
            }

            // Добавляем пространство между блоками
            y += padding; // Дополнительное пространство между блоками
        }

        y += 20; // Extra space between sections

        // Section 3: Рекомендації лікаря
        canvas.drawText("Рекомендації лікаря", pageWidth / 2, y, headerPaint);
        String[] doctorRecommendations = {
                "Рекомендується вживати більше овочів та фруктів.",
                "Пити достатню кількість води протягом дня.",
                "Уникати вживання надмірної кількості солі та цукру.",
                "Регулярно займатися фізичними вправами."
        };

        for (String recommendation : doctorRecommendations) {
            List<String> lines = breakTextIntoLines(recommendation, paint, maxWidth);

            float textHeight = (paint.descent() - paint.ascent()) * lines.size();
            float rectTop = y + paint.ascent() - padding;
            float rectBottom = y + textHeight + padding * (lines.size() + 1);

            for (String line : lines) {
                y += paint.descent() - paint.ascent() + padding;
                canvas.drawText(line, 60, y, paint);
            }

            canvas.drawRect(50, rectTop, pageWidth - 50, rectBottom, rectPaint);


            // Move to the next block
            y += padding/2; // Extra space between blocks
        }
        y += 40; // Extra space between sections

        // Section 4: Інформація про лікаря
        canvas.drawText("Інформація про лікаря", pageWidth / 2, y, headerPaint);
        y += 30;
        String[] doctorInfo = {
                "Лікар: Дмитро Петров",
                "Спеціалізація: Дієтологія",
                "Контактний номер: +38 (050) 123-45-67",
                "Електронна пошта: d.petrov@example.com"
        };

        for (String info : doctorInfo) {
            canvas.drawText(info, 60, y, paint);
            y += paint.descent() - paint.ascent() + padding;
        }
    }
    private static List<String> breakTextIntoLines(String text, Paint paint, float maxWidth) {
        List<String> lines = new ArrayList<>();
        if (text == null || text.isEmpty()) {
            return lines;
        }
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String testLine = currentLine + " " + word;
            float lineWidth = paint.measureText(testLine.trim());

            if (lineWidth > maxWidth) {
                lines.add(currentLine.toString().trim());
                currentLine = new StringBuilder(word);
            } else {
                currentLine.append(" ").append(word);
            }
        }

        if (currentLine.length() > 0) {
            lines.add(currentLine.toString().trim());
        }

        return lines;
    }

    private static void openPdf(Context context, File file) {
        Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);

        // Show the dialog to send the PDF after viewing it
        showSendDialog(context, file);
    }

    private static void showSendDialog(Context context, File file) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Send PDF");
        builder.setMessage("Do you want to send this meal plan?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ShareHelper.sharePdf(context, file);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}

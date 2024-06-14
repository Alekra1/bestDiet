package com.example.bestdiet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;

public class ShareHelper {
    public static void sharePdf(Context context, File file) {
//        Uri pdfUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
//
//// Создание интента для отправки файла в Telegram
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setType("application/pdf");
//        intent.putExtra(Intent.EXTRA_STREAM, pdfUri);
//        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//// Запуск активности для отправки файла
//        context.startActivity(Intent.createChooser(intent, "Share PDF"));

        TelegramBotClient telegramBotClient = new TelegramBotClient();
        telegramBotClient.sendMessage("751112696","Ваш план харчування");
        if(file != null)
            telegramBotClient.sendPdfDocument("751112696",file);
        else Log.e("file","file is null");

    }
}

package com.example.bestdiet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Product_info_adapter extends ArrayAdapter<String> {

    public Product_info_adapter(Context context, List<String> results) {
        super(context, 0, results);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.product_info_windw, parent, false);
        }

        String result = getItem(position);

        ImageView productImage = convertView.findViewById(R.id.product_image);
        TextView Name = convertView.findViewById(R.id.name);
        Button addButton = convertView.findViewById(R.id.add_button);

        // Здесь можно загрузить реальное изображение продукта
        // productImage.setImageResource(...);

        Name.setText(result);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }
}



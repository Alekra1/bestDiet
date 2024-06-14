package com.example.bestdiet;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class redact_meal_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Item> items;
        private OnItemClickListener listener;

        public redact_meal_Adapter(List<Item> items, OnItemClickListener listener) {
            this.items = items;
            this.listener = listener;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 0) {
                return new com.example.bestdiet.redact_meal_Adapter.bodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.redact_meal_element, parent, false));
            }
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (getItemViewType(position) == 0) {
                redact_meal_element meal_element = (redact_meal_element) items.get(position).geObject();
                ((redact_meal_Adapter.bodyViewHolder) holder).setBodyData(meal_element, listener, position);
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        @Override
        public int getItemViewType(int position) {
            return items.get(position).getType();
        }

        public String getTextAtPosition(int position) {
            if (position < 0 || position >= items.size()) {
                return null;
            }
            Item item = items.get(position);
             if (item.getType() == 0) {
                redact_meal_element meal_element = (redact_meal_element) item.geObject();
                return meal_element.getname(); // или другой текст из body
            }
            return null;
        }

        static class bodyViewHolder extends RecyclerView.ViewHolder {
            private TextView name, type;

            private ImageView photo_product;

            private TextInputEditText count;
            private ImageButton delete_element; // добавьте вашу кнопку

            public bodyViewHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.name);
                delete_element = itemView.findViewById(R.id.delete_element);
                type = itemView.findViewById(R.id.type);
                count = itemView.findViewById(R.id.count_product);
                photo_product = itemView.findViewById(R.id.photo_prod);
            }

            void setBodyData(redact_meal_element meal_element, final OnItemClickListener listener, final int position) {
                if (name != null) {
                    name.setText(meal_element.getname());
                } else {
                    Log.e("meal_of_plan_Adapter", "TextView is not initialized");
                }
                if (type != null) {
                    type.setText(String.valueOf(meal_element.getType()));
                } else {
                    Log.e("meal_of_plan_Adapter", "TextView is not initialized");
                }
                if (count != null) {
                    count.setText(String.valueOf(meal_element.getcount()));
                } else {
                    Log.e("meal_of_plan_Adapter", "TextView is not initialized");
                }

                if (photo_product != null && meal_element.getphoto() != null) {
                    Glide.with(photo_product.getContext())
                            .load(meal_element.getphoto())
                            .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(30, 0)))
                            .into(photo_product);
                }

                delete_element.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDeleteConfirmationDialog(v.getContext(), listener, position);
                    }
                });
            }

            private void showDeleteConfirmationDialog(Context context, final OnItemClickListener listener, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Підтвердження видалення")
                        .setMessage("Ви впевнені, що хочете видалити цей елемент з плану харчування?")
                        .setPositiveButton("Видалити", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listener.onHeaderButtonClick(position);
                                Log.e("Удаление елемента", "Елемент видалено з плану харчування");
                            }
                        })
                        .setNegativeButton("Скасувати", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        }
    }

package com.example.bestdiet;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class meal_of_plan_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Item> items;
    private OnItemClickListener listener;

    private static String clientId;


    public meal_of_plan_Adapter(List<Item> items, OnItemClickListener listener,String clientId) {
        this.items = items;
        this.listener = listener;
        this.clientId = clientId;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new headerViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.plan_element_header, parent, false));
        } else if (viewType == 1) {
            return new bodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.plan_element_body, parent, false));
        }
        else if (viewType == 2) {
            return new bodyViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.redact_meal_element, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == 0) {
            header_plan_menu header = (header_plan_menu) items.get(position).geObject();
            ((headerViewHolder) holder).setHeaderData(header, listener, position);
        } else if (getItemViewType(position) == 1) {
            body_plan_menu body = (body_plan_menu) items.get(position).geObject();
            ((bodyViewHolder) holder).setBodyData(body, listener, position);
        }
        else if (getItemViewType(position) == 1) {
            redact_meal_element meal_element = (redact_meal_element) items.get(position).geObject();
            ((meal_elementViewHolder) holder).setmeal_elementData(meal_element, listener, position);
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
            header_plan_menu header = (header_plan_menu) item.geObject();
            return header.getName_meal(); // или другой текст из header
        } else if (item.getType() == 1) {
            body_plan_menu body = (body_plan_menu) item.geObject();
            return body.getproduct().getPhoto_product(); // или другой текст из body
        }
        else if (item.getType() == 2) {
            redact_meal_element meal_element = (redact_meal_element) item.geObject();
            return meal_element.getname(); // или другой текст из body
        }
        return null;
    }

    static class headerViewHolder extends RecyclerView.ViewHolder {
        private TextView name, time;
        private ImageButton headerButton, redact_meal; // добавьте вашу кнопку

        public headerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_of_meal);
            redact_meal = itemView.findViewById(R.id.redact_meal);
            time = itemView.findViewById(R.id.textView3);
        }

        void setHeaderData(header_plan_menu header, final OnItemClickListener listener, final int position) {
            if (name != null) {
                name.setText(header.getName_meal());
            } else {
                Log.e("meal_of_plan_Adapter", "TextView is not initialized");
            }
            if (time != null) {
                time.setText(String.valueOf(header.gettime()) +"-"+ String.valueOf(header.gettime() + 2) + " pm");
            } else {
                Log.e("meal_of_plan_Adapter", "TextView is not initialized");
            }

            redact_meal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onHeaderButtonClick(position);
                    Log.e("Редактирование плана", "Редактирование плана");
                    Intent intent = new Intent(v.getContext(), redact_meal_activity.class);
                    intent.putExtra("CLIENT_ID", String.valueOf(clientId));
                    intent.putExtra("Meal_name", String.valueOf(header.getName_meal()));
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    static class bodyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, count,type,proteins,fats,uglivods,kalories;
        private ImageView prod_photo;

        public bodyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            type = itemView.findViewById(R.id.type);
            count = itemView.findViewById(R.id.amount);
            prod_photo = itemView.findViewById(R.id.prod_photo);
            proteins = itemView.findViewById(R.id.proteins);
            fats = itemView.findViewById(R.id.fats);
            uglivods = itemView.findViewById(R.id.uglivods);
            kalories = itemView.findViewById(R.id.kkal);
        }

        void setBodyData(body_plan_menu body, final OnItemClickListener listener, final int position) {
            if (name != null) {
                name.setText(body.getproduct().getProductname());
            } else {
                Log.e("meal_of_plan_Adapter", "TextView is not initialized");
            }
            if(body.getproduct() != null){
                type.setText("Продукт");
            }
            else if(body.getdish() != null)
                type.setText("Страва");
            if (count != null) {
                count.setText(String.valueOf(body.getcount()) + " г");
            } else {
                Log.e("meal_of_plan_Adapter", "TextView is not initialized");
            }
            if (body.getproduct() != null) {
                if(body.getproduct().getPhoto_product() != null)
                {
                    Glide.with(prod_photo.getContext())
                            .load(body.getproduct().getPhoto_product())
                            .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(30, 0)))
                            .into(prod_photo);
                }
//                else if(body.getdish().getPhoto_product()))
//                {
//                    image.setImageResource(R.mipmap.bludo_1_foreground);
//                }
                if (body.getproduct().getProtein() != -1) {
                    proteins.setText(String.format("%.2f г", (body.getproduct().getProtein() / 100.0) * body.getcount()));
                }
                if (body.getproduct().getFats() != -1) {
                    fats.setText(String.format("%.2f г", (body.getproduct().getFats() / 100.0) * body.getcount()));
                }
                if (body.getproduct().getKkal() != -1) {
                    kalories.setText(String.format("%.2f kkal", (body.getproduct().getKkal() / 100.0) * body.getcount()));
                }
                if (body.getproduct().getUglivod() != -1) {
                    uglivods.setText(String.format("%.2f г", (body.getproduct().getUglivod() / 100.0) * body.getcount()));
                }

            }

        }
    }

    static class meal_elementViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextInputEditText count;
        private ImageButton delete_element; // добавьте вашу кнопку

        public meal_elementViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_of_meal);
            delete_element = itemView.findViewById(R.id.redact_meal);
            count = itemView.findViewById(R.id.firstname_input);
        }

        void setmeal_elementData(redact_meal_element meal_element, final OnItemClickListener listener, final int position) {
            if (name != null) {
                name.setText(meal_element.getname());
            } else {
                Log.e("meal_of_plan_Adapter", "TextView is not initialized");
            }
            if (count != null) {
                count.setText(String.valueOf(meal_element.getcount()));
            } else {
                Log.e("meal_of_plan_Adapter", "TextView is not initialized");
            }

            delete_element.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onHeaderButtonClick(position);
                    Log.e("удаление елемента", "удаление елемента");
                }
            });
        }
    }
}



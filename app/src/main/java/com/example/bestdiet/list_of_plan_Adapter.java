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

import java.util.List;

public class list_of_plan_Adapter extends RecyclerView.Adapter<list_of_plan_Adapter.plansViewHolder> {
    private List<Plan> plans;

    public list_of_plan_Adapter(List<Plan> plans) {
        this.plans = plans;
    }

    @NonNull
    @Override
    public list_of_plan_Adapter.plansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plan_element, parent, false);
        return new plansViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull plansViewHolder holder, int position) {
        Plan plan = plans.get(position);
        if (holder.name_plan != null) {
            holder.name_plan.setText(plan.getName());
        } else {
            Log.e("meal_of_plan_Adapter", "TextView is not initialized");
        }
        if (holder.type != null) {
            holder.type.setText(String.valueOf(plan.getType()));
        } else {
            Log.e("meal_of_plan_Adapter", "TextView is not initialized");
        }
        if (holder.description != null) {
            holder.description.setText(String.valueOf(plan.getDescription()));
        } else {
            Log.e("meal_of_plan_Adapter", "TextView is not initialized");
        }
    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    static class plansViewHolder extends RecyclerView.ViewHolder {

        ImageButton redact_menu;
        private TextView name_plan, type, description;

        public plansViewHolder(@NonNull View itemView) {
            super(itemView);
            redact_menu = itemView.findViewById(R.id.imageButtonredact_menu);
            name_plan = itemView.findViewById(R.id.textViewname_plan);
            type = itemView.findViewById(R.id.textViewtype_plan);
            description = itemView.findViewById(R.id.textViewdescription);


            redact_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), redact_of_plan_activity.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}


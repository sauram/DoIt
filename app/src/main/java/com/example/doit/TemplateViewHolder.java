package com.example.doit;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class TemplateViewHolder extends RecyclerView.ViewHolder{
    public TextView Title;
    public TextView Description;
    public MaterialButton DeleteButton;
    public MaterialCardView materialCardView;
    public TemplateViewHolder(@NonNull View itemView){
        super(itemView);
        Title = itemView.findViewById(R.id.template_title);
        Description = itemView.findViewById(R.id.template_description);
        DeleteButton = itemView.findViewById(R.id.delete_template_button);
        materialCardView = itemView.findViewById(R.id.template_material_card);
    }
}

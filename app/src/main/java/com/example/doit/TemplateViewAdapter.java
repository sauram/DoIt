package com.example.doit;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class TemplateViewAdapter extends RecyclerView.Adapter<TemplateViewHolder> {
    ArrayList<Pair<Integer, Pair<String, String>>> dataset;
    public TemplateViewAdapter(ArrayList<Pair<Integer, Pair<String, String>>> dataset){
        this.dataset=dataset;
    }

    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_view,parent,false);
        return new TemplateViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateViewHolder holder, int position) {
        if(dataset!=null && position<dataset.size()){
            if(dataset.get(position).second.first.isEmpty()){
                holder.Title.setText(R.string.string_untitled);
            }else{
                holder.Title.setText(dataset.get(position).second.first);
            }
            if(dataset.get(position).second.second.isEmpty()){
                holder.Description.setText(R.string.no_description_text);
            }else{
                holder.Description.setText(dataset.get(position).second.second);
            }
            holder.DeleteButton.setEnabled(false);
            holder.DeleteButton.setVisibility(View.INVISIBLE);
            holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String selection = DatabaseClass.DATA_COLUMN_ID +" = ?";
                    Integer value = dataset.get(holder.getAdapterPosition()).first ;
                    String[] selectionArg = {value.toString()};
                    new DatabaseClass(v.getContext())
                            .getWritableDatabase()
                            .delete(DatabaseClass.DATA_TABLE_NAME,selection,selectionArg);
                    dataset.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                    //notifyItemRangeChanged(holder.getAdapterPosition(), getItemCount()); //could be removed

                    holder.DeleteButton.setEnabled(false);
                    holder.DeleteButton.setVisibility(View.INVISIBLE);
                }
            });
            holder.materialCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.DeleteButton.isEnabled()){
                        holder.DeleteButton.setEnabled(false);
                        holder.DeleteButton.setVisibility(View.INVISIBLE);
                    }else{
                        holder.DeleteButton.setVisibility(View.VISIBLE);
                        holder.DeleteButton.setEnabled(true);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}

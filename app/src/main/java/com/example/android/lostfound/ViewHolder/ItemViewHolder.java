package com.example.android.lostfound.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.lostfound.Interface.ItemClickListener;
import com.example.android.lostfound.R;

import org.jetbrains.annotations.NotNull;

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtAuthorName,txtDescription,txtDateTime,txtPhone;
    public ImageView imageView;
    public ItemClickListener listener;

    public ItemViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtAuthorName = (TextView) itemView.findViewById(R.id.posted_by);
        txtPhone=(TextView)itemView.findViewById(R.id.user_phone_number);
        txtDateTime = (TextView) itemView.findViewById(R.id.item_date_time);
    }
    public void setItemClickListener(ItemClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition(),false);
    }
}

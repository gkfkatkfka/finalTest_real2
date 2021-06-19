package com.inhatc.finaltest;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.Holder> {
    ArrayList<BookVO> array;
    Context context;

    public BookAdapter(Context context, ArrayList<BookVO> array) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    public void onBindViewHolder(Holder holder, int position) {
        String image=array.get(position).getImage();
        int price=array.get(position).getPrice();

        holder.txtprice.setText(array.get(position).getPrice() + "원");
        holder.txttitle.setText(Html.fromHtml(array.get(position).getTitle()));

        // Picasso.with(context).load(image).into(holder.image);
        Picasso.get().load(image).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView txttitle, txtprice;

        public Holder(@NonNull View itemView) {
            super(itemView);
            txttitle=itemView.findViewById(R.id.txttitle);
            txtprice=itemView.findViewById(R.id.txtprice);
            image=itemView.findViewById(R.id.image);
        }
    }
}
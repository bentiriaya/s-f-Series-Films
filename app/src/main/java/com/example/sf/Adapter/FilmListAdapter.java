package com.example.sf.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sf.Activity.DetailActivity;
import com.example.sf.Domain.FilmItem;
import com.example.sf.Domain.listFilm;
import com.example.sf.R;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.ViewHolder> {
    listFilm items;
    Context context;

    public FilmListAdapter(listFilm items) {
        this.items = items;
    }

    @NonNull
    @Override
    public FilmListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate= LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_film,parent,false);
        context=parent.getContext();
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmListAdapter.ViewHolder holder, int position) {
        holder.titleTxt.setText(items.getData().get(position).getTitle());
        holder.scoreTxt.setText(""+items.getData().get(position).getImdbRating());
        Glide.with(holder.itemView.getContext()).load(items.getData().get(position).getPoster()).into(holder.pic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("id",items.getData().get(position).getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return items.getData().size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView titleTxt,scoreTxt;
        ImageView pic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.titleTxt);
            scoreTxt=itemView.findViewById(R.id.scoreTxt);
            pic=itemView.findViewById(R.id.pic);
        }
    }
}

package com.acosaf.bazarhazar;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.net.IDN;
import java.util.ArrayList;

public class adapter_allAds extends RecyclerView.Adapter<adapter_allAds.ViewHolder> {

    private ArrayList<String> id;
    private ArrayList<String> image;
    private ArrayList<String> categ;


    public adapter_allAds(ArrayList<String> id,ArrayList<String> categ,ArrayList<String> image)
    {
        this.id = id;
        this.image = image;
        this.categ = categ;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.allads_layout, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();

        final String Id = id.get(position);
        final String Image = image.get(position);
        final String Categ = categ.get(position);
        holder.categ.setText(Categ);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ads_detail.class);
                intent.putExtra("postid", Id);
                context.startActivity(intent);
            }
        });
        Picasso.get().load(Image).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return id.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView categ;
        ImageView imageView;
        LinearLayout linearLayout;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categ = itemView.findViewById(R.id.adtype_adall);
            imageView=itemView.findViewById(R.id.imageView);
            linearLayout = itemView.findViewById(R.id.linearlayout_allads);


        }
    }
}
package com.example.mohamed.capstoneapp.Trailers;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamed.capstoneapp.R;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.Holder> {

    ArrayList<Trailer> trailers;
    Context context;
    OnItemClickListener onItemClickListener;

  public   interface OnItemClickListener {
        void onItemClick(int position);
    }


    public TrailerAdapter(Context mcontext,ArrayList<Trailer> mtrailers,OnItemClickListener monItemClickListener){
        context=mcontext;
        trailers=mtrailers;
        onItemClickListener=monItemClickListener;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.trailer_item,viewGroup,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.textView.setText("trailer"+position);
        holder.imageView.setImageResource(R.drawable.play);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class Holder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        public Holder(@NonNull View itemView) {
            super(itemView);

            imageView=(ImageView) itemView.findViewById(R.id.trailer_icon);
            textView=(TextView) itemView.findViewById(R.id.trailer_number);
        }
    }
}

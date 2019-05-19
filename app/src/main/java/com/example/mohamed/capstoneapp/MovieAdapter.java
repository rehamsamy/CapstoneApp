package com.example.mohamed.capstoneapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.Holder> {

    ArrayList<Movie> movies=new ArrayList<>();
    OnItemClickListener  mOnItemClickListener;
    Context context;

   public interface  OnItemClickListener{
      void onItemClick(int position,View view);
   }


   public MovieAdapter(Context mContext,ArrayList<Movie> mMovies,OnItemClickListener onItemClickListener){
       context=mContext;
       mOnItemClickListener=onItemClickListener;
       movies=mMovies;


   }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(context).inflate(R.layout.movie_item,viewGroup,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        Movie movie = movies.get(position);
        final ImageView poster_image = (ImageView) holder.itemView.findViewById(R.id.poster_image);
        String poster_string = movie.getPoster_path();
        String value = "w185";
        String base_url = "http://image.tmdb.org/t/p/";
        final String full_url = base_url + value + "/" + poster_string;

        Picasso.get().load(full_url).into(poster_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(position,poster_image);
            }
        });



    }

    @Override
    public int getItemCount() {

        return movies.size();

    }


     public  void updateTasks(ArrayList<Movie> tasks){
       movies.clear();
       movies.addAll(tasks);
       notifyDataSetChanged();
     }

    class Holder extends RecyclerView.ViewHolder{
         View itemView;
        public Holder(@NonNull View item) {
            super(item);
            itemView= item;
        }
    }
}


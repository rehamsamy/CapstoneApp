package com.example.mohamed.capstoneapp;

import android.content.Context;
import android.provider.Contacts;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.Holder> {

    ArrayList<Person> movies=new ArrayList<>();
    PersonAdapter.OnItemClickListener mOnItemClickListener;
    Context context;

    public interface  OnItemClickListener{
        void onPersonClick(int position);
    }


    public PersonAdapter(Context mContext,ArrayList<Person> mMovies,OnItemClickListener onItemClickListener){
        context=mContext;
        mOnItemClickListener=onItemClickListener;
        movies=mMovies;


    }

    @NonNull
    @Override
    public PersonAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.movie_item,viewGroup,false);

        return new PersonAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {

        Person person = movies.get(position);
        ImageView poster_image = (ImageView) holder.itemView.findViewById(R.id.poster_image);
        String poster_string = person.getProfile_path();
        String value = "w185";
        String base_url = "http://image.tmdb.org/t/p/";
        final String full_url = base_url + value + "/" + poster_string;
        poster_image.setMaxWidth(70);
        poster_image.setMaxHeight(70);

        Picasso.get().load(full_url).into(poster_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onPersonClick(position);

            }
        });
    }

   @Override
    public int getItemCount() {

        return movies.size();

    }

    class Holder extends RecyclerView.ViewHolder{
        View itemView;
        public Holder(@NonNull View item) {
            super(item);
            itemView= item;
        }
    }



    public void getPeople(ArrayList<Person> peoples){
        movies.clear();
        movies.addAll(peoples);
        notifyDataSetChanged();


    }
}

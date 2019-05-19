package com.example.mohamed.capstoneapp;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.example.mohamed.capstoneapp.Database.MovieDatabase;
import com.example.mohamed.capstoneapp.Database.MovieModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public  class GridFactory extends RemoteViewsService {
    public static ArrayList<Movie> widgetMovies;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.putParcelableArrayListExtra("list",widgetMovies);
        return new GridRemoteFactory(this.getApplicationContext(),widgetMovies);

    }
}

     class GridRemoteFactory implements RemoteViewsService.RemoteViewsFactory {


         Context context;
         ArrayList<Movie> movies;
         Retrofit retrofit;
         RetrofitInterface retrofitInterface;
         ArrayList<Movie> widgetMovies;

        public GridRemoteFactory(Context applicationContext,ArrayList<Movie>movies) {
           this. context=applicationContext;
           widgetMovies=movies;
           this.movies=widgetMovies;


        }

        @Override
        public void onCreate() {


        }

        @Override
        public void onDataSetChanged() {


            retrofit=new Retrofit.Builder()
                    .baseUrl(RetrofitInterface.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            retrofitInterface=retrofit.create(RetrofitInterface.class);
          Call<MovieList> movieListCall=  retrofitInterface.getPopular("popular",RetrofitInterface.apiKey);
          movieListCall.enqueue(new Callback<MovieList>() {
              @Override
              public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                 movies= response.body().getMovies();
                 widgetMovies=movies;
                 Log.v("GridFactory","widget"+movies.size());
              }

              @Override
              public void onFailure(Call<MovieList> call, Throwable t) {

              }
          });

            //

        }

        @Override
        public void onDestroy() {
            movies.clear();

        }

        @Override
        public int getCount(){
//            if(movies==null){
//                Toast.makeText(context,"error",Toast.LENGTH_LONG).show();
//            }
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {

            Log.v("GridFactory","movie widget"+movies.size());
            if(movies.size()==0)  return null;
            Movie movie=movies.get(position);
            String poster=movie.getPoster_path();
            String value = "w185";
            String base_url = "http://image.tmdb.org/t/p/";
            final String full_url = base_url + value + "/" + poster;

            RemoteViews views=new RemoteViews(context.getPackageName(),R.layout.widget_item);
            // Picasso.get().load(full_url).into(views.ge);
            Uri uri=Uri.parse(full_url);
            views.setImageViewUri(R.id.poster_image,uri);

            //views.setTextViewText(R.id.text,movie.getOriginal_title());

            return  views;

        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }



     }


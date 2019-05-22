package com.example.mohamed.capstoneapp.Widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.mohamed.capstoneapp.Database.MovieDatabase;
import com.example.mohamed.capstoneapp.Movie;
import com.example.mohamed.capstoneapp.R;
import com.example.mohamed.capstoneapp.RetrofitInterface;

import java.util.ArrayList;

import retrofit2.Retrofit;


public  class GridFactory extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
       ArrayList<Movie> movies= intent.getParcelableArrayListExtra("list");
        return new GridRemoteFactory(this.getApplicationContext(),movies);

    }
}

     class GridRemoteFactory implements RemoteViewsService.RemoteViewsFactory {


    MovieDatabase database;
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

            database=MovieDatabase.getInstance(context);
          movies= (ArrayList<Movie>) database.taskDao().getWidgetTasks();
            Log.v("GridFactory","widget"+movies.size());


            //

        }

        @Override
        public void onDestroy() {
            movies.clear();

        }

        @Override
        public int getCount(){

            return movies.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            Log.v("GridFactory","movie widget"+movies.size());
            //if(movies.size()==0)  return null;
            RemoteViews views = null;

            for(int i=0;i<movies.size();i++){
                Movie movie=movies.get(i);

                String poster=movie.getPoster_path();
                String value = "w185";
                String base_url = "http://image.tmdb.org/t/p/";
                final String full_url = base_url + value + "/" + poster;

                views=new RemoteViews(context.getPackageName(), R.layout.widget_item);
                // Picasso.get().load(full_url).into(views.ge);
//            Uri uri=Uri.parse(full_url);
//            views.setImageViewUri(R.id.poster_image,uri);
                Log.v("GridFactory","widgettttt"+movies.get(1).getOriginal_title());

                views.setTextViewText(R.id.text,movie.getOriginal_title());

            }

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


//            retrofit=new Retrofit.Builder()
//                    .baseUrl(RetrofitInterface.baseUrl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//
//                    retrofitInterface=retrofit.create(RetrofitInterface.class);
//        Call<MovieList> movieListCall=  retrofitInterface.getPopular("popular",RetrofitInterface.apiKey);
//        movieListCall.enqueue(new Callback<MovieList>() {
//@Override
//public void onResponse(Call<MovieList> call, Response<MovieList> response) {
//        movies= response.body().getMovies();
//        widgetMovies=movies;
//        Log.v("GridFactory","widget"+movies.size());
//        }
//
//@Override
//public void onFailure(Call<MovieList> call, Throwable t) {
//
//        }
//        });

package com.example.mohamed.capstoneapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public  class GridFactory extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
       ArrayList<Movie>movies= intent.getParcelableArrayListExtra("list");
        return new GridRemoteFactory(this.getApplicationContext(),movies);
    }
}



     class GridRemoteFactory implements RemoteViewsService.RemoteViewsFactory {

         Context context;
         ArrayList<Movie> movies;

        public GridRemoteFactory(Context applicationContext,ArrayList<Movie>movies) {
           this. context=applicationContext;
           this.movies=movies;

        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
           movies= MainActivity.widgetMovie;

            Log.v("GridFactory","movie widget"+movies.size());

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount(){
                int x= movies.size();
               return x;
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


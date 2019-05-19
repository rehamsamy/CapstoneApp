package com.example.mohamed.capstoneapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.KeyEventDispatcher;
import android.util.Log;

import com.example.mohamed.capstoneapp.Database.MovieDatabase;

import java.util.ArrayList;
import java.util.List;

public class WidgetService extends IntentService {
 public  static final String MOVIE_ACTION="movie";
 MovieDatabase database;

    public WidgetService() {
        super("WidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action=intent.getAction();
        if(MOVIE_ACTION.equals(action)){
          // ArrayList<Movie> movies= intent.getParcelableArrayListExtra("list");
         //  Log.v("WidgetService","aaaaaaa"+movies.size());
            updateWidget();
        }

    }

    private void updateWidget() {

        database= MovieDatabase.getInstance(this);
        ArrayList<Movie> movies= (ArrayList<Movie>) database.taskDao().getWidgetTasks();
        Log.v("GridFactory","widget"+movies.size());

        AppWidgetManager manager=AppWidgetManager.getInstance(this);
        int [] id=manager.getAppWidgetIds(new ComponentName(this,AppWidget.class));
        manager.notifyAppWidgetViewDataChanged(id,R.layout.app_widget);
       // ArrayList<Movie> movies=GridFactory.widgetMovies;
        AppWidget.updateWidget(getApplicationContext(),manager,id,movies);
//     Log.v("WidgetService","sssss"+movies.size());


    }

    public static void startWidgetService(Context context){
        Intent intent=new Intent(context,WidgetService.class);
       // intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) movies);
        intent.setAction(MOVIE_ACTION);
        context.startService(intent);
    }
}

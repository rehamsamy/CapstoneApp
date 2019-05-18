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

import java.util.ArrayList;
import java.util.List;

public class WidgetService extends IntentService {
 public  static final String MOVIE_ACTION="movie";

    public WidgetService() {
        super("WidgetService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action=intent.getAction();
        if(action=="movie"){
           ArrayList<Movie> movies= intent.getParcelableArrayListExtra("list");
         //  Log.v("WidgetService","aaaaaaa"+movies.size());
            updateWidget(movies);
        }

    }

    private void updateWidget(ArrayList<Movie> movies) {
        AppWidgetManager manager=AppWidgetManager.getInstance(this);
        int [] id=manager.getAppWidgetIds(new ComponentName(this,AppWidget.class));
        manager.notifyAppWidgetViewDataChanged(id,R.layout.app_widget);
        AppWidget.updateWidget(getApplicationContext(),manager,id,movies);
    // Log.v("WidgetService","sssss"+movies.size());


    }

    public static void startWidgetService(Context context,List<Movie> movies){
        Intent intent=new Intent(context,WidgetService.class);
        intent.putParcelableArrayListExtra("list", (ArrayList<? extends Parcelable>) movies);
        intent.setAction(MOVIE_ACTION);
        context.startService(intent);
    }
}

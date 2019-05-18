package com.example.mohamed.capstoneapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidget extends AppWidgetProvider {

    static ArrayList<Movie> mMovies;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId,ArrayList<Movie> movies) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
       // mMovies=movies;
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);

        Intent intent=new Intent(context,GridFactory.class);
        intent.putParcelableArrayListExtra("list",movies);

        //movies=intent.getParcelableArrayListExtra("list");
//        Log.v("AppWidget","ssssssss"+movies.size());
        views.setRemoteAdapter(R.id.widget_grid,intent);
        views.setTextViewText(R.id.text,"Movie App");
       // views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        WidgetService.startWidgetService(context,mMovies);
       // Log.v("AppWidget","widget"+MainActivity.movies.size());

    }

    public static void updateWidget(Context context, AppWidgetManager appWidgetManager, int [] appWidgetIds, ArrayList<Movie> movies){
        mMovies=movies;
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId,movies);

        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}


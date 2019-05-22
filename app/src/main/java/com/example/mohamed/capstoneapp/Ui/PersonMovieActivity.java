package com.example.mohamed.capstoneapp.Ui;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.mohamed.capstoneapp.Movie;
import com.example.mohamed.capstoneapp.MovieAdapter;
import com.example.mohamed.capstoneapp.PersonData.Person;
import com.example.mohamed.capstoneapp.PersonData.PeopleMovieList;
import com.example.mohamed.capstoneapp.R;

import java.util.ArrayList;

public class PersonMovieActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener {


    ArrayList<PeopleMovieList> peopleMovieLists;
    RecyclerView recyclerView;
    ArrayList<Movie> movieArrayList;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_movie);



        toolbar=(Toolbar) findViewById(R.id.person_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Popular People");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        ArrayList<Person>people= intent.getParcelableArrayListExtra("list");
       int position=intent.getIntExtra("position",0);
       Log.v("PersonMovieActivity","people size"+people.size());

       peopleMovieLists=new ArrayList<>();
       recyclerView=(RecyclerView) findViewById(R.id.movie_list);
       recyclerView.setLayoutManager(new GridLayoutManager(this,3));
       recyclerView.setHasFixedSize(true);

       Person person=people.get(position);

      movieArrayList= person.getPeopleMovie();
        Log.v("PersonMovieActivity","xxxxxxxxx"+movieArrayList.size());

       MovieAdapter adapter=new MovieAdapter(this,movieArrayList,this);
       recyclerView.setAdapter(adapter);


    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onItemClick(int position, View view) {
        ImageView imageView= (ImageView) view;
        ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this,imageView,
                ViewCompat.getTransitionName(imageView));
        Intent intent=new Intent(this,DetailActivity.class);
        intent.putParcelableArrayListExtra("list",movieArrayList);
        intent.putExtra("position",position);
        startActivity(intent,optionsCompat.toBundle());

    }
}

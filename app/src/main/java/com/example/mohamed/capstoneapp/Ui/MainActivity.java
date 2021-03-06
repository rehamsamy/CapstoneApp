package com.example.mohamed.capstoneapp.Ui;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mohamed.capstoneapp.Database.MovieDatabase;
import com.example.mohamed.capstoneapp.Database.MovieModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.mohamed.capstoneapp.Movie;
import com.example.mohamed.capstoneapp.MovieAdapter;
import com.example.mohamed.capstoneapp.MovieList;
import com.example.mohamed.capstoneapp.PersonData.Person;
import com.example.mohamed.capstoneapp.PersonData.PersonAdapter;
import com.example.mohamed.capstoneapp.PersonData.PersonList;
import com.example.mohamed.capstoneapp.R;
import com.example.mohamed.capstoneapp.RetrofitInterface;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import  com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements MovieAdapter.OnItemClickListener,PersonAdapter.OnItemClickListener {
    RecyclerView recyclerView;
    MovieAdapter adapter;
    PersonAdapter mAdapter;
   public static ArrayList<Movie> movies;
    ArrayList<Person> people;
    static final String TAG="MainActivity";
    ProgressDialog progressDialog;
    Call<MovieList>movieListCall;
    Call<PersonList>personListCall;
    Retrofit retrofit;
    RetrofitInterface retrofitInterface;
    public static ArrayList<Movie> widgetMovie;
    String movieType;
    MovieDatabase database;

    private AdView adView;
    MenuItem item1;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movies=new ArrayList<>();

        MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");
        adView=(AdView) findViewById(R.id.adView);
        AdRequest adRequest=new AdRequest.Builder()
                .addTestDevice("33BE2250B43518CCDA7DE426D04EE231").build();
        adView.loadAd(adRequest);

        toolbar=(Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Poster Display");


        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(this,StartActivity.class));
            finish();
        }


        database=MovieDatabase.getInstance(getApplicationContext());

         retrofit=new Retrofit.Builder()
                .baseUrl(RetrofitInterface.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

         retrofitInterface=retrofit.create(RetrofitInterface.class);


        recyclerView=(RecyclerView) findViewById(R.id.movie_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        adapter = new MovieAdapter(getApplicationContext(), new ArrayList<Movie>(), MainActivity.this);
        recyclerView.setAdapter(adapter);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("download movies");
        progressDialog.setMessage("please wait until finishing");
        progressDialog.show();

        //movies=new ArrayList<>();
        people=new ArrayList<>();
        movieType="popular";


       if(savedInstanceState == null) {
           movieListCall = retrofitInterface.getPopular(movieType, RetrofitInterface.apiKey);
           readRetrofitData(movieType, movieListCall);

       }
           // recyclerView.getLayoutManager().onRestoreInstanceState((Parcelabl) movies);
            else{
           progressDialog.hide();
           if(savedInstanceState.containsKey("list")){
               movies=savedInstanceState.getParcelableArrayList("list");
               adapter.updateTasks(movies);
           }
          else if(savedInstanceState.containsKey("person")){
               people=savedInstanceState.getParcelableArrayList("person");
               mAdapter=new PersonAdapter(this,new ArrayList<Person>(),this);
               recyclerView.setAdapter(mAdapter);
                mAdapter.getPeople(people);
           }


              // Log.v(TAG,"dddd"+movies.size());

           }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("list",movies);
        outState.putParcelableArrayList("person",people);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id=item.getItemId();
         item1=(MenuItem) findViewById(R.id.people);

        if(id==R.id.top_rated){
            movieType="top_rated";
            getSupportActionBar().setTitle("Top Rated");
           movieListCall=retrofitInterface.getMovies("top_rated",RetrofitInterface.apiKey);
           readRetrofitData(movieType,movieListCall);

        }else if(id==R.id.popular){
            getSupportActionBar().setTitle("Popular Movie");
            movieType="popular";
            movieListCall=retrofitInterface.getMovies(movieType,RetrofitInterface.apiKey);
            readRetrofitData(movieType,movieListCall);
          //  WidgetService.startWidgetService(this,movies);

        }else if(id==R.id.up_coming){
            getSupportActionBar().setTitle("Upcoming");
            movieType="upcoming";
            movieListCall=retrofitInterface.getMovies(movieType,RetrofitInterface.apiKey);
            readRetrofitData(movieType,movieListCall);

        }else if(id==R.id.now_playing){
            getSupportActionBar().setTitle("Now playing");
            progressDialog.dismiss();
            movieType="now_playing";
            movieListCall=retrofitInterface.getNowPlaying(movieType,RetrofitInterface.apiKey);
            readRetrofitData(movieType,movieListCall);

        }

        else if(id==R.id.people){
            getSupportActionBar().setTitle("Popular People");
            progressDialog.dismiss();
            movieType="popular";
          personListCall=retrofitInterface.getPerson(movieType,RetrofitInterface.apiKey);
           readPersonData(movieType,personListCall);

        }else if(id==R.id.favorite){
            getSupportActionBar().setTitle("Favorites");
            retreiveData();
        }

        else if(id==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,StartActivity.class));
            finish();
        }

        return true;
    }

    private void retreiveData() {
        MovieModel movieModel= ViewModelProviders.of(this).get(MovieModel.class);
        LiveData<List<Movie>> listLiveData=movieModel.getListLiveData();

        listLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> favMovies) {

                 movies= (ArrayList<Movie>) favMovies;
                if(movies==null){
                    Toast.makeText(getApplicationContext(),"favorite is empty",Toast.LENGTH_LONG).show();

                }else{
                   // MainActivity.this.movies= (ArrayList<Movie>) movies;
                    Toast.makeText(getApplicationContext(),"size"+favMovies.size(),Toast.LENGTH_LONG).show();
                    Log.v(TAG,"llllllll"+favMovies.size());
                    adapter=new MovieAdapter(MainActivity.this, (ArrayList<Movie>) favMovies,MainActivity.this);
                    recyclerView.setAdapter(adapter);
                   // WidgetService.startWidgetService(getApplicationContext());
                }
            }
        });

    // ArrayList<Movie> movies= (ArrayList<Movie>) database.taskDao().getMovieTasks();



    }


    void readRetrofitData(String movieType,Call<MovieList>movieListCall){
    movieListCall.enqueue(new Callback<MovieList>() {
           @Override
           public void onResponse(Call<MovieList> call, Response<MovieList> response) {
               movies=response.body().getMovies();
               widgetMovie=movies;

               if(movies==null){
                   Toast.makeText(getApplicationContext(),"size is null",Toast.LENGTH_LONG).show();
               }else {
                   Log.v(TAG, "movie size is" + movies.size());
                   progressDialog.hide();
                   adapter = new MovieAdapter(getApplicationContext(), movies, MainActivity.this);
                   recyclerView.setAdapter(adapter);

               }

           }

           @Override
           public void onFailure(Call<MovieList> call, Throwable t) {

           }
       });


   }

  void readPersonData(String movieType, Call<PersonList> personListCall){

        personListCall.enqueue(new Callback<PersonList>() {
            @Override
            public void onResponse(Call<PersonList> call, Response<PersonList> response) {
               people= response.body().getPeople();
              // adapter=new MovieAdapter(getApplicationContext(),people,MainActivity.this);
                Log.v(TAG,"person size is"+people.size());

                mAdapter=new PersonAdapter(getApplicationContext(),people,MainActivity.this);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),3));
                recyclerView.setAdapter(mAdapter);


            }

            @Override
            public void onFailure(Call<PersonList> call, Throwable t) {

            }
        });

   }



    @Override
    public void onPersonClick(int position) {
        Intent intent=new Intent(this,PersonMovieActivity.class);
        Log.v(TAG,"position"+position);
        intent.putParcelableArrayListExtra("list",people);
        intent.putExtra("position",position);
        startActivity(intent);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onItemClick(int position, View view) {
        ImageView imageView= (ImageView) view;
        ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(this,imageView,
                ViewCompat.getTransitionName(imageView));
        Intent intent=new Intent(this,DetailActivity.class);
        intent.putParcelableArrayListExtra("list",movies);
        intent.putExtra("position",position);
        startActivity(intent,optionsCompat.toBundle());

    }



    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(this,StartActivity.class));
            finish();
        }
    }







}




package com.example.mohamed.capstoneapp.Ui;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.capstoneapp.Database.MovieDatabase;
import com.example.mohamed.capstoneapp.Database.MovieModel;
import com.example.mohamed.capstoneapp.Movie;
import com.example.mohamed.capstoneapp.R;
import com.example.mohamed.capstoneapp.RetrofitInterface;
import com.example.mohamed.capstoneapp.Trailers.Trailer;
import com.example.mohamed.capstoneapp.Trailers.TrailerAdapter;
import com.example.mohamed.capstoneapp.Trailers.TrailerList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity  implements TrailerAdapter.OnItemClickListener {
 static  final String TAG="DetailActivity";
    TextView title, overview, release, vote;
    ImageView poster;
    String mTitle,mOverview,mRelease,mVote,mPosterPath;
    MovieDatabase database;
    RecyclerView recyclerView;
    Movie movieDatabase;
  public static   ArrayList<Movie> widgetMovies;
    String mid;
    String poster_string;
     Movie movie;
    int id;
    ArrayList<Trailer> trailers;
    ProgressDialog progressDialog;
    Button button;
    String url;
    boolean state=true;
    TrailerAdapter adapter;
    Toolbar toolbar;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        progressDialog=new ProgressDialog(this);

        toolbar=(Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Detail Display");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        database=MovieDatabase.getInstance(this);


               recyclerView=(RecyclerView) findViewById(R.id.trailer_list);
               recyclerView.setHasFixedSize(true);
               recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

          button=(Button) findViewById(R.id.ff);
        title = (TextView) findViewById(R.id.title);
        overview = (TextView) findViewById(R.id.overview);
        release = (TextView) findViewById(R.id.release);
        vote = (TextView) findViewById(R.id.vote);
        poster = (ImageView) findViewById(R.id.poster);

        Intent intent = getIntent();
        ArrayList<Movie> movies = intent.getParcelableArrayListExtra("list");
        int position=intent.getIntExtra("position",0);
        Log.v(TAG,"position"+position);

        movie=movies.get(position);

        title.setText(movie.getOriginal_title());
        overview.setText(movie.getOverview());
        release.setText(movie.getRelease_date());
        vote.setText(movie.getVote_average());

        mTitle=movie.getOriginal_title();
        mOverview=movie.getOverview();
        mRelease=movie.getRelease_date();
        mVote=movie.getVote_average();
         poster_string = movie.getPoster_path();

        checkFavorite();


        String value = "w185";
        String base_url = "http://image.tmdb.org/t/p/";
        final String full_url = base_url + value + "/" + poster_string;

        Picasso.get().load(full_url).into(poster);

         mid=movie.getId();
        url = "http://api.themoviedb.org/3/movie/" + mid + "/videos?api_key=89f2f5dacd021ea83c2b2aff5a2b3db7";

        //Log.v(TAG,"id"+id);




      button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state){

                    movieDatabase=new Movie(mTitle,mVote,poster_string,mOverview,mRelease,mid);

                    Log.v("DetailActivity","dddddd"+movie.getPoster_path());
                    database.taskDao().insertMovieTask(movieDatabase);
                    id=movieDatabase.getIdTable();
                    Log.v(TAG,"pos"+movieDatabase.getIdTable());
                    //database.taskDao().insertMovieTask(movie);
                    Toast.makeText(DetailActivity.this, "add to vaforite", Toast.LENGTH_LONG).show();
                   state=false;
                }

                else{
                    movieDatabase=new Movie(id,mTitle,mVote,poster_string,mOverview,mRelease,mid);

                    // movieDatabase=new Movie(id,mTitle,mVote,poster_string,mOverview,mRelease,mid);
                    database.taskDao().deleteMovieTask(movieDatabase);
                    button.setBackground(getDrawable(R.drawable.like));
                    Log.v(TAG,"pos"+movieDatabase.getIdTable());
                    Toast.makeText(DetailActivity.this, "delete from vaforite", Toast.LENGTH_LONG).show();
                    state=true;
                }

            }
        });


        progressDialog.setMessage("Please wait");
        progressDialog.setTitle("download trailer");
        progressDialog.show();
        getTrailer();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkFavorite() {

        MovieModel movieModel= ViewModelProviders.of(this).get(MovieModel.class);
        LiveData<List<Movie>> listLiveData=movieModel.getListLiveData();

        listLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {

                for (int i = 0; i < movies.size(); i++) {

                    if (mid.equals(movies.get(i).getId())) {
                        state = false;
                        button.setBackground(getDrawable(R.drawable.heart));
                        //Log.v(TAG,"pos"+movieDatabase.getIdTable());
                        Toast.makeText(DetailActivity.this, "this movie already exist", Toast.LENGTH_LONG).show();
                        break;
                    }
                }


            }
        });


    }



    void getTrailer(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(RetrofitInterface.baseUrl)
                .addConverterFactory(GsonConverterFactory.create()).build();

        RetrofitInterface retrofitInterface=retrofit.create(RetrofitInterface.class);

        Call<TrailerList> trailerListCall=retrofitInterface.getTrailers(mid,RetrofitInterface.apiKey);

        trailerListCall.enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(Call<TrailerList> call, Response<TrailerList> response) {
                trailers= response.body().getTrailers();
               if(trailers.size()==0){
                   progressDialog.hide();
               }else {
                   progressDialog.hide();
                   Log.v(TAG, "trailer size" + trailers.size());
                   adapter = new TrailerAdapter(getApplicationContext(), trailers, DetailActivity.this);
                   recyclerView.setAdapter(adapter);
               }

            }

            @Override
            public void onFailure(Call<TrailerList> call, Throwable t) {

            }
        });


    }

    @Override
    public void onItemClick(int position) {
        String key=trailers.get(position).getKey();
        progressDialog.show();
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        String url = "https://www.youtube.com/watch?v=" + key;
        Uri uri=Uri.parse(url);
        intent.setData(uri);
        progressDialog.dismiss();
        startActivity(intent);



    }
}

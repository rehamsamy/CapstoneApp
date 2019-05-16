package com.example.mohamed.capstoneapp;

import android.support.annotation.CallSuper;
import android.support.annotation.StringRes;

import com.example.mohamed.capstoneapp.PersonData.PeopleMovieList;
import com.example.mohamed.capstoneapp.Trailers.TrailerList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    String apiKey = "89f2f5dacd021ea83c2b2aff5a2b3db7";
    String baseUrl = "http://api.themoviedb.org";

    @GET("/3/movie/{popular}?")
    Call<MovieList> getPopular(@Path("popular")String popular, @Query("api_key")String api_key);


    @GET("/3/movie/{top_rated}?")
     Call<MovieList> getTopRated(@Path("top_rated") String topRated, @Query("api_key") String api_key);

    @GET("/3/movie/{upcoming}?")
    Call<MovieList> getUpComing(@Path("upcoming")String upComing, @Query("api_key")String api_key);


    @GET("/3/movie/{now_playing}?")
    Call<MovieList> getNowPlaying(@Path("now_playing")String nowPlaying, @Query("api_key")String api_key);


    @GET("/3/person/{popular}?")
    Call<PersonList> getPerson(@Path("popular")String person, @Query("api_key")String api_key);



    @GET("/3/person/{popular}?")
    Call<PeopleMovieList> getPeopleMovie(@Path("popular")String person,@Query("api_key") String api_key);

    //url = "http://api.themoviedb.org/3/movie/" + mid + "/videos?api_key=89f2f5dacd021ea83c2b2aff5a2b3db7";


    @GET("3/movie/{id}/videos?")
    Call<TrailerList> getTrailers(@Path("id")String id , @Query("api_key")String api_key);
}
package com.example.projectmobile;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.projectmobile.data.DogAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson goonInstance;
    private static DogAPI dogApiInstance;
    private static SharedPreferences sharedPreferencesInstance;

    public static Gson getGoonInstance(){
        if(goonInstance == null){
            goonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return goonInstance;
    }

    public static DogAPI getDogApiInstance(){
        if(dogApiInstance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGoonInstance()))
                    .build();

            dogApiInstance = retrofit.create(DogAPI.class);
        }
        return dogApiInstance;
    }

    public static SharedPreferences getSharedPreferencesInstance(Context context) {
        if(sharedPreferencesInstance == null){
            sharedPreferencesInstance = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }
}

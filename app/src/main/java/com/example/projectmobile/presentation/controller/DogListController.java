package com.example.projectmobile.presentation.controller;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.projectmobile.Constants;
import com.example.projectmobile.Singletons;
import com.example.projectmobile.presentation.model.Dog;
import com.example.projectmobile.presentation.model.RestDogResponse;
import com.example.projectmobile.presentation.view.DogListActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DogListController {

    private SharedPreferences sharedPreferences;
    private Gson gson;
    private DogListActivity view;


    public DogListController(SharedPreferences sharedPreferences, Gson gson, DogListActivity view) {
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
        this.view = view;
    }

    public void onStart(){

        List<Dog> dogList = getDataFromCache();

        if(dogList != null){
            view.showList(dogList);
        }else {
            MakeAPICall();
        }
    }

    private void MakeAPICall(){

        Call<RestDogResponse> call = Singletons.getDogApiInstance().getBreedResponse();
        call.enqueue(new Callback<RestDogResponse>() {
            @Override
            public void onResponse(Call<RestDogResponse> call, Response<RestDogResponse> response) {
                if(response.isSuccessful() && (response.body() != null)){
                    List<Dog> dogList = response.body().getInformation();
                    Toast.makeText(view.getApplicationContext(),"API success", Toast.LENGTH_SHORT).show();
                    saveList(dogList);
                    view.showList(dogList);
                }
            }

            @Override
            public void onFailure(Call<RestDogResponse> call, Throwable t) {
                view.error();
            }
        });
    }

    private void saveList(List<Dog> dogList) {
        String jsonString = gson.toJson(dogList);

        sharedPreferences
                .edit()
                .putString(Constants.KEY_DOG_LIST, jsonString)
                .apply();
        Toast.makeText(view.getApplicationContext(),"Dog List Saved", Toast.LENGTH_SHORT).show();

    }

    private List<Dog> getDataFromCache() {
        String jsonDog = sharedPreferences.getString(Constants.KEY_DOG_LIST, null);

        if(jsonDog == null){
            return null;
        }else{
            Type listType = new TypeToken<List<Dog>>(){}.getType();
            return gson.fromJson(jsonDog, listType);
        }
    }

    public void onItemClick(Dog dog){
        view.navigateToDetails(dog);
    }

}

package com.example.projectmobile.presentation.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmobile.R;
import com.example.projectmobile.Singletons;
import com.example.projectmobile.presentation.controller.DogListController;
import com.example.projectmobile.presentation.model.Dog;

import java.util.List;

public class DogListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private DogListController dogListController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doglist);

        dogListController = new DogListController(
                Singletons.getSharedPreferencesInstance(getApplicationContext()),
                Singletons.getGoonInstance(),
                this
        );
        dogListController.onStart();
    }

    public void error() {
        Toast.makeText(getApplicationContext(),"API error", Toast.LENGTH_SHORT).show();
    }


    public void showList(List<Dog> dogList){
         recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

         mAdapter = new ListAdapter(dogList, getApplicationContext(), new ListAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Dog item) {
                dogListController.onItemClick(item);
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    public void navigateToDetails(Dog dog) {
        Intent myIntent = new Intent(DogListActivity.this, DetailActivity.class);
        DogListActivity.this.startActivity(myIntent);

    }


}
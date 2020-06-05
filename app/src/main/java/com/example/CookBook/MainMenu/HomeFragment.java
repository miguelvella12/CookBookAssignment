package com.example.CookBook.MainMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.CookBook.ListRecipe.ListActivity;
import com.example.CookBook.R;

public class HomeFragment extends Fragment {
    //For demonstration purposes, categories are hardcoded, and not saved in a table of a database
    ImageButton salads;
    ImageButton desserts;
    ImageButton datenight;
    ImageButton breakfast;
    ImageButton christmas;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        //
        Button showall = (Button)view.findViewById(R.id.button);
        showall.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewAllRecipes(v);
            }
        });
        //
        salads = (ImageButton)view.findViewById(R.id.salads);
        salads.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                viewRecipes("Salads");
            }
        });
        //
        desserts = (ImageButton)view.findViewById(R.id.desserts);
        desserts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRecipes("Desserts");
            }
        });
        //
        datenight = (ImageButton) view.findViewById(R.id.datenight);
        datenight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRecipes("Date Night");
            }
        });
        //
        breakfast = (ImageButton) view.findViewById(R.id.breakfast);
        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRecipes("Date Night");
            }
        });
        //
        christmas = (ImageButton)view.findViewById(R.id.christmas);
        christmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewRecipes("Christmas");
            }
        });
        return view;
    }

    //view all recipes in db
    public void viewAllRecipes(View v)
    {
        Intent single = new Intent(getActivity(), ListActivity.class);
        single.putExtra("category", "All");
        startActivity(single);
    }

    //view recipes by selected category
    public void viewRecipes(String category)
    {
        //here we will pas category to another activity
        Intent single = new Intent(getActivity(), ListActivity.class);
        single.putExtra("category", category);
        startActivity(single);
    }
}

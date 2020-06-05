package com.example.CookBook.ListRecipe;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


import com.example.CookBook.R;
import com.example.CookBook.RecipeShow.SingleRecipeFragment;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public ListAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from((parent.getContext()))
                .inflate(R.layout.recclerview_listitem, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ListItem listItem = listItems.get(position);

            //to show these properties for each item in a list
            holder.textViewHead.setText(listItem.getHead());
            holder.textViewDesc.setText(listItem.getDesc());
            holder.textViewId.setText(listItem.getId());
            holder.textViewDifficulty.setText(listItem.getDifficulty());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }



    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        //declaring view holder class which is to be injected in the recycler view.
        public TextView textViewHead;
        public TextView textViewDesc;
        public TextView textViewId;
        public TextView textViewDifficulty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHead = (TextView) itemView.findViewById(R.id.textViewHead);
            textViewDesc= (TextView) itemView.findViewById(R.id.textViewDesc);
            textViewId= (TextView) itemView.findViewById(R.id.recipeID);
            textViewDifficulty = (TextView) itemView.findViewById(R.id.text_view_difficulty);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            String id =textViewId.getText().toString();
            Toast.makeText(view.getContext(), id, Toast.LENGTH_SHORT).show();
               openListFragment(id);
        }


        public void  openListFragment(String id){
            //when user clicks on if of the recipes in the list, a new fragment opens
            Fragment fragment = new SingleRecipeFragment();

            FragmentManager fragmentManager = ((ListActivity) context).getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);

            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


            Bundle bundle=new Bundle();
            //we need to pass the id of the chosen recipe to then get that recipe from the db by that id
            bundle.putString("id", id);
            //set FragmentClass Arguments
            //pass arguments to fragment for later reference
            fragment.setArguments(bundle);

//            Fragment selectedFragment = new ListFragment();

//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    selectedFragment).commit();
        }

    }
}

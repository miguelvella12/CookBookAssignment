package com.example.CookBook.UploadRecipe;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.example.CookBook.R;
import com.example.CookBook.SqLite.SQLiteHelper;
import com.example.CookBook.login_register.login;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class UploadActivity extends AppCompatActivity {
        //upload is spit in 4 fragments


    Fragment frag = new Fragment();
    Fragment fragment1;
    Fragment fragment2;
    Fragment fragment3;
    Fragment fragment4;
    ArrayList<String> ingredientListMain;
    EditText editName, editInstructions;
    Spinner editCategory, editDifficulty, editTime;
    String name, ingredients, instructions, category, time, difficulty;
    byte[] img;
    ImageView imageView;
    SQLiteHelper myDb;
    Button next;
    Button back;
    String userId;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_upload);
            myDb = new SQLiteHelper(this);

           back = findViewById(R.id.back);
           back.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    openPrevFragment();
                }
            });
           back.setText("Cancel");

            SharedPreferences settings = getSharedPreferences(login.MyPREFERENCES, Context.MODE_PRIVATE);
           userId = settings.getString("idKey", "");

            next = findViewById(R.id.next);
            next.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    openNextFragment();
                }
            });
            ingredientListMain =  new ArrayList<String>();


            //initializing each fragment so as to keep data in them when going between on fragment and the other
            fragment1 = new Create1Fragment();
            fragment2 = new Create2Fragment();
            fragment3 = new Create3Fragment();
            fragment4 = new Create4Fragment();

              //load fragment 1 upon launching activity
            if (savedInstanceState != null)
            {
                frag = getSupportFragmentManager().getFragment(savedInstanceState, "frag");
            }
            else
                loadFragment(fragment1);




            //to initialize first fragment
            //openAddFragment();
        }
        //method to open next fragment when button is clicked
        public void openNextFragment(){
            Fragment fragment = null;

            //apart from switching fragment data inputed in current fragment is stored here so as to be available when creating a recipe
            if ( frag == fragment1) {
                //set name and category and go to frag2
                editName = (EditText) frag.getActivity().findViewById(R.id.editText);
                editCategory = (Spinner) frag.getActivity().findViewById(R.id.spinner1) ;
                 name = editName.getText().toString();
                 category = editCategory.getSelectedItem().toString();
                back.setText("Back");
                fragment = fragment2;
            } else if (frag == fragment2) {
                //get all ingredients and go to frag3
                if(ingredientListMain.size()!=0)
                     ingredients = getAllIngredients();
                fragment = fragment3;
            } else if (frag == fragment3) {
                //get instructions and go to frag4
                editInstructions = (EditText) frag.getActivity().findViewById(R.id.editTex2);
                instructions = editInstructions.getText().toString();
                next.setText("Add");
                fragment = fragment4;
            }else if(frag == fragment4){
                //get time and difficulty and try to add recipe.
                editDifficulty = (Spinner) frag.getActivity().findViewById(R.id.difficulty) ;
                difficulty= editDifficulty.getSelectedItem().toString();
                editTime = (Spinner)frag.getActivity().findViewById(R.id.time);
                time = editTime.getSelectedItem().toString();

                imageView= (ImageView)frag.getActivity().findViewById(R.id.imageView);

                //Following code checks all fields in fragments to see if they are filled before recipe is created
                //check if all fields have been filled prior to attempting to create a recipe
                //for future apart from giving an error message in toast, take user to the fragment where data is missing
                if(imageView.getDrawable() != null)
                   img = imageViewToByte(imageView);
                else {
                    img = null;
                    Toast.makeText(UploadActivity.this,"Please select an image",Toast.LENGTH_LONG).show();
                }
                if((name.length() == 0) || (instructions.length() == 0)) {
                    if(name.length() == 0)
                        Toast.makeText(UploadActivity.this,"Please give a name to the recipe",Toast.LENGTH_LONG).show();
                    if(instructions.length() == 0)
                        Toast.makeText(UploadActivity.this,"Instructions are empty. Please fill" +
                                "in some instructions",Toast.LENGTH_LONG).show();
                }else{

                    save();
                    finish();
                }
            }
            if (fragment != null)
             loadFragment(fragment);
        }

        //method to transform list of ingredients in one string for saving in db
    //note that user id not allowed to insert comma in ingredient so as not to disrupt the flow of converting to a string
        public String getAllIngredients(){
            String allIngredients = "";
            for(String ing : ingredientListMain){

                     allIngredients+= (ing +", ");
            }
            //to remove last comma and space
            return allIngredients.substring(0,allIngredients.length()-2);
        }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //Method for saving recipe into database
    public boolean save(){
        boolean isInserted = myDb.insertDataRecipe(name,
                ingredients,
                instructions, time, difficulty, category, img, userId );
        if(isInserted == true)
            Toast.makeText(UploadActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(UploadActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();

        return isInserted;
    }


    //method to open previous fragment when button is clicked
    public void openPrevFragment(){

        Fragment fragment = null;
            //changing button text from back to cancel or from add to next depending on which fragment user is currently on.
        if ( frag == fragment1) {
            finish();
        } else if (frag == fragment2) {
            back.setText("Cancel");
            fragment = fragment1;
        } else if (frag == fragment3) {
            fragment = fragment2;
        }else if (frag == fragment4) {
            fragment = fragment3;
            next.setText("Next");
        }

        loadFragment(fragment);
    }

    //method to load the needed fragment but keep the previous fragment for possible future reference
    private boolean loadFragment(Fragment fragment)
    {
        if (fragment != null)
        {
            getSupportFragmentManager().beginTransaction().hide(fragment1).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            getSupportFragmentManager().beginTransaction().show(fragment).commit();

            frag = fragment;
            return true;
        }
        return false;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "Frag", frag);
    }



    //unused method
    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = UploadActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

}



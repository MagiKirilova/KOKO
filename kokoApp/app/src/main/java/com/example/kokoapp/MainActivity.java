package com.example.kokoapp;

import static android.view.View.GONE;
import static androidx.constraintlayout.widget.Constraints.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    SQLiteDatabase database;
    DataBaseHelper dataBaseHelper;
    TextView h1, h2;
    ImageView imageView;
    ArrayList<String> eng = new ArrayList<>();
    ArrayList<String> bg = new ArrayList<>();
    ArrayList<String> cat = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_Layout);
        navigationView = findViewById(R.id.navigationView);
        h1 = findViewById(R.id.h1);
        h2 = findViewById(R.id.h2);
        imageView = findViewById(R.id.dog_icon);

        dataBaseHelper = new DataBaseHelper(this);

        database = dataBaseHelper.getReadableDatabase();

        if(dataBaseHelper.checkIsEmpty()){
            insertToTable("Summer","Лято", "seasons" );
            insertToTable("Winter","Зима", "seasons");
            insertToTable("Spring","Пролет", "seasons");
            insertToTable("Autumn","Есен", "seasons");
            insertToTable("Mother","Майка", "family");
            insertToTable("Grandfather","Дядо", "family");
            insertToTable("Brother","Брат", "family");
            insertToTable("Uncle","Чичо", "family");
            insertToTable("Grandmother","Баба", "family");
            insertToTable("Father","Баща", "family");
            insertToTable("Sandwich","Сандвич", "food");
            insertToTable("Cake","Торта", "food");
            insertToTable("Salad","Салата", "food");
            insertToTable("Burger","Бургер", "food");
            insertToTable("Pizza","Пица", "food");
            insertToTable("Fries","Картофки", "food");
            insertToTable("Soccer","Футбол", "sport");
            insertToTable("Tennis","Тенис", "sport");
            insertToTable("Volleyball","Волейбол", "sport");
            insertToTable("Swimming","Плуване", "sport");
            insertToTable("Basketball","Баскетбол", "sport");

            dataBaseHelper.printTableData("words",eng, bg, cat);
        }
        else {
          Log.d(TAG, "already inserted table");
        }

        // If needed to clear table
        //database.delete("words", null, null);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.menu,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                h1.setVisibility(View.GONE);
                h2.setVisibility(View.GONE);
                imageView.setVisibility(GONE);

                switch (item.getItemId()){

                    case R.id.seasons:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Toast.makeText(MainActivity.this, "Сезони", Toast.LENGTH_SHORT).show();

                        fragmentR(new SeasonsFragment());
                        break;

                    case R.id.family:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Toast.makeText(MainActivity.this, "Семейство", Toast.LENGTH_SHORT).show();

                        fragmentR(new FamilyFragment());
                        break;


                    case R.id.food:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Toast.makeText(MainActivity.this, "Храна", Toast.LENGTH_SHORT).show();
                        fragmentR(new FoodFragment());
                        break;


                    case R.id.sport:
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Toast.makeText(MainActivity.this, "Спорт", Toast.LENGTH_SHORT).show();
                        fragmentR(new SportFragment());
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }

                return true;
            }
        });


    }

    private void fragmentR(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();

    }

    private void insertToTable(String eng, String bg, String cat)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("bg", bg);
        contentValues.put("eng", eng);
        contentValues.put("category", cat);
        database.insert("words","eng", contentValues);

    }

}
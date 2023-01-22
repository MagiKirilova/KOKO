package com.example.kokoapp;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodFragment extends Fragment {

    SQLiteDatabase database;
    DataBaseHelper dataBaseHelper;
    ArrayList<String> eng = new ArrayList<>();
    ArrayList<String> bg = new ArrayList<>();
    ArrayList<String> cat = new ArrayList<>();
    int count_g = 1;
    int[] food  = {R.drawable.sandwich, R.drawable.cake, R.drawable.salad, R.drawable.burger, R.drawable.pizza, R.drawable.fries};

    public FoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dataBaseHelper = new DataBaseHelper(getActivity());

        database = dataBaseHelper.getReadableDatabase();

        dataBaseHelper.printTableData("words",eng, bg, cat);

        View view = inflater.inflate(R.layout.fragment_food, container, false);
        Button button_show = view.findViewById(R.id.button);
        TextView en_word = view.findViewById(R.id.en_word);
        ImageView word_icon = view.findViewById(R.id.word_icon);
        ImageButton button_next = view.findViewById(R.id.next);
        ImageButton button_back = view.findViewById(R.id.back);
        TextView bg_word = view.findViewById(R.id.bg_word);

        int foodIndex = cat.indexOf("food");
        Log.d(TAG, "index" + foodIndex);

        en_word.setText(eng.get(foodIndex));
        bg_word.setText(bg.get(foodIndex));

        bg_word.setVisibility(View.INVISIBLE);
        button_back.setVisibility(View.GONE);


        button_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bg_word.setVisibility(View.VISIBLE);
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count_g == food.length-1) {
                    button_next.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Последна думичка!", Toast.LENGTH_SHORT).show();
                }

                if(count_g < food.length)
                {
                    Toast.makeText(getActivity(), count_g + 1 + "/" + food.length, Toast.LENGTH_SHORT).show();
                    button_back.setVisibility(View.VISIBLE);
                    changeImage(count_g, word_icon);
                    addWordToSeason(en_word,bg_word, count_g+foodIndex);
                    bg_word.setVisibility(View.INVISIBLE);
                }
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count_g == 2) {
                    changeImage(0,word_icon);
                    en_word.setText(eng.get(count_g+foodIndex-2));
                    bg_word.setText(bg.get(count_g+foodIndex-2));
                    count_g = count_g - 1;
                    Toast.makeText(getActivity(),count_g  + "/" + food.length, Toast.LENGTH_SHORT).show();
                    button_back.setVisibility(View.GONE);
                    bg_word.setVisibility(View.INVISIBLE);
                }
                else {
                    changeImage(count_g-2,word_icon);
                    en_word.setText(eng.get(count_g+foodIndex-2));
                    bg_word.setText(bg.get(count_g+foodIndex-2));
                    count_g = count_g - 1;
                    bg_word.setVisibility(View.INVISIBLE);
                    if(button_next.GONE == 8) button_next.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), count_g + 1 + "/" + food.length, Toast.LENGTH_SHORT).show();
                }
                //if (count_g == seasons.length) {
                //   button_next.setVisibility(View.GONE);
                //   Toast.makeText(getActivity(), "Няма повече думички!", Toast.LENGTH_SHORT).show();
                // }
            }
        });

        return view;
    }

    private void addWordToSeason (TextView enw, TextView bgw, int count)
    {
        enw.setText(eng.get(count));
        bgw.setText(bg.get(count));

        count_g = count_g + 1;
    }

    private void changeImage (int count, ImageView image)
    {
        image.setImageResource(food[count]);
    }
}
package com.example.kokoapp;

import static androidx.constraintlayout.widget.Constraints.TAG;

import android.content.ContentValues;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SeasonsFragment extends Fragment {
    SQLiteDatabase database;
    DataBaseHelper dataBaseHelper;
    ArrayList<String> eng = new ArrayList<>();
    ArrayList<String> bg = new ArrayList<>();
    ArrayList<String> cat = new ArrayList<>();
    int count_g = 1;
    int[] seasons  = {R.drawable.sun2, R.drawable.snowflake2, R.drawable.spring, R.drawable.tree};

    public SeasonsFragment() {
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

        //Log.d(TAG, "x1: " + eng.get(0) + bg.get(0) + cat.get(0));
        View view = inflater.inflate(R.layout.fragment_seasons, container, false);
        Button button_show = view.findViewById(R.id.button);
        TextView en_word = view.findViewById(R.id.en_word);
        ImageView word_icon = view.findViewById(R.id.word_icon);
        ImageButton button_next = view.findViewById(R.id.next);
        ImageButton button_back = view.findViewById(R.id.back);
        TextView bg_word = view.findViewById(R.id.bg_word);

        en_word.setText(eng.get(0));
        bg_word.setText(bg.get(0));

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
                if (count_g == seasons.length-1) {
                    button_next.setVisibility(View.GONE);
                   Toast.makeText(getActivity(), "Последна думичка!", Toast.LENGTH_SHORT).show();
                }

                if(count_g < seasons.length)
                {
                    Toast.makeText(getActivity(), count_g + 1 + "/" + seasons.length, Toast.LENGTH_SHORT).show();
                    button_back.setVisibility(View.VISIBLE);
                    changeImage(count_g, word_icon);
                    addWordToSeason(en_word,bg_word, count_g);
                    bg_word.setVisibility(View.INVISIBLE);
                }

            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count_g == 2) {
                    changeImage(0,word_icon);
                    en_word.setText(eng.get(count_g-2));
                    bg_word.setText(bg.get(count_g-2));
                    count_g = count_g - 1;
                    bg_word.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), count_g  + "/" + seasons.length, Toast.LENGTH_SHORT).show();
                    button_back.setVisibility(View.GONE);
                }
                else {
                    changeImage(count_g-2,word_icon);
                    en_word.setText(eng.get(count_g-2));
                    bg_word.setText(bg.get(count_g-2));
                    count_g = count_g - 1;
                    bg_word.setVisibility(View.INVISIBLE);
                    if(button_next.GONE == 8) button_next.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), count_g + 1 + "/" + seasons.length, Toast.LENGTH_SHORT).show();
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
        image.setImageResource(seasons[count]);
    }

}
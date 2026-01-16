    package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

    public class MainActivity extends AppCompatActivity {

    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    View addPanel;
    EditText editCityName;
    Button btnAddCity;
    Button btnConfirmAdd;
    Button btnDeleteCity;

    int selectedPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);

        addPanel = findViewById(R.id.addPanel);
        editCityName = findViewById(R.id.editCityName);
        btnAddCity = findViewById(R.id.btnAddCity);
        btnConfirmAdd = findViewById(R.id.btnConfirmAdd);
        btnDeleteCity = findViewById(R.id.btnDeleteCity);

        String[] cities = { "Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Delhi" };
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, R.id.content_view, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPos = position;
            cityList.setItemChecked(position, true);
        });

        btnAddCity.setOnClickListener(v -> {
            if (addPanel.getVisibility() == View.VISIBLE) {
                addPanel.setVisibility(View.GONE);
            } else {
                addPanel.setVisibility(View.VISIBLE);
                editCityName.setText("");
                editCityName.requestFocus();
            }
        });

        btnConfirmAdd.setOnClickListener(v -> {
            String name = editCityName.getText().toString().trim();
            if (name.isEmpty()) return;

            for (String s : dataList) {
                if (s.equalsIgnoreCase(name)) {
                    addPanel.setVisibility(View.GONE);
                    return;
                }
            }

            dataList.add(name);
            cityAdapter.notifyDataSetChanged();
            addPanel.setVisibility(View.GONE);
        });

        btnDeleteCity.setOnClickListener(v -> {
            if (selectedPos == -1 || selectedPos >= dataList.size()) return;

            dataList.remove(selectedPos);
            cityAdapter.notifyDataSetChanged();
            cityList.clearChoices();
            selectedPos = -1;
        });
    }
}
package com.example.alarm5;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmSettingsActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Button btnSave, btnCancel;


    ToggleButton toggleSunday, toggleMonday, toggleTuesday, toggleWednesday, toggleThursday, toggleFriday, toggleSaturday;
    private ArrayList<Integer> selectedDays = new ArrayList<>(); // 用于存储选择的天数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alarm_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 获取 ToggleButton 的引用
        toggleSunday = findViewById(R.id.toggleSunday);
        toggleMonday = findViewById(R.id.toggleMonday);
        toggleTuesday = findViewById(R.id.toggleTuesday);
        toggleWednesday = findViewById(R.id.toggleWednesday);
        toggleThursday = findViewById(R.id.toggleThursday);
        toggleFriday = findViewById(R.id.toggleFriday);
        toggleSaturday = findViewById(R.id.toggleSaturday);

        timePicker = findViewById(R.id.timePicker);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> {
            // Save alarm settings and return to main screen
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            String time = String.format("%02d:%02d", hour, minute);



            if (toggleSunday.isChecked()) selectedDays.add(Calendar.SUNDAY);
            if (toggleMonday.isChecked()) selectedDays.add(Calendar.MONDAY);
            if (toggleTuesday.isChecked()) selectedDays.add(Calendar.TUESDAY);
            if (toggleWednesday.isChecked()) selectedDays.add(Calendar.WEDNESDAY);
            if (toggleThursday.isChecked()) selectedDays.add(Calendar.THURSDAY);
            if (toggleFriday.isChecked()) selectedDays.add(Calendar.FRIDAY);
            if (toggleSaturday.isChecked()) selectedDays.add(Calendar.SATURDAY);


            Intent resultIntent = new Intent();
            resultIntent.putIntegerArrayListExtra("days", selectedDays);
            resultIntent.putExtra("time", time);

            Log.d("TAG_AlarmSettings", time);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }


}




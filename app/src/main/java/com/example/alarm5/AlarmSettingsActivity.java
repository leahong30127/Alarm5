package com.example.alarm5;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AlarmSettingsActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Button btnSave, btnCancel;
    private ArrayList<String> selectedDays = new ArrayList<>(); // 选中的星期

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

        timePicker = findViewById(R.id.timePicker);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> {
            // Save alarm settings and return to main screen
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            String time = String.format("%02d:%02d", hour, minute);

            Intent resultIntent = new Intent();
            resultIntent.putStringArrayListExtra("days", selectedDays);
            resultIntent.putExtra("time", time);

            Log.d("TAG", time);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }


}




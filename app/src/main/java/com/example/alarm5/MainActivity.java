package com.example.alarm5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnAddAlarm;
   // private AlarmAdapter alarmAdapter;
    private List<Alarm> alarmList = new ArrayList<>(); //用于保存闹钟清单<Alarm>类型 里面有星期几，时间
    private static final String PREFS_NAME = "AlarmPrefs";
    private static final String KEY_ALARMS = "alarms";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 加载已保存的闹钟数据
        loadAlarms();


        btnAddAlarm = findViewById(R.id.btnAddAlarm);
        btnAddAlarm.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AlarmSettingsActivity.class);
            startForResult.launch(intent);
        });

    }

    // 注册 ActivityResultLauncher
    private ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            List<String> days = data.getStringArrayListExtra("days");
                            String time = data.getStringExtra("time");
                            Log.d("TAG1", time);

                            // 将新闹钟添加到列表并刷新 RecyclerView
                            Alarm newAlarm = new Alarm(days, time);
                            alarmList.add(newAlarm); // 将新闹钟添加到 alarmList
                            saveAlarms();
                            // alarmAdapter.addAlarm(newAlarm);
                        }
                    }
                }
            }
    );

    private void saveAlarms() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        List<String> alarmStrings = new ArrayList<>();

        for (Alarm alarm : alarmList) {
            alarmStrings.add(alarm.toString());
        }

        editor.putStringSet(KEY_ALARMS, new HashSet<>(alarmStrings));
        editor.apply();
        Log.d("TAG", "Write SharedPreferences");
    }

    // 从 SharedPreferences 加载闹钟数据
    private void loadAlarms() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Set<String> alarmStrings = prefs.getStringSet(KEY_ALARMS, new HashSet<>());

        alarmList.clear();
        for (String alarmString : alarmStrings) {
            alarmList.add(Alarm.fromString(alarmString));
        }

        Log.d("TAG", "read SharedPreferences");
        // 输出读取到的闹钟信息
        for (Alarm alarm : alarmList) {
            Log.d("TAG", alarm.toString());
        }
        ///alarmAdapter.notifyDataSetChanged();
    }


}


package com.example.alarm5;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnAddAlarm;
   // private AlarmAdapter alarmAdapter;
    private List<Alarm> alarmList = new ArrayList<>(); //用于保存闹钟清单<Alarm>类型 里面有星期几，时间
    private static final String PREFS_NAME = "AlarmPrefs";
    private static final String KEY_ALARMS = "alarms";
    private AlarmAdapter alarmAdapter;

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

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        alarmAdapter = new AlarmAdapter(alarmList);
        recyclerView.setAdapter(alarmAdapter);

        // 设置长按事件监听器
        alarmAdapter.setOnItemLongClickListener(position -> {
            // 从 alarmList 中删除项目
            alarmList.remove(position);
            alarmAdapter.notifyItemRemoved(position);

            // 保存更改后的闹钟数据
            saveAlarms();
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
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {

                            ArrayList<Integer> days = data.getIntegerArrayListExtra("days");
                            String time = data.getStringExtra("time");
                            Log.d("TAG1", time);
                            assert days != null;
                            Log.d("TAG2", String.valueOf(days));

                            // 将新闹钟添加到列表并刷新 RecyclerView
                             Alarm newAlarm = new Alarm(days, time);
                             alarmList.add(newAlarm); // 将新闹钟添加到 alarmList
                             saveAlarms();
                            alarmAdapter.notifyDataSetChanged();
                            // alarmAdapter.addAlarm(newAlarm);
                        }
                    }
                }
            }
    );

    private void saveAlarms() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        // 使用 Gson 将 alarmList 转换为 JSON 字符串
        Gson gson = new Gson();
        String json = gson.toJson(alarmList);

        editor.putString(KEY_ALARMS, json);
        editor.apply();
        Log.d("TAG", "Write SharedPreferences");
    }

    // 从 SharedPreferences 加载闹钟数据
    private void loadAlarms() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String json = prefs.getString(KEY_ALARMS, null);

        if (json != null) {
            // 使用 Gson 将 JSON 字符串转换回 List<Alarm>
            Gson gson = new Gson();
            List<Alarm> savedAlarms = gson.fromJson(json, new TypeToken<List<Alarm>>() {}.getType());
            alarmList.clear(); // 清除当前列表中的数据
            alarmList.addAll(savedAlarms); // 加载保存的闹钟数据
        } else {
            alarmList.clear(); // 如果没有数据则清空列表
        }

        Log.d("TAG", "Read SharedPreferences");
        alarmAdapter.notifyDataSetChanged(); // 通知适配器数据已更改
    }
}


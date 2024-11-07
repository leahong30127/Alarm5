package com.example.alarm5;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
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

    private static final int REQUEST_CODE_PICK_SOUND = 1;
    private Switch alarmSoundSwitch;
    private TextView selectedSoundTextView;
    private Uri selectedSoundUri;

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


        alarmSoundSwitch = findViewById(R.id.alarm_sound_switch);
        selectedSoundTextView = findViewById(R.id.selected_sound);

        // 设置默认闹钟声音状态
        alarmSoundSwitch.setChecked(true);
        //selectedSoundTextView.setText("未选择");

        // 如果闹钟声开关关闭，显示“未选择”
        alarmSoundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                selectedSoundTextView.setText("未选择");
                selectedSoundUri = null;
            }
        });





        Intent resultIntent = new Intent();
        resultIntent.putIntegerArrayListExtra("days", selectedDays);
        resultIntent.putExtra("time", time);

        Log.d("TAG_AlarmSettings", time);
        setResult(RESULT_OK, resultIntent);
        finish();
        });
    }
    // 单击选择声音栏时，打开文件选择器
    public void onChooseSoundClick(android.view.View view) {
        Log.d("TAG","onChooseSoundClick");
        if (alarmSoundSwitch.isChecked()) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            intent.setType("audio/*");
            startActivityForResult(intent, REQUEST_CODE_PICK_SOUND);
        } else {
            Toast.makeText(this, "请启用闹钟声", Toast.LENGTH_SHORT).show();
        }
    }

    // 处理用户选择的声音文件
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_PICK_SOUND) {
            Uri soundUri = data.getData();
            selectedSoundUri = soundUri;
            String soundName = getFileNameFromUri(soundUri);
            selectedSoundTextView.setText(soundName);
        }
    }

    // 从Uri中提取文件名
    private String getFileNameFromUri(Uri uri) {
        String[] projection = { MediaStore.Audio.Media.DISPLAY_NAME };
        try (android.database.Cursor cursor = getContentResolver().query(uri, projection, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
            }
        }
        return "未知音频";
    }

}




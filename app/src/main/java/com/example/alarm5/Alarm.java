package com.example.alarm5;

import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

public class Alarm {
    private List<String> days;
    private String time;

    public Alarm(List<String> days, String time) {
        this.days = days;
        this.time = time;
    }

    public List<String> getDays() {
        return days;
    }

    public String getTime() {
        return time;
    }


    // 将 Alarm 对象转换为字符串
    public String toString() {
        return TextUtils.join(",", days) + ";" + time;
    }

    // 从字符串解析出 Alarm 对象
    public static Alarm fromString(String str) {
        String[] parts = str.split(";");
        List<String> days = Arrays.asList(parts[0].split(","));
        String time = parts[1];
        return new Alarm(days, time);
    }
}

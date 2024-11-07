package com.example.alarm5;

import android.text.TextUtils;

import java.util.ArrayList;

public class Alarm {
    private ArrayList<Integer> days;
    private String time;

    public Alarm(ArrayList<Integer> days, String time) {
        this.days = days;
        this.time = time;
    }

    public ArrayList<Integer> getDays() {
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
        // 将 days 的字符串列表转换为 Integer 类型的 ArrayList
        ArrayList<Integer> days = new ArrayList<>();
        for (String day : parts[0].split(",")) {
            days.add(Integer.parseInt(day.trim())); // 转换为整数并添加到 days 中
        }
        String time = parts[1];
        return new Alarm(days, time);
    }

    public String getFormattedDays() {
        StringBuilder sb = new StringBuilder();
        String[] dayNames = {"日", "一", "二", "三", "四", "五", "六"};
        for (int day : days) {
            sb.append(dayNames[day-1]).append(" ");
        }
        return sb.toString().trim();
    }
}

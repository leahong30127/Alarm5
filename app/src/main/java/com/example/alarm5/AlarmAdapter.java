package com.example.alarm5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    private List<Alarm> alarmList;



    public AlarmAdapter(List<Alarm> alarmList) {
        this.alarmList = alarmList;
    }


    //定义点击事件接口
    private OnItemClickListener2 onItemClickListener;
    public interface OnItemClickListener2 {
        void onItemClick(int position);
    }

    //设置点击事件监听器
    public void setOnItemClickListener2(OnItemClickListener2 onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

     //定义长按接口
     private OnItemLongClickListener longClickListener;
    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }
//    private OnItemLongClickListener longClickListener;
//    public interface OnItemLongClickListener {
//        void onItemLongClick(int position);
//    }
//
//    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
//        this.longClickListener = listener;
//    }



    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        return new AlarmViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm alarm = alarmList.get(position);
        holder.alarmTime.setText(alarm.getTime());

        // 显示星期几
        String days = alarm.getFormattedDays();
        holder.alarmDays.setText(days);

        // 可以为开关设置初始状态或点击事件
        holder.alarmSwitch.setChecked(true);

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(position);
            }
        });

        // 设置长按事件
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(position);
                return true;  // 返回 true 表示事件已被处理
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return alarmList.size();
    }

    static class AlarmViewHolder extends RecyclerView.ViewHolder {
        TextView alarmTime, alarmDays;
        Switch alarmSwitch;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            alarmTime = itemView.findViewById(R.id.alarmTime);
            alarmDays = itemView.findViewById(R.id.alarmDays);
            alarmSwitch = itemView.findViewById(R.id.alarmSwitch);


        }
    }


}

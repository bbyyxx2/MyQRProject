package com.bbyyxx2.myqrproject.ui.setting.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bbyyxx2.myqrproject.Util.MMKVUtil;
import com.bbyyxx2.myqrproject.databinding.SettingItem2Binding;
import com.bbyyxx2.myqrproject.databinding.SettingItem3Binding;
import com.bbyyxx2.myqrproject.databinding.SettingItemBinding;
import com.bbyyxx2.myqrproject.ui.setting.model.SetData;

import java.util.ArrayList;
import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter {

    List<SetData> setDataList;
    Context context;

    public SettingAdapter(Context context, List<SetData> setDataList) {
        this.setDataList = setDataList;
        this.context = context;
    }

    public List<SetData> getSetDataList() {
        return setDataList;
    }

    class SettingViewHolder1 extends RecyclerView.ViewHolder{

        SettingItemBinding binding;

        public SettingViewHolder1(SettingItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class SettingViewHolder2 extends RecyclerView.ViewHolder{

        SettingItem2Binding binding;

        public SettingViewHolder2(SettingItem2Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class SettingViewHolder3 extends RecyclerView.ViewHolder{

        SettingItem3Binding binding;

        public SettingViewHolder3(SettingItem3Binding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == 1){
            SettingItemBinding binding = SettingItemBinding.inflate(layoutInflater, parent, false);
            return new SettingViewHolder1(binding);
        } else if (viewType == 2){
            SettingItem2Binding binding = SettingItem2Binding.inflate(layoutInflater, parent, false);
            return new SettingViewHolder2(binding);
        } else {
            SettingItem3Binding binding = SettingItem3Binding.inflate(layoutInflater, parent, false);
            return new SettingViewHolder3(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (holder instanceof SettingViewHolder1){

            ((SettingViewHolder1) holder).binding.setName.setText(setDataList.get(position).getTitle());

            boolean flag = MMKVUtil.getBoolean(setDataList.get(position).getKeyName());

            ((SettingViewHolder1) holder).binding.setSwitch.setChecked(flag);

            ((SettingViewHolder1) holder).binding.setSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    MMKVUtil.put(setDataList.get(position).getKeyName(), isChecked);
                }
            });

        } else if (holder instanceof SettingViewHolder2){
            ((SettingViewHolder2) holder).binding.setName.setText(setDataList.get(position).getTitle());
        } else if (holder instanceof SettingViewHolder3){
            ((SettingViewHolder3) holder).binding.setName.setText(setDataList.get(position).getTitle());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.setOnClick(v,position);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return setDataList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return setDataList.size();
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener{
        void setOnClick(View v, int pos);
        void setOnLongClick(View v, int pos);
    }
}

package com.bbyyxx2.myqrproject.ui.history.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.bbyyxx2.database.database.AppDatabase;
import com.bbyyxx2.database.entities.QRRecord;
import com.bbyyxx2.database.entities.ScanRecord;
import com.bbyyxx2.myqrproject.R;
import com.bbyyxx2.myqrproject.Util.ThreadUtil;
import com.bbyyxx2.myqrproject.Util.TimeUtil;
import com.bbyyxx2.myqrproject.databinding.ItemQrRecordBinding;
import com.bbyyxx2.myqrproject.databinding.ItemScanRecordBinding;
import com.bbyyxx2.myqrproject.ui.base.BaseActivity;
import com.bbyyxx2.myqrproject.ui.history.util.HistoryConstant;
import com.bbyyxx2.myqrproject.ui.image.ShowImageActivity;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsBuildBitmapOption;
import com.huawei.hms.ml.scan.HmsScan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class RecordAdapter<T> extends ListAdapter<T, RecordAdapter.RecordViewHolder> {

    private final Map<String, Integer> typeToLayoutMap = new HashMap<>();
    private String type;
    private Context mContext;

    private List<T> mList;

    public RecordAdapter(Context context, List<T> list, String type) {
        super(new DiffUtil.ItemCallback<T>() {
            @Override
            public boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull T oldItem, @NonNull T newItem) {
                return false;
            }
        });
        this.mContext = context;
        this.type = type;
        this.mList = list;
        // 初始化类型到布局的映射
        typeToLayoutMap.put(HistoryConstant.SCAN_RECORD, R.layout.item_scan_record);
        typeToLayoutMap.put(HistoryConstant.QR_RECORD, R.layout.item_qr_record);
    }

    private ViewBinding getBinding(int layoutId, ViewGroup parent) {
        switch (layoutId) {
            case R.layout.item_scan_record:
                return ItemScanRecordBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            case R.layout.item_qr_record:
                return ItemQrRecordBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            default:
                throw new IllegalArgumentException("Unsupported layout ID");
        }
    }

    // 具体的 RecordViewHolder 类型定义
    static class RecordViewHolder extends RecyclerView.ViewHolder {

        private ViewBinding binding;
        public RecordViewHolder(ViewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建并返回 RecordViewHolder 的实例
        ViewBinding viewBinding = getBinding(typeToLayoutMap.get(type), parent);
        return new RecordViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        // 在此处绑定数据到 ViewHolder
        if (holder.binding instanceof ItemQrRecordBinding){
            List<QRRecord> qrRecordList = (List<QRRecord>) mList;
            //内容转二维码
            int type = HmsScan.QRCODE_SCAN_TYPE;
            int width = 150;
            int height = 150;
            HmsBuildBitmapOption options = new HmsBuildBitmapOption.Creator().setBitmapBackgroundColor(Color.WHITE).setBitmapColor(Color.BLACK).setBitmapMargin(3).create();
            try {
                Bitmap qrBitmap = ScanUtil.buildBitmap(qrRecordList.get(position).getContent(), type, width, height, options);
                ImageView imageView = ((ItemQrRecordBinding) holder.binding).image;
                imageView.setImageBitmap(qrBitmap);
                //点击放大，带共享元素动画
                imageView.setOnClickListener(v -> {
                    Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext, imageView,"shareElement").toBundle();
                    Intent intent = new Intent(mContext, ShowImageActivity.class);
                    intent.putExtra("content", qrRecordList.get(position).getContent());
                    mContext.startActivity(intent, bundle);
                });
            } catch (Exception e){
                e.printStackTrace();
            }
            ((ItemQrRecordBinding) holder.binding).content.setText(qrRecordList.get(position).getContent());
            //时间戳转时间
            ((ItemQrRecordBinding) holder.binding).createTime.setText(TimeUtil.getFormat(qrRecordList.get(position).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        } else if (holder.binding instanceof ItemScanRecordBinding){
            List<ScanRecord> scanRecordList = (List<ScanRecord>) mList;
            ((ItemScanRecordBinding) holder.binding).content.setText(scanRecordList.get(position).getContent());
            ((ItemScanRecordBinding) holder.binding).createTime.setText(TimeUtil.getFormat(scanRecordList.get(position).getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        }
        //长按删除
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteRecord(holder.getBindingAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private void deleteRecord(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("是否删除该条记录？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    ThreadUtil.runInNewThread(() -> {
                        if (mList.get(position) instanceof QRRecord){
                            AppDatabase.instance.qrRecordDao().deleteQRRecord((QRRecord) mList.get(position));
                        } else if (mList.get(position) instanceof ScanRecord){
                            AppDatabase.instance.scanRecordDao().deleteScanRecord((ScanRecord) mList.get(position));
                        }
                        return null;
                    }).get();
                    mList.remove(position);
                    notifyItemRemoved(position);
                } catch (ExecutionException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).show();
    }
}


package com.bbyyxx2.myqrproject.ui.history;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bbyyxx2.database.database.AppDatabase;
import com.bbyyxx2.database.entities.QRRecord;
import com.bbyyxx2.database.entities.ScanRecord;
import com.bbyyxx2.myqrproject.Util.ThreadUtil;
import com.bbyyxx2.myqrproject.databinding.ActivityHistoryBinding;
import com.bbyyxx2.myqrproject.ui.base.BaseActivity;
import com.bbyyxx2.myqrproject.ui.history.adapter.RecordAdapter;
import com.bbyyxx2.myqrproject.ui.history.util.HistoryConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HistoryActivity extends BaseActivity<ActivityHistoryBinding, HistoryViewModel> {

    /**
     * 获取来源{@link com.bbyyxx2.myqrproject.ui.history.util.HistoryConstant}
     */
    private String type;
    private RecordAdapter adapter;

    @Override
    public void initView() {
        super.initView();

        binding.toolbar.setTitle("历史记录");
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        type = getIntent().getExtras().getString("type", "");

        if (HistoryConstant.SCAN_RECORD.equals(type)) {
            ThreadUtil.runInNewThread(() -> {
                List<ScanRecord> scanRecordList = AppDatabase.instance.scanRecordDao().getAllOrderByCreateTime();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new RecordAdapter<>(context, scanRecordList, type);
                        binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        binding.recyclerView.setAdapter(adapter);
                    }
                });
                return null;
            });
        } else if (HistoryConstant.QR_RECORD.equals(type)) {
            ThreadUtil.runInNewThread(() -> {
                List<QRRecord> qrRecordList = AppDatabase.instance.qrRecordDao().getAllOrderByCreateTime();
                runOnUiThread(() -> {
                    adapter = new RecordAdapter<>(context, qrRecordList, type);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    binding.recyclerView.setAdapter(adapter);
                });
                return null;
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // This ID represents the Home/Up button. In the case of this activity,
            // either showing the parent activity in a hierarchically structured app,
            // or exiting the app for top-level activities.
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

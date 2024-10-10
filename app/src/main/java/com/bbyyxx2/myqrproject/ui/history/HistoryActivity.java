package com.bbyyxx2.myqrproject.ui.history;

import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bbyyxx2.database.database.AppDatabase;
import com.bbyyxx2.database.entities.QRRecord;
import com.bbyyxx2.database.entities.ScanRecord;
import com.bbyyxx2.myqrproject.R;
import com.bbyyxx2.myqrproject.Util.ThreadUtil;
import com.bbyyxx2.myqrproject.databinding.ActivityHistoryBinding;
import com.bbyyxx2.myqrproject.ui.base.BaseActivity;
import com.bbyyxx2.myqrproject.ui.history.adapter.RecordAdapter;
import com.bbyyxx2.myqrproject.ui.history.util.HistoryConstant;

import java.util.List;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        if (HistoryConstant.SCAN_RECORD.equals(type)) {
            searchView.setQueryHint("请输入备注");
        } else if (HistoryConstant.QR_RECORD.equals(type)) {
            searchView.setQueryHint("请输入备注或内容");
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (HistoryConstant.SCAN_RECORD.equals(type)) {
                    ThreadUtil.runInNewThread(() -> {
                        List<ScanRecord> scanRecordList = AppDatabase.instance.scanRecordDao().selectScanRecordList(newText);
                        runOnUiThread(() -> {
                            adapter.setNewData(scanRecordList);
                        });
                        return null;
                    });
                } else if (HistoryConstant.QR_RECORD.equals(type)) {
                    ThreadUtil.runInNewThread(() -> {
                        List<QRRecord> qrRecordList = AppDatabase.instance.qrRecordDao().selectQRRecordList(newText);
                        runOnUiThread(() -> {
                            adapter.setNewData(qrRecordList);
                        });
                        return null;
                    });
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
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

package com.bbyyxx2.myqrproject.data;

import com.bbyyxx2.database.database.AppDatabase;
import com.bbyyxx2.database.entities.ScanRecord;
import com.bbyyxx2.myqrproject.Util.MMKVUtil;
import com.bbyyxx2.myqrproject.Util.ThreadUtil;
import com.bbyyxx2.myqrproject.ui.base.Constant;

/**
 * 扫码业务的 Repository 类
 * 负责统一管理数据来源（Room 数据库、MMKV 缓存、网络请求等）
 */
public class ScanRepository {

    /**
     * 保存扫码结果的逻辑
     * 将原本在 Fragment 中的业务逻辑迁移到这里，实现解耦
     * @param content 扫码获得的内容
     */
    public void saveScanRecord(String content) {
        // 1. 处理业务逻辑：检查保存开关是否开启
        if (MMKVUtil.getBoolean(Constant.LAST_SCAN_SWITCH, false)) {
            
            // 2. 数据持久化：更新 MMKV 缓存
            MMKVUtil.put(Constant.LAST_SCAN_CONTENT, content);
            
            // 3. 数据持久化：异步存入 Room 数据库
            // 注意：数据库操作不能在主线程进行，这里沿用原有的 ThreadUtil
            ThreadUtil.runInNewThread(() -> {
                AppDatabase.instance.scanRecordDao().insertScanRecord(new ScanRecord(content));
                return null;
            });
        }
    }
    
    // 以后如果需要获取历史记录，也可以在这里添加方法
    // public List<ScanRecord> getAllRecords() { ... }
}

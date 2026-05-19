package com.bbyyxx2.myqrproject.ui.setting;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceDataStore;

import com.bbyyxx2.myqrproject.Util.MMKVUtil;

import java.util.Set;

/**
 * 自定义 PreferenceDataStore，将 PreferenceFragmentCompat 的数据持久化对接到 MMKV
 * 
 * 作用：
 * 1. 替代 PreferenceFragmentCompat 默认的 SharedPreferences 存储方式
 * 2. 统一项目的数据持久化方案为 MMKV
 * 3. 避免同一配置项被重复写入 SharedPreferences 和 MMKV，造成数据冗余
 * 
 * 使用方式：
 * 在 SettingsFragment 的 onCreatePreferences() 中调用：
 * getPreferenceManager().setPreferenceDataStore(new MMKVPreferenceDataStore());
 */
public class MMKVPreferenceDataStore extends PreferenceDataStore {

    // ==================== Boolean 类型 ====================
    
    @Override
    public void putBoolean(String key, boolean value) {
        MMKVUtil.put(key, value);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return MMKVUtil.getBoolean(key, defValue);
    }

    // ==================== String 类型 ====================
    
    @Override
    public void putString(String key, @Nullable String value) {
        if (value == null) {
            MMKVUtil.removeKey(key);
        } else {
            MMKVUtil.put(key, value);
        }
    }

    @Nullable
    @Override
    public String getString(String key, @Nullable String defValue) {
        return MMKVUtil.getString(key, defValue);
    }

    // ==================== StringSet 类型 ====================
    
    @Override
    public void putStringSet(String key, @Nullable Set<String> values) {
        if (values == null) {
            MMKVUtil.removeKey(key);
        } else {
            MMKVUtil.putSet(key, values);
        }
    }

    @Nullable
    @Override
    public Set<String> getStringSet(String key, @Nullable Set<String> defValues) {
        Set<String> result = MMKVUtil.getStringSet(key);
        return (result == null || result.isEmpty()) ? defValues : result;
    }

    // ==================== Int 类型 ====================
    
    @Override
    public void putInt(String key, int value) {
        MMKVUtil.put(key, value);
    }

    @Override
    public int getInt(String key, int defValue) {
        return MMKVUtil.getInt(key, defValue);
    }

    // ==================== Long 类型 ====================
    
    @Override
    public void putLong(String key, long value) {
        MMKVUtil.put(key, value);
    }

    @Override
    public long getLong(String key, long defValue) {
        return MMKVUtil.getLong(key, defValue);
    }

    // ==================== Float 类型 ====================
    
    @Override
    public void putFloat(String key, float value) {
        MMKVUtil.put(key, value);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return MMKVUtil.getFloat(key, defValue);
    }
}

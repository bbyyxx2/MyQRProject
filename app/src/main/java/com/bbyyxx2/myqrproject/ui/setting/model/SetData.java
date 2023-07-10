package com.bbyyxx2.myqrproject.ui.setting.model;

import java.io.Serializable;

public class SetData implements Serializable {

    /**
     * 标签
     */
    String label;

    /**
     * 设置行的名称用于展示
     */
    String title;

    /**
     * 用于跳转型进行跳转
     */
    Class toClass;

    /**
     * 用于开关型查询开关状态
     */
    String keyName;

    /**
     * 设置行的类型
     * 1为开关型的布局
     * 2为跳转型的布局
     * 3只是显示的布局
     */
    int type;

    public SetData(String label, String title, Class toClass, String keyName, int type) {
        this.label = label;
        this.title = title;
        this.toClass = toClass;
        this.keyName = keyName;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public Class getToClass() {
        return toClass;
    }

    public void setToClass(Class toClass) {
        this.toClass = toClass;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

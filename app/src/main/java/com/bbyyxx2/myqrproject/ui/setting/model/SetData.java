package com.bbyyxx2.myqrproject.ui.setting.model;

import java.io.Serializable;

/**
 * 设置界面的动态布局控制对象
 */
public class SetData implements Serializable {

    /**
     * 开关型的布局
     */
    public static final int SWITCH_TYPE = 1;

    /**
     * 跳转型的布局
     */
    public static final int CLASS_TYPE = 2;

    /**
     * 文本显示的布局
     */
    public static final int TEXT_TYPE = 3;

    /**
     * 标签
     */
    private String label;

    /**
     * 设置行的名称用于展示
     */
    private String title;

    /**
     * 用于跳转型进行跳转
     */
    private Class toClass;

    /**
     * 用于开关型查询开关状态
     */
    private String keyName;

    /**
     * 设置行的类型
     * {@link #SWITCH_TYPE} or
     * {@link #CLASS_TYPE} or
     * {@link #TEXT_TYPE}
     */
    private int type;

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

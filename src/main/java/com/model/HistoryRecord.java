package com.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "HISTORY_RECORD")
public class HistoryRecord {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 期数
     */
    private String code;

    /**
     * 开奖日期
     */
    private String date;

    /**
     * 周日期
     */
    private String week;

    /**
     * 红色数字
     */
    private String red;

    /**
     * 蓝色数字
     */
    private String blue;

    /**
     * 蓝色数字2
     */
    private String blue2;

    /**
     * 奖金
     */
    private String sales;

    /**
     * 奖池
     */
    private String poolmoney;

    /**
     * 结果
     */
    private String content;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 响应报文
     */
    private byte[] msg;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取期数
     *
     * @return code - 期数
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置期数
     *
     * @param code 期数
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取开奖日期
     *
     * @return date - 开奖日期
     */
    public String getDate() {
        return date;
    }

    /**
     * 设置开奖日期
     *
     * @param date 开奖日期
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 获取周日期
     *
     * @return week - 周日期
     */
    public String getWeek() {
        return week;
    }

    /**
     * 设置周日期
     *
     * @param week 周日期
     */
    public void setWeek(String week) {
        this.week = week;
    }

    /**
     * 获取红色数字
     *
     * @return red - 红色数字
     */
    public String getRed() {
        return red;
    }

    /**
     * 设置红色数字
     *
     * @param red 红色数字
     */
    public void setRed(String red) {
        this.red = red;
    }

    /**
     * 获取蓝色数字
     *
     * @return blue - 蓝色数字
     */
    public String getBlue() {
        return blue;
    }

    /**
     * 设置蓝色数字
     *
     * @param blue 蓝色数字
     */
    public void setBlue(String blue) {
        this.blue = blue;
    }

    /**
     * 获取蓝色数字2
     *
     * @return blue2 - 蓝色数字2
     */
    public String getBlue2() {
        return blue2;
    }

    /**
     * 设置蓝色数字2
     *
     * @param blue2 蓝色数字2
     */
    public void setBlue2(String blue2) {
        this.blue2 = blue2;
    }

    /**
     * 获取奖金
     *
     * @return sales - 奖金
     */
    public String getSales() {
        return sales;
    }

    /**
     * 设置奖金
     *
     * @param sales 奖金
     */
    public void setSales(String sales) {
        this.sales = sales;
    }

    /**
     * 获取奖池
     *
     * @return poolmoney - 奖池
     */
    public String getPoolmoney() {
        return poolmoney;
    }

    /**
     * 设置奖池
     *
     * @param poolmoney 奖池
     */
    public void setPoolmoney(String poolmoney) {
        this.poolmoney = poolmoney;
    }

    /**
     * 获取结果
     *
     * @return content - 结果
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置结果
     *
     * @param content 结果
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取响应报文
     *
     * @return msg - 响应报文
     */
    public byte[] getMsg() {
        return msg;
    }

    /**
     * 设置响应报文
     *
     * @param msg 响应报文
     */
    public void setMsg(byte[] msg) {
        this.msg = msg;
    }
}
package com.vo;

import com.annotation.ExcelTitle;

import java.util.Objects;

public class ShopVo implements Comparable<ShopVo> {
    @ExcelTitle("店名")
    private String name;
    @ExcelTitle("星级")
    private String star;
    @ExcelTitle("区域")
    private String address;
    @ExcelTitle("详细地址")
    private String detailAddress;
    @ExcelTitle("联系电话")
    private String phone;
    @ExcelTitle("评价数")
    private String commentCount;
    @ExcelTitle("明细地址URL")
    private String detailUrl;
    @ExcelTitle("原始描述")
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ShopVo{" +
                "name='" + name + '\'' +
                ", star='" + star + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public int compareTo(ShopVo o) {
        if (Double.parseDouble(this.star) - Double.parseDouble(o.star) < 0) {
            return -1;
        } else if (Double.parseDouble(this.star) - Double.parseDouble(o.star) == 0) {
            return 0;
        }
        return  1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ShopVo shopVo = (ShopVo) o;
        return name.equals(shopVo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

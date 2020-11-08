package com.vo;

import com.annotation.ExcelTitle;

import java.util.Objects;

public class ShopVo implements Comparable<ShopVo> {
    @ExcelTitle("店名")
    private String name;
    @ExcelTitle("星级")
    private String star;
    @ExcelTitle("地址")
    private String address;

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

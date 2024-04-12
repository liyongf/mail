package com.liyf.boot.entity;

public class TestCase {
    String dapartName;
    String postName;
    String pastType;
    String quantity;

    String deduction;

    public String getDapartName() {
        return dapartName;
    }

    public void setDapartName(String dapartName) {
        this.dapartName = dapartName;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPastType() {
        return pastType;
    }

    public void setPastType(String pastType) {
        this.pastType = pastType;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDeduction() {
        return deduction;
    }

    public void setDeduction(String deduction) {
        this.deduction = deduction;
    }

    @Override
    public String toString() {
        return "TestCase{" +
                "dapartName='" + dapartName + '\'' +
                ", postName='" + postName + '\'' +
                ", pastType='" + pastType + '\'' +
                ", quantity='" + quantity + '\'' +
                ", deduction='" + deduction + '\'' +
                '}';
    }
}

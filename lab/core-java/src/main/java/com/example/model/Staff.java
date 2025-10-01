package com.example.model;

import java.math.BigDecimal;

public class Staff extends User{
    private String department;
    private Float salary;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }
}

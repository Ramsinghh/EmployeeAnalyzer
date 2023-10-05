package com.ram.model;

import io.github.rushuat.ocell.annotation.FieldName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class Employee {

    @FieldName("Position ID")
    public String positionId;

    @FieldName("Position Status")
    public String positionStatus;

    @FieldName("Time")
    public String time;

    @FieldName("Time Out")
    public String timeOut;

    @FieldName("Timecard Hours (as Time)")
    public String timeHour;

    @FieldName("Pay Cycle Start Date")
    public Date payCycleStart;

    @FieldName("Pay Cycle End Date")
    public Date payCycleEnd;

    @FieldName("Employee Name")
    public Date emp_name;

    @FieldName("File Number")
    public Date file_no;
}

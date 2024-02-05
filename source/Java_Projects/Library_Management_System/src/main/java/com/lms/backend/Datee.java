package com.lms.backend;

/**
 *
 * @author Innov8
 */
public class Datee {
    private int day;
    private int month;
    private int year;

    Datee(int YYYY, int MM, int DD){
        this.day = DD;
        this.month = MM;
        this.year= YYYY;
    }
    int getDay(){return this.day;}
    int getMonth(){return this.month;}
    int getYear(){return this.year;}
}

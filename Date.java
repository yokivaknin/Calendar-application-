package sample;

import java.io.Serializable;
import java.util.Objects;

public class Date implements Serializable {
    private int _day;
    private int _month;
    private int _year;

    public Date(int day, int month, int year){
        _day = day;
        _month = month;
        _year = year;
    }

    public int getDay(){
        return _day;
    }

    public int getMonth(){
        return _month;
    }

    public int getYear(){
        return _year;
    }

    public void setDay(int day){
        _day = day;
    }

    public void setMonth(int month){
        _month = month;
    }

    public void setYear(int year){
        _year = year;
    }

    @Override
    public boolean equals(Object obj){

        if ((obj instanceof Date) &&
                (((Date) obj).getDay() == _day) &&
                (((Date) obj).getMonth() == _month) &&
                (((Date) obj).getYear() == _year))
            return true;
        else
            return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(_day,_month,_year);
    }
}

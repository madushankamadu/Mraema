package com.example.mraema.MedicineReminder.db;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class MedicineAlarm implements Comparable<MedicineAlarm>{
    private long id;
    private int hour;
    private String minute;
    private String pilName;
    private String doseQuantity;
    private String doseUnit;
    private String dateString;
    private int alarmId;
    private Boolean sunday,monday,tuesday,wednsday,thursday, friday,saterday, everyday;

    public MedicineAlarm() {
    }






    public MedicineAlarm(long id, int hour, String minute, String pilName, String doseQuantity, String doseUnit, String dateString, int alarmId, Boolean sunday, Boolean monday, Boolean tuesday, Boolean wednsday, Boolean thursday, Boolean friday, Boolean saterday,Boolean everyday, List<Long> ids, boolean[] dayOfWeek) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.pilName = pilName;
        this.doseQuantity = doseQuantity;
        this.doseUnit = doseUnit;
        this.dateString = dateString;
        this.alarmId = alarmId;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednsday = wednsday;
        this.thursday = thursday;
        this.friday = friday;
        this.saterday = saterday;
        this.ids = ids;
        this.dayOfWeek = dayOfWeek;
        this.everyday = everyday;
    }

    public Boolean getSunday() {
        return sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }

    public Boolean getMonday() {
        return monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    public Boolean getTuesday() {
        return tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    public Boolean getWednsday() {
        return wednsday;
    }

    public void setWednsday(Boolean wednsday) {
        this.wednsday = wednsday;
    }

    public Boolean getThursday() {
        return thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    public Boolean getFriday() {
        return friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    public Boolean getSaterday() {
        return saterday;
    }

    public void setSaterday(Boolean saterday) {
        this.saterday = saterday;
    }

    private List<Long> ids = new LinkedList<Long>();
    private boolean[] dayOfWeek = new boolean[7];

    public boolean[] getDayOfWeek() {
        return dayOfWeek;
    }

    public List<Long> getIds() {
        return Collections.unmodifiableList(ids);
    }

    public void addId(long id) {
        ids.add(id);
    }

    public void setDayOfWeek(boolean[] dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getPilName() {
        return pilName;
    }

    public void setPilName(String pilName) {
        this.pilName = pilName;
    }

    public String getDoseQuantity() {
        return doseQuantity;
    }

    public void setDoseQuantity(String doseQuantity) {
        this.doseQuantity = doseQuantity;
    }

    public String getDoseUnit() {
        return doseUnit;
    }

    public void setDoseUnit(String doseUnit) {
        this.doseUnit = doseUnit;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dataString) {
        this.dateString = dataString;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public void setEveryday(boolean everyday) {
        this.everyday = everyday;
    }

    public boolean getEveryday() {
        return everyday;
    }




    @Override
    public int compareTo(MedicineAlarm medicineAlarm) {

        if (hour < medicineAlarm.getHour())
            return -1;
        else if (hour > medicineAlarm.getHour())
            return 1;
        else{
            if (Integer.parseInt(minute) < Integer.parseInt(medicineAlarm.getMinute()))
                return -1;
            else if (Integer.parseInt(minute) > Integer.parseInt(medicineAlarm.getMinute()))
                return 1;
            else
                return 0;
        }
    }

    public String getStringTime(){

        return String.format(Locale.getDefault(),"%d:%s",hour,minute);
    }

    public String getDose(){
        return String.format(Locale.getDefault(),"%s %s", doseQuantity,doseUnit);
    }


}

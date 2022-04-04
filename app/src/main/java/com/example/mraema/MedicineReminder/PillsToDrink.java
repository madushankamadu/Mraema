package com.example.mraema.MedicineReminder;

import com.example.mraema.MedicineReminder.db.MedicineAlarm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PillsToDrink {

    private String pillName;
    private long pillId;
    private List<MedicineAlarm> medicineAlarms = new LinkedList<>();

    public PillsToDrink() {
    }

    public PillsToDrink(String pillName, long pillId) {
        this.pillName = pillName;
        this.pillId = pillId;
    }

    public String getPillName() {
        return pillName;
    }

    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public long getPillId() {
        return pillId;
    }

    public void setPillId(long pillId) {
        this.pillId = pillId;
    }

    public void addAlarm(MedicineAlarm medicineAlarm){
        medicineAlarms.add(medicineAlarm);
        Collections.sort(medicineAlarms);
    }

}

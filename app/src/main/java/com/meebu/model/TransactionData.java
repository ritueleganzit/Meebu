package com.meebu.model;

import java.util.ArrayList;

/**
 * Created by eleganz on 20/3/19.
 */

public class TransactionData {
    String date;
    ArrayList<PerDayTransaction> arrayList;

    public TransactionData(String date, ArrayList<PerDayTransaction> arrayList) {
        this.date = date;
        this.arrayList = arrayList;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<PerDayTransaction> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<PerDayTransaction> arrayList) {
        this.arrayList = arrayList;
    }
}

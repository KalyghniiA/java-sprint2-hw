package data_base;


import data_base.data_per_year.DataPerYear;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataBase {
    private final Map<Integer, DataPerYear> database;

    public DataBase() {
        database = new HashMap<>();
    }

    public void addInfo(List<String> info, int year) {
        if (!database.containsKey(year)) {
            database.put(year, new DataPerYear());
        }

        database.get(year).addInfo(info);
    }

    public void addInfo(List<String> info, int year, String month) {
        if (!database.containsKey(year)) {
            database.put(year, new DataPerYear());
        }

        database.get(year).addInfo(info, month);
    }

    public void getInfoForYear(int year) {
        if (!database.containsKey(year)) {
            System.out.println("За данный год нет информации");
        } else {
            database.get(year).getInfoForYear();
        }
    }

    public void getInfoForMonths(int year) {
        if (!database.containsKey(year)) {
            System.out.println("Данных за этот год нет");
        }

        database.get(year).getInfoForMonths();
    }

    public void verificationFiles(int year) {
        if (!database.containsKey(year)) {
            System.out.println("За данный год нет информации");
            return;
        }

        database.get(year).verificationFiles();
    }
}

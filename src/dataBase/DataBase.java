package dataBase;


import dataBase.dataPerYear.DataPerYear;
import dataBase.dataPerYear.dataMonth.transaction.TransactionMonth;
import dataBase.dataPerYear.dataYear.transaction.TransactionYear;

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

    public void removeInfo(int year, String month) {
        if (!database.containsKey(year)) {
            System.out.println("Данных за этот год нет");
            return;
        }

        database.get(year).removeInfoForMonths(month);
    }

    public Map<String, TransactionYear> getInfoForYear(int year) {
        if (!database.containsKey(year)) {
            System.out.println("За данный год нет информации");
            return null;
        }
        return database.get(year).getInfoForYear();

    }

    public Map<String, List<TransactionMonth>> getInfoForMonths(int year) {
        if (!database.containsKey(year)) {
            System.out.println("Данных за этот год нет");
            return null;
        }

        return database.get(year).getInfoForMonths();
    }


}

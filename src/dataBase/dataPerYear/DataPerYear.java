package dataBase.dataPerYear;

import dataBase.dataPerYear.dataMonth.DataMonth;
import dataBase.dataPerYear.dataMonth.transaction.TransactionMonth;
import dataBase.dataPerYear.dataYear.DataYear;
import dataBase.dataPerYear.dataYear.transaction.TransactionYear;

import java.util.*;

public class DataPerYear {
    private final DataYear dataYear;
    private final Set<DataMonth> dataMonths;

    public DataPerYear() {
         this.dataYear = new DataYear();
         this.dataMonths = new HashSet<>();
    }

    public void addInfo(List<String> info) {
        dataYear.addInfo(info);
    }

    public void addInfo(List<String> info, String month) {
        DataMonth data = new DataMonth(info, month);

        if (dataMonths.contains(data)) {
            System.out.println("Данный файл уже был сохранен");
            return;
        }
        dataMonths.add(data);
    }

    public Map<String, List<TransactionMonth>> getInfoForMonths() {
        Map<String, List<TransactionMonth>> result = new HashMap<>();
        dataMonths.forEach(month -> result.put(month.getMonth(), month.getProducts()));
        return result;
    }

    public Map<String, TransactionYear> getInfoForYear() {
        return dataYear.getInfo();
    }

    public void removeInfoForMonths(String month) {
        DataMonth data = null;
        for (DataMonth dataMonth: dataMonths) {
            if (dataMonth.getMonth().equals(month)) {
                data = dataMonth;
            }
        }
        dataMonths.remove(data);
    }
}

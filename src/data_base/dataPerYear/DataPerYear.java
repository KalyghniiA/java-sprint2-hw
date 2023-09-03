package data_base.dataPerYear;

import data_base.dataPerYear.dataMonth.DataMonth;
import data_base.dataPerYear.dataYear.DataYear;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataPerYear {
    private final DataYear dataYear;
    private final Set<DataMonth> dataMonths;

    public DataPerYear() {
         this.dataYear = new DataYear();
         this.dataMonths = new HashSet<>();
    }

    public void addInfo(List<String> info) {

    }

    public void addInfo(List<String> info, String month) {
        DataMonth data = new DataMonth(info, month);

        if (dataMonths.contains(data)) {
            System.out.println("Данный файл уже был сохранен");
            return;
        }
        dataMonths.add(data);
    }

    public void getInfo() {
        for (var dataMonth: dataMonths) {
            System.out.println(dataMonth.getMonth());
            for (var product: dataMonth.getProducts()) {
                System.out.println(product);
            }
        }
    }
}

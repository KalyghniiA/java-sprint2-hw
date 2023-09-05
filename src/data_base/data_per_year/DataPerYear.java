package data_base.data_per_year;

import data_base.data_per_year.data_month.DataMonth;
import data_base.data_per_year.data_year.DataYear;

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

    public void getInfoForMonths() {
        dataMonths.forEach(info -> System.out.println(info.getInfo()));
    }

    public void getInfoForYear() {
        System.out.println(dataYear.getInfo());
    }
}

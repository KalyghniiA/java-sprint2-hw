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

    public void verificationFiles() {
        if (dataYear.getSizeMonthList() == 0) {
            System.out.println("В файле за год не было информации или файл не считан!");
            return;
        }

        if (dataMonths.isEmpty()) {
            System.out.println("В файлах за месяц не было информации или файлы не считаны!");
            return;
        }

        if (dataYear.getSizeMonthList() > dataMonths.size()) {
            System.out.println("Были считаны не все файлы за месяц или файлов за месяц не хватает!");
            return;
        }

        if (dataYear.getSizeMonthList() < dataMonths.size()) {
            System.out.printf("В файле за год не хватает информации за %s месяц!%n", dataMonths.size() - dataYear.getSizeMonthList());
            return;
        }

        int allRevenueToYearFile = dataYear.getAllRevenue();
        int allExpenseToYearFile = dataYear.getAllExpense();

        int allRevenueToMonthsFiles = dataMonths.stream().map(DataMonth::getAllRevenue).reduce(Integer::sum).orElse(0);
        int allExpenseToMonthsFiles = dataMonths.stream().map(DataMonth::getAllExpenses).reduce(Integer::sum).orElse(0);

        if (allExpenseToMonthsFiles == allExpenseToYearFile && allRevenueToMonthsFiles == allRevenueToYearFile) {
            System.out.println("Расходы и доходы сошлись");

        } else if (allExpenseToMonthsFiles != allExpenseToYearFile && allRevenueToMonthsFiles != allRevenueToYearFile) {
            System.out.println("Расходы и доходы не сходятся");
        } else if (allExpenseToMonthsFiles != allExpenseToYearFile) {
            System.out.println("Расходы не сходятся");
        } else {
            System.out.println("Доходы не сходятся");
        }

    }
}

package data_base.data_per_year.data_year;

import exceptions.FormatterException;
import util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataYear {
    private final Map<String, MonthInfo> listMonth;

    public DataYear() {
        listMonth = new HashMap<>();
    }

    public void addInfo(List<String> info) {
        info.stream()
                .map(elem -> elem.split(","))
                .forEach(elem -> {
                    try {
                        String month = Util.formattingMonth(elem[0]);
                        if (!listMonth.containsKey(month)) {
                            listMonth.put(month, new MonthInfo(month));
                        }

                        if (Boolean.parseBoolean(elem[2])) {
                            listMonth.get(month).setExpense(Integer.parseInt(elem[1]));
                        } else {
                            listMonth.get(month).setRevenue(Integer.parseInt(elem[1]));
                        }

                    } catch (FormatterException e) {
                        System.out.println("В файле указан неверный месяц, проверьте файл");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("В файле не хватает информации, проверьте данные");
                    }//просьба посмотреть!!
                });


    }

    public String getInfo() {
        if (listMonth.isEmpty()) {
            return "Данных пока нет, требуется сначала считать файл";
        }

        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, MonthInfo> info: listMonth.entrySet()) {
            result.append(String.format("Информация за %s месяц%n", info.getKey()))
                    .append(String.format("Доходы: %s%n", info.getValue().getRevenue()))
                    .append(String.format("Расходы: %s%n", info.getValue().getExpense()))
                    .append("---////---");
        }

        return result.toString();
    }
    public int getSizeMonthList() {
        return listMonth.size();
    }

    public int getAllRevenue() {
       return listMonth.values().stream().map(MonthInfo::getRevenue).reduce(Integer::sum).orElse(0);
    }

    public int getAllExpense() {
        return listMonth.values().stream().map(MonthInfo::getExpense).reduce(Integer::sum).orElse(0);
    }

    // Требуется посмотреть!!!
    static class MonthInfo {
        private String month;
        private int expense = 0;
        private int revenue = 0;

        MonthInfo(String month) {
            this.month = month;
        }

        public int getExpense() {
            return expense;
        }

        public void setExpense(int expense) {
            this.expense = expense;
        }

        public int getRevenue() {
            return revenue;
        }

        public void setRevenue(int revenue) {
            this.revenue = revenue;
        }
    }
}


package dataBase.dataPerYear.dataYear;

import dataBase.dataPerYear.dataYear.transaction.TransactionYear;
import exceptions.FormatterException;
import util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataYear {
    private final Map<String, TransactionYear> listMonth;

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
                            listMonth.put(month, new TransactionYear(month));
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
                    }
                });


    }

    public Map<String, TransactionYear> getInfo() {
        return new HashMap<>(listMonth);
    }

}


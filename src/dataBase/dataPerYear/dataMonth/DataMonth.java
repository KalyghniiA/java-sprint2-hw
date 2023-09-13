package dataBase.dataPerYear.dataMonth;

import dataBase.dataPerYear.dataMonth.transaction.TransactionMonth;

import java.util.*;

public class DataMonth {
    private final String month;
    private final List<TransactionMonth> transactionMonths;
    //нужен совет по реализации
    public DataMonth(List<String> info, String month) {
        this.month = month;
        transactionMonths = new ArrayList<>();
        for (String product: info) {
            String[] productInfo = product.split(",");
            transactionMonths.add(new TransactionMonth(productInfo[0], Boolean.parseBoolean(productInfo[1]), Integer.parseInt(productInfo[2]), Integer.parseInt(productInfo[3])));
        }
    }

    public String getMonth() {
        return month;
    }

    public List<TransactionMonth> getProducts() {
        return transactionMonths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataMonth)) return false;
        DataMonth dataMonth = (DataMonth) o;
        return Objects.equals(month, dataMonth.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month);
    }
}

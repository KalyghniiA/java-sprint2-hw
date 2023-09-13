package dataBase.dataPerYear.dataMonth.transaction;

import java.util.Objects;

public class TransactionMonth {
    private String productName;
    private Boolean isExpense;
    private Integer quantity;
    private Integer price;

    public TransactionMonth(String productName, Boolean isExpense, Integer quantity, Integer price) {
        this.productName = productName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.price = price;
    }

    public TransactionMonth(String productName, String isExpense, String quantity, String price) {
        this.productName = productName;
        this.isExpense = Boolean.parseBoolean(isExpense);
        this.quantity = Integer.parseInt(quantity);
        this.price = Integer.parseInt(price);
    }

    public String getProductName() {
        return productName;
    }

    public Boolean isExpense() {
        return isExpense;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public int getFullPrice () {
        return price * quantity;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setExpense(Boolean expense) {
        isExpense = expense;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String createLineToFile() {
        return String.format("%s,%s,%s,%s", productName, isExpense, quantity, price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransactionMonth)) return false;
        TransactionMonth transactionMonth = (TransactionMonth) o;
        return Objects.equals(productName, transactionMonth.productName) && Objects.equals(isExpense, transactionMonth.isExpense) && Objects.equals(quantity, transactionMonth.quantity) && Objects.equals(price, transactionMonth.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, isExpense, quantity, price);
    }

    @Override
    public String toString() {
        return String.format("Наименование: %s%nТип: %s%nКоличество: %s%nЦена: %s%n",
                productName,
                isExpense ? "Расход" : "Доход",
                quantity,
                price);

    }
}

package data_base.data_per_year.data_month.product;

import java.util.Objects;

public class Product {
    private String productName;
    private Boolean isExpense;
    private Integer quantity;
    private Integer price;

    public Product(String productName, Boolean isExpense, Integer quantity, Integer price) {
        this.productName = productName;
        this.isExpense = isExpense;
        this.quantity = quantity;
        this.price = price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(productName, product.productName) && Objects.equals(isExpense, product.isExpense) && Objects.equals(quantity, product.quantity) && Objects.equals(price, product.price);
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

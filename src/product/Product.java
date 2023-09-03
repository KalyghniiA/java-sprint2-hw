package product;

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

    public Boolean getExpense() {
        return isExpense;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getPrice() {
        return price;
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
        return String.format("%s,%b,%s,%s", productName, isExpense, quantity, price);
    }
}

package data_base.dataPerYear.dataMonth;

import product.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataMonth {
    private final String month;
    private final List<Product> products;
    //нужен совет по реализации
    public DataMonth(List<String> info, String month) {
        this.month = month;
        products = new ArrayList<>();
        for (String product: info) {
            String[] productInfo = product.split(",");
            products.add(new Product(productInfo[0], Boolean.parseBoolean(productInfo[1]), Integer.parseInt(productInfo[2]), Integer.parseInt(productInfo[3])));
        }
    }

    public String getMonth() {
        return month;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataMonth)) return false;
        DataMonth dataMonth = (DataMonth) o;
        return Objects.equals(month, dataMonth.month) && Objects.equals(products, dataMonth.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, products);
    }
}

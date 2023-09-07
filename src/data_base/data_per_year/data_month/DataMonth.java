package data_base.data_per_year.data_month;

import data_base.data_per_year.data_month.product.Product;

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

    public String getInfo() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("Отчет за %s месяц: %n", month));

        for (Product product: products) {
            result.append(product.toString());
        }

        return result.append("---///---").toString();
    }

    public int getAllRevenue() {
        return products.stream().filter(product -> !product.isExpense()).map(Product::getFullPrice).reduce(Integer::sum).orElse(0);
    }

    public int getAllExpenses() {
        return products.stream().filter(Product::isExpense).map(Product::getFullPrice).reduce(Integer::sum).orElse(0);
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

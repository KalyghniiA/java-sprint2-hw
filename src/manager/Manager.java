package manager;

import dataBase.DataBase;
import dataBase.dataPerYear.dataMonth.transaction.TransactionMonth;
import dataBase.dataPerYear.dataYear.transaction.TransactionYear;
import exceptions.FormatterException;
import parser.Parser;
import util.Constant;
import util.Util;
import writer.Writer;

import java.io.File;
import java.util.*;

public class Manager {
    private final Scanner sc;
    private final DataBase base;

    public Manager(Scanner sc, DataBase base) {
        this.sc = sc;
        this.base = base;
    }

    public void readInfoForMonth()  {
        try {
            System.out.println("Укажите год, за который нужна информация");
            int year = sc.nextInt();
            System.out.println("Укажите месяц, за который нужна информация");
            String month = Util.formattingMonth(sc.next());

            File file = new File(Util.createPathToCSV(year, month));

            if (!file.exists()) {
                System.out.println("За данный месяц нет информации");
                return;
            }
            if (readInfo(file, year, month)) {
                System.out.println("Данные успешно сохранены");
            }

        } catch (FormatterException e) {
            System.out.println(e.getMessage());
            readInfoForMonth();
        } catch (InputMismatchException e) {
            System.out.println("Год введен в неверном формате, попробуйте снова");
            sc.next();
            readInfoForMonth();
        }

    }

    public void readInfoAllMonthToYear() {
        try {
            System.out.println("Укажите год, за который считываем информацию?");
            int year = sc.nextInt();
            int iterator = 0;

            for (var i = 1; i <= 12; i++) {
                String month = Util.formattingMonth(String.valueOf(i));
                File file = new File(Util.createPathToCSV(year, month));
                if (!file.exists()) {
                    iterator++;
                    continue;
                }
                if (!readInfo(file, year, month)) {
                    iterator++;
                }
            }

            System.out.println(iterator == 12
                    ? "За данный год нет информации"
                    : String.format("За %s год была доступна информация по %s месяцам, данные сохранены", year, 12 - iterator));
        } catch (InputMismatchException e) {
            System.out.println("Год введен в неверном формате, попробуйте снова");
            sc.next();
            readInfoAllMonthToYear();
        } catch (FormatterException e) {
            System.out.println(e.getMessage());
            readInfoAllMonthToYear();
        }
    }

    public void readInfoForYear() {
        try {
            System.out.println("За какой год считываем информацию?");
            int year = sc.nextInt();

            File file = new File(Util.createPathToCSV(year));


            if (!file.exists()) {
                System.out.println("За данный год нет информации");
                return;
            }

            if (readInfo(file, year)) {
                System.out.println("Данные успешно сохранены");
            }

        } catch (InputMismatchException e) {
            System.out.println("Год указан не в верном формате, попробуйте снова");
            sc.next();
            readInfoForYear();
        }
    }

    public void getInfoForMonths() {
        try {
            System.out.println("Введите год, за который хотите получить информацию");
            int year = sc.nextInt();
            Map<String, List<TransactionMonth>> info = base.getInfoForMonths(year);

            if (info == null) {
                return;
            }

            if (info.isEmpty()) {
                System.out.println("Данных пока нет, требуется сначала считать файл");
            }

            StringBuilder result = new StringBuilder();
            for (Map.Entry<String, List<TransactionMonth>> infoForMonth: info.entrySet()) {
                result.append(String.format("Месяц %s%n", infoForMonth.getKey()));
                infoForMonth.getValue().forEach(transactionMonth -> result.append(transactionMonth.toString()));
                result.append("---///---\n");
            }

            System.out.println(result);



        } catch (InputMismatchException e) {
            System.out.println("Год введен в неверном формате, попробуйте снова");
            sc.next();
            getInfoForYear();
        }
    }

    public void getInfoForYear() {
        System.out.println("Введите год, за который хотите получить информацию");
        int year = sc.nextInt();

        Map<String, TransactionYear> info = base.getInfoForYear(year);
        if (info == null) {
            return;
        }

        if (info.isEmpty()) {
            System.out.println("Данных пока нет, требуется сначала считать файл");
        }

        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, TransactionYear> infoMonth: info.entrySet()) {
            result.append(String.format("Информация за %s месяц%n", infoMonth.getKey()))
                    .append(String.format("Доходы: %s%n", infoMonth.getValue().getRevenue()))
                    .append(String.format("Расходы: %s%n", infoMonth.getValue().getExpense()))
                    .append("---////---");
        }

        System.out.println(result);

    }

    public void verificationFiles() {
        System.out.println("За какой год вы хотите проверить информацию");
        try {
            int year = sc.nextInt();

            Map<String, List<TransactionMonth>> infoMonths = base.getInfoForMonths(year);
            Map<String, TransactionYear> infoYear = base.getInfoForYear(year);

            if (infoYear == null || infoYear.isEmpty()) {
                System.out.println("В файле за год не было информации или файл не считан!");
                return;
            }

            if (infoMonths == null || infoMonths.isEmpty()) {
                System.out.println("В файлах за месяц не было информации или файлы не считаны!");
                return;
            }

            if (infoYear.size() > infoMonths.size()) {
                System.out.println("Были считаны не все файлы за месяц или файлов за месяц не хватает!");
                return;
            }

            if (infoYear.size() < infoMonths.size()) {
                System.out.printf("В файле за год не хватает информации за %s месяц!%n", infoMonths.size() - infoYear.size());
                return;
            }

            int allRevenueToYearFile = getFullValueToYearInfo(false, infoYear);
            int allExpenseToYearFile =  getFullValueToYearInfo(true, infoYear);

            int allRevenueToMonthsFiles = getFullValueToMonthInfo(false, infoMonths);
            int allExpenseToMonthsFiles = getFullValueToMonthInfo(true, infoMonths);

            if (allExpenseToMonthsFiles == allExpenseToYearFile && allRevenueToMonthsFiles == allRevenueToYearFile) {
                System.out.println("Расходы и доходы сошлись");
            } else if (allExpenseToMonthsFiles != allExpenseToYearFile && allRevenueToMonthsFiles != allRevenueToYearFile) {
                System.out.println("Расходы и доходы не сходятся");
            } else if (allExpenseToMonthsFiles != allExpenseToYearFile) {
                System.out.println("Расходы не сходятся");
            } else {
                System.out.println("Доходы не сходятся");
            }


        } catch (InputMismatchException e) {
            System.out.println("Год введен в неверном формате, попробуйте снова");
            sc.next();
            verificationFiles();
        }
    }

    private int getFullValueToYearInfo(boolean expense, Map<String, TransactionYear> info) {
        int sum = 0;
        for (Map.Entry<String, TransactionYear> infoForMonth: info.entrySet()) {
            sum += expense
                    ? infoForMonth.getValue().getExpense()
                    : infoForMonth.getValue().getRevenue();
        }

        return sum;
    }

    private int getFullValueToMonthInfo(boolean expense, Map<String, List<TransactionMonth>> info) {
        int sum = 0;
        for (Map.Entry<String, List<TransactionMonth>> infoMonth: info.entrySet()) {
            sum += infoMonth.getValue()
                    .stream()
                    .filter(line -> line.isExpense().equals(expense))
                    .map(TransactionMonth::getFullPrice)
                    .reduce(Integer::sum)
                    .orElse(0);
        }

        return sum;
    }

    private boolean readInfo(File file, int year, String month) {
        List<String> list = Parser.parseData(file);
        if (list.isEmpty()) {
            System.out.printf("Файл за %s.%s пуст. Проверьте правильность передачи данных%n", month, year);
            return false;
        }
        base.addInfo(list, year, month);
        return true;
    }

    private boolean readInfo(File file, int year) {
        List<String> list = Parser.parseData(file);
        if (list.isEmpty()) {
            System.out.printf("Файл за %s пуст. Проверьте правильность передачи данных%n", year);
            return false;
        }
        base.addInfo(list, year);
        return true;
    }

    // create
    public void createFileForMonth() {
        try {
            System.out.println("За какой год вы хотите создать отчет?");
            int year = sc.nextInt();
            System.out.println("За какой месяц вы хотите создать отчет?");
            String month = Util.formattingMonth(sc.next());

            File file = new File(Util.createPathToCSV(year, month));

            if (file.exists()) {
                System.out.println("Файл за данный месяц уже существует, для редактирования требуется вызвать соответствующий пункт");
                return;
            }

            boolean isContinue;
            List<String> products = new ArrayList<>();

            do {
                createProductLine(products);
                System.out.println("Хотите добавить еще?(да/нет)");
                String isGoOn = sc.next();

                isContinue = isGoOn.equalsIgnoreCase("да") || isGoOn.equalsIgnoreCase("yes");

            } while(isContinue);

            Writer.saveData(file, Constant.HEADER_MONTH_FILE.getConstant(), products);

            System.out.println("Данных файл требуется считать?(да/нет)");
            String isReading = sc.next();
            if (isReading.equalsIgnoreCase("да") || isReading.equalsIgnoreCase("yes")) {
                base.addInfo(products, year, month);
            }

        } catch (InputMismatchException e) {
            System.out.println("Указан неверный формат, попробуйте снова");
            sc.next();
            createFileForMonth();
        } catch (FormatterException e) {
            System.out.println(e.getMessage());
            System.out.println("Попробуйте снова");
            createFileForMonth();
        }
    }

    public void refactorFileToMonth() {
        System.out.println("За какой год вы хотите отредактировать файл?");
        int year;
        try {
            year = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Год введен в неверном формате, попробуйте снова");
            sc.next();
            return;
        }

        System.out.println("За какой месяц вы хотите отредактировать файл?");
        String month;
        try {
            month = Util.formattingMonth(sc.next());
        } catch (FormatterException e) {
            System.out.println(e.getMessage());
            return;
        }

        File file = new File(Util.createPathToCSV(year, month));

        if (!file.exists()) {
            System.out.println("За данную дату отсутствует файл, проверьте верность указанных данных");
            return;
        }

        List<String> linesToFile = Parser.parseData(file);
        Map<String, TransactionMonth> transactions = new HashMap<>();
        linesToFile.forEach(line -> {
            String[] transaction = line.split(",");
            transactions.put(transaction[0].toLowerCase(), new TransactionMonth(transaction[0], transaction[1], transaction[2], transaction[3]));
        });

        boolean isContinue;

        do {
            System.out.println("Какое действие вы хотите совершить?");
            System.out.println("Варианты: Добавить / Удалить / Редактировать");
            String value = sc.next();

            switch (value) {
                case "delete":
                case "удалить":
                    deleteTransitionToFile(transactions);
                    break;
                case "редактировать":
                case "edit":
                    editTransitionToFile(transactions);
                    break;
                case "добавить":
                case "add":
                    addingTransactionToFile(transactions);
                    break;
                default:
                    System.out.println("Выбрано неподходящее действие");
                    break;
            }

            System.out.println("Требуется совершить еще действие?(да/нет)");
            String isGoOn = sc.next();

            isContinue = isGoOn.equalsIgnoreCase("да") || isGoOn.equalsIgnoreCase("yes");


        } while (isContinue);

        List<String> newLines = new ArrayList<>();
        transactions.values().forEach(line -> newLines.add(line.createLineToFile()));

        Writer.saveData(file, Constant.HEADER_MONTH_FILE.getConstant(), newLines);
        System.out.println("Файл изменен");

        System.out.println("Данных файл требуется считать?(да/нет)");
        String isReading = sc.next();
        if (isReading.equalsIgnoreCase("да") || isReading.equalsIgnoreCase("yes")) {
            base.removeInfo(year, month);
            base.addInfo(newLines, year, month);
        }
    }

    private void createProductLine(List<String> products) {
        try {
            System.out.println("Укажите наименование товара");
            String nameItem = sc.next();
            System.out.println("Укажите цену товара");
            String price = String.valueOf(sc.nextInt());
            System.out.println("Укажите количество");
            String quantity = String.valueOf(sc.nextInt());
            System.out.println("Это расход?(введите ответ в формате да/нет)");
            String isExpense = Util.formattingIsExpense(sc.next());

            products.add(String.format("%s,%s,%s,%s", nameItem, isExpense, quantity, price));


        } catch (FormatterException e) {
            System.out.println(e.getMessage());
            System.out.println("Попробуйте снова");
            createProductLine(products);
        }
    }

    private void deleteTransitionToFile(Map<String, TransactionMonth> transactions) {
        System.out.println("Данные, доступные для удаления:");
        for (String transactionName: transactions.keySet()) {
            System.out.println(transactionName);
        }

        System.out.println("Какой продукт вы хотите удалить?(Введите название, если передумали, то введите назад)");
        String value = sc.next().toLowerCase();
        if (value.equalsIgnoreCase("назад") || value.equalsIgnoreCase("back")) {
            return;
        }

        if (!transactions.containsKey(value)) {
            System.out.println("Данного товара нет, проверьте верность написания");
            return;
        }

        transactions.remove(value);
        System.out.println("Данные удалены");
    }
    private void editTransitionToFile(Map<String, TransactionMonth> transactions) {
        System.out.println("Данные, доступные для редактирования:");
        for (String transactionName: transactions.keySet()) {
            System.out.println(transactionName);
        }

        System.out.println("Какой продукт вы хотите редактировать?(Введите название, если передумали, то введите назад)");
        String value = sc.next().toLowerCase();
        if (value.equalsIgnoreCase("назад") || value.equalsIgnoreCase("back")) {
            return;
        }

        if (!transactions.containsKey(value)) {
            System.out.println("Данного товара нет, проверьте верность написания");
            return;
        }

        TransactionMonth transaction = transactions.get(value);

        System.out.println("Какое значение вы хотите отредактировать?");
        System.out.println("Варианты: Цена / Количество / Тип / Название");
        String meaning = sc.next().toLowerCase();

        switch (meaning) {
            case "цена":
            case "price":
                editPrice(transaction);
                break;
            case "количество":
            case "quantity":
                editQuantity(transaction);
                break;
            case "тип":
            case "type":
                editExpense(transaction);
                break;
            case "название":
            case "name":
                editName(transactions, transaction);
                break;
            default:
                System.out.println("Неизвестное значение");
                return;
        }

        System.out.println("Данные изменены");

    }
    private void addingTransactionToFile(Map<String, TransactionMonth> transactions) {
        System.out.println("Введите название товара");
        String name = sc.next().toLowerCase();
        if (transactions.containsKey(name)) {
            System.out.println("Данный товар уже есть");
            return;
        }

        System.out.println("Введите цену");
        int price = 0;
        try {
            price = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Введено неправильное значение цены");
        }

        if (price <= 0) {
            System.out.println("нельзя указывать отрицательную или нулевую цену");
            return;
        }

        System.out.println("Введите количество");
        int quantity;
        try {
            quantity = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Введено некорректное значение количество");
            return;
        }

        if (quantity <= 0) {
            System.out.println("нельзя указывать отрицательное или нулевое значение количества");
            return;
        }

        System.out.println("Это расход или доход?");
        String type = sc.next();
        boolean isExpense;

        switch (type.toLowerCase()) {
            case "доход":
            case "revenue":
                isExpense = false;
                break;
            case "расход":
            case "expense":
                isExpense = true;
                break;
            default:
                System.out.println("Введено некорректное значение типа");
                return;
        }

        TransactionMonth newTransaction = new TransactionMonth(name, isExpense, quantity, price);
        transactions.put(name, newTransaction);

    }

    private void editPrice(TransactionMonth transaction) {
        System.out.println("Введите новое значение");
        int newPrice;
        try {
            newPrice = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Введено некорректное значение");
            return;
        }

        transaction.setPrice(newPrice);
    }

    private void editQuantity(TransactionMonth transaction) {
        System.out.println("Введите новое значение");
        int newQuantity;
        try {
            newQuantity = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Введено некорректное значение");
            return;
        }

        transaction.setQuantity(newQuantity);
    }

    private void editExpense(TransactionMonth transaction) {
        System.out.println("Укажите - доход или расход");
        String value = sc.next().toLowerCase();
        switch (value) {
            case "доход":
            case "revenue":
                transaction.setExpense(false);
            case "расход":
            case "expense":
                transaction.setExpense(true);
            default:
                System.out.println("Введение неизвестное значение");

        }
    }

    private void editName(Map<String, TransactionMonth> transactions, TransactionMonth transaction) {
        System.out.println("Укажите новое имя");
        String newName = sc.next().toLowerCase();
        if (transactions.containsKey(newName)) {
            System.out.println("Данное имя уже есть");
            return;
        }

        transactions.remove(transaction.getProductName().toLowerCase());
        transaction.setProductName(newName);
        transactions.put(newName, transaction);

    }
}

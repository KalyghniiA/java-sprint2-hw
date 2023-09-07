package manager;

import data_base.DataBase;
import exceptions.FormatterException;
import parser.Parser;
import util.Constant;
import util.Util;
import writer.Writer;

import java.io.File;
import java.io.Reader;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

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
            Integer year = sc.nextInt();
            base.getInfoForMonths(year);
        } catch (InputMismatchException e) {
            System.out.println("Год введен в неверном формате, попробуйте снова");
            sc.next();
            getInfoForYear();
        }
    }

    public void getInfoForYear() {
        System.out.println("Введите год, за который хотите получить информацию");
        Integer year = sc.nextInt();
        base.getInfoForYear(year);
    }

    public void verificationFiles() {
        System.out.println("За какой год вы хотите проверить информацию");
        try {
            int year = sc.nextInt();
            base.verificationFiles(year);
        } catch (InputMismatchException e) {
            System.out.println("Год введен в неверном формате, попробуйте снова");
            sc.next();
            verificationFiles();
        }
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

    // посмотри плизз!
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

                isContinue = isGoOn.equalsIgnoreCase("да");

            } while(isContinue);

            Writer.saveData(file, Constant.HEADER_MONTH_FILE.getConstant(), products);

            File fileYear = new File(Util.createPathToCSV(year));

            if (!fileYear.exists()) {
                System.out.println("Файла за данный год не существует, требуется создание?");
            } else {
                System.out.println("Требуется редактирование файла за год?");
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

            products.add(String.format("%s,%s,%s,%s%n", nameItem, isExpense, quantity, price));


        } catch (FormatterException e) {
            System.out.println(e.getMessage());
            System.out.println("Попробуйте снова");
            createProductLine(products);
        }
    }

}

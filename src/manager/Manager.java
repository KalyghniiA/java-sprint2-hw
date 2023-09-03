package manager;

import data_base.DataBase;
import exceptions.FormatterException;
import parser.Parser;
import util.Util;

import java.io.File;
import java.util.ArrayList;
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
            Integer year = sc.nextInt();
            System.out.println("Укажите месяц, за который нужна информация");
            String month = Util.formattingMonth(sc.next());

            File file = new File(Util.createPathToCSV(year, month));
            System.out.println(Util.createPathToCSV(year, month));

            List<String> list;
            if (file.exists()) {
                list = Parser.parseDataToMonth(file);
            } else {
                System.out.println("За данный месяц данных нет");
                return;
            }
            base.addInfo(list, year, month);

        } catch (FormatterException e) {
            System.out.println(e.getMessage());
        }

    }

    public void getInfo() {
        System.out.println("Введите год, за который хотите получить информацию");
        Integer year = sc.nextInt();
        base.getInfo(year);
    }

}

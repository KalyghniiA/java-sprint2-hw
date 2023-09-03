package app;

import data_base.DataBase;
import manager.Manager;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    private final Scanner sc = new Scanner(System.in);
    private final DataBase base = new DataBase();
    private final Manager manager = new Manager(sc, base);

    public void start() {

        System.out.println("Добро пожаловать!");
        while (true) {
            System.out.println("Выберете одну из команд");
            getMenu();
            try {
                int current = sc.nextInt();
                switch (current) {
                    case 1:
                        manager.readInfoForMonth();
                        break;
                    case 2:
                        System.out.println("Start 2");
                        break;
                    case 3:
                        System.out.println("Start 3");
                        break;
                    case 4:
                        System.out.println("Start 4");
                        break;
                    case 5:
                        System.out.println("Start 5");
                        break;
                    case 6:
                        System.out.println("Start 6");
                        break;
                    case 7:
                        manager.getInfo();
                        break;
                    case 0:
                        System.out.println("Всего доброго!");
                        return;
                    default:
                        System.out.println("Данной команды еще нет");
                }


            } catch (InputMismatchException e) {
                System.out.print("Введена не корректная команда");
            }

        }
    }

    private void getMenu() {
        System.out.println("1 -- Считать отчет за определенный месяц");
        System.out.println("2 -- Считать все месячные отчеты за год");
        System.out.println("3 -- Считать годовой отчет");
        System.out.println("4 -- Сверить отчеты");
        System.out.println("5 -- Создать месячный отчет");
        System.out.println("6 -- Отредактировать отчет за месяц");
        System.out.println("7 -- Вывести информацию о всех месячных отчетах");
        System.out.println("8 -- Вывести информацию о годовом отчете");
        System.out.println("0 -- Выход");
    }



}

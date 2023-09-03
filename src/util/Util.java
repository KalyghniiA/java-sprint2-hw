package util;

import exceptions.FormatterException;

public class Util {
    public static String formattingMonth(String month) throws FormatterException {
        switch (month.toLowerCase()) {
            case "1":
            case "01":
            case "январь":
            case "january":
                return "01";
            case "2":
            case "02":
            case "февраль":
            case "february":
                return "02";
            case "3":
            case "03":
            case "март":
            case "march":
                return "03";
            case "4":
            case "04":
            case "апрель":
            case"april":
                return "04";
            case "5":
            case "05":
            case "май":
            case "may":
                return "05";
            case "6":
            case "06":
            case "июнь":
            case "june":
                return "06";
            case "7":
            case "07":
            case "июль":
            case "july":
                return "07";
            case "8":
            case "08":
            case "август":
            case "august":
                return "08";
            case "9":
            case "09":
            case "сентябрь":
            case "september":
                return "09";
            case "10":
            case "октябрь":
            case "october":
                return "10";
            case "11":
            case "ноябрь":
            case "november":
                return "11";
            case "12":
            case "декабрь":
            case "december":
                return "12";
            default:
                throw new FormatterException();
        }
    }
    public static String createPathToCSV(Integer year, String month) {
        return String.format("%sm.%s%s.csv", Constant.HOME.getConstant(), year, month);
    }

    public static String createPathToCSV(Integer year) {
        return String.format("%sy.%h.csv", Constant.HOME.getConstant(), year);
    }
}

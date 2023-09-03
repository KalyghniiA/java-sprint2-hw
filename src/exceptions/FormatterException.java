package exceptions;

public class FormatterException extends Exception {
    @Override
    public String getMessage() {
        return "Указан неизвестный формат";
    }
}

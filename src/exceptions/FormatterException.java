package exceptions;

public class FormatterException extends Exception {
    private final String format;

    public FormatterException(String format) {
        this.format = format;
    }

    @Override
    public String getMessage() {
        return "Указан неизвестный формат. Ошибка в формате: " + format;
    }
}

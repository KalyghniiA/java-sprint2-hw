package util;

public enum Constant {
    HOME(System.getProperty("user.dir") + System.getProperty("file.separator") + "resources" + System.getProperty("file.separator")),
    HEADER_MONTH_FILE("item_name,is_expense,quantity,unit_price"),
    HEADER_YEAR_FILE("month,amount,is_expense");

    private String constant;

    Constant(String constant) {
       this.constant = constant;
    }

    public String getConstant() {
       return constant;
    }
}

package util;

public enum Constant {
   HOME(System.getProperty("user.dir") + System.getProperty("file.separator") + "resources" + System.getProperty("file.separator"));
   private String constant;

   Constant(String constant) {
       this.constant = constant;
   }

   public String getConstant() {
       return constant;
   }
}

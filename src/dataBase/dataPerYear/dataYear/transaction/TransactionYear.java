package dataBase.dataPerYear.dataYear.transaction;

public class TransactionYear {
        private String month;
        private int expense = 0;
        private int revenue = 0;

        public TransactionYear(String month) {
            this.month = month;
        }

        public int getExpense() {
            return expense;
        }

        public void setExpense(int expense) {
            this.expense = expense;
        }

        public int getRevenue() {
            return revenue;
        }

        public void setRevenue(int revenue) {
            this.revenue = revenue;
        }

}

public class Exercise1 {
    public static void main(String[] args){
        BankAccount account1 = new BankAccount(1000);
        BankAccount account2 = new BankAccount();
        System.out.println("Bank balance of account 1 : "+ account1.getBalance());
        System.out.println("Bank balance of account 2 : "+ account2.getBalance());
    }
}

class BankAccount{
    private double balance ;

    public BankAccount(double initBal){
        this.balance = initBal;
    }
    public BankAccount(){
        this.balance = 0.0;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
}

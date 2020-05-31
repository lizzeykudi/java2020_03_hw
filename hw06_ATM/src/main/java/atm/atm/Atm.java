package atm.atm;

public interface Atm {

    void deposit(int sum) throws AtmException;

    void withdraw(int sum) throws AtmException;

    void balance();
}

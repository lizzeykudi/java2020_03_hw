package atm.atm;

import atm.defaultAtm.Nominal;

public interface Atm {

    void deposit(Nominal sum) throws AtmException;

    void withdraw(int sum) throws AtmException;

    void balance();
}

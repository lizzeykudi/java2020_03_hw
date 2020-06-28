package atm.atm;

import atm.atmDepartment.AtmDepartment;
import atm.atmDepartment.Memento;
import atm.defaultAtm.Nominal;

public interface Atm extends Memento {

    void deposit(Nominal sum) throws AtmException;

    void withdraw(int sum) throws AtmException;

    int balance();

    void attachToAtmDepartment(AtmDepartment atmDepartment);

    void detachFromAtmDepartment(AtmDepartment atmDepartment);

    void notify(String notification);
}

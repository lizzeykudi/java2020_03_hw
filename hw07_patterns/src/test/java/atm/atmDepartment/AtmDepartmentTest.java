package atm.atmDepartment;

import atm.defaultAtm.DefaultAtm;
import atm.defaultAtm.Nominal;
import org.junit.Test;

public class AtmDepartmentTest {

    @Test
    public void balance() {
        AtmDepartment atmDepartment = new AtmDepartment();
        atmDepartment.addAtm(new DefaultAtm(new Nominal[]{Nominal.HUNDRED, Nominal.FIVE_HUNDRED, Nominal.THOUSAND}));
        atmDepartment.addAtm(new DefaultAtm(new Nominal[]{Nominal.HUNDRED, Nominal.FIVE_HUNDRED, Nominal.THOUSAND}));
        atmDepartment.atmsBalance();
    }

    @Test
    public void restore() {
        DefaultAtm defaultAtm = new DefaultAtm(new Nominal[]{Nominal.HUNDRED, Nominal.FIVE_HUNDRED, Nominal.THOUSAND});
        AtmDepartment atmDepartment = new AtmDepartment();
        atmDepartment.addAtm(defaultAtm);
        defaultAtm.withdraw(1000);
        System.out.println(defaultAtm.balance());
        atmDepartment.atmsRestore();
        System.out.println(defaultAtm.balance());
    }

}
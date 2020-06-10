package atm.defaultAtm;

import atm.atm.Atm;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class DefaultAtmTest {
    Atm atm;

    @Before
    public void setUp() throws Exception {
        atm = new DefaultAtm(new Nominal[]{Nominal.HUNDRED, Nominal.FIVE_HUNDRED, Nominal.THOUSAND});
    }

    @Test
    public void deposit() {
        atm.balance();
        atm.deposit(Nominal.FIVE_HUNDRED);
        atm.balance();
    }

    @Test
    public void withdraw() {
        atm.balance();
        atm.withdraw(3200);
        atm.balance();
    }
}
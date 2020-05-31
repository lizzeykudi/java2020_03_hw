package atm.defaultAtm;

import atm.atm.Atm;
import org.junit.Before;
import org.junit.Test;

public class DefaultAtmTest {
    Atm atm;

    @Before
    public void setUp() throws Exception {
        atm = new DefaultAtm(new int[]{100, 500, 1000}, 2);
    }

    @Test
    public void deposit() {
        atm.balance();
        atm.deposit(500);
        atm.balance();
    }

    @Test
    public void withdraw() {
        atm.balance();
        atm.withdraw(3200);
        atm.balance();
    }
}
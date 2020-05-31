package atm.defaultAtm;

import atm.atm.Atm;
import atm.atm.AtmException;
import atm.atm.Vault;

public class DefaultAtm implements Atm {
    Vault vault;

    public DefaultAtm(int[] cells, Integer initialSellCapacity) {
        this.vault = new Cells(cells, initialSellCapacity);
    }

    @Override
    public void deposit(int sum) throws AtmException {
        vault.deposit(sum);
    }

    @Override
    public void withdraw(int sum) throws AtmException {
        vault.withdraw(sum);
    }

    @Override
    public void balance() {
        vault.balance();
    }
}

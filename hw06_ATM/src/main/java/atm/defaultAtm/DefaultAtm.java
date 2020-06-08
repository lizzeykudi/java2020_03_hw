package atm.defaultAtm;

import atm.atm.Atm;

import java.util.HashMap;
import java.util.List;

public class DefaultAtm implements Atm {
    Cells cells;


    public DefaultAtm(Nominal[] cells) {
        this.cells = new Cells(cells);
    }

    @Override
    public void deposit(Nominal nominal) {
        if (!cells.containsNominal(nominal)) {
            throw new DefaultAtmException();
        }
        cells.add(nominal);
        System.out.println("deposited : " + nominal);
    }

    @Override
    public void withdraw(int sum) {
        HashMap sumBuilder = new HashMap();
        List<Nominal> nominals = cells.getSortedSellNominals();

        for (Nominal nominal : nominals) {
            int quotient = sum / nominal.getValue();
            if (quotient > 0) {
                int nominalAmount = Math.min(quotient, cells.getAvailableNominalAmount(nominal));
                sumBuilder.put(nominal, nominalAmount);
                sum = sum - (nominal.getValue() * nominalAmount);
                cells.remove(nominal, nominalAmount);
            }
        }

        if (sum != 0) {
            throw new DefaultAtmException();
        }
        System.out.println("withdrawn : " + sumBuilder);
    }

    @Override
    public void balance() {
        int sum = 0;
        for (Cell cell : cells.getNominalValues()) {
            sum = sum + cell.getNominal().getValue() * cell.getCount();
        }
        System.out.println("balance : " + sum);
    }
}

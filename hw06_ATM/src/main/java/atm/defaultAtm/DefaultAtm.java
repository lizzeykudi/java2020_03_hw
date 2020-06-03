package atm.defaultAtm;

import atm.atm.Atm;
import atm.atm.AtmException;
import atm.atm.Vault;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultAtm implements Atm {
    Cells cells;


    public DefaultAtm(int[] cells, Integer initialSellCapacity) {
        List<Integer> cellsList = Arrays.stream(Cells.Nominal.values()).map(nominal -> nominal.getValue()).collect(Collectors.toList());
        List<Integer> nominals = Arrays.stream(cells).boxed().collect(Collectors.toList());
        if (!nominals.containsAll(cellsList)) {throw new DefaultAtmException();}
        this.cells = new Cells(cells, initialSellCapacity);
    }

    @Override
    public void deposit(int nominal) {
        if (!cells.containsNominal(nominal)) {
            throw new DefaultAtmException();
        }
        cells.add(nominal);
        System.out.println("deposited : " + nominal);
    }

    @Override
    public void withdraw(int sum) {
        HashMap sumBuilder = new HashMap();
        List<Integer> nominals = cells.getSortedSellNominals();

        for (Integer nominal : nominals) {
            int quotient = sum / nominal;
            if (quotient > 0) {
                int nominalAmount = Math.min(quotient, cells.getAvailableNominalAmount(nominal));
                sumBuilder.put(nominal, nominalAmount);
                sum = sum - (nominal * nominalAmount);
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
        for (Map.Entry<Integer, Integer> entry : cells.getNominalValues()) {
            sum = sum + entry.getKey() * entry.getValue();
        }
        System.out.println("balance : " + sum);
    }
}

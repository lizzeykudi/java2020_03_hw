package atm.defaultAtm;

import atm.atm.Vault;

import java.util.*;

public class Cells implements Vault {
    private HashMap<Integer, Integer> cells = new HashMap<>();

    public Cells(int[] cells, Integer initialSellCapacity) {
        Arrays.stream(cells).forEach(cell -> this.cells.put(cell, initialSellCapacity));
    }

    @Override
    public void deposit(int nominal) {
        if (!cells.containsKey(nominal)) {
            throw new DefaultAtmException();
        }
        add(nominal);
        System.out.println("deposited : " + nominal);
    }

    @Override
    public void withdraw(int sum) {
        HashMap sumBuilder = new HashMap();
        List<Integer> nominals = getSortedSellNominals();

        for (Integer nominal : nominals) {
            int quotient = sum / nominal;
            if (quotient > 0) {
                int nominalAmount = Math.min(quotient, getAvailableNominalAmount(nominal));
                sumBuilder.put(nominal, nominalAmount);
                sum = sum - (nominal * nominalAmount);
                remove(nominal, nominalAmount);
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
        for (Map.Entry<Integer, Integer> entry : cells.entrySet()) {
            sum = sum + entry.getKey() * entry.getValue();
        }
        System.out.println("balance : " + sum);
    }

    private void add(int nominal) {
        cells.put(nominal, cells.get(nominal) + 1);
    }

    private void remove(int nominal, int count) {
        cells.put(nominal, cells.get(nominal) - count);
    }

    private int getAvailableNominalAmount(int nominal) {
        return cells.get(nominal);
    }


    private List<Integer> getSortedSellNominals() {
        List<Integer> cellNominals = new ArrayList<>(cells.keySet());
        Collections.sort(cellNominals, Collections.reverseOrder());
        return cellNominals;
    }
}

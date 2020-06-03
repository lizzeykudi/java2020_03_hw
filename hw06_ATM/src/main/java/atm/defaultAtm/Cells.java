package atm.defaultAtm;

import atm.atm.Vault;

import java.util.*;

public class Cells implements Vault {
    private HashMap<Integer, Integer> cells = new HashMap<>();

    public Cells(int[] cells, Integer initialSellCapacity) {
        Arrays.stream(cells).forEach(cell -> this.cells.put(cell, initialSellCapacity));
    }

    public void add(int nominal) {
        cells.put(nominal, cells.get(nominal) + 1);
    }

    public void remove(int nominal, int count) {
        cells.put(nominal, cells.get(nominal) - count);
    }

    public int getAvailableNominalAmount(int nominal) {
        return cells.get(nominal);
    }


    public List<Integer> getSortedSellNominals() {
        List<Integer> cellNominals = new ArrayList<>(cells.keySet());
        Collections.sort(cellNominals, Collections.reverseOrder());
        return cellNominals;
    }

    public boolean containsNominal(int nominal) {
        return cells.containsKey(nominal);
    }

    public Set<Map.Entry<Integer, Integer>> getNominalValues() {
        return cells.entrySet();
    }

    enum Nominal{
        HUNDRED(100), FIVE_HUNDRED(500), THOUSAND(1000);
        private int value;
        Nominal(int value){
            this.value = value;
        }
        public int getValue(){ return value;}
    }
}

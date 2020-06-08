package atm.defaultAtm;

import java.util.*;

public class Cells {
    private HashMap<Nominal, Cell> cells = new HashMap<>();

    public Cells(Nominal[] cells) {
        Arrays.stream(cells).forEach(cell -> this.cells.put(cell, new Cell(cell)));
    }

    public void add(Nominal nominal) {
        cells.get(nominal).add();
    }

    public void remove(Nominal nominal, int count) {
        cells.get(nominal).remove(count);
    }

    public int getAvailableNominalAmount(Nominal nominal) {
        return cells.get(nominal).getCount();
    }


    public List<Nominal> getSortedSellNominals() {
        List<Nominal> cellNominals = new ArrayList<>(cells.keySet());
        Collections.sort(cellNominals, Collections.reverseOrder());
        return cellNominals;
    }

    public boolean containsNominal(Nominal nominal) {
        return cells.containsKey(nominal);
    }

    public Collection<Cell> getNominalValues() {
        return cells.values();
    }
}

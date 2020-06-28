package atm.defaultAtm;

import atm.atm.Atm;
import atm.atmDepartment.AtmDepartment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefaultAtm implements Atm {
    private Cells cells;
    private String snapshot;
    private List<AtmDepartment> atmDepartments = new ArrayList<>();


    public DefaultAtm(Nominal[] cells) {
        this.cells = new Cells(cells);
        snapshot = backup();
    }

    @Override
    public void deposit(Nominal nominal) {
        if (!cells.containsNominal(nominal)) {
            throw new DefaultAtmException();
        }
        cells.add(nominal);
        String log = "deposited : " + nominal;
        //System.out.println("deposited : " + nominal);
        notify(log);
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
        String log = "withdrawn : " + sumBuilder;
        //System.out.println("withdrawn : " + sumBuilder);
        notify(log);
    }

    @Override
    public int balance() {
        int sum = 0;
        for (Cell cell : cells.getNominalValues()) {
            sum = sum + cell.getNominal().getValue() * cell.getCount();
        }
        String log = "balance : " + sum;
        //System.out.println("balance : " + sum);
        notify(log);
        return sum;
    }

    @Override
    public void attachToAtmDepartment(AtmDepartment atmDepartment) {
        atmDepartments.add(atmDepartment);
    }

    @Override
    public void detachFromAtmDepartment(AtmDepartment atmDepartment) {
        atmDepartments.remove(atmDepartment);
    }

    @Override
    public void notify(String notification) {
        atmDepartments.forEach(atmDepartment -> atmDepartment.update(notification));
    }

    public String backup() {
        return new Gson().toJson(this);
    }

    public void restore(String state) {
        cells = new Gson().fromJson(state, this.getClass()).getCells();
        atmDepartments = new Gson().fromJson(state, this.getClass()).getAtmDepartments();
    }

    @Override
    public void restore() {
        restore(snapshot);
    }

    public Cells getCells() {
        return cells;
    }

    public List<AtmDepartment> getAtmDepartments() {
        return atmDepartments;
    }
}

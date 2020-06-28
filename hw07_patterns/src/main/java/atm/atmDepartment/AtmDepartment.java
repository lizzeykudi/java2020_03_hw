package atm.atmDepartment;

import atm.atm.Atm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AtmDepartment implements Listener {

    List<Atm> atms = new ArrayList<>();

    public void addAtm(Atm atm) {
        atms.add(atm);
        atm.attachToAtmDepartment(this);
    }

    public int atmsBalance() {
        int sum = atms.stream()
                .mapToInt(Atm::balance)
                .sum();

        System.out.println("Atms balance : " + sum);
        return sum;
    }

    public void atmsRestore() {
        atms.forEach(Memento::restore);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AtmDepartment that = (AtmDepartment) o;
        return Objects.equals(atms, that.atms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(atms);
    }

    @Override
    public void update(String notification) {
        System.out.println("new notification : " + notification);
    }
}

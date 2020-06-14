package atm.defaultAtm;

public enum Nominal {
    HUNDRED(100), FIVE_HUNDRED(500), THOUSAND(1000);
    private int value;

    Nominal(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

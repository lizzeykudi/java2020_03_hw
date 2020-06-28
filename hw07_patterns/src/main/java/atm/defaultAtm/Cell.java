package atm.defaultAtm;

public class Cell {
    private Nominal nominal;
    private Integer count;

    private static final Integer DEFAULT_COUNT = 2;

    public Cell(Nominal nominal) {
        this.nominal = nominal;
        this.count = DEFAULT_COUNT;
    }

    public Nominal getNominal() {
        return nominal;
    }

    public void setNominal(Nominal nominal) {
        this.nominal = nominal;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void add() {
        count++;
    }
    public void remove(int count) {
        this.count-=count;
    }

}

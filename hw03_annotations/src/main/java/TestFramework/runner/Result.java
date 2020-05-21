package TestFramework.runner;

public class Result {

    private int total=0;
    private int fail=0;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    @Override
    public String toString() {
        return "Result{" +
                "total=" + total +
                ", fail=" + fail +
                '}';
    }
}

package gc;

import java.time.LocalTime;

class Benchmark implements BenchmarkMBean, Runnable {
    private final int loopCounter;
    private volatile int size = 0;

    public Benchmark(int loopCounter) {
        this.loopCounter = loopCounter;
    }

    @Override
    public void run() {
        try {
            for (int idx = 0; idx < loopCounter; idx++) {
                int local = size;
                Object[] array = new Object[local];
                for (int i = 0; i < local; i++) {
                    array[i] = new String(new char[0]);
                }
                try {
                    Thread.sleep(10); //Label_1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }catch (OutOfMemoryError e) {
            System.out.println(e);
            System.out.println(LocalTime.now());
            System.exit(1);
        }
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void setSize(int size) {
        System.out.println("new size:" + size);
        this.size = size;
    }

}

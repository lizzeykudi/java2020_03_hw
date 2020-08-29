package executors;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyThread {
    private static final Logger logger = LoggerFactory.getLogger(MyThread.class);
    private String last = "two thread";

    private static final List<String> list = Arrays.asList(new String[]{"1","2","3","4","5","6","7","8","9","10","9","8","7","6","5","4","3","2","1"});

    private synchronized void action(String message) {
        for (int i = 0; i < list.size(); i++) {
            try {
                //spurious wakeup https://en.wikipedia.org/wiki/Spurious_wakeup
                //поэтому не if
                while (last.equals(message)) {
                    this.wait();
                }

                //logger.info(message);
                last = message;
                sleep();
                notifyAll();
                System.out.println(message + " " + list.get(i));
                //logger.info(message + " " + list.get(i));
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new NotInterestingException(ex);
            }
        }
    }

    public static void main(String[] args) {
        MyThread pingPong = new MyThread();
        new Thread(() -> pingPong.action("one thread")).start();
        new Thread(() -> pingPong.action("two thread")).start();
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private static class NotInterestingException extends RuntimeException {
        NotInterestingException(InterruptedException ex) {
            super(ex);
        }
    }
}


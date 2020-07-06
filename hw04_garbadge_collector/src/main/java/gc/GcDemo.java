package gc;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.time.LocalTime;
import java.util.List;

public class GcDemo {
    public static void main(String... args) throws Exception {
        System.out.println(LocalTime.now());
        Info info = new Info();
        switchOnMonitoring(info);
        long beginTime = System.currentTimeMillis();

        int size = 5 * 1000 * 1000*11;
        int loopCounter = 1000;
        //int loopCounter = 100000;
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.otus:type=Benchmark");

        Benchmark mbean = new Benchmark(loopCounter);
        mbs.registerMBean(mbean, name);
        mbean.setSize(size);
        Thread thread = new Thread(mbean);
        try {
        thread.start(); }catch (OutOfMemoryError e) {
            System.out.println("lol");
            throw e;}
        //mbean.run();

        while (true) {
            Thread.sleep(60000);
            System.out.println(info);
            info.init();

        }

        //System.out.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);

    }

    private static void switchOnMonitoring(Info info) {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            info.names.put(gcbean.getName(),0);
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo information = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = information.getGcName();
                    info.names.put(gcName, info.names.get(gcName)+1);

                    long duration = information.getGcInfo().getDuration();
                    info.duration+=duration;
                    String gcAction = information.getGcAction();
                    String gcCause = information.getGcCause();

                    long startTime = information.getGcInfo().getStartTime();


//                    System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

}

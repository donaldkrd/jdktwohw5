package Version;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {
    private static final int WAITPHILOSOPH = 5;
    public static void main(String[] args) {
        CountDownLatch cdlWaitReadyPhilosoph = new CountDownLatch(WAITPHILOSOPH);
        CountDownLatch cdlStartNewIteration = new CountDownLatch(1);

        Table table = new Table(cdlWaitReadyPhilosoph, cdlStartNewIteration);
        Thread thread = new Thread(table);
        thread.start();

        Thread thread0 = new Thread(new Philosoph(cdlWaitReadyPhilosoph, cdlStartNewIteration, "Аристотель", table), "Аристотель");
        Thread thread1 = new Thread(new Philosoph(cdlWaitReadyPhilosoph, cdlStartNewIteration, "Сократ", table), "Сократ");
        Thread thread2 = new Thread(new Philosoph(cdlWaitReadyPhilosoph, cdlStartNewIteration, "Македонский", table), "Македонский");
        Thread thread3 = new Thread(new Philosoph(cdlWaitReadyPhilosoph, cdlStartNewIteration, "Платон", table), "Платон");
        Thread thread4 = new Thread(new Philosoph(cdlWaitReadyPhilosoph, cdlStartNewIteration, "Пифагор", table), "Пифагор");

        thread0.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
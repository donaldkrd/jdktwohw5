import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread{
    private String name;
    private int leftFork;
    private int rightFork;
    private int countEat;
    private Random random;
    private CountDownLatch cdl;
    private Table table;

    public Philosopher(String name, Table table, int leftFork, int rightFork, CountDownLatch cdl) {
        this.name = name;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.countEat = 0;
        this.random = new Random();
        this.cdl = cdl;
        this.table = table;
    }

    @Override
    public void run() {
        while (countEat < 3) {
            try {
                thinking();
                eating();
            } catch (InterruptedException e) {
                e.fillInStackTrace();
            }
        }
        System.out.println(name + " всё съел");
        cdl.countDown();
    }
    private void eating() throws InterruptedException{
        if (table.tryGetFork(leftFork, rightFork)) {
            System.out.println(name + " есть с помощью вилок " + leftFork + " и " + rightFork);
            sleep(random.nextLong(3000, 6000));
            table.putForks(leftFork, rightFork);
            System.out.println(name + " покушал, пошел думать");
            countEat++;
        }
    }
    private void thinking() throws InterruptedException{
        sleep(random.nextLong(100, 2000));
    }
}

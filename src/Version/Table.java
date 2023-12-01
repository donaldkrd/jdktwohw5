package Version;

import org.w3c.dom.ls.LSOutput;

import javax.crypto.spec.PSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class Table extends Thread{
    private CountDownLatch cdlWaitReadyPhilosoph;
    private CountDownLatch cdlStartNewIteration;
    private volatile Map<Integer, Integer> plateOnTable;
    private Map<Integer, Boolean> forkOnTable;
    private List<Philosoph> philosophList;
    private List<Philosoph> goOn;

    public Table(CountDownLatch cdlWaitReadyPhilosoph, CountDownLatch cdlStartNewIteration) {
        this.cdlWaitReadyPhilosoph = cdlWaitReadyPhilosoph;
        this.cdlStartNewIteration = cdlStartNewIteration;
        philosophList = new ArrayList<>();
        goOn = new ArrayList<>();
        this.plateOnTable = new HashMap<>();
        this.forkOnTable = new HashMap<>();
        forkOnTable.put(forkOnTable.size(), true);
        forkOnTable.put(forkOnTable.size(), true);
        forkOnTable.put(forkOnTable.size(), true);
        forkOnTable.put(forkOnTable.size(), true);
        forkOnTable.put(forkOnTable.size(), true);
    }

    public int giveFork (int numberFork, Table table){
        if (numberFork > 0) {
            if (forkOnTable.get(numberFork).equals(true) &&
                    forkOnTable.get(numberFork - 1).equals(true)) {
                System.out.println("Смотрим вилки " + philosophList.get(numberFork).getName() + " и " + forkOnTable.get(numberFork - 1));
                forkOnTable.put(numberFork, false);
                forkOnTable.put((numberFork - 1), false);
                return 2;
            }
        } else {
            if (forkOnTable.get(numberFork).equals(true) &&
                    forkOnTable.get(forkOnTable.size() - 1)){
                System.out.println("Смотрим вилки для " + philosophList.get(numberFork).getName() + " " + forkOnTable.get(numberFork) + " и " + forkOnTable.get(forkOnTable.size() - 1));
                forkOnTable.put(numberFork, false);
                forkOnTable.put((forkOnTable.size() - 1), false);
                return 2;
            }
        }
        return 0;
    }

    public List<Philosoph> getGoOn() {
        return goOn;
    }

    public Map<Integer, Integer> getPlateOnTable() {
        return plateOnTable;
    }

    public Map<Integer, Boolean> getForkOnTable() {
        return forkOnTable;
    }

    public List<Philosoph> getPhilosophList() {
        return philosophList;
    }

    public void startTable(){
        if (isInterrupted()) this.start();
    }
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            cdlWaitReadyPhilosoph.await();
            System.err.println("Version2.Table печатает статус действий:");
            System.out.println(philosophList.get(0).getAction());
            for (Philosoph philosoph : philosophList) {
                System.err.println(philosoph.getAction()); // Сообщили, кто что будет делать из философов
            }
            System.err.println("Текущее состояние вилок " + forkOnTable);
            resetForkOnTable();
            System.err.println("Новое состояние вилок " + forkOnTable);
            philosophList.forEach((Philosoph::startIteration));
            if (!goOn.isEmpty()) {
                for (Philosoph p : goOn) {
                    System.out.println(p.getAction());
                }
            }
//            this.interrupt();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private void resetForkOnTable() {
        forkOnTable.forEach((k, v) -> forkOnTable.put(k, true));
    }
}

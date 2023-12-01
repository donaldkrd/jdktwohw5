package Version2;

import java.util.*;

public class Table {
    private volatile Map<Integer, Integer> plateOfSpaghetti;
    private volatile Map<Integer, Boolean> fork;
    private List<Philosoph> philosoph;

    public Table() {
        philosoph = new ArrayList<>();
        this.plateOfSpaghetti = new HashMap<>();
//        plateOfSpaghetti.forEach((k, v) -> System.out.println("Now in plate " + k + " " + v));
        this.fork = new HashMap<>();
        fork.put(fork.size(), true);
        fork.put(fork.size(), true);
        fork.put(fork.size(), true);
        fork.put(fork.size(), true);
        fork.put(fork.size(), true);
    }
    public Map<Integer, Integer> getPlateMap(){
        return this.plateOfSpaghetti;
    }
    public synchronized Map<Integer, Boolean> getForkMap(){
        return this.fork;
    }
    public synchronized void getFork(Integer fork, Philosoph philosoph) {
        this.fork.put(fork, false);
        for (Philosoph phil : this.philosoph) {
            if (phil.equals(philosoph)) philosoph.setFork(1);
        }
    }
    public List<Philosoph> getPhilosophList(){
        return philosoph;
    }
    public void resetFork(){
        synchronized (Table.class) {
            fork.forEach((k, v) -> fork.put(k, true));
        }
    }

}
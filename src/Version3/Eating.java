package Version3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Eating {
    private List<Philosopher> philosopherList;
    private final HashMap<Integer,Integer> fork = new HashMap<>(); //0 - её нет в наличии , 1 - есть в наличии
    ReentrantLock locker;
    Condition condition;

    Eating(){
        philosopherList = new ArrayList<>();
        locker = new ReentrantLock();
        condition = locker.newCondition();
    }

    public List<Philosopher> getPhilosopherList() {
        return philosopherList;
    }

    void addFork() {
        fork.put(1, 1);
        fork.put(2, 1);
        fork.put(3, 1);
        fork.put(4, 1);
        fork.put(5, 1);
    }
    void getFork(int left,int right){

        locker.lock();
        try {

            while (true) {
                if (fork.get(left) == 1 && fork.get(right) == 1) {
                    fork.put(left, 0);
                    fork.put(right, 0);
                    System.out.println("Philosopher " + Thread.currentThread().getName() + " give " + left + " and " + right + " forks || Status table: "+fork);
                    Thread.sleep(1000);
                    fork.put(left, 1);
                    fork.put(right, 1);
                    System.out.println("Philosopher " + Thread.currentThread().getName() + "was eating         || Status table: "+fork);
                    condition.signalAll();

                }

                condition.await();
            }
        }
        catch (InterruptedException e){
            System.out.println("Error");
        }
        finally {
            locker.unlock();
        }


    }
}
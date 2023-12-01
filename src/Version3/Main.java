package Version3;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        var philosopher = new ArrayList<Thread>();
        Eating eating = new Eating();
        eating.addFork();
        philosopher.add(new Thread(new Philosopher(1,2,eating, "Аристотель"), "Аристотель"));
        philosopher.add(new Thread(new Philosopher(2,3,eating, "Сократ"), "Сократ"));
        philosopher.add(new Thread(new Philosopher(3,4,eating, "Македонский"), "Македонский"));
        philosopher.add(new Thread(new Philosopher(4,5,eating, "Платон"), "Платон"));
        philosopher.add(new Thread(new Philosopher(5,1,eating, "Пифагор"), "Пифагор"));
        philosopher.forEach(Thread::start);
    }
}
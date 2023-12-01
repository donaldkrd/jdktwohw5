package Version;

import javax.crypto.spec.PSource;
import java.util.concurrent.CountDownLatch;

public class Philosoph extends Thread{
    private boolean isActive;
    private CountDownLatch cdlWaitReadyPhilosoph;
    private CountDownLatch cdlStartNewIteration;
    private String name;
    private String action;
    private int forkPhilosoph = 0;
    private int platePhilosoph;
    private int countEat = 0;
    private boolean readyEat = true;
    private Table table;
    private final Object monitor;

    public Philosoph(CountDownLatch cdlWaitReadyPhilosoph,
                     CountDownLatch cdlStartNewIteration, String name,
                     Table table) {
        isActive = true;
        this.cdlWaitReadyPhilosoph = cdlWaitReadyPhilosoph;
        this.cdlStartNewIteration = cdlStartNewIteration;
        this.name = name;
        this.table = table;
        this.monitor = Table.class;
        table.getPhilosophList().add(this);
        table.getPlateOnTable().put(table.getPlateOnTable().size(), countEat);
        this.platePhilosoph = table.getPlateOnTable().size() - 1;
    }
    private void disableThread(){
        isActive = false;
    }
    public void addForkPhilosoph(int addFork) {
        this.forkPhilosoph += addFork;
    }

    public String getAction() {
        return action;
    }
    public void startIteration(){
        if (isInterrupted()) this.start();
    }
    @Override
    public void run() {
        while (isActive) {
//            if (!table.getPhilosophList().isEmpty() && table.isInterrupted())
//                table.startTable(); // если список философов не пустой, перезапускаем
            if (countEat < 3) { // Если философ не всё съел
                if (readyEat) { // Если философ на текущем ходе может кушать
                    try {
                        Thread.sleep(500);
                        System.out.println(countEat + " Философ " + name + " не всё съел, готовность поесть " + readyEat);
                        Thread.sleep(200);
                        synchronized (Table.class) {
                            System.out.println("Текущее состояние вилок " + table.getForkOnTable() + " для " + name);
                            for (int i = 0; i < table.getPlateOnTable().size(); i++) { // находим свою тарелку
                                if (table.getPlateOnTable().get(i) == platePhilosoph) {
                                    Thread.sleep(200);
                                    System.out.println(countEat + " философ " + name + " нашел тарелку: " + table.getPlateOnTable().get(i) + " = " + platePhilosoph);
                                    forkPhilosoph = table.giveFork(i, table); // берм вилки со стола
                                    System.out.println(countEat + " У философа " + name + " " + forkPhilosoph + " вилки");
                                    break;
                                }
                            }
                            System.out.println("Новое состояние вилок " + table.getForkOnTable() + " для " + name);
                        }
                        Thread.sleep(200);
                        System.out.println(countEat + " перед едой у философа " + name + " " + forkPhilosoph + " вилки");
                        if (forkPhilosoph == 2) {
                            System.err.println("Проверка 2 вилок");
                            countEat++; // Философ поел
                            readyEat = !readyEat; // Следующий раз философ не может поесть
                            action = "Философ " + name + " покушал! " + countEat + " раз(а)!";
                            cdlWaitReadyPhilosoph.countDown(); // Даём сигнал к действию
                            System.err.println(countEat + " Философ " + name + " поел");
//                            disableThread();
                            this.interrupt(); // Прерываем поток
                        }
                        action = "Философу " + name + " не хватило вилок на столе, пойдет размышлять о еде...";
                        cdlWaitReadyPhilosoph.countDown();
//                        disableThread();
                        this.interrupt();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    readyEat = !readyEat;
                    action = "Философ " + name + " пошел поразмышлять...";
                    cdlWaitReadyPhilosoph.countDown(); // даем сигнал к действию
//                    disableThread();
                    this.interrupt();
                }
            }
            if (countEat == 3) { // Если философ сожрал все... и тарелку...
                action = "Филосов " + name + " съел всю еду и вышел из-за стола.";
                table.getGoOn().add(this); // Добавляем философа в новый список, кто уже все съел
                for (Philosoph philosoph : table.getPhilosophList()) {
                    if (philosoph.getName().equals(this.name)) {
                        table.getPhilosophList().remove(this); // Убираем философа из списка
                    }
                }
//                disableThread();
                this.interrupt(); // Прерываем поток
            }
        }
    }
}

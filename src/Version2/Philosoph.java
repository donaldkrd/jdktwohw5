package Version2;

public class Philosoph extends Thread {
    private String name;
    private int fork = 0;
    private int plate;
    private boolean readyEat = true;
    private Boolean nextAction = true;
    private int countEat = 3;
    private Table table;

    public Philosoph(String name, Table table) {
        this.name = name;
        this.table = table;
        Object monitor = Table.class;
        table.getPhilosophList().add(this);
        table.getPlateMap().put(table.getPlateMap().size(), countEat);
        this.plate = table.getPlateMap().size() - 1;
    }
    @Override
    public void run() {
        System.out.println(table.getPhilosophList().size());
        int step= 0;
        while (this.countEat > 0) {

            try {
//                System.out.println("доступные вилки на начало итерации " + table.getForkMap());
                System.out.println(name + step + ": проверка countEat > 0 -> " + countEat);
//                System.out.println("Доступные тарелки " + table.getPlateMap());
                if (!this.readyEat) {
//                    System.out.println(name + step + " текущая готовность поесть " + readyEat);
                    System.err.println(name + " go to think about something...");
                    this.readyEat = !this.readyEat;
//                    System.out.println(name + step + " кушать не будет, новая готовность поесть " + readyEat);
                    continue;
                } else {
                    System.out.println(name + " готов поесть");
                }
                Thread.sleep(500);
                System.out.println(name + step + ": sleep 500");
//                System.out.println("Количество тарелок = " + table.getPlateMap().size());
                for (int i = 0; i < table.getPlateMap().size(); i++) {
//                    System.out.println(name + step + ": Проверка цикла поиска тарелки с номером: " + plate +" и "+ i);
//                    System.out.println("Доступные тарелки для " + name + " " + table.getPlateMap());
                    if (table.getPlateMap().keySet().stream().toList().get(i) == this.plate) {
                        System.out.println(name + step + " нашел тарелку " + plate + " " +
                                table.getPlateMap().keySet().stream().toList().get(i));
                        if(checkFork(i, table)) {
                            System.out.println(name + step + " проверил наличие вилок");
                            System.out.println(name+ step +  " доступные вилки: " + table.getForkMap());
                            synchronized (Philosoph.class){
                                giveFork(i, table);
                            }
                            System.out.println(name + step + " взял вилки " + table.getForkMap());
                            this.countEat--;
                            readyEat = !readyEat;
                            step++;
                            table.resetFork();
                            System.out.println("Обновили статус вилок " + table.getForkMap());
                            System.err.println(name + step + " eat spaghetti!");
                            System.out.println(name + " готовность поесть " + readyEat);
                            this.interrupt();
                        } else {
                            System.err.println(name + step + " go to think about something...");
                            step++;
//                            break;
                            this.interrupt();
                        }
                        table.getPlateMap().put(this.plate, --countEat);
                    }
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            try {
                Thread.sleep(300);
                if (this.countEat > 0 && isInterrupted()) this.start();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if (countEat == 0) {
            System.err.println(name + " eating all plate spaghetti!");
            this.interrupt();
        }
    }
    public boolean checkFork(int i, Table table) {
        return (i > 0) ? (table.getForkMap().get(i) && table.getForkMap().get(i - 1)) :
                (table.getForkMap().get(i) && table.getForkMap().get(table.getForkMap().size() - 1));
    }
    public void giveFork(int i, Table table) {
        table.getForkMap().put(i, false);
        if (i > 0) {
            table.getForkMap().put(i - 1, false);
        } else {
            table.getForkMap().put(table.getForkMap().size() - 1, false);
        }
    }



//    public synchronized void checkEat() throws InterruptedException{
//        Thread.sleep(200);
//        System.out.println("Counter =  "+ countEat + " " + name);
//        if (countEat >= 0){
//            Thread.sleep(200);
//            System.out.println("Next = " + nextAction + " " + name);
//            if (nextAction) {
//                for (int i = 0; i < table.getPlateList().size(); i++) {
//                    Thread.sleep(200);
//                    if (table.getPlateList().get(i).equals(this.plate)){
//                        Thread.sleep(200);
//                        System.out.println(name + " plate = " + plate);
//                        System.out.println(name + " сравниваем c " + table.getPlateList().get(i));
//                        if(table.getForkMap().get("fork" + String.valueOf(i)) &&
//                                table.getForkMap().get("fork" + String.valueOf(table.getForkMap().size() - 1))
//                        ) {
//                            Thread.sleep(200);
//                            System.out.println(name + " Old1 fork " + table.getForkMap().get("fork" + String.valueOf(i)));
//                            Thread.sleep(200);
//                            System.out.println(name + " old2 fork " + table.getForkMap().get("fork" + String.valueOf(table.getForkMap().size() - 1)));
//                            Thread.sleep(200);
//                            table.getForkMap().put("fork" + String.valueOf(i), false);
//                            System.out.println(name + " put1 " + table.getForkMap().get("fork" + String.valueOf(i)));
//                            table.getForkMap().put("fork" + String.valueOf(table.getForkMap().size() - 1), false);
//                            Thread.sleep(200);
//                            System.out.println(name + " put2" + table.getForkMap().get("fork" + String.valueOf(table.getForkMap().size() - 1)));
//                            nextAction = !nextAction;
//                            Thread.sleep(200);
//                            System.out.println(name + " next" + nextAction);
//                            System.out.println(name + " count" + countEat);
//                            countEat--;
//                            Thread.sleep(200);
//                            System.out.println(name + " count" + countEat);
//                            System.out.println("Version2.Philosoph " + name + " eating");
////                            cdlReadyEat.countDown();
//
//                        }
//                    }
//                }
//            }
//        }
//        System.out.println("Version2.Philosoph " + name + " go to think about something...");
////        cdlReadyEat.countDown();
//    }


    public void setFork(int fork) {
        this.fork += fork;
    }
}

package Version3;

public class Philosopher implements Runnable{
    private String name;
    Eating eating;
    int right,left;
    public Philosopher(int left,int right,Eating eating, String name){
        this.left=left;
        this.right=right;
        this.eating=eating;
        this.name = name;
        eating.getPhilosopherList().add(this);
    }
    @Override
    public void run(){
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        eating.getFork(left,right);
    }
}

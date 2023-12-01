package Version2;

/**
 * Пять безмолвных философов сидят вокруг круглого стола, перед каждым философом стоит тарелка спагетти.
 * Вилки лежат на столе между каждой парой ближайших философов.
 * Каждый философ может либо есть, либо размышлять.
 * Философ может есть только тогда, когда держит две вилки — взятую справа и слева.
 * Философ не может есть два раза подряд, не прервавшись на размышления (можно не учитывать)
 * Философ может взять только две вилки сразу, то есть обе вилки должны быть свободны
 * Описать в виде кода такую ситуацию. Каждый философ должен поесть три раза
 */
public class Main {
    public static void main(String[] args) {
        Table table = new Table();
        Thread thread = new Thread (new Philosoph("Aristotel", table));
        Thread thread1 = new Thread (new Philosoph("Socrat", table));
        Thread thread2 = new Thread (new Philosoph("Makedonsky", table));
        Thread thread3 = new Thread (new Philosoph("Platon", table));
        Thread thread4 = new Thread (new Philosoph("Pifagor", table));
        thread.start();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();


//        Version2.Table table = new Version2.Table();
//        new Thread(table).start();
//        Thread thread = new Thread (new Version2.Philosoph("Aristotel", table, "plate"));
//        Thread thread1 = new Thread (new Version2.Philosoph("Socrat", table, "plate"));
//        Thread thread2 = new Thread (new Version2.Philosoph("Makedonsky", table, "plate"));
//        Thread thread3 = new Thread (new Version2.Philosoph("Platon", table, "plate"));
//        Thread thread4 = new Thread (new Version2.Philosoph("Pifagor", table, "plate"));
//        thread.start();
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();

//        Map<String, Integer> plateOfSpaghetti = new HashMap<>();
//        int count = 3;
//        plateOfSpaghetti.put("plate" + plateOfSpaghetti.size(), count);
//        plateOfSpaghetti.put("plate" + plateOfSpaghetti.size(), count);
//        plateOfSpaghetti.put("plate" + plateOfSpaghetti.size(), count);
//        plateOfSpaghetti.put("plate" + plateOfSpaghetti.size(), count);
//        plateOfSpaghetti.put("plate" + plateOfSpaghetti.size(), count);
//        System.out.println(plateOfSpaghetti);
//        for (String find : plateOfSpaghetti.keySet()) {
//            if (find.equals("plate2")) {
//                while (count > 0){
//                    plateOfSpaghetti.put("plate2", --count);
//                    System.out.println(count);
//                    System.out.println(plateOfSpaghetti);
//                }
//            }
//        }
//        Map<Integer, Integer> map = new HashMap<>();
//        map.put(map.size(), 3);
//        map.put(map.size(), 3);
//        map.put(map.size(), 5);
//        map.put(map.size(), 3);
//        map.put(map.size(), 3);
//        System.out.println(map);
//        System.out.println(map.get(0));
//        System.out.println(map.get(1));
//        System.out.println(map.get(2));
//        System.out.println(map.keySet().stream().toList().get(0));
//        System.out.println(check(4, map));

    }
//    public static boolean check(int i, Map<Integer, Integer> map) {
//        if (i > 0) {
//            System.out.println("Сравниваем " + i + " " + map.get(i) + " " + map.get(i - 1));
//        } else {
//            System.out.println("Сравниваем " + i + " " + (map.get(i) + " " + map.get(map.size() - 1)));
//        }
//        return (i > 0) ? (map.get(i) && map.get(i - 1)) : (map.get(i) && map.get(map.size() - 1));
//    }
}

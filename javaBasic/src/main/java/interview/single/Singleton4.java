package interview.single;


import java.util.concurrent.*;

/**
 * 懒汉式：
 * 使用双重校验锁，保证性能和线程安全
 */
public class Singleton4 {

    private static Singleton4 instance;

    private Singleton4() {

    }

    public static Singleton4 getStance() {
        if (instance == null) {
            synchronized (Singleton4.class) {
                if (instance == null)
                    instance = new Singleton4();
            }
        }
        return instance;
    }
}


class Singleton4Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Callable<Singleton4> c = new Callable<Singleton4>() {
            @Override
            public Singleton4 call() throws Exception {

                return Singleton4.getStance();
            }
        };
        ExecutorService es = Executors.newFixedThreadPool(2);
        Future<Singleton4> f1 = es.submit(c);
        Future<Singleton4> f2 = es.submit(c);

        Singleton4 s1 = f1.get();
        Singleton4 s2 = f2.get();
        System.out.println(s1 == s2);
        es.shutdown();

    }
}
package lab8.s06_threads;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * Slide 6 — lazy NU e thread-safe. Cu mai multe thread-uri apar mai multe instanțe.
 * Constructorul „durează" puțin (sleep), ca fereastra de concurență să fie vizibilă.
 */
public class Demo04LazyThreads {

    static class UnsafeCache {
        private static UnsafeCache instance;
        private UnsafeCache() { sleep(50); }
        static UnsafeCache getInstance() {
            if (instance == null) {              // două thread-uri trec simultan de aici
                instance = new UnsafeCache();
            }
            return instance;
        }
    }

    static class SyncCache {
        private static SyncCache instance;
        private SyncCache() { sleep(50); }
        static synchronized SyncCache getInstance() {   // simplu, dar fiecare apel ia lock-ul
            if (instance == null) {
                instance = new SyncCache();
            }
            return instance;
        }
    }

    static class HolderCache {
        private HolderCache() { sleep(50); }
        private static class Holder {            // se inițializează la primul getInstance()
            static final HolderCache INSTANCE = new HolderCache();
        }
        static HolderCache getInstance() { return Holder.INSTANCE; }   // JVM garantează o singură inițializare
    }

    interface Getter { Object get(); }

    static int distinctInstances(Getter g) throws InterruptedException {
        Set<Integer> ids = ConcurrentHashMap.newKeySet();
        CountDownLatch start = new CountDownLatch(1);
        Thread[] ts = new Thread[8];
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new Thread(() -> {
                try { start.await(); } catch (InterruptedException ignored) { }
                ids.add(System.identityHashCode(g.get()));
            });
            ts[i].start();
        }
        start.countDown();                       // toate pornesc deodată
        for (Thread t : ts) t.join();
        return ids.size();
    }

    static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException ignored) { }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("8 thread-uri cer instanța în același timp:");
        System.out.println("  lazy simplu   -> " + distinctInstances(UnsafeCache::getInstance) + " instanțe distincte");
        System.out.println("  synchronized  -> " + distinctInstances(SyncCache::getInstance) + " instanță");
        System.out.println("  holder        -> " + distinctInstances(HolderCache::getInstance) + " instanță");
        System.out.println("\nLa laborator și teme nu avem thread-uri, deci varianta simplă ajunge.");
    }
}

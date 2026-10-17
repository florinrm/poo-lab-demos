package lab8.s17_proxy;

import java.util.HashMap;
import java.util.Map;

/**
 * Slide 17 — [Nice to know] Proxy: același contract, acces controlat.
 * 1. virtual proxy (lazy) — încarcă abia la prima utilizare;
 * 2. dar două proxy-uri pentru același fișier încarcă de două ori;
 * 3. caching proxy — un singur obiect real per cheie;
 * 4. protection proxy — verifică dreptul înainte să delege.
 */
public class Demo13Proxy {

    interface Map3D { String render(); }

    /** Obiectul real, scump de creat. */
    static class RealMap3D implements Map3D {
        static int loads = 0;
        private final String region;
        RealMap3D(String region) {
            this.region = region;
            loads++;
            System.out.println("   ... încarc harta " + region + " (2 GB) — încărcarea #" + loads);
        }
        public String render() { return "hartă 3D: " + region; }
    }

    /** 1. Virtual proxy: creează obiectul real abia la primul render(). */
    static class LazyMap3D implements Map3D {
        private final String region;
        private RealMap3D real;
        LazyMap3D(String region) { this.region = region; }
        public String render() {
            if (real == null) real = new RealMap3D(region);
            return real.render();
        }
    }

    /** 3. Caching proxy: un obiect real per regiune, partajat de toți. */
    static class CachedMap3D implements Map3D {
        private static final Map<String, RealMap3D> CACHE = new HashMap<>();
        private final String region;
        CachedMap3D(String region) { this.region = region; }
        public String render() {
            return CACHE.computeIfAbsent(region, RealMap3D::new).render();
        }
    }

    /** 4. Protection proxy. */
    static class PremiumOnlyMap3D implements Map3D {
        private final Map3D inner;
        private final boolean premium;
        PremiumOnlyMap3D(Map3D inner, boolean premium) { this.inner = inner; this.premium = premium; }
        public String render() {
            if (!premium) return "acces refuzat: hărțile 3D sunt doar pentru conturi premium";
            return inner.render();
        }
    }

    public static void main(String[] args) {
        System.out.println("1. Virtual proxy:");
        Map3D m = new LazyMap3D("Carpați");
        System.out.println("   proxy creat, nimic încărcat (loads = " + RealMap3D.loads + ")");
        System.out.println("   " + m.render());
        System.out.println("   " + m.render() + "   <- a doua oară, fără încărcare");

        System.out.println("\n2. Două proxy-uri lazy pentru aceeași regiune:");
        RealMap3D.loads = 0;
        new LazyMap3D("Delta").render();
        new LazyMap3D("Delta").render();
        System.out.println("   loads = " + RealMap3D.loads + "   <- lazy nu înseamnă partajat");

        System.out.println("\n3. Caching proxy:");
        RealMap3D.loads = 0;
        new CachedMap3D("Delta").render();
        new CachedMap3D("Delta").render();
        System.out.println("   loads = " + RealMap3D.loads);

        System.out.println("\n4. Protection proxy:");
        Map3D free = new PremiumOnlyMap3D(new LazyMap3D("Bucegi"), false);
        System.out.println("   " + free.render());

        // Clientul vede peste tot doar Map3D: nu știe care e proxy și care e obiectul real.
    }
}

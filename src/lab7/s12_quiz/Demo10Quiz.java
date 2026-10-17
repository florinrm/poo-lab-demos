package lab7.s12_quiz;

/**
 * Slide 12 — "Ce afișează?". Rulează după ce clasa a răspuns.
 */
public class Demo10Quiz {

    static String q1() {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("A");
            Integer.parseInt("x");
            sb.append("B");
        } catch (NumberFormatException e) {
            sb.append("C");
        } finally {
            sb.append("D");
        }
        sb.append("E");
        return sb.toString();
    }

    static String q2() {
        StringBuilder sb = new StringBuilder();
        try {
            try {
                sb.append("1");
                throw new IllegalStateException();
            } catch (IllegalArgumentException e) {
                sb.append("2");
            } finally {
                sb.append("3");
            }
            sb.append("4");
        } catch (RuntimeException e) {
            sb.append("5");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("Q1: " + q1());   // ACDE
        System.out.println("Q2: " + q2());   // 135
    }
}

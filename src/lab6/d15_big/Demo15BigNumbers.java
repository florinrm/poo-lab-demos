package lab6.d15_big;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Slide 15 — [Optional] BigInteger și BigDecimal.
 */
public class Demo15BigNumbers {
    public static void main(String[] args) {
        // long face overflow, BigInteger nu
        System.out.println("Long.MAX_VALUE + 1 = " + (Long.MAX_VALUE + 1));
        BigInteger big = BigInteger.valueOf(Long.MAX_VALUE).add(BigInteger.ONE);
        System.out.println("BigInteger         = " + big);

        BigInteger fact = BigInteger.ONE;
        for (int i = 2; i <= 30; i++) fact = fact.multiply(BigInteger.valueOf(i));
        System.out.println("30! = " + fact);

        // double nu poate reprezenta exact 0.1
        System.out.println("0.1 + 0.2 = " + (0.1 + 0.2));
        BigDecimal sum = new BigDecimal("0.1").add(new BigDecimal("0.2"));
        System.out.println("BigDecimal: " + sum);

        // Construiește din String, nu din double
        System.out.println("new BigDecimal(0.1)   = " + new BigDecimal(0.1));
        System.out.println("BigDecimal.valueOf(0.1) = " + BigDecimal.valueOf(0.1));

        // Împărțire: fără precizie → excepție dacă rezultatul nu e finit
        try {
            BigDecimal.ONE.divide(new BigDecimal("3"));
        } catch (ArithmeticException e) {
            System.out.println("1/3 fără scale → ArithmeticException");
        }
        System.out.println("1/3, 10 zecimale, HALF_UP = " + BigDecimal.ONE.divide(new BigDecimal("3"), 10, RoundingMode.HALF_UP));
        System.out.println("12.345 → 2 zecimale HALF_EVEN = " + new BigDecimal("12.345").setScale(2, RoundingMode.HALF_EVEN));

        // equals ține cont de scale, compareTo nu
        BigDecimal x = new BigDecimal("1.0"), y = new BigDecimal("1.00");
        System.out.println("1.0 equals 1.00 ? " + x.equals(y) + ", compareTo = " + x.compareTo(y));

        // Imutabile: add întoarce un obiect nou
        BigDecimal price = new BigDecimal("10");
        price.add(BigDecimal.ONE);
        System.out.println("după price.add(1) fără atribuire: " + price);

        // DEMO EROARE: decomentează → bad operand types for binary operator '+'
        // BigInteger wrong = big + BigInteger.ONE;
    }
}

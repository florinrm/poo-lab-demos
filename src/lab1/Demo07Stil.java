package lab1.demo;

/**
 * Demo 7 - Coding style.
 * Aratati intai varianta BadStyle, intrebati ce e gresit, apoi Ctrl+Alt+L
 * (Reformat Code) in IntelliJ si comparati cu varianta buna de mai jos.
 */
public class Demo07Stil {

    // ---- Varianta REA (copiati-o intr-un fisier separat pentru demo) ----
    // public class BadStyle{
    // public static void main(String[]args){
    // int x=10;int y=20;
    // if(x>0) System.out.println("Pozitiv");else{System.out.println("Negativ");}
    // for(int i=0;i<y;i++){System.out.print(i+",");}
    // System.out.println("x="+x+" y="+y);
    // } }

    /**
     * Punctul de intrare al demo-ului.
     *
     * @param args nu sunt folosite
     */
    public static void main(String[] args) {
        int temperature = 10;
        int maxSteps = 20;

        if (temperature > 0) {
            System.out.println("Pozitiv");
        } else {
            System.out.println("Negativ");
        }

        for (int step = 0; step < maxSteps; step++) {
            System.out.print(step + ",");
        }
        System.out.println();
        System.out.println("temperature=" + temperature + " maxSteps=" + maxSteps);
    }
}

package lab2.s08_access.other;

import lab2.s08_access.Student;

/**
 * Alt pachet → rămâne vizibil doar ce e public.
 * Decomentați liniile pe rând ca să arătați erorile de compilare.
 */
public class AccessFromOtherPackage {
    public static void main(String[] args) {
        Student st = new Student();

        // System.out.println(st.fullName);           // private
        // System.out.println(st.getAvgRoundedGrade()); // default — alt pachet
        // System.out.println(st.faculty);            // protected — alt pachet, nu e subclasă
        System.out.println(st.preferredName);
        System.out.println(st.getPromotedMsg());
    }
}

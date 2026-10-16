package lab2.s08_access;

/**
 * Același pachet ca Student → default și protected sunt vizibile, private nu.
 */
public class AccessSamePackage {
    public static void main(String[] args) {
        Student st = new Student();

        // System.out.println(st.fullName);          // private → eroare de compilare
        System.out.println(st.preferredName);         // public
        System.out.println(st.getAvgRoundedGrade());  // default → OK, același pachet
        System.out.println(st.faculty);               // protected → OK, același pachet
        System.out.println(st.getPromotedMsg());
        System.out.println(st.reveal());
    }
}

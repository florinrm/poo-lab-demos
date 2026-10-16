package lab2.demo.s08_access;

/**
 * Slide 8: specificatori de acces. Vezi și other/AccessFromOtherPackage.java.
 */
public class Student {
    private String fullName = "Ion Popescu";
    public String preferredName = "Ion";
    float avgGrade = 9.4f;                 // default (package-private)
    protected String faculty = "ACS";

    int getAvgRoundedGrade() {             // default
        return (int) (avgGrade + 0.5f);
    }

    public String getPromotedMsg() {
        return avgGrade > 5.f ? "Congrats, you passed!" : "Oh no...";
    }

    private String secret() {
        return "doar în clasă: " + fullName;
    }

    public String reveal() {
        return secret();                    // în interiorul clasei avem acces la private
    }
}

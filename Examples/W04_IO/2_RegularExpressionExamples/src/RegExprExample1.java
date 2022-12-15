import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** @author Jon Lewis (jon.lewis@st-andrews.ac.uk) */
public class RegExprExample1 {

    public static void main(String[] args) {
        String test = "The flying fox did something.";

        // MATCH ON EVERY CHARACTER
        Pattern p = Pattern.compile(".");

        Matcher m = p.matcher(test);

        System.out.println(m.replaceAll("X"));

        // MATCH ON SINGLE CHARACTER
        p = Pattern.compile("o");

        m = p.matcher(test);

        System.out.println(m.replaceFirst("a"));
        System.out.println(m.replaceAll("a"));
    }
}

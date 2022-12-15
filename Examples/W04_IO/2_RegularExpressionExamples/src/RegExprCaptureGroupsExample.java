import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** @author Jon Lewis (jon.lewis@st-andrews.ac.uk) */
public class RegExprCaptureGroupsExample {
    public static void main(String[] args) {
        String test = "Hello, my name is Jon Lewis";

        Pattern p = Pattern.compile("(.*)(my name is)\\s([\\w ]+)");
        Matcher m = p.matcher(test);

        if (!m.matches()) {
            System.out.println("Didn't match");
        } else {
            System.out.println("Your name is " + m.group(3));
        }

        // (?:X) match X as non-capturing group
        p = Pattern.compile("(?:.*)(?:my name is)\\s([\\w ]+)");
        m = p.matcher(test);

        if (!m.matches()) {
            System.out.println("Didn't match");
        } else {
            System.out.println("Your name is " + m.group(1));
        }
    }
}

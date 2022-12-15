import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** @author Jon Lewis (jon.lewis@st-andrews.ac.uk) */
public class RegExprExample3 {

    public static void main(String[] args) {
        String test = "The cat hid in the argos catalogue. Cat's like cat food.";
        Pattern p = Pattern.compile("(cat)");
        Matcher m = p.matcher(test);

        System.out.println(m.replaceAll("dog"));

        // FIX CATALOGUE
        p = Pattern.compile("\\bcat\\b");
        m = p.matcher(test);

        System.out.println(m.replaceAll("dog"));

        // FIX CAT'S
        p = Pattern.compile("\\b(Cat|cat)\\b"); // (Cat|cat), [Cc]at
        m = p.matcher(test);

        System.out.println(m.replaceAll("dog"));
    }
}

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** @author Jon Lewis (jon.lewis@st-andrews.ac.uk) */
public class RegExprExample2 {

    public static void main(String[] args) {
        String test = "This line starts with This";

        // MATCH ALL OCCURENCES
        Pattern p = Pattern.compile("This");

        Matcher m = p.matcher(test);

        System.out.println(m.replaceAll("That"));

        // MATCH AT START OF LINE
        p = Pattern.compile("^This");

        m = p.matcher(test);

        System.out.println(m.replaceAll("That"));

        // MATCH AT END OF LINE
        p = Pattern.compile("This$");

        m = p.matcher(test);

        System.out.println(m.replaceAll("a word."));
    }
}

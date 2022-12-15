import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;

/**
 * Program to count the number of occurrences of a
 * word in a given document and print the result to console.
 *
 * @author MatriculationId: 220032472
 * @version 06/10/2022
 */
public class WordCounter {
    /**
     * The main method of the program accepts string array as arguments
     * and returns the count of number of occurrence of words in the given
     * document.
     * @param args - arrays of string arguments of the format <filename> <searchTerms>.
     * @exception IllegalArgumentException is thrown when the argument format is incorrect.
     * @version 06/10/2022
     */
    public static void main(String[] args) throws IllegalArgumentException {
        try {
            if (!(args.length > 1)) {
                throw new IllegalArgumentException("Usage: java WordCounter <filename> <searchTerm>");
            } else {
                String filePath = args[0];
                String searchFlag = args[args.length - 1];
                String[] searchTerms = Arrays.copyOfRange(args, 1, args.length);
                Map<String, Integer> wordMatches = new LinkedHashMap<String, Integer>();
                for (String searchTerm : searchTerms) {
                    wordMatches.put(searchTerm, fileReader(filePath, searchTerm));
                }
                if (wordMatches.size() > 1) {
                    displayMultipleResult(searchTerms, wordMatches);
                }
                else {
                    displaySingleResult(wordMatches);
                }
            }
        } catch (FileNotFoundException | IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            // All other exceptions needs to be handled when required.
            return;
        }
    }

    /**
     * This method is used read a file using a file path and find the word occurence
     * in that given file path.
     *
     * @param filePath   Path to file in which the word needs to be searched in.
     * @param searchTerm Text that needs to be searched.
     * @return Count of number of occurrence of words in the given document.
     * @throws FileNotFoundException when the file is not found in the given
     *                               location.
     * @version 06/01/2022
     */
    static int fileReader(String filePath, String searchTerm) throws FileNotFoundException {
        int wordCount = 0;
        File textFile = new File(filePath);
        try {
            Scanner fileReader = new Scanner(textFile);
            String fileContent;
            while (fileReader.hasNextLine()) {
                fileContent = fileReader.nextLine();
                wordCount += wordMatchCounter(fileContent, searchTerm);
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            Path path = Paths.get(filePath);
            Path fileName = path.getFileName();
            throw new FileNotFoundException("File not found: " + fileName.toString());
        }
        return wordCount;
    }

    /**
     * This method is used get the frequency of the occurrence of a given
     * word in a given text.
     *
     * @param String searchText - Text in which the word needs to be searched in.
     * @param String searchTerm - Text that needs to be searched.
     * @return Number of times a word appears in the given document.
     * @version 06/01/2022
     */
    static int wordMatchCounter(String searchText, String searchTerm) {
        /**
         * The logic to find the exact word match using regex pattern
         * was referenced from a post in stackoverflow
         * https://stackoverflow.com/questions/2765482/find-a-complete-word-in-a-string-javat"
         */
        int wordOccurrenceCounter = 0;
        Pattern pattern = Pattern.compile(".*?\\b" + searchTerm + "\\b.*?");
        Matcher match = pattern.matcher(searchText);
        while (match.find()) {
            wordOccurrenceCounter++;
        }
        return wordOccurrenceCounter;
    }

    /**
     * This method is used to print word counter results in case of
     * multiple words.
     *
     * @param searchTerms - array of string that is being searched.
     * @param wordMatches - contains dictionary of words and their occurrence
     *                      frequency in file .
     * @version 06/10/2022
     */
    private static void displayMultipleResult(String[] searchTerms, Map<String, Integer> wordMatches) {
        int totalOccurrenceCount = 0;
        String wordHeader = "WORD";
        String countHeader = "COUNT";
        String totalHeader = "TOTAL";
        int additionalSpaceOnSide = 2;

        // Creating an ArrayList of count value in word match
        int[] wordCountArray = new int[wordMatches.size()];
        int index = 0;
        for (int count : wordMatches.values()) {
            wordCountArray[index] = count;
            index++;
        }

        int leftIndentSpace = largestCountValueLength(wordCountArray) < countHeader.length() ? countHeader.length()
                : largestCountValueLength(wordCountArray);
        int spacesToIndent = longestStringLength(searchTerms) < wordHeader.length() + additionalSpaceOnSide
                ? wordHeader.length() + additionalSpaceOnSide
                : longestStringLength(searchTerms);
        String leftAlignFormat = "| %" + -spacesToIndent + "s | %" + leftIndentSpace + "s | %n";
        String divider = "|%" + -spacesToIndent + "s|%" + leftIndentSpace + "s|%n";
        System.out.printf(divider, "-".repeat(spacesToIndent + additionalSpaceOnSide),
                "-".repeat(leftIndentSpace + additionalSpaceOnSide));

        System.out.printf(leftAlignFormat, wordHeader, countHeader);
        System.out.printf(divider, "-".repeat(spacesToIndent + additionalSpaceOnSide),
                "-".repeat(leftIndentSpace + additionalSpaceOnSide));

        for (Map.Entry<String, Integer> eachSearchTerm : wordMatches.entrySet()) {
            String searchTerm = eachSearchTerm.getKey();
            Integer occurrenceCount = eachSearchTerm.getValue();
            System.out.printf(leftAlignFormat, searchTerm, occurrenceCount);
            totalOccurrenceCount += occurrenceCount;
        }

        System.out.printf(divider, "-".repeat(spacesToIndent + additionalSpaceOnSide),
                "-".repeat(leftIndentSpace + additionalSpaceOnSide));
        System.out.printf(leftAlignFormat, totalHeader, totalOccurrenceCount);
        System.out.printf(divider, "-".repeat(spacesToIndent + additionalSpaceOnSide),
                "-".repeat(leftIndentSpace + additionalSpaceOnSide));
    }

    /**
     * This method is used to print word counter results in case of
     * single words.
     *
     * @param wordMatches - contains dictionary of words and their occurrence
     *                    frequency in file .
     * @version 06/10/2022
     */
    private static void displaySingleResult(Map<String, Integer> wordMatches) {
        Map.Entry<String, Integer> firstSearchTerm = wordMatches.entrySet().iterator().next();
        System.out.println("The word '" + firstSearchTerm.getKey() + "' appears " + firstSearchTerm.getValue()
                + (firstSearchTerm.getValue() == 1 ? " time." : " times."));
    }

    /**
     * This method is used to find the length of the longest word in
     * input string array.
     *
     * @param inputSearchStringArray - array of string that is being searched.
     * @return length of the longest word.
     * @version 06/10/2022
     */
    static int longestStringLength(String[] inputSearchStringArray) {
        int longestWordLength = 0;
        for (String searchString : inputSearchStringArray) {
            if (searchString.length() > longestWordLength) {
                longestWordLength = searchString.length();
            }
        }
        return longestWordLength;
    }

    /**
     * This method is used to find the length of the largest count value.
     *
     * @param wordOccurrenceCount - array of word count values.
     * @return length of the largest count value.
     * @version 06/10/2022
     */
    static int largestCountValueLength(int[] wordOccurrenceCount) {
        int largestCountValue = 0;
        for (int count : wordOccurrenceCount) {
            if (largestCountValue < count) {
                largestCountValue = count;
            }
        }
        return String.valueOf(largestCountValue).length();
    }
}

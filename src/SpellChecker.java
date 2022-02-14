import java.sql.Array;
import java.util.*;
import java.io.*;

public class SpellChecker {
    public static void main(String[] args) {

        // Step 1: Demonstrate tree capabilities
        testTree();

        // Step 2: Read the dictionary and report the tree statistics
        BinarySearchTree<String> dictionary = readDictionary();
        reportTreeStats(dictionary);

        // Step 3: Perform spell checking
        spellCheck(readDictionary(), readFileLetter());
    }

    /**
     * This method is used to help develop the BST, also for the grader to
     * evaluate if the BST is performing correctly.
     */
    public static void testTree() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();

        //
        // Add a bunch of values to the tree
        tree.insert("Olga");
        tree.insert("Tomeka");
        tree.insert("Benjamin");
        tree.insert("Ulysses");
        tree.insert("Tanesha");
        tree.insert("Judie");
        tree.insert("Tisa");
        tree.insert("Santiago");
        tree.insert("Chia");
        tree.insert("Arden");

        //
        // Make sure it displays in sorted order
        tree.display("--- Initial Tree State ---");
        reportTreeStats(tree);

        //
        // Try to add a duplicate
        if (tree.insert("Tomeka")) {
            System.out.println("oops, shouldn't have returned true from the insert");
        }
        tree.display("--- After Adding Duplicate ---");
        reportTreeStats(tree);

        //
        // Remove some existing values from the tree
        tree.remove("Olga");    // Root node
        tree.remove("Arden");   // a leaf node
        tree.display("--- Removing Existing Values ---");
        reportTreeStats(tree);

        //
        // Remove a value that was never in the tree, hope it doesn't crash!
        tree.remove("Karl");
        tree.display("--- Removing A Non-Existent Value ---");
        reportTreeStats(tree);
    }

    /**
     * This method is used to report on some stats about the BST
     */
    public static void reportTreeStats(BinarySearchTree<String> tree) {
        System.out.println("-- Tree Stats --");
        System.out.printf("Total Nodes : %d\n", tree.numberNodes());
        System.out.printf("Leaf Nodes  : %d\n", tree.numberLeafNodes());
        System.out.printf("Tree Height : %d\n", tree.height());
    }

    /**
     * This method reads the dictionary and constructs the BST to be
     * used for the spell checking.
     */
    public static BinarySearchTree<String> readDictionary() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        // make an array list to store all the words into so that they can be shuffled
        ArrayList<String> wordList = new ArrayList<>();

        // read the file and put the words into an array list
        File file = new File("Dictionary.txt");
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                String word = input.nextLine();
                wordList.add(word);
            }
        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the file: " + ex);
        }

        // randomize the array list
        Collections.shuffle(wordList, new java.util.Random(System.currentTimeMillis()));

        //make words lower case
        toLowerCase(wordList);


        // now insert the items from the array list into the search tree
        for (int wordIndex = 0; wordIndex < wordList.size(); wordIndex++) {
            tree.insert(wordList.get(wordIndex));
        }


        return tree;
    }


    // a method to read the letter.txt file and return it as a search tree
    public static ArrayList<String> readFileLetter() {
        // make an array list to store the words in from letter
        ArrayList<String> listOfWords = new ArrayList<>();

        // read the file Letter.txt and break it up into words
        File file = new File("Letter.txt");
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                String word = input.next();
                listOfWords.add(word);
            }
        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the file: " + ex);
        }


        //make words lower case
        toLowerCase(listOfWords);

        // remove all the special characters from each word
        removeChar(listOfWords);


        return listOfWords;
    }


    // method to print and compare the words from Letter.txt to Dictionary.txt
    public static void spellCheck(BinarySearchTree<String> dictionary, ArrayList<String> letter) {
        System.out.println("--- Misspelled Words ---");
        for ( int word = 0; word < letter.size(); word++) {
            String wordCheck = letter.get(word);
            if (!dictionary.search(wordCheck)) {
                System.out.println(wordCheck);
            }
        }
    }

    // method that takes an array list and makes all the elements lower case
    public static ArrayList<String> toLowerCase(ArrayList<String> list) {
        //make words lower case
        for (int wordIndex = 0; wordIndex < list.size(); wordIndex++) {
            list.set(wordIndex, list.get(wordIndex).toLowerCase());
        }
        return list;
    }

    // remove all the special characters from each word
    public static ArrayList<String> removeChar(ArrayList<String> list) {
        // remove all the special characters from each word
        for (int wordIndex = 0; wordIndex < list.size(); wordIndex++) {
            String currentString = list.get(wordIndex);
            Character lastCharacter = currentString.charAt(currentString.length()-1);
            if(!Character.isLetterOrDigit(lastCharacter)) {
                list.set(wordIndex,currentString.substring(0,currentString.length()-1));
            }

        }
        return list;
    }


}



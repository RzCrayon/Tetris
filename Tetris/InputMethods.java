import java.awt.Color;
import java.util.*;

import javax.swing.*;

/**
 * @author Ari Kralios
 *         Period 2 APCSA
 * @version 1.0
 *          <h1>InputMethods</h1>Set of static methods to expedite the process
 *          of obtaining valid user input
 */
public class InputMethods {
    /**
     * @param low  lowest possible value for the random number
     * @param high highest possible value for the random number
     * @return random integer between low and high inclusive
     */
    public static int getRandom(int low, int high) {
        return (int) (Math.random() * (high - low + 1) + low);
    }

    /**
     * console input of integer from user
     * 
     * @param message prompt to user
     * @param low     lowest possible input value
     * @param high    highest possible input value
     * @return user entered value between low and high inclusive
     */
    public static int getIntBetween(String message, int low, int high) {
        Scanner input = new Scanner(System.in);
        boolean done = false;
        int num = Integer.MAX_VALUE;
        do {
            try {
                System.out.print(message + " between " + low + " and " + high + ": ");
                num = input.nextInt();
                if (num >= low && num <= high)
                    done = true;

            } catch (InputMismatchException ex) {
                System.out.println("Please enter a valid integer");
                input.nextLine(); // flush input stream since input was a character
            }

        } while (!done);

        return num;
    }

    /**
     * GUI input of integer from user
     * 
     * @param message prompt to user
     * @param low     lowest possible input value
     * @param high    highest possible input value
     * @return user entered value between low and high inclusive
     */
    public static int getIntBetweenJOP(String message, int low, int high) {

        boolean done = false;
        int num = Integer.MAX_VALUE;
        do {
            try {

                num = Integer
                        .parseInt(JOptionPane.showInputDialog(message + " between " + low + " and " + high + ": "));
                if (num >= low && num <= high)
                    done = true;

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid integer");

            }

        } while (!done);

        return num;
    }

    /**
     * console input of double from user
     * 
     * @param message prompt to user
     * @param low     lowest possible input value
     * @param high    highest possible input value
     * @return user entered value between low and high inclusive
     */
    public static double getDoubleBetween(String message, double low, double high) {
        Scanner input = new Scanner(System.in);
        boolean done = false;
        double num = Double.MAX_VALUE;
        do {
            try {
                System.out.print(message + " between " + low + " and " + high + ": ");
                num = input.nextDouble();
                if (num >= low && num <= high)
                    done = true;

            } catch (InputMismatchException ex) {
                System.out.println("Please enter a valid decimal value");
                input.nextLine(); // flush input stream since input was a character
            }

        } while (!done);

        return num;
    }

    /**
     * GUI input of integer from user
     * 
     * @param message prompt to user
     * @param low     lowest possible input value
     * @param high    highest possible input value
     * @return user entered value between low and high inclusive
     */
    public static double getDoubleBetweenJOP(String message, double low, double high) {

        boolean done = false;
        double num = Double.MAX_VALUE;
        do {
            try {

                num = Double
                        .parseDouble(JOptionPane.showInputDialog(message + " between " + low + " and " + high + ": "));
                if (num >= low && num <= high)
                    done = true;

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid integer");

            }

        } while (!done);

        return num;
    }

    /**
     * Gets a Y/N choice from the user- console based
     * 
     * @param question- y/n question for the user
     * @return true if user answers y, false otherwise
     */
    public static boolean getChoiceYN(String question) {
        boolean repeat = true;
        String choice;
        Scanner input = new Scanner(System.in);

        do {
            System.out.print(question + "(Enter y or n): ");
            choice = input.next().substring(0, 1).toLowerCase();

            if (choice.equals("y"))
                return true;
            else if (choice.equals("n"))
                return false;
            else {
                System.out.println("Invalid Input: enter y or n");

            }
        } while (repeat);
        return false; // should never happen
    }

    /**
     * @param question- y/n question for the user
     * @return true if user answers y, false otherwise
     */
    public static boolean getChoiceYNJOP(String question) {

        int choice;

        do {

            choice = JOptionPane.showConfirmDialog(null,
                    question,
                    "Choose an option",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION)
                return true;
            else if (choice == JOptionPane.NO_OPTION)
                return false;

        } while (choice == JOptionPane.CLOSED_OPTION);

        return false; // should never happen
    }

    public static boolean getChoiceYNJOP_withPicture(String question) {

        int choice;

        ImageIcon icon = new ImageIcon("nextPieceIcon.png");

        do {

            choice = JOptionPane.showConfirmDialog(null,
                    new JLabel("\n" + question, icon, JLabel.LEFT),
                    "Choose an option",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION)
                return true;
            else if (choice == JOptionPane.NO_OPTION)
                return false;

        } while (choice == JOptionPane.CLOSED_OPTION);

        return false; // should never happen
    }

    /**
     * Console version -gets user choice out of a list of options
     * 
     * @param question Question to ask the user
     * @param choice   string array of user choices
     * @return zero based index of choice
     */
    public static int menuConsole(String question, String[] choices) {

        int choice;

        System.out.println(question + "\n");

        // output all choices
        for (int i = 1; i <= choices.length; i++) {
            System.out.println((i) + ". " + choices[i - 1]);

        }

        choice = getIntBetween("\nEnter choice:", 1, choices.length);

        System.out.println(choice);

        return choice - 1; // return array-based choice
    }

    /**
     * GUI version-Gets user choice from a list of buttons
     * 
     * @param question Question to ask the user
     * @param choice   string array of user choices
     * @return zero based index of choice
     */
    public static int getButtonChoiceJOP(String question, String[] choices) {
        int choice;
        do {
            choice = JOptionPane.showOptionDialog(null,
                    question,
                    "Choose an Option",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choices,
                    choices[0]);

        } while (choice == JOptionPane.CLOSED_OPTION);
        return choice;
    }

    /**
     * Obtains a choice from the user, given a list of choices. GUI based
     * 
     * @param question Intitial question for the user
     * @param choices  String array of possible choices
     * @return string choice of the user
     */
    public static String getDropdownMenuChoiceJOP(String question, String[] choices) {
        String choice = null;

        do {
            choice = (String) JOptionPane.showInputDialog(null,
                    question,
                    "Choose an Option",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choices,
                    choices[0]);

        } while (choice == null);
        return choice;
    }

    public static String getDropdownMenuChoiceJOP_modified(String question, String[] choices) {
        String choice = null;

        choice = (String) JOptionPane.showInputDialog(null,
                question,
                "Choose an Option",
                JOptionPane.QUESTION_MESSAGE,
                null,
                choices,
                choices[0]);

        return choice;
    }

}

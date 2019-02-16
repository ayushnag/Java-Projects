/*
 * LottoSim.java
 * by Ayush Nag and Ali Ankoud
 * 1/12/18
 */
package Unit_7;

import java.util.*;

public class LottoSim {

    private static final int BET_MIN = 1;
    private static final int BET_MAX = 1000;
    private static final int BETSIZE_MIN = 1;
    private static final int BETSIZE_MAX = 49;
    private static final int BET_SIZE = 6;
    public static int[] winNum = new int[BET_SIZE];
    public static int[] userNum = new int[BET_SIZE];
    public static int[] array1 = new int[BET_SIZE];
    public static int[] array2 = new int[BET_SIZE];
    private static int JACKPOT = 1000000;
    public static int winnings = 0;
    public static int expenses = 0;
    public static int netValue = 0;
    public static int matches;
    public static int betCreationCounter = 0;
    public static int betLines = 0;
    public static boolean continueGame;

    public static void main(String[] args) {
        Scanner kbReader = new Scanner(System.in);
        Random gen = new Random();
        String chooseBets = "";
        System.out.println("Welcome to the Washington Lotto!!");
        while (promptUser(kbReader) == true) {
            System.out.println("The jackpot is worth " + JACKPOT + " dollars!!");
            chooseBets = initialize(chooseBets, kbReader);
            winNum = generateBetValues(gen, array1);
            if (chooseBets.equals("y")) {
                choseYes(kbReader, chooseBets);
            }
            if (chooseBets.equals("n")) {
                choseNo(kbReader, chooseBets, gen);
            }
            if (matches == 6) {
                JACKPOT = 1000000;
            }
            JACKPOT += 1000000;
        }
        System.out.println("Winnings: " + winnings + " dollars");
        System.out.println("Expenses: " + expenses + " dollars");
        System.out.println("Net value: " + (winnings - expenses) + " dollars");
        System.out.println("Thanks for playing! ");
    }

    public static String initialize(String chooseBets, Scanner kbReader) {
        boolean validInput = false;
        double betAmount = 0;
        System.out.print("Please print your bet amount ($1 - $1000): $");
        while (validInput == false) {
            if (kbReader.hasNextDouble()) {
                betAmount = kbReader.nextDouble();
                if (betAmount <= BET_MAX && betAmount >= BET_MIN) {
                    validInput = true;
                    continue;
                } else {
                    System.out.print("Please enter a valid bet amount: $");
                }
            } else {
                System.out.print("Please enter a valid bet amount, you weren't even close: $");
                kbReader.next();
            }
        }

        expenses += betAmount;
        betLines = (int) betAmount * 2;
        System.out.println("You can pick a set of 6 numbers making one play. Or you can have the computer pick 6 numbers for you.");
        System.out.print("Would you like to choose your own plays? (y or n)?: ");
        chooseBets = kbReader.next();

        while (!chooseBets.equals("y") && !chooseBets.equals("n")) {
            System.out.print("Please, type y or n: ");
            kbReader.reset();
            chooseBets = kbReader.next().toLowerCase();
        }

        while (!chooseBets.equals("y") && !chooseBets.equals("n")) {
            System.out.print("Please, type y or n: ");
            chooseBets = kbReader.nextLine();
        }
        return chooseBets;
    }

    public static void choseYes(Scanner kbReader, String chooseBets) {
        int counter = 0;
        int betValue = 0;
        System.out.println("Bets can only be between 1 and 49");
        while (betCreationCounter < betLines) {
            counter = 0;
            for (int x = 0; x < userNum.length; x++) {
                userNum[x] = 0;
            }
            for (int i = 1; i <= 6; i++) {
                boolean validInput = false;
                System.out.print("Enter play slot " + i + ": ");
                while (validInput == false) {
                    if (kbReader.hasNextDouble()) {
                        betValue = kbReader.nextInt();
                        if (betValue <= BETSIZE_MAX && betValue >= BETSIZE_MIN) {
                            if (numberNotInArray(betValue, userNum) == true) {
                                validInput = true;
                                counter = testUserValues(betValue, counter, userNum);
                                continue;
                            } else {
                                System.out.print("Please enter a valid play slot, you already had one of those!: ");
                            }
                        } else {
                            System.out.print("Please enter a valid play slot, has to be between 1 and 49: ");
                        }
                    } else {
                        System.out.print("Please enter a valid play slot, it's not that hard: ");
                        kbReader.next();
                    }
                }
            }
            betCreationCounter++;
            System.out.print("Play #" + betCreationCounter + ": ");
            for (int x = 0; x < userNum.length; x++) {
                System.out.print(userNum[x] + " ");
            }
            matches = compareArrays(kbReader, userNum, winNum);
            winnings += determineAmountWon(matches);
            counter = 0;
            System.out.println();
        }
    }

    public static void choseNo(Scanner kbReader, String chooseBets, Random gen) {
        for (int i = 1; i <= betLines; i++) {
            userNum = generateBetValues(gen, array2);
            matches = compareArrays(kbReader, winNum, userNum);
            System.out.print("Play #" + i + ": ");
            for (int x = 0; x < userNum.length; x++) {
                System.out.print(userNum[x] + " ");
            }
            winnings += determineAmountWon(matches);
            System.out.println();
        }
        System.out.println("You won " + winnings + " dollars");
    }

    public static boolean promptUser(Scanner kbReader) {
        System.out.print("Do you want to play the lotto? (y or n): ");
        String wantToPlay = kbReader.next().toLowerCase();

        while (!wantToPlay.equals("y") && !wantToPlay.equals("n")) {
            System.out.print("Please, type y or n: ");
            kbReader.reset();
            wantToPlay = kbReader.next().toLowerCase();
        }

        if (wantToPlay.equals("y")) {
            continueGame = true;
        } else if (wantToPlay.equals("n")) {
            continueGame = false;
        }
        return continueGame;
    }

    public static boolean numberNotInArray(int n, int[] a) {
        for (int i = 0; i < a.length; i++) {
            if (n == a[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] generateBetValues(Random gen, int[] arrayCreator) {
        int counter = 0;
        do {
            int candidateNumber = gen.nextInt(BETSIZE_MAX) + 1;
            if (numberNotInArray(candidateNumber, arrayCreator)) {
                arrayCreator[counter] = candidateNumber;
                counter++;
            }
        } while (counter < BET_SIZE);
        return arrayCreator;
    }

    public static int testUserValues(int betValue, int counter, int[] userNum) {
        int candidateNumber = betValue;
        if (numberNotInArray(candidateNumber, userNum)) {
            userNum[counter] = candidateNumber;
            counter++;
        }
        return counter;
    }

    public static int compareArrays(Scanner kbReader, int[] array1, int[] array2) {
        int matches = 0;
        for (int x = 0; x < array1.length; x++) {
            for (int y = 0; y < array2.length; y++) {
                if (array1[x] == array2[y]) {
                    matches++;
                }
            }
        }
        return matches;
    }

    public static int determineAmountWon(int matches) {
        if (matches == 0 || matches == 1 || matches == 2) {
            return 0;
        } else if (matches == 3) {
            return 3;
        } else if (matches == 4) {
            return 30;
        } else if (matches == 5) {
            return 1000;
        } else if (matches == 6) {
            return JACKPOT;
        }
        return 0;
    }
}
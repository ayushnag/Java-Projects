/*
 * LottoBet.java
 * by Ayush Nag
 * 1/5/2018
 */
package Unit1;
/**
 *
 * @author s-anag
 */
import java.util.Random;
import java.util.Arrays;

public class LottoBet 
{
    public static final int BETSIZE = 6;
    public static final int MIN = 1;
    public static final int MAX = 49;
    public static void main(String[] args)
    {
        Random gen = new Random() ;
        int[] myBet = new int[BETSIZE];
        int[] winNum = new int[BETSIZE];
        int matches, tries = 0;
        long counter = 0, total = 0;
        for(int x = 1; x <= 10; x++)
        {
            counter = 0;
            do
            {
                Logic(gen, myBet, winNum);
                matches = checkMatch(myBet, winNum);
                Arrays.sort(myBet);
                Arrays.sort(winNum);
                counter ++;
            }
            while(matches != 6);
            System.out.println("Matched after " + counter + " tries");
            total += counter;
            tries ++;
        }
        System.out.println("The averge amount of tries is " + (total/tries));
    }
    public static void Logic(Random gen, int[] myBet, int[] winNum)
    {
        int counter  = 0;
        int candidateNumber;
        do
        {
            candidateNumber = gen.nextInt(MAX) + 1;
            if(numberNotInArray(candidateNumber, myBet))
            {
                myBet[counter] = candidateNumber;
                counter++;
            }
        }
        while(counter < BETSIZE);
        counter = 0;
        do
        {
            candidateNumber = gen.nextInt(MAX) + 1;
            if(numberNotInArray(candidateNumber, winNum))
            {
                winNum[counter] = candidateNumber;
                counter++;
            }
        }
        while(counter < BETSIZE);
    }
    public static int checkMatch(int[] myBet, int[] winNum)
    {
        int matches = 0;
        for(int x = 0; x < myBet.length; x++)
        {
            for(int y = 0; y < winNum.length; y++)
            {
                if(myBet[x] == winNum[y])
                {
                    matches ++;
                }
            }
        }
        return matches;
    }
    public static boolean numberNotInArray(int n, int[] a)
    {
        for(int i = 0; i < a.length; i++)
        {
            if(n == a[i])
            {
                return false;
            }
        }
        return true;
    }
}
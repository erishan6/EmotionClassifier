package com.teamlab.ss18.ec;

/**
 * Created by deniz on 13.04.18.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

/**
 * this class serves the sole purpose of having a main method to try small code snippets and see what they do
 *
 */
public class Scribble_Deniz {
    public static void main(String[] args) throws FileNotFoundException {
//        int a = 8;
//        int b = -8;
//        System.out.println(a%b);
        String filePath = "data/trial.csv";
        String goldPath = "data/trial.labels";
        Scanner scanner = new Scanner(new File(filePath));
        scanner.useDelimiter("\n");
        Scanner scanner2 = new Scanner(new File(goldPath));
        scanner2.useDelimiter("\n");
        PrintWriter fs = new PrintWriter("data/train_trial.csv");
        while (scanner.hasNext() && scanner2.hasNext()) {
            String mystring = scanner.next();
            String arr[] = mystring.split("\t| ", 2);
//            String label = arr[0];
            String sentence = arr[1];
            String goldLabel = scanner2.next();
            fs.print(goldLabel+ " "+sentence+"\n");
        }
        scanner.close();
        scanner2.close();
        fs.close();
    }
}

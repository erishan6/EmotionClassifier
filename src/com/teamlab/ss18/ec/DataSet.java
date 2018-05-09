package com.teamlab.ss18.ec;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by deniz on 02.05.18.
 */
public class DataSet {
    String[] train;
    String[] test;


    public void trainTestSplit(String filePath, double trainPercentage, boolean writeToFile) throws IOException {

        Scanner scanner = new Scanner(new File(filePath));
        scanner.useDelimiter("\n");

        int numberOfSamples = 0;
        while (scanner.hasNext()) {
            numberOfSamples++;
            scanner.next();
        }


        int numberOfTrainSamples = (int) (numberOfSamples * trainPercentage);
        int numberOfTestSamples = numberOfSamples - numberOfTrainSamples;
        this.train = new String[numberOfTrainSamples];
        this.test = new String[numberOfTestSamples];


        String fileNameRoot = "";
        if (filePath.contains(".csv"))
            fileNameRoot = filePath.substring(0,filePath.indexOf(".csv"));
        else
            fileNameRoot = filePath;


        String trainOutFileName = fileNameRoot+"_train.csv";
        String testOutFileName = fileNameRoot+"_test.csv";

        Writer trainWriter = new Writer(trainOutFileName);
        Writer testWriter = new Writer(testOutFileName);

        scanner = new Scanner(new File(filePath));
        scanner.useDelimiter("\n");
        for (int i = 0; i < numberOfTrainSamples; i++) {
            String nextTweetRaw = scanner.next();
            if(writeToFile)
                trainWriter.write(nextTweetRaw+"\n");
            train[i] = nextTweetRaw;
        }

        for (int i = 0; i < numberOfTestSamples; i++) {
            String nextTweetRaw = scanner.next();
            if(writeToFile)
                testWriter.write(nextTweetRaw+"\n");
            test[i] = nextTweetRaw;
        }

        scanner.close();
        trainWriter.close();
        testWriter.close();
    }

    //TODO: implement 10-fold cross-validation



    public static void main(String[] args) throws IOException {
        DataSet dataSet = new DataSet();
        boolean writeToFile = false;
        //dataSet.trainTestSplit("data/full/data_original", 0.7, writeToFile);
        dataSet.trainTestSplit("data/small/trial_small.csv", 0.5, writeToFile);
        String[] trainSamples = dataSet.train;
        String[] testSamples = dataSet.test;
        for (String trainSample : trainSamples) {
            System.out.println(trainSample);
        }
        for (String testSample : testSamples) {
            System.out.println(testSample);
        }


    }
}

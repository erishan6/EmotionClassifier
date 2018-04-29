package com.teamlab.ss18.ec;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
        // TODO Ask prof for repeation case and approach
        /*
        ****************************
        * Set init parameters      *
        ****************************
        */
        String filepath = "data/small/trial_joy.csv";
        int epochs = 100;
        boolean shuffle = true;

        /*
        ****************************
        * Set init parameters      *
        ****************************
        */

        Corpus trainCorpus = new Corpus("trainCorpus", filepath, 6);

        //Perceptron perceptron = new Perceptron();
        PerceptronMap perceptron = new PerceptronMap();
        perceptron.fit(trainCorpus,epochs, shuffle, 2, 10);
        perceptron.predict(trainCorpus);


        ArrayList<Tweet> a = new ArrayList<>();
        a.addAll(trainCorpus.getTweets().values());

        System.out.println("-----------------");
        System.out.println("Evaluation on testset:");

        //testCorpus zum test nehmen
        Evaluator evaluator = new Evaluator(trainCorpus); //TODO: pass testCorpus not trainCorpus
        evaluator.printConfusionMatrix();
        System.out.println();
        evaluator.printEvalResults();

        String filepath2 = "data/train.csv";
        Corpus trainCorpus2 = new Corpus("trainCorpus", filepath2, 6);
        System.out.println(trainCorpus2.wordsList().get("\uD83D\uDE0B"));
//        trainCorpus.printWordEmotionCount();
        System.out.println(trainCorpus2.getVocabularySize()*0.01);

    }
}

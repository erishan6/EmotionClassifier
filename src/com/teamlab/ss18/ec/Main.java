package com.teamlab.ss18.ec;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
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
        System.out.println("training on " + filepath2);
        Corpus trainCorpus2 = new Corpus("trainCorpus", filepath2, 6);
//        trainCorpus2.printWordEmotionCount();
        BayesianClassifier bayesianClassifier = new BayesianClassifier(trainCorpus2);
//        bayesianClassifier.predictLabel_BayesianClassifier("@USERNAME @USERNAME Forget about him Bill, I've looked at his twitter feed & it obvious that he has mental health issues. We shouldn't mock the afflicted![NEWLINE]He gets [#TRIGGERWORD#] because of his inadequacies & just like a bully he tries to take it out on others.[NEWLINE]I do feel sorry for him!()*^%@!!-");
        Corpus c = bayesianClassifier.do_classify();
        Evaluator evaluator2 = new Evaluator(c);
        evaluator2.printConfusionMatrix();
        System.out.println();
        evaluator2.printEvalResults();
        System.out.println("******************************");
        String filepath3 = "data/train_trial.csv";
        System.out.println("testing on " + filepath3);
        Corpus testCorpus = new Corpus("testCorpus", filepath3, 6);
        Corpus c2 = bayesianClassifier.predictLabel_BayesianClassifier(testCorpus);
        Evaluator evaluator3 = new Evaluator(c2);
        evaluator3.printConfusionMatrix();
        System.out.println();
        evaluator3.printEvalResults();
        System.out.println("******************************");
        System.out.println("training on " + filepath3);
        Corpus trainCorpus3 = new Corpus("trainCorpus2", filepath3, 6);
        BayesianClassifier bayesianClassifier2 = new BayesianClassifier(trainCorpus3);
        Corpus c3 = bayesianClassifier2.do_classify();
        Evaluator evaluator4 = new Evaluator(c3);
        evaluator4.printConfusionMatrix();
        System.out.println();
        evaluator4.printEvalResults();
    }
}

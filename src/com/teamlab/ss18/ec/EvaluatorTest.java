package com.teamlab.ss18.ec;

/**
 * Created by deniz on 13.04.18.
 */

/**
 * claass to test Evaluator
 */
public class EvaluatorTest {

    public static void main(String[] args) {
        String[] yPred = {"a", "a", "a", "a", "a", "a"};
        String[] yGold = {"b", "b", "c", "b", "a", "a"};
        Evaluator testEvaluator = new Evaluator(yPred, yGold, 3);

        /*
        System.out.println("confusionMatix");
        for (double[] row : testEvaluator.getConfusionMatrix()) {
            for (double cell : row) {
                System.out.print(cell+" ");
            }
            System.out.println();
        }
        System.out.println();

        */

        for (String label : Label.getLabelsMap().keySet()) {
            System.out.println("label: '"+label+"'");
            System.out.println("\tprecision: "+testEvaluator.getPrecisionFor(label));
            System.out.println("\trecall: "+testEvaluator.getRecallFor(label));
            System.out.println("\tf1: "+testEvaluator.getFScoreFor(label));
        }

        System.out.println("avg precision: "+testEvaluator.getPrecisionAverage());
        System.out.println("avg recall: "+testEvaluator.getRecallAverage());
        System.out.println("avg Fscore: "+testEvaluator.getFScoreAverage());
    }
}

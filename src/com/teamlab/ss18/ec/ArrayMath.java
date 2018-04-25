package com.teamlab.ss18.ec;

/**
 * Created by deniz on 19.04.18.
 */
public class ArrayMath {

    /**
     * calculates dotProduct of two vectors
     * @param vectorA
     * @param vectorB
     * @return
     */
    public static int dot(int[] vectorA, int[] vectorB){
        int result = 0;
        for (int i = 0; i < vectorA.length; i++) {
            result += vectorA[i]*vectorB[i];
        }
        return result;
    }

    /**
     * adds vectorA and vectorB
     * @param vectorA
     * @param vectorB
     * @return
     */
    public static int[] add(int[] vectorA, int[] vectorB){
        int[] result = new int[vectorA.length];

        for (int i = 0; i < vectorA.length; i++) {
            result[i] = vectorA[i]+vectorB[i];
        }
        return result;
    }

    /**
     * subtracte vectorB from VectorA
     * @param vectorA
     * @param vectorB
     * @return
     */
    public static int[] subtract(int[] vectorA, int[] vectorB){
        int[] result = new int[vectorA.length];

        for (int i = 0; i < vectorA.length; i++) {
            result[i] = vectorA[i]-vectorB[i];
        }
        return result;
    }

    /**
     * returns index of max value
     * @param vector
     * @return
     */
    public static int argmax(int[] vector){
        int maxIndex = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < vector.length; i++) {
            int value = vector[i];
            if (value > max){
                max = value;
                maxIndex = i;
            }
        }
        return maxIndex;
    }




    public static void main(String[] args) {
        int[] a = {1,2,3,4};
        int[] b = {4,3,2,1};

        int d = dot(a,b);
        System.out.println(d);

        System.out.println(argmax(a));
        System.out.println(argmax(b));


        int[] longArr = new int[5000000];
        int[] otherLongArr = new int[5000000];
        for (int i = 0; i < 5000000; i++) {
            int val = (int) Math.round(Math.random());
            longArr[i] = val;
            val = (int) Math.round(Math.random());
            otherLongArr[i] = val;
        }

        System.out.println();

        int[] add = add(a,b);
        int[] sub = subtract(a,b);
        for (int i : add) {
            System.out.println(i);
        }
        System.out.println();
        for (int i : sub) {
            System.out.println(i);
        }









    }
}

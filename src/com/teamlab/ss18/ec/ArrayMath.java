package com.teamlab.ss18.ec;

import java.util.ArrayList;
import java.util.Arrays;

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

    public static int argmax(double[] vector) {
        int maxIndex = -1;
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < vector.length; i++) {
            double value = vector[i];
            if (value > max) {
                max = value;
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public static void printArrayAsAligned(ArrayList<String> headers, double[][] matrix){
        int longestString = 0;
        for (String header : headers) {
            int length = header.length();
            if (longestString<length)
                longestString = length;
        }

        for (double[] ints : matrix) {
            for (double i : ints) {

                int length = Integer.toString((int)i).length();
                if (longestString<length)
                    longestString = length;
            }
        }


        System.out.print("\tP\\G"+new String(new char[longestString-1]).replace('\0', ' '));
        for (String header : headers) {
            int stringLength = header.length();
            int padding = longestString - stringLength + 2;
            String space = new String(new char[padding]).replace('\0', ' ');

            System.out.print(header + space);
        }
        System.out.println();

        for (int row = 0; row < matrix.length; row++) {

            String header = null;
            try {
                header = headers.get(row);
            } catch (IndexOutOfBoundsException e) {
                header = new String(new char[longestString+2]).replace('\0', ' ');
            }
            System.out.print("\t"+header + new String(new char[longestString-header.length()+2]).replace('\0', ' '));
            double[] ints = matrix[row];

            for (double i : ints) {
                String iString = Integer.toString((int)i);
                int stringLength = iString.length();

                int padding = longestString - stringLength + 2;

                String space = new String(new char[padding]).replace('\0', ' ');

                    System.out.print(iString + space);
            }
            System.out.println();

        }


    }




    public static void main(String[] args) {
        int[] a = {1,2,3,4};
        int[] b = {4,3,2,1};
        int[][] matrix = {a,b};

        ArrayList<String> headers = new ArrayList<>();
        String[] tmp = {"a","b","c","d"};
        headers.addAll(Arrays.asList(tmp));

        for (String header : headers) {
            System.out.println(header);
        }









    }
}

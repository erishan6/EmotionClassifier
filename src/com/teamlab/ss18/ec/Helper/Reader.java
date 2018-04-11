package com.teamlab.ss18.ec.Helper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by deniz on 11.04.18.
 */
public class Reader extends BufferedReader{
    public Reader(String fileName) throws FileNotFoundException {
        super(new FileReader(fileName));
    }
}



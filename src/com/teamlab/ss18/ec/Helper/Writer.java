package com.teamlab.ss18.ec.Helper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by deniz on 11.04.18.
 */
public class Writer extends PrintWriter {
    public Writer(String outFileName) throws IOException {
        super(new BufferedWriter(new FileWriter(outFileName)));
    }
}
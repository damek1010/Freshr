package com.company;

import picocli.CommandLine;
import picocli.CommandLine.*;

public class Main {
    public static void main(String[] args) {
        CommandLine.run(new Freshr(), args);
    }
}

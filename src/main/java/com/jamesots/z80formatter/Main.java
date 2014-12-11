package com.jamesots.z80formatter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Main {
    private static Pattern label = Pattern.compile("[a-z0-9_$]+:.*");
    private static Pattern comment = Pattern.compile(";.*");
    private static Pattern equ = Pattern.compile("[a-z0-9_$]+\\s+equ\\s+.*");
    private static Pattern empty = Pattern.compile("\\s*");

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java -jar z80formatter.jar <src.z80>");
            return;
        }

        List<String> comments = new ArrayList<String>();

        try {
            final BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String line;
            while ((line = reader.readLine()) != null) {
                if (empty.matcher(line).matches()) {
                    comments.add(line);
                } else if (line.length() > 0 && line.charAt(0) == '\t') {
                    for (String comment : comments) {
                        System.out.println(comment);
                    }
                    comments.clear();
                    System.out.println(line);
                } else if (equ.matcher(line).matches()) {
                    for (String comment : comments) {
                        System.out.println(comment);
                    }
                    comments.clear();
                    System.out.println(line);
                } else if (comment.matcher(line).matches()) {
                    comments.add(line);
                } else if (label.matcher(line).matches()) {
                    for (String comment : comments) {
                        System.out.println(comment);
                    }
                    comments.clear();
                    System.out.println(line);
                } else {
                    for (String comment : comments) {
                        System.out.print("\t");
                        System.out.println(comment);
                    }
                    comments.clear();
                    System.out.print("\t");
                    System.out.println(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

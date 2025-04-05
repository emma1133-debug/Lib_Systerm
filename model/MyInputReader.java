package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyInputReader {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static String nextLine() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return "";
        }
    }

    public static int nextInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number. Please try again.");
            }
        }
    }

    public static double nextDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("❌ Invalid number. Please try again.");
            }
        }
    }
}


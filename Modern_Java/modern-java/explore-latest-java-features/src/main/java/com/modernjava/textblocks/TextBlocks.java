package com.modernjava.textblocks;

public class TextBlocks {
    public static String multiLineString() {
        var multiLine = "This is a\n" +
                "    multiline string\n" +
                "with newlines inside";

        return  multiLine;
    }

    public static String getTextBlock() {
        return """
                Hello, %s!
                This is a
                multiline string
                with newlines inside
                
                select * from table
                where first_name = 'Animesh' and last_name = 'Paul'
                
                {
                    "first_name": "Animesh",
                    "first_name": "Paul"
                }
                """.formatted("Animesh"); // triple quote defines the start of the indentation
    }


    public static void main(String[] args) {
        // System.out.println("multiLineString = " + multiLineString());
        System.out.println("TextBlock = " + getTextBlock());
    }
}

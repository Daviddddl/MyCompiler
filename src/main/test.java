package main;

import Parser.LR1Parser.SyntaxError;
import Parser.TestGrammar;
import Util.FileUtil;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException, SyntaxError {

        //new TestGrammar();

        /*System.out.println(MainTest.grammar(
                "S��as A d;\nS��b A c;\nS��as e c;\nS��b e d;\nA��e",
                "S,A",
                "as,b,c,d,e",
                "S",
                "as e d#"
        ));*/
        new TestGrammar();
    }
}

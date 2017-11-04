package main;

import Parser.LR1Parser.SyntaxError;
import Parser.TestGrammar;
import Util.FileUtil;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException, SyntaxError {

        MainTest.grammar("S��ag A d;\nS��b A c;\nS�� ag e c;\nS��b e d;\nA�� e","S,A","ag,b,c,d,e","S","ag e d#");

        //MainTest.grammar("S��a A d;\nS��b A c;\nS�� a e c;\nS��b e d;\nA�� e","S,A","a,b,c,d,e","S","a e d#");
        /*System.out.println(MainTest.grammar(
                "S��aAd;\nS��bAc;\nS��aec;\nS��bed;\nA��e",
                "S,A",
                "a,b,c,d,e",
                "S",
                "aed#"
        ));*/
    }
}

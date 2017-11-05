package main;

import Parser.LR1Parser.SyntaxError;
import Parser.TestGrammar;
import Util.FileUtil;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException, SyntaxError {

        MainTest.grammar("S��ar A dw;\nS��b A c;\nS�� ar e c;\nS��b e dw;\nA�� e","S,A","ar,b,c,dw,e","S","ar e dw#");

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

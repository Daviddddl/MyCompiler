package Parser;

import Parser.LR1Parser.SyntaxError;
import main.MainTest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Parser.GrammarServlet")
public class GrammarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        String nonterminal = request.getParameter("nonterminal");
        String terminal = request.getParameter("terminal");
        String start = request.getParameter("start");
        String text = request.getParameter("text");
        String parser = request.getParameter("parser");
        //System.out.println(nonterminal+"---" + terminal +"+++" + start + "---" + text + "+++" + parser);

        try {
            String result = MainTest.grammar(parser,nonterminal,terminal,start,text);
            System.out.println(result);
            out.print(result);
        } catch (SyntaxError syntaxError) {
            syntaxError.printStackTrace();
        }
    }
}

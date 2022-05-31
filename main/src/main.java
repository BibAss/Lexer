public class main {
    public static void main(String[] args) {
        Lexer lex = new Lexer("a = 1 + 3; while((a + b) < (3+4)) {a = 6 + 7; b = 1 + a;}");
        lex.LexicalAnalysis();
        Parser pars = new Parser(lex.Tokens());
        pars.Parsing();
        System.out.println(pars.getExprList());
    }
}
public class main {
    public static void main(String[] args) {
        Lexer lex = new Lexer("mama = 3+4; papa = 6    +  7");
        lex.LexicalAnalysis();
        Parser pars = new Parser(lex.Tokens());
        pars.Parsing();
    }
}
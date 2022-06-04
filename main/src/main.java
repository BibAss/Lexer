public class main {
    public static void main(String[] args) {
        Lexer lex = new Lexer("a = 1 + 2; if(a<b){a=2;}else{a = 1;} while((a + 2) < (10 + b)) {a = a + 2; b = b + 1;}");
        lex.LexicalAnalysis();
        Parser pars = new Parser(lex.Tokens());
        pars.Parsing();
        System.out.println(pars.getExprList());
        POLIZ poliz = new POLIZ(pars.getExprList());
        poliz.interpreter();
        System.out.println(poliz.variables.toString());
        int a = 1;
        int b = 0;
        while ((a + 2) < (10 + b)) {a = a + 2; b = b + 1;}
        System.out.println(a + "\n" + b + "\n" );
    }
}
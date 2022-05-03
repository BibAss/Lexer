public class main {
    public static void main(String[] args) {
        Lexer lex = new Lexer("mama = (3+4);\n" +
                "papa = ((a+b+c+d+var*c)) if (n=m) m;");
        lex.Analise();
        lex.Tokens();
    }
}
public class main {
    public static void main(String[] args) {
        Lexer lex = new Lexer("if (mama = (3 + 4))");
        lex.Analise();
    }
}
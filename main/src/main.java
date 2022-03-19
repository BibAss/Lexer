public class main {
    public static void main(String[] args) {
        Lexer lex = new Lexer("papa=((a+b+c+d+var*c))if(n=m)m"); // mama=(3+4)
        lex.Analise();
        lex.Tokens();
    }
}
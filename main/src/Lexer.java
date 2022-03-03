import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;


public class Lexer {
    private String code;
    List<Token> tokens = new LinkedList<>();

    Lexer(String code){
        this.code = code;
    }

    public void Analise(){
        for (String lexemName: Regexp.lexems.keySet()) {
            Matcher m = Regexp.lexems.get(lexemName).matcher(code);
            if (m.find()) {
                System.out.println(lexemName + " found ");
                tokens.add(new Token(lexemName, m.group()));
            }
        }

        for (Token token: tokens) {
            System.out.println(token);
        }
    }
}

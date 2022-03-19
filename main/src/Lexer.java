import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;


public class Lexer
{
    private final String code;
    List<Token> tokens = new LinkedList<>();

    Lexer(String code)
    {
        this.code = code;
    }

    public void Analise()
    {
        int staticIndex = 0, dynamicIndex = 0;
        int crutch = 0;
        boolean stop = false;
        while(true)
        {
            dynamicIndex += 1;
            if(stop)
            {
                break;
            }
            if(dynamicIndex > code.length())
            {
                dynamicIndex -= 1;
                stop = true;
            }
            StringBuilder buffer = new StringBuilder(code.substring(staticIndex, dynamicIndex));
            if(stop)
            {
                buffer.append(" ");
            }
            for (String lexemeName: Regexp.lexems.keySet())
            {
                Matcher matcher = Regexp.lexems.get(lexemeName).matcher(buffer);
                if(matcher.matches())
                {
                    crutch += 1;
                }
            }
            if(crutch == 0)
            {
                buffer.delete(buffer.length() - 1, buffer.length());
                String token = buffer.toString();
                for (String lexemeName: Regexp.lexems.keySet())
                {
                    Matcher matcher = Regexp.lexems.get(lexemeName).matcher(buffer);
                    if(matcher.matches())
                    {
                        tokens.add(new Token(lexemeName, token));
                        break;
                    }
                }
                staticIndex = dynamicIndex - 1;
            }
            crutch = 0;
        }
    }

    public void Tokens()
    {
        for (Token token: tokens) {
            System.out.println(token);
        }
    }
}

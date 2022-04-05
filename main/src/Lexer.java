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
        boolean exit = false;
        boolean success = false;
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
            String buf = buffer.toString();
            if(buf.equals("\n") || buf.equals(" "))
            {
                staticIndex = dynamicIndex;
                continue;
            }
            if(stop)
            {
                buffer.append(" ");
            }
            for (String lexemeName: Regexp.lexems.keySet())
            {
                Matcher matcher = Regexp.lexems.get(lexemeName).matcher(buffer);
                if(matcher.matches())
                {
                    success = true;
                    break;
                }
            }
            if(!success)
            {
                buffer.delete(buffer.length() - 1, buffer.length());
                String token = buffer.toString();
                for (String lexemeName: Regexp.lexems.keySet())
                {
                    Matcher matcher = Regexp.lexems.get(lexemeName).matcher(buffer);
                    if(matcher.matches())
                    {
                        if(lexemeName == "VAR")
                        {
                            for(String lexeme: Regexp.KeyWords.keySet())
                            {
                                Matcher matcherPriority = Regexp.KeyWords.get(lexeme).matcher(buffer);
                                if(matcherPriority.matches())
                                {
                                    exit = true;
                                    tokens.add(new Token(lexeme, token));
                                    break;
                                }
                            }
                            if(exit)
                            {
                                exit = false;
                                break;
                            }
                        }
                        tokens.add(new Token(lexemeName, token));
                        break;
                    }
                }
                dynamicIndex -= 1;
                staticIndex = dynamicIndex;
            }
            success = false;
        }
    }

    public void Tokens()
    {
        for (Token token: tokens) {
            System.out.println(token);
        }
    }
}

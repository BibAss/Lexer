import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Regexp {
    public static final Map<String, Pattern> lexems = new HashMap<>();
    static {
        lexems.put("VAR", Pattern.compile("[a-z][a-z0-9]*"));
        lexems.put("DIGIT", Pattern.compile("0|[1-9][0-9]*"));
        lexems.put("ASSIGN_OP", Pattern.compile("="));
        lexems.put("OP", Pattern.compile("[+]|[-]|[*]|[/]"));
        lexems.put("BRA_OPEN", Pattern.compile("\\("));
        lexems.put("BRA_CLOSE", Pattern.compile("\\)"));
        lexems.put("SEMICOLON", Pattern.compile(";"));
        lexems.put("CYCLE_START", Pattern.compile("\\{"));
        lexems.put("CYCLE_STOP", Pattern.compile("}"));
        lexems.put("COMP_OP", Pattern.compile("[>]|[<]"));
    }
    public static final Map<String, Pattern> KeyWords = new HashMap<>();
    static {
        KeyWords.put("IF_KW", Pattern.compile("if"));
        KeyWords.put("ELSE_KW", Pattern.compile("else"));
        KeyWords.put("FOR_KW", Pattern.compile("for"));
        KeyWords.put("WHILE_KW", Pattern.compile("while"));
    }
}

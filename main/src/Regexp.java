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
        lexems.put("IF_KW", Pattern.compile("if"));
        lexems.put("ELSE_KW", Pattern.compile("else"));
        lexems.put("FOR_KW", Pattern.compile("for"));
        lexems.put("WHILE_KW", Pattern.compile("while"));
    }
}

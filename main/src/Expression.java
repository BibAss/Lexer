import java.util.LinkedList;
import java.util.List;

public class Expression {
    private final List<Token> exprList = new LinkedList<>();
    private final StringBuilder expression = new StringBuilder("");

    public void Add(Token token)
    {
        this.exprList.add(token);
        this.expression.append(token.getValue());
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}
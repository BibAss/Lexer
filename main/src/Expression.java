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

    public void Del()
    {
        this.exprList.remove(exprList.size()-1);
        this.expression.delete(expression.length() - 1, expression.length());
    }

    @Override
    public String toString() {
        return expression.toString();
    }

    public int getSize() {
        return exprList.size();
    }

    public Token getToken(int i){
        return exprList.get(i);
    }
}

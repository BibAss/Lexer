import java.util.LinkedList;
import java.util.List;

public class Expression {
    private List<Token> exprList = new LinkedList<>();

    public void Add(Token token)
    {
        this.exprList.add(token);
    }

    @Override
    public String toString() {
        return "Expression{" + exprList + '}';
    }
}

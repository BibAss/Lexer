import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Parser {
    private List<Token> tokenList;
    private List<Expression> expressionList = new LinkedList<>();
    private Token currentToken;
    private ListIterator dynamicIter;
    private ListIterator staticIter;

    Parser(List<Token> tokens)
    {
        this.tokenList = tokens;
        this.dynamicIter = tokenList.listIterator();
    }

    public void Parsing()
    {
        staticIter = tokenList.listIterator();
        while (dynamicIter.hasNext())
        {
            SyntaxAnalysis();
        }
    }

    private void SyntaxAnalysis()
    {
        Token token = (Token) dynamicIter.next();
        switch (token.getType())
        {
            case ("VAR"):
                assign();
                break;
            default:
                System.out.println("Error: there is no such expression starting with " + token.getType());
        }

    }

    private void assign()
    {
        EndOfCode();
        currentToken = (Token) dynamicIter.next();
        if (currentToken.getType().equals("ASSIGN_OP"))
        {
            expr_value();
        }
    }

    private void expr_value()
    {
        currentToken = (Token) dynamicIter.next();
        if (currentToken.getType().equals("VAR") | currentToken.getType().equals("DIGIT"))
        {
            if(EndOfString())
            {
                dynamicIter.next();
                MakeExpression();
            }
            else op_value();
        }
    }

    private void op_value()
    {
        currentToken = (Token) dynamicIter.next();
        if (currentToken.getType().equals("VAR") | currentToken.getType().equals("DIGIT"))
        {
            if(EndOfString())
            {
                dynamicIter.next();
                MakeExpression();
                System.out.println(expressionList);
            }
            else op_value();
        }
        else if (currentToken.getType().equals("OP"))
        {
            op_value();
        }
    }

    private void MakeExpression()
    {
        Expression expression = new Expression();
        while(staticIter.nextIndex() != dynamicIter.nextIndex())
        {
            currentToken = (Token) staticIter.next();
            expression.Add(currentToken);
        }
        expressionList.add(expression);
        return;
    }

    private boolean EndOfString()
    {
        currentToken = (Token) dynamicIter.next();
        dynamicIter.previous();
        if(currentToken.getType().equals("SEMICOLON"))
        {
            return true;
        }
        else return false;
    }

    private void EndOfCode()
    {
        if(!dynamicIter.hasNext())
        {
            System.out.println("Error: there is no ; or expression is wrong");
            return;
        }
    }
}

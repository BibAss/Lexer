import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Parser {
    private List<Token> tokenList;
    private List<Expression> expressionList = new LinkedList<>();
    private Token currentToken;
    private ListIterator dynamicIter;
    private ListIterator staticIter;
    private boolean stop = false;

    Parser(List<Token> tokens)
    {
        this.tokenList = tokens;
        this.dynamicIter = tokenList.listIterator();
    }
///////////Ставлю статический индекс перед листом токенов и пока не кончится лист произвожу его синтаксический анализ///
    public void Parsing()
    {
        staticIter = tokenList.listIterator();
        while (dynamicIter.hasNext())
        {
            SyntaxAnalysis();
            if (stop) return;
        }
    }
///////////Проверяю какой терминал стоит в начале обрабатываемого участка (выражения)///////////////////////////////////
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
//////Обрабатываю нетерминал assign/////////////////////////////////////////////////////////////////////////////////////
    private void assign()
    {
        if(EndOfCode())
        {
            return;
        }
        currentToken = (Token) dynamicIter.next();
        if (currentToken.getType().equals("ASSIGN_OP"))
        {
            expr_value();
        }
        else
        {
            System.out.println("Error: syntax error in assign expression");
            stop = true;
            return;
        }
    }
/////////Обрабатываю нетерминал expr_value//////////////////////////////////////////////////////////////////////////////
    private void expr_value()
    {
        if(EndOfCode())
        {
            return;
        }
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
        else
        {
            System.out.println("Error: syntax error in assign expression");
            stop = true;
            return;
        }
    }
//////////Обрабатываю нетерминал op_value///////////////////////////////////////////////////////////////////////////////
    private void op_value()
    {
        if(EndOfCode())
        {
            return;
        }
        currentToken = (Token) dynamicIter.next();
        if (currentToken.getType().equals("VAR") | currentToken.getType().equals("DIGIT"))
        {
            if(EndOfCode())
            {
                return;
            }
            if(EndOfString())
            {
                dynamicIter.next();
                MakeExpression();
            }
            else op_value();
        }
        else if (currentToken.getType().equals("OP"))
        {
            op_value();
        }
        else
        {
            System.out.println("Error: syntax error in assign expression");
            stop = true;
            return;
        }
    }
////////Создаю выражение путём последовательного перехода статического итератора к динамическому////////////////////////
    private void MakeExpression()
    {
        Expression expression = new Expression();
        while(staticIter.nextIndex() != dynamicIter.nextIndex())
        {
            currentToken = (Token) staticIter.next();
            expression.Add(currentToken);
        }
        expressionList.add(expression);
        System.out.println(expressionList);
        return;
    }
////////Отслеживаю конец строки/////////////////////////////////////////////////////////////////////////////////////////
    private boolean EndOfString()
    {
            currentToken = (Token) dynamicIter.next();
            dynamicIter.previous();
            if (currentToken.getType().equals("SEMICOLON")) {
                return true;
            }
            else return false;
    }
////////Отслеживаю конец кода///////////////////////////////////////////////////////////////////////////////////////////
    private boolean EndOfCode()
    {
        if(!dynamicIter.hasNext())
        {
            System.out.println("Error: there is no ; or expression is wrong");
            return true;
        }
        return false;
    }
}

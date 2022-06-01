import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class Parser {
    private final List<Token> tokenList;
    private final List<Expression> expressionList = new LinkedList<>();
    private Token currentToken;
    private final ListIterator dynamicIter;
    private ListIterator staticIter;
    private boolean stop = false;
    private boolean dell = false;

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
            try {
                SyntaxAnalysis();
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("Code ends faster than suggest");
            }
            if (stop) return;
        }
    }

    public List<Expression> getExprList()
    {
        return expressionList;
    }

    private void SyntaxAnalysis()
    {
        Token startToken = (Token) dynamicIter.next();
        dynamicIter.previous();
        if(assign())
        {
            dell = true;
            MakeExpression();
            dell = false;
        }
        else if(while_cycle())
        {
            MakeExpression();
        }
        else {
            System.out.println("Error: there is no such expression starting with " + startToken.getType());
            stop = true;
        }
    }

    private boolean assign()
    {
        if(var())
        {
            if (assign_op())
            {
                return expr_value();
            }
            else
            {
                System.out.println("Error: syntax error in assign expression");
                stop = true;
                return false;
            }
        }
        else return false;
    }

    private boolean expr_value()
    {
        if(expr_br())
        {
            return true;
        }
        if (value())
        {
            return simple_cond();
        }
        else return false;
    }

    private boolean simple_cond()
    {
        if (EndOfString())
        {
            return true;
        }
        if (op())
        {
            if (value() | expr_br())
            {
                return simple_cond();
            }
            else return false;
        }else return false;
    }

    private boolean expr_br()
    {
        if (bra_open())
        {
            if(expr_value())
            {
                if (bra_close())
                {
                    return op_inf();
                }
                else return false;
            }else return false;
        }else return false;
    }

    private boolean op_inf()
    {
        if (EndOfString())
        {
            return true;
        }
        if (op())
        {
            if(expr_value())
            {
                return op_inf();
            }else return false;
        }else return false;
    }

    private boolean while_cycle()
    {
        if(while_kw())
        {
            if(condition())
            {
                if (cycle_start())
                {
                    return cycle_body();
                }
                else
                {
                    System.out.println("Error: syntax error in while cycle starting expression");
                    stop = true;
                    return false;
                }
            }
            else
            {
                System.out.println("Error: syntax error in while condition expression");
                stop = true;
                return false;
            }
        } else return false;
    }

    private boolean cycle_body()
    {
        if (cycle_stop())
        {
            return true;
        }
        if (assign())
        {
            return cycle_body();
        }
        else return false;
    }

    private boolean condition()
    {
        if (bra_open())
        {
            if (cond_value())
            {
                return bra_close();
            }else return false;
        }else return false;
    }

    private boolean cond_value()
    {
        if (expr_value_while())
        {
            if (comp_op())
            {
                return expr_value_while();
            }else return false;
        }else return false;
    }

    private boolean expr_value_while()
    {
        if(expr_br_while())
        {
            return true;
        }
        if (value())
        {
            return simple_cond_while();
        }
        else return false;
    }

    private boolean expr_br_while()
    {
        if (bra_open())
        {
            if(expr_value_while())
            {
                if (bra_close())
                {
                    return op_inf_while();
                }
                else return false;
            }else return false;
        }else return false;
    }

    private boolean op_inf_while()
    {
        if (comp_op())
        {
            dynamicIter.previous();
            return true;
        }
        else if (bra_close())
        {
            dynamicIter.previous();
            return true;
        }
        if (op())
        {
            if(expr_value_while())
            {
                return op_inf_while();
            }else return false;
        }else return false;
    }

    private boolean simple_cond_while()
    {
        if (bra_close())
        {
            dynamicIter.previous();
            return true;
        }
        else if (comp_op())
        {
            dynamicIter.previous();
            return true;
        }
        if (op())
        {
            if (value() | expr_br_while())
            {
                return simple_cond_while();
            }
            else return false;
        }else return false;
    }

    private void MakeExpression()
    {
        Expression expression = new Expression();
        while(staticIter.nextIndex() != dynamicIter.nextIndex())
        {
            currentToken = (Token) staticIter.next();
            expression.Add(currentToken);
        }
        if(dell) expression.Del();
        expressionList.add(expression);
        return;
    }

    private boolean EndOfString()
    {
        currentToken = (Token) dynamicIter.next();
        if(currentToken.getType().equals("SEMICOLON"))
        {
            return true;
        }
        else
        {
            dynamicIter.previous();
            return false;
        }
    }

    private boolean value()
    {
        currentToken = (Token) dynamicIter.next();
        if(currentToken.getType().equals("VAR") | currentToken.getType().equals("DIGIT"))
        {
            return true;
        }
        else
        {
            dynamicIter.previous();
            return false;
        }
    }

    private boolean var()
    {
        currentToken = (Token) dynamicIter.next();
        if(currentToken.getType().equals("VAR"))
        {
            return true;
        }
        else
        {
            dynamicIter.previous();
            return false;
        }
    }

    private boolean digit()
    {
        currentToken = (Token) dynamicIter.next();
        if(currentToken.getType().equals("DIGIT"))
        {
            return true;
        }
        else
        {
            dynamicIter.previous();
            return false;
        }
    }

    private boolean assign_op()
    {
        currentToken = (Token) dynamicIter.next();
        if(currentToken.getType().equals("ASSIGN_OP"))
        {
            return true;
        }
        else
        {
            dynamicIter.previous();
            return false;
        }
    }

    private boolean op()
    {
        currentToken = (Token) dynamicIter.next();
        if(currentToken.getType().equals("OP"))
        {
            return true;
        }
        else
        {
            dynamicIter.previous();
            return false;
        }
    }

    private boolean bra_open()
    {
        currentToken = (Token) dynamicIter.next();
        if(currentToken.getType().equals("BRA_OPEN"))
        {
            return true;
        }
        else
        {
            dynamicIter.previous();
            return false;
        }
    }

    private boolean bra_close()
    {
        currentToken = (Token) dynamicIter.next();
        if(currentToken.getType().equals("BRA_CLOSE"))
        {
            return true;
        }
        else
        {
            dynamicIter.previous();
            return false;
        }
    }

    private boolean while_kw()
    {
        currentToken = (Token) dynamicIter.next();
        if(currentToken.getType().equals("WHILE_KW"))
        {
            return true;
        }
        else
        {
            dynamicIter.previous();
            return false;
        }
    }

    private boolean cycle_start()
    {
        currentToken = (Token) dynamicIter.next();
        if(currentToken.getType().equals("CYCLE_START"))
        {
            return true;
        }
        else
        {
            dynamicIter.previous();
            return false;
        }
    }

    private boolean cycle_stop()
    {
        currentToken = (Token) dynamicIter.next();
        if(currentToken.getType().equals("CYCLE_STOP"))
        {
            return true;
        }
        else
        {
            dynamicIter.previous();
            return false;
        }
    }

    private boolean comp_op()
    {
        currentToken = (Token) dynamicIter.next();
        if(currentToken.getType().equals("COMP_OP"))
        {
            return true;
        }
        else
        {
            dynamicIter.previous();
            return false;
        }
    }
}

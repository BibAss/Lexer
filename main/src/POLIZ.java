import java.util.*;

public class POLIZ {
    private final List<Expression> expressionLinkedList;
    public Map<String, Double> variables = new HashMap<>();
    private boolean enter = false;
    private int part = 0;

    public POLIZ(List<Expression> expr) {
        this.expressionLinkedList = expr;
    }

    public void interpreter() {
        for (Expression expr : expressionLinkedList)
            rpnToAnswer(expressionToRPN(expr));
    }

    private LinkedList<Token> expressionToRPN(Expression expr) {
        LinkedList<Token> current = new LinkedList<>();
        Stack<Token> stack = new Stack<>();
        int priority;
        for (int i = 0; i < expr.getSize(); i++) {
            priority = getPriority(expr.getToken(i));
            if (i != 0 && priority == getPriority(expr.getToken(i - 1)) && priority == 0)
                while (!stack.empty()) current.add(stack.pop());
            if (i != 0 && getPriority(expr.getToken(i - 1)) == -1 && priority == 0)
                while (!stack.empty()) current.add(stack.pop());
            if (expr.getToken(i).getType().equals("SEMICOLON"))
                continue;
            if (priority == -5) {
                while (!stack.empty()) current.add(stack.pop());
                current.add(expr.getToken(i));
            }
            if (expr.getToken(i).getType().equals("WHILE_KW")) enter = true;
            if (enter)
            {
                if(expr.getToken(i).getType().equals("COMP_OP")) enter = false;
                if (expr.getToken(i).getType().equals("VAR") || expr.getToken(i).getType().equals("DIGIT") || expr.getToken(i).getType().equals("OP")) part += 1;
            }

            if (priority == 0) {
                current.add(expr.getToken(i));
                if (expr.getToken(i).getType().equals("VAR")) {
                    variables.putIfAbsent(expr.getToken(i).getValue().toString(), 0.0);
                }
            }
            if (priority == 1) stack.push(expr.getToken(i));
            if (priority > 1) {
                while (!stack.empty()) {
                    if (getPriority(stack.peek()) >= priority) current.add(stack.pop());
                    else break;
                }
                stack.push(expr.getToken(i));

            }
            if (priority == -1) {
                while (getPriority(stack.peek()) != 1) current.add(stack.pop());
                stack.pop();
            }
        }
        while (!stack.empty()) current.add(stack.pop());

        for (Token i : current) {
            System.out.print(i.getValue() + " ");
        }
        System.out.println(part);
        return current;
    }

    private void rpnToAnswer(LinkedList<Token> rpn) {
        if (rpn.get(0).getType().equals("WHILE_KW")) {
            rpnToAnswerWhile(rpn);
            return;
        }
        calculate(rpn);
    }

    private void calculate(LinkedList<Token> rpn) {
        Stack<Token> stack = new Stack<>();

        for (Token i : rpn) {
            if (i.getType().equals("SEMICOLON")) continue;
            if (getPriority(i) == 0) stack.push(i); // если операнд, то просто пишем в стек
            if (getPriority(i) > 1) {
                Token a = stack.pop(), b = stack.pop(); // если оператор, то достаём два операнда из стека
                double dur1, dur2;

                switch (i.getValue()) {
                    case "+" -> {
                        if (a.getType().equals("VAR")) dur1 = variables.get(a.getValue()); // если переменная, то берём из мапы
                        else dur1 = Double.parseDouble(a.getValue()); // иначе это число, берём напрямую

                        if (b.getType().equals("VAR")) dur2 = variables.get(b.getValue());
                        else dur2 = Double.parseDouble(b.getValue());

                        stack.push(new Token("DIGIT", String.valueOf(dur2 + dur1))); // кидаем в стэк числовой токен равный их сумме.
                    }
                    case "-" -> { //остальное по аналогии с +
                        if (a.getType().equals("VAR")) dur1 = variables.get(a.getValue());
                        else dur1 = Double.parseDouble(a.getValue());

                        if (b.getType().equals("VAR")) dur2 = variables.get(b.getValue());
                        else dur2 = Double.parseDouble(b.getValue());

                        stack.push(new Token("DIGIT", String.valueOf(dur2 - dur1)));
                    }
                    case "/" -> {
                        if (a.getType().equals("VAR")) dur1 = variables.get(a.getValue());
                        else dur1 = Double.parseDouble(a.getValue());

                        if (b.getType().equals("VAR")) dur2 = variables.get(b.getValue());
                        else dur2 = Double.parseDouble(b.getValue());

                        stack.push(new Token("DIGIT", String.valueOf(dur2 / dur1)));
                    }
                    case "*" -> {
                        if (a.getType().equals("VAR")) dur1 = variables.get(a.getValue());
                        else dur1 = Double.parseDouble(a.getValue());

                        if (b.getType().equals("VAR")) dur2 = variables.get(b.getValue());
                        else dur2 = Double.parseDouble(b.getValue());

                        stack.push(new Token("DIGIT", String.valueOf(dur2 * dur1)));
                    }
                    case "=" -> {
                        Double val;
                        if (a.getType().equals("DIGIT"))
                            val = Double.parseDouble(a.getValue()); // если верхний токен число, то берём его value
                        else
                            val = variables.get(a.getValue()); // иначе он переменная и тогда берём её значение из мапы
                        variables.replace(b.getValue(), val);  // перезаписываем для b значение равное получ. value
                    }
                }
            }
        }
    }

    private void rpnToAnswerWhile(LinkedList<Token> rpn) {
        LinkedList<Token> cond = new LinkedList<>();
        LinkedList<Token> body = new LinkedList<>();
        int rightBoard = findCondition(rpn);
        for (int i = 1; i < rightBoard; i++) cond.add(rpn.get(i));
        for (int i = rightBoard; i < rpn.size(); i++) body.add(rpn.get(i));
        System.out.println("--------");
        System.out.println(cond);
        System.out.println(body);
        while (condition(cond)) {
            calculate(body);
        }
    }

    private int findCondition(LinkedList<Token> rpn)
    {
        int i = 0;
        while(true)
        {
            if(rpn.get(i).getType().equals("COMP_OP")) return i + 1;
            i++;
        }
    }

    private boolean condition(LinkedList<Token> cond) {
        boolean condition = false;
        LinkedList<Token> firstPart = new LinkedList<>();
        LinkedList<Token> secondPart = new LinkedList<>();

        for (int i = 0; i < part; i++)
        {
            firstPart.add(cond.get(i));
        }
        for (int i = part; i < cond.size() - 1; i++)
        {
            secondPart.add(cond.get(i));
        }

        System.out.println("__________-");
        System.out.println(secondPart);
        System.out.println(firstPart);

        Token a = calculateCondition(secondPart), b = calculateCondition(firstPart);

        double dur1, dur2;

        if (a.getType().equals("VAR")) dur1 = variables.get(a.getValue());
        else dur1 = Double.parseDouble(a.getValue());

        if (b.getType().equals("VAR")) dur2 = variables.get(b.getValue());
        else dur2 = Double.parseDouble(b.getValue());

        if (cond.get(cond.size() - 1).getValue().equals(">")) condition = dur2 > dur1;
        if (cond.get(cond.size() - 1).getValue().equals("<")) condition = dur2 < dur1;
        System.out.println(condition);
        return condition;
    }

    private Token calculateCondition(LinkedList<Token> rpn) {
        Stack<Token> stack = new Stack<>();

        for (Token i : rpn) {
            if (getPriority(i) == 0) stack.push(i); // если операнд, то просто пишем в стек
            if (getPriority(i) > 1) {
                Token a = stack.pop(), b = stack.pop(); // если оператор, то достаём два операнда из стека
                double dur1, dur2;

                switch (i.getValue()) {
                    case "+" -> {
                        if (a.getType().equals("VAR")) dur1 = variables.get(a.getValue()); // если переменная, то берём из мапы
                        else dur1 = Double.parseDouble(a.getValue()); // иначе это число, берём напрямую

                        if (b.getType().equals("VAR")) dur2 = variables.get(b.getValue());
                        else dur2 = Double.parseDouble(b.getValue());

                        stack.push(new Token("DIGIT", String.valueOf(dur2 + dur1))); // кидаем в стэк числовой токен равный их сумме.
                    }
                    case "-" -> { //остальное по аналогии с +
                        if (a.getType().equals("VAR")) dur1 = variables.get(a.getValue());
                        else dur1 = Double.parseDouble(a.getValue());

                        if (b.getType().equals("VAR")) dur2 = variables.get(b.getValue());
                        else dur2 = Double.parseDouble(b.getValue());

                        stack.push(new Token("DIGIT", String.valueOf(dur2 - dur1)));
                    }
                    case "/" -> {
                        if (a.getType().equals("VAR")) dur1 = variables.get(a.getValue());
                        else dur1 = Double.parseDouble(a.getValue());

                        if (b.getType().equals("VAR")) dur2 = variables.get(b.getValue());
                        else dur2 = Double.parseDouble(b.getValue());

                        stack.push(new Token("DIGIT", String.valueOf(dur2 / dur1)));
                    }
                    case "*" -> {
                        if (a.getType().equals("VAR")) dur1 = variables.get(a.getValue());
                        else dur1 = Double.parseDouble(a.getValue());

                        if (b.getType().equals("VAR")) dur2 = variables.get(b.getValue());
                        else dur2 = Double.parseDouble(b.getValue());

                        stack.push(new Token("DIGIT", String.valueOf(dur2 * dur1)));
                    }
                }
            }
        }
        return stack.pop();
    }

    private int getPriority(Token i) {
        if (i.getValue().equals("*") || i.getValue().equals("/")) return 4;
        if (i.getValue().equals("+") || i.getValue().equals("-")) return 3;
        if (i.getType().equals("COMP_OP")) return 3;
        if (i.getType().equals("ASSIGN_OP")) return 2;
        if (i.getType().equals("BRA_OPEN")) return 1;
        if (i.getType().equals("BRA_CLOSE")) return -1;
        if (i.getType().equals("CYCLE_START") || i.getType().equals("CYCLE_STOP")) return -2;
        if (i.getType().equals("ELSE_KW")) return -5;
        else return 0;
    }
}

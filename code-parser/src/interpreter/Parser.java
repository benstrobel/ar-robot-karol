package interpreter;

import instructions.Instruction;
import instructions.controlflow.If;
import instructions.controlflow.While;
import instructions.expressions.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class Parser {

    public static Instruction[] parse(String[] input) {
        return generateInstructions(Arrays.stream(getTokenStream(input)).collect(Collectors.toCollection(ArrayDeque::new)), 0);
    }

    private static Token[] getTokenStream(String[] input) {
        List<Token> tokens = new ArrayList<>();

        int lineNo = 1;
        for(String line : input) {
            String[] tokenStrings = line
                    .replace(Token.SPACETAB.keyword,"\t")
                    .replace("\t", "\t ")
                    .replace(Token.BODYOPEN.keyword, " " + Token.BODYOPEN.keyword)
                    .split(" ");
            keywords: for(String keyword: tokenStrings){
                for(Token token: Token.values() ){
                    if(token.keyword.equals(keyword.toLowerCase())) {
                        tokens.add(token);
                        continue keywords;
                    }
                }
                System.err.println("Keyword \""+ keyword +"\" in line " + lineNo + " not found");
            }
            tokens.add(Token.NEWLINE);
            lineNo += 1;
        }

        return tokens.toArray(Token[]::new);
    }

    // https://stackoverflow.com/questions/15610183/if-else-statements-in-antlr-using-listeners

    private static Instruction[] generateInstructions(Deque<Token> iterator, int tabLevel) {
        List<String> errors = new ArrayList<>();
        List<Instruction> instructions = new ArrayList<>();
        int tabsPassedThisLine = 0;

        while(!iterator.isEmpty()) {
            Token token = iterator.peek();

            if(token == Token.TAB){
                tabsPassedThisLine += 1;
                if(tabsPassedThisLine > tabLevel) {
                    errors.add("Too many tabs (" + tabsPassedThisLine + " instead of " + tabLevel + ")"); // TODO Line (Tokenstream needs line/position of each token aswell)
                }
                iterator.pop();
                continue;
            } else if (tabsPassedThisLine < tabLevel) {
                errors.forEach(System.err::println);
                return instructions.toArray(Instruction[]::new);
            } else if (token == Token.NEWLINE){
                tabsPassedThisLine = 0;
                iterator.pop();
                continue;
            }
            iterator.pop();

            if(token.instructionClass != null) {
                try {
                    instructions.add((Instruction) token.instructionClass.getConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            } else {
                switch (token) {
                    case IF -> {
                        Expression condition = findExpression(iterator, errors);
                        expect(iterator, Token.BODYOPEN, errors);
                        expect(iterator, Token.NEWLINE, errors);
                        if(condition != null) {
                            Instruction[] body = generateInstructions(iterator, tabLevel+1);
                            instructions.add(new If(condition, body));
                        }
                    }
                    case WHILE -> {
                        Expression condition = findExpression(iterator, errors);
                        expect(iterator, Token.BODYOPEN, errors);
                        expect(iterator, Token.NEWLINE, errors);
                        if(condition != null) {
                            Instruction[] body = generateInstructions(iterator, tabLevel+1);
                            instructions.add(new While(condition, body));
                        }
                    }
                }
            }
            tabsPassedThisLine = 0;
        }

        errors.forEach(System.err::println);
        return instructions.toArray(Instruction[]::new);
    }

    private static Expression findExpression(Deque<Token> iterator, List<String> errors) {
        if(iterator.isEmpty()) return null;
        Token token = iterator.pop();
        switch(token) {
            case FALSE:
                return new False();
            case ISBLOCK:
                return new IsBlock();
            case ISBORDER:
                return new IsBorder();
            case ISEAST:
                return new IsEast();
            case ISNORTH:
                return new IsNorth();
            case ISSOUTH:
                return new IsSouth();
            case ISWEST:
                return new IsWest();
            case TRUE:
                return new True();
            case NOT:
                Expression expression = findExpression(iterator, errors);
                return expression == null ? null : new Not(expression);
            default: {
                errors.add("Expected Expression"); // TODO Line (Tokenstream needs line/position of each token aswell)
                return null;
            }
        }
    }

    private static boolean expect(Deque<Token> iterator, Token expected, List<String> errors) {
        if(iterator.isEmpty()) {
            return false;
        }
        Token next = iterator.pop();
        if(next != expected) {
            errors.add("Expected " + expected);
            return false;
        }
        return true;
    }

}

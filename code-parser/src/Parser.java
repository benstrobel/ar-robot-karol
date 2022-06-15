import instructions.Instruction;
import instructions.controlflow.If;
import instructions.controlflow.While;
import instructions.expressions.*;
import instructions.statements.Place;
import instructions.visitors.InstructionPrintVisitor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

public class Parser {

    public static void main(String[] args) throws IOException {

        String[] lines = Files.lines(Paths.get(System.getProperty("user.dir") + "\\testinput.txt")).collect(Collectors.toList()).toArray(String[]::new);

        Token[] tokenStream = getTokenStream(lines);
        for(Token token: tokenStream){
            System.out.println(token);
        }
        Instruction[] instructions = generateInstructions(Arrays.stream(tokenStream).iterator(), 0);
        InstructionPrintVisitor printer = new InstructionPrintVisitor();
        for(Instruction instruction: instructions) {
            instruction.accept(printer);
        }
    }

    private static Token[] getTokenStream(String[] input) {
        List<Token> tokens = new ArrayList<>();

        for(String line : input) {

            String[] tokenStrings = line
                    .replace(Token.SPACETAB.keyword,"\t")
                    .replace("\t", "\t ")
                    .replace(Token.BODYOPEN.keyword, " " + Token.BODYOPEN.keyword)
                    .split(" ");
            for(String keyword: tokenStrings){
                for(Token token: Token.values() ){
                    if(token.keyword.equals(keyword.toLowerCase())) {
                        tokens.add(token);
                        break;
                    }
                }
            }
            tokens.add(Token.NEWLINE);
        }

        return tokens.toArray(Token[]::new);
    }

    // https://stackoverflow.com/questions/15610183/if-else-statements-in-antlr-using-listeners

    private static Instruction[] generateInstructions(Iterator<Token> iterator, int tabLevel) {
        List<String> errors = new ArrayList<>();
        List<Instruction> instructions = new ArrayList<>();
        int tabsPassedThisLine = 0;

        while(iterator.hasNext()) {
            Token token = iterator.next();

            if(token == Token.TAB){
                tabsPassedThisLine += 1;
                if(tabsPassedThisLine > tabLevel) {
                    errors.add("Too many tabs (" + tabsPassedThisLine + " instead of " + tabLevel + ")"); // TODO Line (Tokenstream needs line/position of each token aswell)
                }
                continue;
            } else if (tabsPassedThisLine < tabLevel) {
                return instructions.toArray(Instruction[]::new);
            } else if (token == Token.NEWLINE){
                tabsPassedThisLine = 0;
                continue;
            }

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
        }

        errors.forEach(x -> System.out.println(x));
        return instructions.toArray(Instruction[]::new);
    }

    private static Expression findExpression(Iterator<Token> iterator, List<String> errors) {
        if(!iterator.hasNext()) return null;
        Token token = iterator.next();
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
            default: {
                errors.add("Expected Expression"); // TODO Line (Tokenstream needs line/position of each token aswell)
                return null;
            }
        }
    }

    private static boolean expect(Iterator<Token> iterator, Token expected, List<String> errors) {
        if(!iterator.hasNext()) {
            return false;
        }
        Token next = iterator.next();
        if(next != expected) {
            errors.add("Expected " + expected);
            return false;
        }
        return true;
    }

}

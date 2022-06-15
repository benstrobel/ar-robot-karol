import instructions.expressions.*;
import instructions.statements.*;

public enum Token {
    IF("if"),
    WHILE("while"),
    // AND("and"),
    FALSE("false", False.class),
    ISBLOCK("isblock", IsBlock.class),
    ISBORDER("isborder", IsBorder.class),
    ISEAST("iseast", IsEast.class),
    ISNORTH("isnorth", IsNorth.class),
    ISSOUTH("issouth", IsSouth.class),
    ISWEST("iswest", IsWest.class),
    NOT("not"),
    // OR("or"),
    TRUE("true", True.class),
    END("end", End.class),
    LEFTTURN("leftturn", LeftTurn.class),
    LIFT("lift", Lift.class),
    PLACE("place", Place.class),
    RIGHTTURN("rightturn", RightTurn.class),
    STEP("step", Step.class),
    TAB("\t"),
    SPACETAB("    "),
    NEWLINE("\n"),
    BODYOPEN(":"),
    BRACKETOPEN("("),
    BRACKETCLOSED(")");

    public final String keyword;
    public final Class instructionClass;

    Token(String keyword) {
        this.keyword = keyword;
        this.instructionClass = null;
    }

    Token(String keyword, Class instructionClass) {
        this.keyword = keyword;
        this.instructionClass = instructionClass;
    }
}

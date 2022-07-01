package com.example.robotkarolar.karollogic_ramona.enums

enum class ExpressionTyp {
    ISBLOCK,
    ISBOARDER,
    ISEAST,
    ISNORTH,
    ISSOUTH,
    ISWEST,
    NOTISBLOCK,
    NOTISBOARDER,
    NOTISEAST,
    NOTISNORTH,
    NOTISSOUTH,
    NOTISWEST
}

class ExpressionTypHandler {
    fun fromString(value: String): ExpressionTyp {
        when (value) {
            "ISBLOCK" -> return ExpressionTyp.ISBLOCK
            "ISBOARDER" -> return ExpressionTyp.ISBOARDER
            "ISEAST" -> return ExpressionTyp.ISEAST
            "ISNORTH" -> return ExpressionTyp.ISNORTH
            "ISSOUTH" -> return ExpressionTyp.ISSOUTH
            "ISWEST" -> return ExpressionTyp.ISWEST
            "! ISBLOCK" -> return ExpressionTyp.NOTISBLOCK
            "! ISBOARDER" -> return ExpressionTyp.NOTISBOARDER
            "! ISEAST" -> return ExpressionTyp.NOTISEAST
            "! ISNORTH" -> return ExpressionTyp.NOTISNORTH
            "! ISSOUTH" -> return ExpressionTyp.NOTISSOUTH
            "! ISWEST" -> return ExpressionTyp.NOTISWEST
            else -> return ExpressionTyp.ISBLOCK //Just in case
        }
    }

    fun toString(value: ExpressionTyp): String {
        when (value) {
            ExpressionTyp.ISBLOCK -> return "ISBLOCK"
            ExpressionTyp.ISBOARDER -> return "ISBOARDER"
            ExpressionTyp.ISEAST -> return "ISEAST"
            ExpressionTyp.ISNORTH -> return "ISNORTH"
            ExpressionTyp.ISSOUTH -> return "ISSOUTH"
            ExpressionTyp.ISWEST -> return "ISWEST"
            ExpressionTyp.NOTISBLOCK -> return "! ISBLOCK"
            ExpressionTyp.NOTISBOARDER -> return "! ISBOARDER"
            ExpressionTyp.NOTISEAST -> return "! ISEAST"
            ExpressionTyp.NOTISNORTH -> return "! ISNORTH"
            ExpressionTyp.NOTISSOUTH -> return "! ISSOUTH"
            ExpressionTyp.NOTISWEST -> return "! ISWEST"
            else -> return "ISBLOCK" //Just in case
        }
    }
}
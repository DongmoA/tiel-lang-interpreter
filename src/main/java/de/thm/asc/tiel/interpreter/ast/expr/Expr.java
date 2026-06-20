package de.thm.asc.tiel.interpreter.ast.expr;

import de.thm.asc.tiel.interpreter.ast.Positionable;
import de.thm.asc.tiel.interpreter.lexical.Token;

public sealed abstract class Expr implements Positionable<Expr> permits ArrayAccesExpr, ArrayLiteralExpr, AssignExpr, BinaryExpr, CallExpr, GetExpr, LiteralExpr, LogicalExpr, ThisExpr, UnaryExpr, VariableExpr {

    private Token.Position position;

    @Override
    public Expr withPosition(Token.Position position) {
        this.position = position;

        return this;
    }

    @Override
    public Token.Position getPosition() {
        return position;
    }
}

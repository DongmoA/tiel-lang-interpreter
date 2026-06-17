package de.thm.asc.tiel.interpreter.ast.expr;

import de.thm.asc.tiel.interpreter.lexical.Token;

public final class ClassAccessExpr extends Expr {

    public final Expr classExpr;
    public final Token property;

    public ClassAccessExpr(Expr classExpr, Token property) {
        this.classExpr = classExpr;
        this.property = property;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ClassAccessExpr o)) return false;
        return classExpr.equals(o.classExpr) && property.lexeme().equals(o.property.lexeme());
    }
}

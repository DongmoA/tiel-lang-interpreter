package de.thm.asc.tiel.interpreter.ast.expr;

import java.util.List;

/**
 * Represents an array literal expression in the TiEL programming language.
 * */
public final  class ArrayLiteralExpr extends  Expr {
    public final List<Expr> elements;
    public ArrayLiteralExpr(List<Expr> elements) {
        this.elements = elements;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ArrayLiteralExpr o)) return false;
        return elements.equals(o.elements);
    }

}

package de.thm.asc.tiel.interpreter.ast.expr;

public final class ArrayAccesExpr extends Expr {
    public final Expr array;
    public final Expr index;
    public ArrayAccesExpr(Expr array, Expr index) {
        this.array = array;
        this.index = index;
    }

    @Override
    public String toString() {
        return array + "[" + index + "]";
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ArrayAccesExpr o)) return false;
        return array.equals(o.array) && index.equals(o.index);
    }
}

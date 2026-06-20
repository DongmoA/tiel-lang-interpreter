package de.thm.asc.tiel.interpreter.ast.expr;

public final class ThisExpr extends Expr{

   @Override
    public boolean equals(Object obj) {
       return obj instanceof ThisExpr;
   }
}

package de.thm.asc.tiel.interpreter.evaluation;

import de.thm.asc.tiel.interpreter.lexical.Token;

import java.util.List;
import java.util.Map;

/**
 * Represents a class in the TiEL programming language.
 * */
public class TiELClass implements TiELCallable {
    private final String name;
    private final Map<String, TiELFunction> methods;

    public TiELClass(String name, Map<String, TiELFunction> methods) {
        this.name = name;
        this.methods = methods;
    }

    public TiELFunction findMethod(String name) {
        return methods.get(name);
    }

    @Override
    public int arity() {
        var initializer = findMethod(name);
        if (initializer == null) return 0;
        return initializer.arity();
    }

    /**
     * Creates a new instance, calls the initializer if it exists,
     * and returns the created instance.
     */
    @Override
    public TiELValue call(Evaluator evaluator, List<TiELValue> arguments, Token.Position errorPosition) {
         var instance = new TiELInstance(this);
         var initializer = findMethod(name);
         if (initializer != null) {
             initializer.bind(instance).call(evaluator, arguments, errorPosition);
         }
         return instance;
    }

    @Override
    public String toString() {
        return "<class " + name + ">";
    }
}

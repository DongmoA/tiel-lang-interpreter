package de.thm.asc.tiel.interpreter.evaluation;

import de.thm.asc.tiel.interpreter.error.RuntimeError;
import de.thm.asc.tiel.interpreter.lexical.Token;

import java.util.HashMap;
import java.util.Map;

public final class TiELInstance implements TiELValue {

    private final TiELClass parentClass;
    private final Map<String, TiELValue> fields = new HashMap<>();

    public TiELInstance(TiELClass parentClass) {
        this.parentClass = parentClass;
    }

    public TiELValue get(Token name) {
        var value = fields.get(name.lexeme());
        if (value != null) return value;

        var method = parentClass.findMethod(name.lexeme());
        if (method != null) return method.bind(this);

        throw new RuntimeError("Field or method not found: " + name.lexeme(), name.position());
    }

    public void set(Token name, TiELValue value) {
        fields.put(name.lexeme(), value);
    }

    @Override
    public String toString() {
        return "<instance of " + parentClass + ">";
    }
}

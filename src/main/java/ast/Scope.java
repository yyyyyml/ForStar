package ast;

import ir.Value;

import java.util.ArrayList;
import java.util.HashMap;

public class Scope {

    private final ArrayList<HashMap<String, Value>> tables = new ArrayList<>();

    public Scope() {
        tables.add(new HashMap<>());
    }
    private HashMap<String, Value> curTab() {
        return tables.get(tables.size() - 1);
    }

    public boolean isGlobal() {
        return tables.size() == 1;
    }

    public void scopeIn() {
        tables.add(new HashMap<>());
    }

    public void scopeOut() {
        if (this.isGlobal()) {
            throw new RuntimeException("Try to exit the top (global) scope.\n");
        }
        else {
            tables.remove(tables.size() - 1);
        }
    }

}


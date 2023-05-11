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


    public void addDecl(String name, Value val) {
        // Check name repetition. (Security check)
        //其实这个检查目前还不用
        if(this.duplicateDecl(name)) {
            throw new RuntimeException(String.format(
                    "Try to add an declaration with an existing name \"%s\" into current symbol table.",
                    name
            ));
        }
        // If it's a new name.
        curTab().put(name, val);
    }
    public boolean duplicateDecl(String name) {
        return curTab().get(name) != null;
    }
}


package ast;

import frontend.*;

import ir.Module;
import ir.Value;
import ir.types.*;
import ir.Type;
import ir.values.*;

public class Visitor extends SysY2022BaseVisitor<Void> {
    private final Scope scope = new Scope();
    private final Builder builder = new Builder();
    public Visitor (Module module) {
        Builder builder = new Builder(module);
        //this.initRuntimeFunctions();
    }

    @Override
    public Void visitCompUnit(SysYParser.CompUnitContext ctx) {
        super.visitCompUnit(ctx);
        return null;
    }
}

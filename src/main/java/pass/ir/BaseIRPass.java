package pass.ir;

import ir.Module;

public interface BaseIRPass {
    boolean run(Module module);
}

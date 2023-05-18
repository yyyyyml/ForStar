package pass.ir;

import ir.Module;

public interface BaseIRPass {
    void run(Module module);
}

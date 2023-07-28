package pass.backend.register_allocator;

import backend.RISCBasicBlock;

import java.util.HashMap;
import java.util.Map;

public class Visitor {
    private Map<RISCBasicBlock, Boolean> visitedMap;

    public Visitor() {
        visitedMap = new HashMap<>();
    }

    public boolean isVisited(RISCBasicBlock riscBB) {
        return visitedMap.getOrDefault(riscBB, false);
    }

    public void markVisited(RISCBasicBlock riscBB) {
        visitedMap.put(riscBB, true);
    }
}

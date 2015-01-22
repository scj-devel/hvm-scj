package icecaptools.compiler;

public class OffsetPair {
    public int dheapoffset;
    public int pheapoffset;

    private boolean lockAllocated;

    public OffsetPair(int x, int y) {
        this.dheapoffset = x;
        this.pheapoffset = y;
        lockAllocated = false;
    }

    public OffsetPair(OffsetPair other) {
        dheapoffset = other.dheapoffset;
        pheapoffset = other.pheapoffset;
        lockAllocated = other.lockAllocated;
    }

    public void makeRoomForLock() {
        if (!lockAllocated) {
            lockAllocated = true;
        }
    }

    public boolean hasRoomForLock() {
        return lockAllocated;
    }
}

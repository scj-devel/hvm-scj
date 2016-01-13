package icecaptools;

public class CFuncInfo {

    private MethodOrFieldDesc methodOrFieldDesc;
    private IcecapCFunc cfunc;

    public CFuncInfo(MethodOrFieldDesc methodOrFieldDesc, IcecapCFunc cfunc) {
        this.methodOrFieldDesc = methodOrFieldDesc;
        this.cfunc = cfunc;
    }

    public MethodOrFieldDesc getMethodOrFieldDesc() {
        return methodOrFieldDesc;
    }

    public IcecapCFunc getCfunc() {
        return cfunc;
    }

    @Override
    public String toString() {
        return methodOrFieldDesc.toString();
    }
}

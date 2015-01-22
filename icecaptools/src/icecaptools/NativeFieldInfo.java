package icecaptools;

import icecaptools.compiler.FieldInfo;

public class NativeFieldInfo {

    private FieldInfo field;
    private IcecapCVar cvar;

    public NativeFieldInfo(FieldInfo field, IcecapCVar cvar) {
       this.field = field;
       this.cvar = cvar;
    }

    public FieldInfo getField() {
        return field;
    }

    public IcecapCVar getCVar() {
        return cvar;
    }

}

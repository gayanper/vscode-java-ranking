package org.gap.vscode.javaranking.ext.internal;

public final class SignatureHelper {
    private SignatureHelper() {
    }
    
    public static boolean isVarArg(String sig) {
        return sig != null && sig.startsWith("[");
    }
}

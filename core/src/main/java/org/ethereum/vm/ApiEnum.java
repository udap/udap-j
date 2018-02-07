package org.ethereum.vm;

import org.apache.commons.lang3.ArrayUtils;
import org.ethereum.core.CallTransaction;
import org.ethereum.vm.program.Program;
import org.spongycastle.util.encoders.Hex;

import java.util.Arrays;
import java.util.Comparator;

public enum ApiEnum {

    FOO("foo", CallTransaction.Function.fromSignature("foo", new String[]{"uint256", "uint256"}, new String[]{"uint256"})),
    BAR("bar", CallTransaction.Function.fromSignature("bar", new String[]{"uint256", "uint256"}, new String[]{"uint256"})),
    HELLOWORLD("helloworld", CallTransaction.Function.fromSignature("helloworld", new String[]{"uint256", "uint256"}, new String[]{"uint256"})),;

    private String name;
    private CallTransaction.Function function;
    private byte[] functionHash;

    ApiEnum(String name, CallTransaction.Function function) {
        this.name = name;
        this.function = function;
        this.functionHash = function.encodeSignature();
    }

    public static ApiEnum determin(Program program) {
        byte[] functionHash = null;
        if (program == null)
            return null;
        else {
            functionHash = program.getDataCopy(new DataWord(0), new DataWord(4));
        }
        if (ArrayUtils.isNotEmpty(functionHash)) {
            for (ApiEnum e : ApiEnum.values()) {
                if (Arrays.equals(functionHash, e.functionHash)) {
                    return e;
                }
            }
        }
        return null;
    }

    public void delegate(Program program) {
        if (Arrays.equals(FOO.functionHash, this.functionHash)) {
            Api.foo(program);
        }
        if (Arrays.equals(BAR.functionHash, this.functionHash)) {
            Api.bar(program);
        }
        if (Arrays.equals(HELLOWORLD.functionHash, this.functionHash)) {
            Api.helloworld(program);
        }
    }
}
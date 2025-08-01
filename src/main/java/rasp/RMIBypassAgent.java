package rasp;

import java.lang.instrument.Instrumentation;

public class RMIBypassAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("[RASP] Agent Loaded");
        inst.addTransformer(new RMITransformer(), false);
    }
}

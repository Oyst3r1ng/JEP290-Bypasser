package rasp;

import org.objectweb.asm.*;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class RMITransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) {

        if ("java/rmi/server/RemoteObjectInvocationHandler".equals(className)) {
            System.out.println("[RASP] Hooking RemoteObjectInvocationHandler.invokeRemoteMethod");
            ClassReader reader = new ClassReader(classfileBuffer);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_FRAMES);
            ClassVisitor visitor = new ClassVisitor(Opcodes.ASM9, writer) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor,
                                                 String signature, String[] exceptions) {
                    MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                    if ("invokeRemoteMethod".equals(name)) {
                        return new RemoteHandlerAdapter(mv, access, name, descriptor);
                    }
                    return mv;
                }
            };
            reader.accept(visitor, ClassReader.EXPAND_FRAMES);
            return writer.toByteArray();
        }
        return null;
    }
}

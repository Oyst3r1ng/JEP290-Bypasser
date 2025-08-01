package rasp;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

public class RemoteHandlerAdapter extends AdviceAdapter {
    private boolean replaced = false;

    protected RemoteHandlerAdapter(MethodVisitor mv, int access, String name, String descriptor) {
        super(Opcodes.ASM9, mv, access, name, descriptor);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {

        if (opcode == INVOKESTATIC &&
                "java/rmi/server/RemoteObjectInvocationHandler".equals(owner) &&
                "getMethodHash".equals(name) &&
                "(Ljava/lang/reflect/Method;)J".equals(descriptor)) {

            System.out.println("[RASP] Replacing getMethodHash with fixed value 8370655165776887524L");
            mv.visitInsn(POP);

            mv.visitLdcInsn(8370655165776887524L);
            replaced = true;
        } else {
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }
    }
}

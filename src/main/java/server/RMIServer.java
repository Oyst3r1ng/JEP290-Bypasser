package server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {
    public static void main(String[] args) throws Exception {
        HelloRemoteObject helloRemoteObject = new HelloRemoteObject();
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("helloRemoteObject", helloRemoteObject);
        System.out.println("RMI Server is ready...");
    }
}
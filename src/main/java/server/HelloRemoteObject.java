package server;

import common.IRemoteObject;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloRemoteObject extends UnicastRemoteObject implements IRemoteObject {

    public HelloRemoteObject() throws RemoteException {
        super();
    }

    @Override
    public String sayHello(String name) throws RemoteException {
        return "Hello " + name;
    }

    public String sayHello(Object object) throws RemoteException {
        return "Hello Object";
    }
}

package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteObject extends Remote {
    public String sayHello(String name) throws RemoteException;
    public String sayHello(Object object) throws RemoteException;
}
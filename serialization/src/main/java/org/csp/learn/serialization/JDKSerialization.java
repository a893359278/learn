package org.csp.learn.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * @author 陈少平
 * @date 2023-05-22 21:05
 */
public class JDKSerialization {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        File file = new File("user.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
        User user = new User();
        user.setAddress("csp");
        outputStream.writeObject(user);

        user = new User();
        user.setAge(11);
        user.setAddress("qqqqq");
        outputStream.writeObject(user);

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));

        User object = (User) inputStream.readObject();
        User object1 = (User) inputStream.readObject();
        System.out.println(object.getAddress().equals(object1.getAddress()));
    }
}

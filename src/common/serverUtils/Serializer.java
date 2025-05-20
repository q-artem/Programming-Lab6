package common.serverUtils;

import common.exeptions.SerializeException;

import java.io.*;

public class Serializer {
    // я нагло спер этот код из стаковерфлоа

    public static byte[] serialize(Serializable obj) {
        try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
            try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)){
                objectOutputStream.writeObject(obj);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new SerializeException();
        }
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            try(ObjectInputStream o = new ObjectInputStream(b)){
                return o.readObject();
            }
        }
    }
}

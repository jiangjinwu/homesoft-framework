package top.homesoft.syncData.utils;
import java.util.UUID;
public class UUIDUtils {
    public static String[] getUUID(int number) {
        if (number < 1)
            return new String[0];
        String[] retArray = new String[number];
        for (int i = 0; i < number; i++)
            retArray[i] = getUUID();
        return retArray;
    }

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }
}

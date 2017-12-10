package utils;

import java.util.UUID;

/**
 *
 * @author Administrator
 * @date 2017/12/9
 */
public class CommonsUtils {


    /**
     * 生成uid
     * @return
     */
    public static String getUUID() {

        return UUID.randomUUID().toString();

    }
}

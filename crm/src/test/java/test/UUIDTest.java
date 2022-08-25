package test;

import org.junit.Test;

import java.util.UUID;

/**
 * @Date 2022/8/25 16:39
 */
public class UUIDTest {
    @Test
    public void getUUID(){
        System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
    }
}

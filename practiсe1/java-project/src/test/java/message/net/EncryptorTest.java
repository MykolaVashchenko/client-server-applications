package message.net;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.apache.commons.codec.binary.Hex;

public class EncryptorTest {

    Encryptor encryptor = new Encryptor();
    String input = "Hello, World!";

    @Test
    void processInput (){

        byte[] testArr1 = encryptor.entry_take(input);

        Assertions.assertThat(Hex.encodeHexString(testArr1)).isEqualTo("130d0000000000000082000000154fb8000000a20000008548656c6c6f2c20576f726c6421254a");
    }
}
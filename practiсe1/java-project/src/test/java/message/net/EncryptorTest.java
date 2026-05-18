package message.net;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class EncryptorTest {

    @Test
    void shouldThrowWhenMessageIsEmpty() {
        try {
            new MessagePOJO((byte) 7, 130L, 166, 133, "");
            org.junit.jupiter.api.Assertions.fail("Message is missed");
        } catch (IllegalArgumentException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Message cannot be empty");
        }
    }

    @Test
    void shouldEncryptDataCorrectly() {
        Encryptor encryptor = new Encryptor();
        MessagePOJO pojo = new MessagePOJO((byte) 13, 100L, 10, 5, "Secret");
        byte[] result = encryptor.entry_take(pojo);

        Assertions.assertThat(result[0]).isEqualTo((byte) 0x13);
    }
}
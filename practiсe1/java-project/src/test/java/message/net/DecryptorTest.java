package message.net;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DecryptorTest {
    Encryptor encryptor = new Encryptor();
    Decryptor decryptor = new Decryptor();

    @Test
    void shouldDecodeDataSuccessfully() {
        MessagePOJO originalPojo = new MessagePOJO((byte) 7, 130L, 166, 133, "message1");

        byte[] validPacket = encryptor.entry_take(originalPojo);
        MessagePOJO decodedPojo = decryptor.decrypt(validPacket);

        Assertions.assertThat(decodedPojo.getBPktId()).isEqualTo(130L);
        Assertions.assertThat(decodedPojo.getCType()).isEqualTo(166);
        Assertions.assertThat(decodedPojo.getBUserId()).isEqualTo(133);
        Assertions.assertThat(decodedPojo.getMessage()).isEqualTo("message1");

    }

    @Test
    void shouldThrowWhenMagicByteIsWrong() {
        MessagePOJO pojo = new MessagePOJO((byte) 7, 130L, 166, 133, "message1");
        byte[] badData = encryptor.entry_take(pojo);

        badData[0] = (byte) 0x99;

        try {
            decryptor.decrypt(badData);

        } catch (IllegalArgumentException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Wrong magic byte");
        }
    }

    @Test
    void shouldThrowWhenDataIsDamaged() {
        MessagePOJO pojo = new MessagePOJO((byte) 7, 130L, 166, 133, "message1");
        byte[] damagedData = encryptor.entry_take(pojo);
        damagedData[5] = 0x00;

        try {
            decryptor.decrypt(damagedData);

        } catch (RuntimeException e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Data is damaged");
        }
    }
}
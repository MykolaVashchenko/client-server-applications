package message.net;

import java.nio.ByteBuffer;

public class Encryptor {
    public byte[] entry_take(MessagePOJO pojo){

        byte[] encryptedMessageBytes = CryptoTools.encrypt(pojo.getMessage());
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + 1 + 8 + 4*3 + 2*2 + encryptedMessageBytes.length);

        byteBuffer.put((byte)0x13)
                .put(pojo.getBSrc())
                .putLong(pojo.getBPktId())
                .putInt(encryptedMessageBytes.length + 8);

        byte [] thrtnHolder = new byte[14];
        byteBuffer.position(0);
        byteBuffer.get(thrtnHolder, 0, 14);
        byteBuffer.putShort(Crc16.calculateCrc(thrtnHolder));

        byteBuffer.putInt(pojo.getCType())
                .putInt(pojo.getBUserId())
                .put(encryptedMessageBytes);

        byte [] secHolder = new byte[encryptedMessageBytes.length + 8];
        byteBuffer.get(16, secHolder, 0, secHolder.length);
        byteBuffer.putShort(Crc16.calculateCrc(secHolder));

        return byteBuffer.array();
    }
}
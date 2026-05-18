package message.net;

import java.nio.ByteBuffer;

public class Encryptor {
    public byte[] entry_take(String entryStr){
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + 1 + 8 + 4*3 + 2*2 + entryStr.getBytes().length);

        byteBuffer.put((byte)0x13)
                .put((byte)13)
                .putLong(130)
                .putInt(entryStr.getBytes().length + 8);

        byte [] thrtnHolder = new byte[14];

        byteBuffer.position(0);
        byteBuffer.get(thrtnHolder, 0, 14);

        byteBuffer.putShort(Crc16.calculateCrc(thrtnHolder));

        byteBuffer.putInt(162)
                .putInt(133)
                .put(entryStr.getBytes());

        byte [] secHolder = new byte[entryStr.getBytes().length + 8];
        byteBuffer.get(16, secHolder, 0, secHolder.length);

        byteBuffer.putShort(Crc16.calculateCrc(secHolder));

        return byteBuffer.array();
    }
}
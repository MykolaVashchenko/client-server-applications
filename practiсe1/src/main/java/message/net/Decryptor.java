package message.net;

import java.nio.ByteBuffer;

public class Decryptor {
    public MessagePOJO decrypt(byte[] rawData) {
        if (rawData == null || rawData.length < 16) {
            throw new IllegalArgumentException("Data is empty or too short");
        }

        ByteBuffer buffer = ByteBuffer.wrap(rawData);

        byte magicByte = buffer.get();
        if (magicByte != 0x13) {
            throw new IllegalArgumentException("Wrong magic byte");
        }

        byte[] headerForCrc = new byte[14];
        System.arraycopy(rawData, 0, headerForCrc, 0, 14);
        short calculatedHeaderCrc = Crc16.calculateCrc(headerForCrc);

        buffer.position(14);
        short actualHeaderCrc = buffer.getShort();

        if (calculatedHeaderCrc != actualHeaderCrc) {
            throw new RuntimeException("Data is damaged");
        }


        buffer.position(1);
        byte bSrc = buffer.get();
        long bPktId = buffer.getLong();
        int wLen = buffer.getInt();


        byte[] bodyForCrc = new byte[wLen];
        System.arraycopy(rawData, 16, bodyForCrc, 0, wLen);
        short calculatedBodyCrc = Crc16.calculateCrc(bodyForCrc);

        buffer.position(16 + wLen);
        short actualBodyCrc = buffer.getShort();

        if (calculatedBodyCrc != actualBodyCrc) {
            throw new RuntimeException("Data is damaged.");
        }

        buffer.position(16);
        int cType = buffer.getInt();
        int bUserId = buffer.getInt();

        int messageLength = wLen - 8;
        byte[] encryptedMessage = new byte[messageLength];
        buffer.get(encryptedMessage);

        String decryptedMessage = CryptoTools.decrypt(encryptedMessage);

        return new MessagePOJO(bSrc, bPktId, cType, bUserId, decryptedMessage);
    }
}

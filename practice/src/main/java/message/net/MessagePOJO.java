package message.net;

public class MessagePOJO {
    private final byte bSrc;
    private final long bPktId;

    private final int cType;
    private final int bUserId;
    private final String message;

    public static final int GET_QUANTITY = 1;
    public static final int WRITE_OFF = 2;
    public static final int ADD_QUANTITY = 3;
    public static final int ADD_GROUP = 4;
    public static final int ADD_ITEM_TO_GROUP = 5;
    public static final int SET_PRICE = 6;

    public MessagePOJO(byte bSrc, long bPktId, int cType, int bUserId, String message) {
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }
        if (message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }

        this.bSrc = bSrc;
        this.bPktId = bPktId;
        this.cType = cType;
        this.bUserId = bUserId;
        this.message = message;
    }

    public byte getBSrc() {
        return bSrc;
    }

    public long getBPktId() {
        return bPktId;
    }

    public int getCType() {
        return cType;
    }

    public int getBUserId() {
        return bUserId;
    }

    public String getMessage() {
        return message;
    }
}
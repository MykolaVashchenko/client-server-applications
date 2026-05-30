package message.net;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoreLogicTest {

    @Test
    public void testAllStoreCommands() {
        Store store = new Store();

        MessagePOJO getMessage = new MessagePOJO((byte)1, 1L, MessagePOJO.GET_QUANTITY, 1, "Гречка");
        assertEquals("0", store.proccessCommand(getMessage));

        MessagePOJO addMessage = new MessagePOJO((byte)1, 2L, MessagePOJO.ADD_QUANTITY, 1, "Гречка,15");
        assertEquals("Items added", store.proccessCommand(addMessage));

        assertEquals("15", store.proccessCommand(getMessage));

        MessagePOJO writeOffSuccess = new MessagePOJO((byte)1, 3L, MessagePOJO.WRITE_OFF, 1, "Гречка,5");
        assertEquals("Items written off", store.proccessCommand(writeOffSuccess));

        assertEquals("10", store.proccessCommand(getMessage));

        MessagePOJO writeOffFail = new MessagePOJO((byte)1, 4L, MessagePOJO.WRITE_OFF, 1, "Гречка,20");
        assertEquals("Not enough items", store.proccessCommand(writeOffFail));

        MessagePOJO addGroup = new MessagePOJO((byte)1, 5L, MessagePOJO.ADD_GROUP, 1, "Крупи");
        assertEquals("Group added", store.proccessCommand(addGroup));

        MessagePOJO addItemToGroup = new MessagePOJO((byte)1, 6L, MessagePOJO.ADD_ITEM_TO_GROUP, 1, "Гречка,Крупи");
        assertEquals("Item added to group", store.proccessCommand(addItemToGroup));

        MessagePOJO setPrice = new MessagePOJO((byte)1, 7L, MessagePOJO.SET_PRICE, 1, "Гречка,45.50");
        assertEquals("Price set", store.proccessCommand(setPrice));
    }
}
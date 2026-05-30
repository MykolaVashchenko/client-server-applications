package message.net;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Store {
    private Map<String, Integer> storage = new HashMap<>();
    private Map<String, Double> prices = new HashMap<>();
    private Map<String, String> groups = new HashMap<>();
    private Set<String> availableGroups = new HashSet<>();

    public synchronized String proccessCommand(MessagePOJO messagePOJO){
        int commandType = messagePOJO.getCType();
        String payload = messagePOJO.getMessage();

        String[] words = payload.split(",");
        String itemName = words[0];

        switch (commandType){
            case MessagePOJO.GET_QUANTITY:
                if (storage.containsKey(itemName)) {
                    int amount = storage.get(itemName);
                    return String.valueOf(amount);
                } else {
                    return "0";
                }

            case MessagePOJO.WRITE_OFF:
                int amountToSubtract = Integer.parseInt(words[1]);

                if (storage.containsKey(itemName)) {
                    int currentAmount = storage.get(itemName);

                    if (currentAmount >= amountToSubtract) {
                        int newAmount = currentAmount - amountToSubtract;
                        storage.put(itemName, newAmount);
                        return "Items written off";
                    } else {
                        return "Not enough items";
                    }
                } else {
                    return "Item not found";
                }

            case MessagePOJO.ADD_QUANTITY:
                int amountToAdd = Integer.parseInt(words[1]);

                if (storage.containsKey(itemName)) {
                    int currentAmount = storage.get(itemName);
                    int newAmount = currentAmount + amountToAdd;
                    storage.put(itemName, newAmount);
                } else {
                    storage.put(itemName, amountToAdd);
                }
                return "Items added";

            case MessagePOJO.ADD_GROUP:
                String newGroupName = words[0];
                availableGroups.add(newGroupName);
                return "Group added";

            case MessagePOJO.ADD_ITEM_TO_GROUP:
                String groupName = words[1];
                if (availableGroups.contains(groupName)) {
                    groups.put(itemName, groupName);
                    return "Item added to group";
                } else {
                    return "Group doesn't exist";
                }

            case MessagePOJO.SET_PRICE:
                double price = Double.parseDouble(words[1]);
                prices.put(itemName, price);
                return "Price set";

            default:
                return "Unknown command";


        }
    }
}

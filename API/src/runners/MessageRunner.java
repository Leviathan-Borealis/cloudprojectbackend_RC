package runners;

import DataWrappers.LogEntry;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import connectors.FireBaseConnector;


public class MessageRunner {
    public boolean handleTextMessage(String message){
        LogEntry entry = null;

        try {
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(message);
            JsonObject jsonObject = element.getAsJsonObject();
            entry = new LogEntry(
                    jsonObject.get("userId").getAsString(),
                    jsonObject.get("data").getAsString()
            );
        } catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        if(entry != null){
            DatabaseReference ref = FireBaseConnector.getIntance().getDefaultDatabase().getReference();
            DatabaseReference refEntries = ref.child("log_entries");

            refEntries.child(Long.toString(System.currentTimeMillis())).setValueAsync(entry);

            System.out.println("Valid Json");
            return true;
        }

        System.out.println("Invalid Json");
        return false;
    }

    public String handleBinaryMessage(Byte[] binaryMessage){
        return null;
    }
}

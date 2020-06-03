package connectors;

import DataWrappers.LogEntry;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.*;

public class FireBaseConnector {
    private static FireBaseConnector intance;
    private final FirebaseApp defaultApp;
    private final FirebaseDatabase defaultDatabase;

    private FireBaseConnector() throws IOException {

        FileInputStream serviceAccount =
                new FileInputStream("<PathToYourCredentialsJson>");

        //setDatabaseUrl("https://<ProjectName>.firebaseio.com") need to be changed to your endpoint
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://<ProjectName>.firebaseio.com")
                .build();

        defaultApp = FirebaseApp.initializeApp(options);
        defaultDatabase = FirebaseDatabase.getInstance(defaultApp);
        //getEntries(); //Just for debug
    }

    public static FireBaseConnector getIntance(){
        if(intance == null){
            try {
                intance = new FireBaseConnector();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return intance;
    }

    public FirebaseApp getDefaultApp() {
        return defaultApp;
    }

    public FirebaseDatabase getDefaultDatabase() {
        return defaultDatabase;
    }

    private void getEntries(){
        defaultDatabase.getReference().child("log_entries").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, LogEntry> td = (HashMap<String,LogEntry>) dataSnapshot.getValue();
                for (String s:td.keySet()){
                    System.out.println(Instant.ofEpochMilli(Long.parseLong(s)) + " " + td.get(s));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}

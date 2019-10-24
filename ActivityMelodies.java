package f.kahawaafrica.ising;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class ActivityMelodies extends AppCompatActivity {
    private ListView ListView;
    private ListView lv;
    static final int MY_PERMISSION_REQUEST = 1;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    private static final String TAG = "ActivityMelodies";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_melodies);

        Log.d(TAG, "ActivityMelodies");

        ListView lv;
        final ArrayList<String> FilesInFolder = GetFiles("/sdcard/iSing");
        lv =  findViewById(R.id.listView);

        lv.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, FilesInFolder));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // Clicking on items
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String audioPath =  FilesInFolder.get(position);
                Log.d(TAG, "Audio Path: " + audioPath);
                shareAudio(audioPath);

                return true;
            }
        });
    }

    private void shareAudio(String audioPath) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("audio/mp3");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(
                audioPath        ));
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    public ArrayList<String> GetFiles(String DirectoryPath) {
        Log.d(TAG, "GetFiles called");

        ArrayList<String> MyFiles = new ArrayList<String>();
        File f = new File(DirectoryPath);

        f.mkdirs();
        File[] files = f.listFiles();
        if (files.length == 0) {
            Log.d(TAG, "GetFiles called : files.length == 0");

            return null;
        } else {
            Log.d(TAG, "GetFiles called : files.length != 0");

            for (int i = 0; i<files.length; i++) {
                MyFiles.add(files[i].getName());
            }
        }

        Log.d(TAG, MyFiles.toString());
        return MyFiles;

    }
}


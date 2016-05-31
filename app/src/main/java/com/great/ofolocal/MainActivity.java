package com.great.ofolocal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    static final int RECORD_REQUEST_CODE = 1;
    private EditText id, psw;
    private Button search, importRecord, back, importFile;
    private Finder finder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupComponent();
        finder = new Finder();
    }

    private void setupComponent() {
        id = (EditText)findViewById(R.id.editId);
        back = (Button)findViewById(R.id.backBt);
        search = (Button)findViewById(R.id.searchBt);
        importRecord = (Button)findViewById(R.id.importBt);
        importFile = (Button)findViewById(R.id.importFileBt);
        psw = (EditText)findViewById(R.id.editPsw);
        search.setOnClickListener(searchOnClickListener);
        importRecord.setOnClickListener(importOnClickListener);
        back.setOnClickListener(backOnClickListener);
        importFile.setOnClickListener(importFileOnClickListener);
        init();
    }

    Button.OnClickListener searchOnClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            String key = id.getText().toString();
            String value = psw.getText().toString();
            String op = ((Button)v).getText().toString();
            if(op.compareTo("Search") == 0) {
                if (key.isEmpty())
                    return;
                String psw = finder.findRecord(key);
                if (psw == null)
                    Toast.makeText(getApplicationContext(), "No Record!", Toast.LENGTH_SHORT).show();
                else
                    id.setText(psw);
            }
            else {
                if (key.isEmpty() || value.isEmpty())
                    return;
                if(key.length() != 5 || value.length() != 4) {
                    Toast.makeText(getApplicationContext(), "Invalid Record!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(finder.importRecords(key, value))
                    Toast.makeText(getApplicationContext(), "Import Successful!", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "Record Already Exists", Toast.LENGTH_SHORT).show();
            }
        }
    };

    Button.OnClickListener importOnClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            id.animate().translationY(-200);
            psw.setVisibility(View.VISIBLE);
            importRecord.setVisibility(View.INVISIBLE);
            search.setText("Import");
            back.setVisibility(View.VISIBLE);
            importFile.setVisibility(View.INVISIBLE);
        }
    };

    Button.OnClickListener backOnClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            init();
        }
    };

    Button.OnClickListener importFileOnClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            Intent fileManage = new Intent(Intent.ACTION_GET_CONTENT);
            fileManage.setType("file/*");
            startActivityForResult(fileManage, RECORD_REQUEST_CODE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RECORD_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {

            }
        }
    }

    private void init() {
        id.animate().translationY(0);
        psw.setVisibility(View.INVISIBLE);
        importRecord.setVisibility(View.VISIBLE);
        importFile.setVisibility(View.INVISIBLE);
        search.setText("Search");
        back.setVisibility(View.INVISIBLE);
        id.setText("");
        psw.setText("");
    }
}

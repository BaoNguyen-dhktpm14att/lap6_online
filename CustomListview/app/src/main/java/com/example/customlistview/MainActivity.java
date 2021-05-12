package com.example.customlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    DatabaseHandler databaseHandler;
    Button save_btn, cancel_btn;
    ArrayAdapter<Traval> arrayAdapter;
    ListView listView;
    List<Traval> travalList= new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Traval[] traval = {new Traval(1, "đà nẵng ")};


        travalList.add(traval[0]);
        DatabaseHandler db = new DatabaseHandler(this);
        Log.d("TAG", "onCreate: ");
        db.addTraval(traval[0]);
        final List<Traval>[] travals = new List[]{db.getAllTravalList()};
        ArrayAdapter<Object> adapter = new ArrayAdapter<>(this, R.layout.activity_main, R.id.tv_ten);
        listView.setAdapter(adapter);

        save_btn.setOnClickListener(new View.OnClickListener() {
            private BreakIterator editText;
            private String editText1;

            @Override
            public void onClick(View v) {
                int flag = 0;
                String name = editText.getText().toString();
                for (int i = 0; i < travals[0].size(); i++) {
                    if (travals[0].get(i).getName() != name && editText1.length() != 0) {
                        traval[0] = new Traval();
                        db.addTraval(traval[0]);
                        adapter.add(traval[0]);
                        Toast.makeText(MainActivity.this, "Thêm travel thành công!!!", Toast.LENGTH_SHORT).show();
                        flag++;
                        break;
                    } else
                        flag = 0;
                }
                if (flag == 0)
                    Toast.makeText(MainActivity.this, "Tên travel không hợp lệ hoặc bị trùng!!!", Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private BreakIterator editText;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = view.findViewById(R.id.btn_xoa);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteTravel(travalList, position);
                        recreate();
                    }
                });
                ImageView btnEdit = view.findViewById(R.id.btn_eidt);
                String name = editText.getText().toString();
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.updateTravel(name, travalList, position);
                        recreate();
                    }
                });
            }
        });


    }
}
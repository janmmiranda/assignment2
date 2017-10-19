package com.example.kendric.contactlist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    private EditText inputContactName, inputContactNumber;
    private ListView contactListView;
    private Button addContactButton, deleteContactButton, addPersonButton;
    private ArrayList<Contact> contactArrayList = new ArrayList<>();
    CustomAdapter contactAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactAdapter = new CustomAdapter();

        contactListView = (ListView) findViewById(R.id.contact_list);
        contactListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        contactListView.setAdapter(contactAdapter);


        addContactButton = (Button) findViewById(R.id.add_button);

        if (getIntent().getExtras() != null) {
            Toast.makeText(this, "intent != null", Toast.LENGTH_LONG).show();

            contactArrayList = (ArrayList<Contact>) getIntent().getExtras().getSerializable("Contact list");
            contactAdapter.notifyDataSetChanged();
        }

        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int k = 0; k < contactArrayList.size(); k++) {
                    contactArrayList.get(k).setSelected(false);
                }
                Intent intent = new Intent(MainActivity.this, AddContactActivity.class);
                intent.putExtra("Contact list", (Serializable) contactArrayList);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Contact list", (Serializable) contactArrayList);
                intent.putExtra("Bundle", bundle);
                startActivity(intent);
            }
        });

        deleteContactButton = (Button) findViewById(R.id.delete_button);

        deleteContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int k = 0;
                while (k < contactArrayList.size()) {
                    if (contactArrayList.get(k).isSelected()) {
                        contactArrayList.remove(k);
                        contactAdapter.notifyDataSetChanged();
                        k--;
                    }
                    k++;
                }
            }
        });
    }


    // CustomAdapter to change the view for our ListView
    class CustomAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return contactArrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        // Set the text for the list of tasks
        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.contact_list_layout, null);

            TextView contact_name = (TextView) view.findViewById(R.id.contact_name_text);

            contact_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int k = 0; k < contactArrayList.size(); k++) {
                        contactArrayList.get(k).setSelected(false);
                    }
                    Intent intent = new Intent(MainActivity.this, ViewContactActivity.class);
                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("Contact list", (Serializable) contactArrayList);
//                    bundle.putSerializable("Contact", (Serializable) contactArrayList.get(i));
                    intent.putExtra("Bundle", bundle);
                    intent.putExtra("Contact list", (Serializable) contactArrayList);
                    intent.putExtra("Contact", (Serializable) contactArrayList.get(i));
                    startActivity(intent);
                }
            });


            final CheckBox contact_checkbox = (CheckBox) view.findViewById(R.id.contact_list_checkbox);

            contact_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    if (isChecked) {
                        contact_checkbox.setChecked(true);
                        contactArrayList.get(i).setSelected(true);
                    }
                    else {
                        contact_checkbox.setChecked(false);
                        contactArrayList.get(i).setSelected(false);
                    }
                }
            });


            contact_name.setText(contactArrayList.get(i).getName());
            contact_checkbox.setChecked(contactArrayList.get(i).isSelected());
            return view;
        }
    }

}

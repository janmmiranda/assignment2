package com.example.kendric.contactlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AddContactActivity extends AppCompatActivity {

    EditText inputName, inputNumber;
    Button addPersonButton;
    ListView relationshipView;
    ArrayList<Contact> contactArrayList, relationshipArrayList, copyArraylist;
    RelationshipAdapter relationshipAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);

        inputName = (EditText) findViewById(R.id.input_name);
        inputNumber = (EditText) findViewById(R.id.input_number);
        addPersonButton = (Button) findViewById(R.id.add_person_button);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        contactArrayList = (ArrayList<Contact>) bundle.getSerializable("Contact list");
//        for (Contact pair : contactArrayList) {
//            Toast.makeText(this, pair.getName(), Toast.LENGTH_LONG).show();
//        }

        relationshipView = (ListView) findViewById(R.id.relationship_edit);
        relationshipAdapter = new RelationshipAdapter();
        relationshipView.setAdapter(relationshipAdapter);


        copyArraylist = new ArrayList<Contact>();
//        if (copyArraylist == null) {
//            Toast.makeText(this, "Copyarraylist = NULL", Toast.LENGTH_LONG).show();
//        }
        for (Contact contact: contactArrayList) {
            copyArraylist.add(contact);
        }

        addPersonButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String name = inputName.getText().toString();
                String number = inputNumber.getText().toString();

                if (name.trim().equals("")) {
                    inputName.setError("Missing a input name");
                } else if (number.trim().equals("")) {
                    inputNumber.setError("Missing a input number");
                } else {

                    relationshipArrayList = new ArrayList<Contact>();
                    for(int k = 0; k < copyArraylist.size(); k++) {
                        if(copyArraylist.get(k).isSelected()) {
                            relationshipArrayList.add(copyArraylist.get(k));
                        }
                    }
                    contactArrayList.add(new Contact(name, number, relationshipArrayList));
                    for (int k = 0; k < contactArrayList.size(); k++) {
                        contactArrayList.get(k).setSelected(false);
                    }
                    Intent intent = new Intent(AddContactActivity.this, MainActivity.class);
                    intent.putExtra("Contact list", (Serializable) contactArrayList);
                    startActivity(intent);
                }
            }
        });

    }
    class RelationshipAdapter extends BaseAdapter {
        @Override
        public int getCount() {
//            return copyArraylist.size();
            if (copyArraylist == null) {
                return 0;
            }
            return copyArraylist.size();
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
            view = getLayoutInflater().inflate(R.layout.add_contact_layout, null);
            TextView contact_name = (TextView) view.findViewById(R.id.add_relationship_name);

            final CheckBox contact_checkbox = (CheckBox) view.findViewById(R.id.relationship_checkbox);

            contact_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    if (isChecked) {
                        Contact temp = new Contact(copyArraylist.get(i).getName(), copyArraylist.get(i).getNumber(), copyArraylist.get(i).getRelationshipList());
                        copyArraylist.remove(i);
                        copyArraylist.add(0,temp);
                        contact_checkbox.setChecked(true);
                        relationshipAdapter.notifyDataSetChanged();
                        copyArraylist.get(0).setSelected(true);
                    }
                    else {
                        Contact temp = new Contact(copyArraylist.get(i).getName(), copyArraylist.get(i).getNumber(), copyArraylist.get(i).getRelationshipList());
                        copyArraylist.remove(i);
                        copyArraylist.add(temp);
                        contact_checkbox.setChecked(false);
                        relationshipAdapter.notifyDataSetChanged();
                        copyArraylist.get(copyArraylist.size()-1).setSelected(false);
                    }
                }
            });

            contact_name.setText(copyArraylist.get(i).getName());
            contact_checkbox.setChecked(copyArraylist.get(i).isSelected());
            return view;
        }
    }
}

package com.example.kendric.contactlist;

import android.content.Context;
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

/**
 * Created by Kendric on 10/18/2017.
 */

public class ViewContactActivity extends AppCompatActivity {

    TextView nameBox, numberBox;
    ListView relationshipView;
    ArrayList<Contact> contactArrayList, relationshipArrayList;
    ContactAdapter contactViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
//        contactArrayList = (ArrayList<Contact>) bundle.getSerializable("Contact list");
        contactArrayList = (ArrayList<Contact>) intent.getSerializableExtra("Contact list");
//        Contact contact = (Contact) bundle.getSerializable("Contact");
        Contact contact = (Contact) intent.getSerializableExtra("Contact");

        if (contactArrayList == null) {
            Toast.makeText(this, "WHY IS THIS HAPPENING", Toast.LENGTH_LONG).show();
        }

        nameBox = (TextView) findViewById(R.id.contact_name);
        numberBox = (TextView) findViewById(R.id.contact_number);

        relationshipArrayList = contact.getRelationshipList();

        nameBox.setText(contact.getName());
        numberBox.setText(contact.getNumber());
        relationshipView = (ListView) findViewById(R.id.relationship_list);
        contactViewAdapter = new ContactAdapter();
        relationshipView.setAdapter(contactViewAdapter);
    }

    class ContactAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            if (relationshipArrayList == null) {
                return 0;
            }
            return relationshipArrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.view_contact_layout, null);

            TextView contact_name = (TextView) view.findViewById(R.id.view_contact_list_name);

            contact_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    for (int k = 0; k < contactArrayList.size(); k++) {
//                        contactArrayList.get(k).setSelected(false);
//                    }
                    Intent intent = new Intent(ViewContactActivity.this, ViewContactActivity.class);
//                    Bundle bundle = new Bundle();
//                    intent.putExtra("Bundle", bundle);
                    intent.putExtra("Contact list", (Serializable) contactArrayList);
                    intent.putExtra("Contact", (Serializable) relationshipArrayList.get(i));
//                    finish();
                    startActivity(intent);
                }
            });

            contact_name.setText(relationshipArrayList.get(i).getName());
            return view;
        }
    }


}

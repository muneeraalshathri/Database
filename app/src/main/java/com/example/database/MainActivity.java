package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText productQuantity, productName;
    TextView productID;

    int pID;
    String pName, pQuant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bttnAdd = (Button)findViewById(R.id.bttnAdd);
        Button bttnFind = (Button)findViewById(R.id.bttnFind);
        Button bttnDelete = (Button)findViewById(R.id.bttnDelete);

        myDB = new DatabaseHelper(this);

        productID = (TextView)findViewById(R.id.productID);
        productQuantity = (EditText)findViewById(R.id.productQuantity);
        productName = (EditText)findViewById(R.id.productName);


        bttnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pName = productName.getText().toString();
                pQuant = productQuantity.getText().toString();
                if(!myDB.addData("FirstItem", "9")) {
                    Toast.makeText(MainActivity.this, "Insertion Failed", Toast.LENGTH_SHORT).show();
                }

                else {
                    myDB.addData(pName, pQuant);
                    Toast.makeText(MainActivity.this, "Insertion Successful", Toast.LENGTH_SHORT).show();
                }

                //myDB.addData("SecondItem", "13");
            }
        });

        bttnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pID = Integer.parseInt(productID.getText().toString());
                pName = productName.getText().toString();
                pQuant = productQuantity.getText().toString();
                /*Cursor cursor = myDB.structuredQuery(pID);
                String cID = cursor.getString(0);
                String cName = cursor.getString(1);
                String cPrQuant = cursor.getString(2);
                Toast.makeText(MainActivity.this,
                        cID+","+cName+","+cPrQuant, Toast.LENGTH_LONG).show();*/
                Cursor cur = myDB.viewData();
                StringBuffer buffer = new StringBuffer();
                while(cur.moveToNext())
                {
                    buffer.append("id: " + cur.getString(0)+ "\n");
                    buffer.append("Name: " + cur.getString(1)+ "\n");
                    buffer.append("Quantity: " + cur.getString(2)+ "\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);  // a dialog box that can be closed
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();



            }
        });

        bttnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pID = Integer.parseInt(productID.getText().toString());
                myDB.deleteData(pID);
                //Boolean checkDel = myDB.deleteData(pID);
            }
        });
    }
}
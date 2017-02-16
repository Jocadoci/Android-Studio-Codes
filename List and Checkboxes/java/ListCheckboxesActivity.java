package jocadoci.;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListCheckboxesActivity extends Activity {

    MyCustomAdapter dataAdapter = null;
    TextView head;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        head = (TextView) findViewById(R.id.f);
        head.setText("Focacha MENU");

        //Generate list View from ArrayList
        displayListView();
        //Generate another ArrayList with the items selected from the old ArrayList
        createItemstoArray();


    }

    public void displayListView() {

        //Array list of menu
        ArrayList<Products> productsList = new ArrayList<>();

        products = new Products("HEADER","(DESCRIPTIONS)","PRICE",false);
        productsList.add(products);
        products = new Products("HEADER","(DESCRIPTIONS)","PRICE",false);
        productsList.add(products);
        // How many we want to add.

        //Create an ArrayAdapter from the String Array
        dataAdapter = new MyCustomAdapter(this,
                R.layout.list_info, productsList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Products products = (Products) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + products.getName() + " " + products.getPrice(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    public class MyCustomAdapter extends ArrayAdapter<Products> {

        public ArrayList<Products> productsList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Products> productsList) {
            super(context, textViewResourceId, productsList);
            this.productsList = new ArrayList<Products>();
            this.productsList.addAll(productsList);
        }
        //Holders
        public class ViewHolder {
            CheckBox name;
            TextView code;
            TextView price;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Here is where is called the Producst class.
            Products products = productsList.get(position);
            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_info, null);

                holder = new ViewHolder();
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.price = (TextView) convertView.findViewById(R.id.price);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Products products = (Products) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                 cb.getText(),
                                Toast.LENGTH_LONG).show();
                        products.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            //Set the holder to how would show in the ListView
            holder.code.setText("" +  products.getCode() + "");
            holder.price.setText("$" + products.getPrice());
            holder.name.setText(products.getName());
            holder.name.setChecked(products.isSelected());
            holder.name.setTag(products);

            return convertView;

        }

    }

    public void createItemstoArray() {


        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //int go = 1;//Its to validate when use this activity for second time
                //int total = 0; //Initialize an integer to get the total price of items selected
                final ArrayList<String> pedidos = new ArrayList<>();//Generate a new ArrayList from items selected
                final ArrayList<Products> productsList = dataAdapter.productsList;//Set old ArrayList
                //Start a loop to know what items are selected
                for (int i = 0; i < productsList.size(); i++) {
                    Products products = productsList.get(i);

                    if (products.isSelected()) {
                        //If the product is selected add to the new ArrayList the Name and Price
                        pedidos.add(products.getName()+"    "+"$"+products.getPrice());
                        // If you want, you can make a Toast to show what checkboxes were selected.
                        //From the price of every item selected, keep adding to get total
                        total = total +  Integer.parseInt(products.getPrice());

                    }

                }

            }
        });

    }



}
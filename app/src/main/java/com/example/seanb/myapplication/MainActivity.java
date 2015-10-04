package com.example.seanb.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    ArrayList<String> arrayList = new ArrayList<String>();  // create array list
    String string = "";
    String string1 = "";

    // two text views are used, textView1 which shows the result after inputs and textView2, which
    // shows the numbers that are being inputted
    // onClick2 is the method that will handle all button mappings for buttons {0-9,+,-,/,*,.,}
    // (all buttons except =)
    public void onClick2(View v){

        TextView textView2 = (TextView) findViewById(R.id.textView2);
        Button button = (Button) v;
        string = (String) button.getText().toString();  // map values to string

        // as soon as an operator is entered, it gets it's own value in the array
        // eg. if I enter the numbers 567 and then "+", the "+" gets it's own index in the array while
        // 567 stays as one value in the array. (567 has index 0, while + has index 1)
        if(!string.contains("+") && !string.contains("-") && !string.contains("*") && !string.contains("/")) {
            string1 = string1 + string;

            if (arrayList.size()>0) {
                arrayList.remove((arrayList.size()-1)); // remove the index of last entered value

            }
            arrayList.add(string1); // add current string to array
        }
        else {
            arrayList.add(string); // because we remove the last entered symbol
            arrayList.add(string); // we add it twice, so one remains.
            string1="";            // clear string value to null
                                   // entering "+" gives us {+, +}
                                   // then entering a digit replaces the 2nd "+" with that digit
        }
        textView2.setText(textView2.getText().toString()+string); // input gets printed on textView2

    }

    // method onClick2 handles the calculations between values entered by onClick2.  Basically it
    // handles the "=".
    public void onClick1 (View v) {

        // textView1 is the final value
        TextView textView1 = (TextView) findViewById(R.id.textView);

        float calcValue = 0; // default value
        int c = arrayList.size();

        // if the array has the values {2, +, 3, *, 4, -, 3}, size  = 7
        while (c != 1) {
            if (c > 3) {
                // index 3 is the 2nd operator
                // index 4 is the 3rd digit
                if (arrayList.get(3).contains("*") || arrayList.get(3).contains("/")) {
                    if (arrayList.get(3).contains("*")) {
                        calcValue = Float.parseFloat(arrayList.get(2)) * Float.parseFloat(arrayList.get(4));
                    }
                    if (arrayList.get(3).contains("/")) {
                        calcValue = Float.parseFloat(arrayList.get(2)) * Float.parseFloat(arrayList.get(4));
                    }
                    // calc value = 12, array = {2, +, 3, *, 4, -, 3}
                    arrayList.remove(2);    // {2, +, *, 4, -, 3}
                    arrayList.remove(2);    // {2, +, 4, -, 3}
                    arrayList.remove(2);    // {2, +, -, 3}
                    arrayList.add(2, Float.toString(calcValue)); // {2, +, 12, -, 3}
                    c = arrayList.size();  // size = 5
                }
                else {
                    // {2, +, 12, -, 3}
                    if (arrayList.get(1).contains("+")) {
                        calcValue = Float.parseFloat(arrayList.get(0)) + Float.parseFloat(arrayList.get(2));
                    }
                    if (arrayList.get(1).contains("-")) {
                        calcValue = Float.parseFloat(arrayList.get(0)) - Float.parseFloat(arrayList.get(2));
                    }
                    if (arrayList.get(1).contains("*")) {
                        calcValue = Float.parseFloat(arrayList.get(0)) * Float.parseFloat(arrayList.get(2));
                    }
                    if (arrayList.get(1).contains("/")) {
                        calcValue = Float.parseFloat(arrayList.get(0)) / Float.parseFloat(arrayList.get(2));
                    }

                    // calc value = 14
                    arrayList.remove(0);    // {+, 12, -, 3}
                    arrayList.remove(0);    // {12, -, 3}
                    arrayList.remove(0);    // {-, 3}
                    arrayList.add(0, Float.toString(calcValue));  // {14, -, 3}
                    c = arrayList.size();   // size = 3
                }
            }
            // size <= 3
            else {
                // index 0 will be the first digit
                // index 1 will be the 1st operator
                // index 2 is the 2nd digit
                if (arrayList.get(1).contains("+")) {   // sum first and second digit
                    calcValue = Float.parseFloat(arrayList.get(0)) + Float.parseFloat(arrayList.get(2));
                }
                if (arrayList.get(1).contains("-")) {   // subtract
                    calcValue = Float.parseFloat(arrayList.get(0)) - Float.parseFloat(arrayList.get(2));
                }
                if (arrayList.get(1).contains("*")) {   // multiply
                    calcValue = Float.parseFloat(arrayList.get(0)) * Float.parseFloat(arrayList.get(2));
                }
                if (arrayList.get(1).contains("/")) {   // divide
                    calcValue = Float.parseFloat(arrayList.get(0)) / Float.parseFloat(arrayList.get(2));
                }
                // calc value = 11
                arrayList.remove(0);    // {-, 3}
                arrayList.remove(0);    // {3}
                arrayList.remove(0);    // ()
                arrayList.add(0, Float.toString(calcValue));   // {9}
                c = arrayList.size();   //  size = 1
                // when size = 1, loop ends
            }
        }
        textView1.setText(Float.toString(calcValue));
    }

    // method that maps to the clear button
    public void clear (View v) {
        TextView textView1 = (TextView)findViewById(R.id.textView);
        TextView textView2 = (TextView)findViewById(R.id.textView2);

        string1 = "";
        string = "";
        textView1.setText("0");
        textView2.setText("");
        arrayList.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

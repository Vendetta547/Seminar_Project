package com.adafruit.bluefruit.le.connect.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.adafruit.bluefruit.le.connect.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class contact_activ extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // set title of action bar and enable back arrow
        setTitle("Contact Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // grab input from the UI fields
        final EditText your_name        = (EditText) findViewById(R.id.your_name);
        final EditText your_email       = (EditText) findViewById(R.id.your_email);
        final EditText your_subject     = (EditText) findViewById(R.id.your_subject);
        final EditText your_message     = (EditText) findViewById(R.id.your_message);


        // submit button
        Button email = (Button) findViewById(R.id.post_message);

        // things to do when the submit button is clicked
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // convert EditText objects to strings
                String name      = your_name.getText().toString();
                String email     = your_email.getText().toString();
                String subject   = your_subject.getText().toString();
                String message   = your_message.getText().toString();

                // ensure each field has user input
                /**********************************************************************************/
                if (name.matches("")){
                    your_name.setError("Name cannot be empty");
                    your_name.requestFocus();
                    return;
                }

                if (email.matches("")) {
                    your_email.setError("Email cannot be empty");
                } else if (!isValidEmail(email)) {
                    your_email.setError("Invalid Email");
                }

                if (subject.matches("")){
                    your_subject.setError("Subject cannot be empty");
                    your_subject.requestFocus();
                    return;
                }

                if (message.matches("")){
                    your_message.setError("Message cannot be empty");
                    your_message.requestFocus();
                    return;
                }
                /**********************************************************************************/

                // create intent to send email and fill it with necessary data
                Intent sendEmail = new Intent(Intent.ACTION_SEND)
                    .setType("plain/text")
                    .putExtra(Intent.EXTRA_EMAIL, new String[]{"dtm7c@uvawise.edu", "led7t@uvawise.edu", "mcs2hw@uvawise.edu"})
                    .putExtra(Intent.EXTRA_SUBJECT, subject)
                    .putExtra(Intent.EXTRA_TEXT, message);

                // open activity chooser
                startActivity(Intent.createChooser(sendEmail, "Send Email to Developers"));
            }
        }); // end email.setOnClickListener
    }




    /* compares user supplied email against regular expression to ensure it follows an email address format
     *
     * Resource referenced for regular expression: https://howtodoinjava.com/regex/java-regex-validate-email-address/
     *
     * Regular expressions tested against test cases here: https://www.freeformatter.com/java-regex-tester.html#ad-output
     *
     * Use cases from this source were included in our tests: https://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
     */
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        // check that regular expression is valid and then match against input
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }




    /* makes the back arrow in the action bar behave the same as the android hardware back button */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




} // end class contact_activ

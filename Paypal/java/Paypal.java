package jocadoci.soloordena;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Paypal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //Object need it from the Paypal activity
    TextView m_response;
    PayPalConfiguration m_configuration;

    // the id is the link to the paypal account, we have to create an app and get its id
    String m_paypalClientId = " ";
    Intent m_service;
    int m_paypalRequestCode = 999; // or any number you want

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paypal_layout);
        //Button from the payment
        pay = (Button) findViewById(R.id.Pay);
        pay.setEnabled(true);

        //TextView need it from Paypal responsegf
        m_response = (TextView) findViewById(R.id.response);

        m_configuration = new PayPalConfiguration()
                    .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // sandbox for test, production for real
                    .clientId(m_paypalClientId);

        m_service = new Intent(this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration); // configuration above
        startService(m_service); // paypal service, listening to calls to paypal app

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPalPayment payment = new PayPalPayment(new BigDecimal(total), "USD", "Test payment with Paypal",
                        PayPalPayment.PAYMENT_INTENT_SALE);

                Intent intent = new Intent(Paypal.this, PaymentActivity.class); // it's not paypalpayment, it's paymentactivity !
                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
                startActivityForResult(intent, m_paypalRequestCode);
                
                final_pedidos.setText(" ");
                final_pedidos.setEnabled(false);
                pay.setEnabled(false);
            }
        });

    }

    // Asking to the server for the request and data.
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == m_paypalRequestCode)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                // we have to confirm that the payment worked to avoid fraud
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if(confirmation != null)
                {
                    String state = confirmation.getProofOfPayment().getState();

                    if(state.equals("approved")) // if the payment worked, the state equals approved
                        m_response.setText("payment approved");
                    else
                        m_response.setText("error in the payment");
                }
                else
                    m_response.setText("confirmation is null");
            }
        }
    }
    //...
}
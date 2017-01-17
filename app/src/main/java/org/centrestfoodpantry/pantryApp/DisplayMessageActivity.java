package org.centrestfoodpantry.pantryApp;

import android.content.Context;
import android.content.Intent;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class DisplayMessageActivity extends AppCompatActivity {

    // Generate an HTML document on the fly:
    String htmlDocument = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        Bundle orderInfo = intent.getBundleExtra("ORDER_INFO");
        String shopperName = orderInfo.getString("SHOPPER_NAME");
        String familySize = orderInfo.getString("FAMILY_SIZE");
        HashMap foodItems = (HashMap) orderInfo.getSerializable("FOOD_ITEMS");

        String outputPrint = "Name: " + shopperName + "\n" + "Family Size: " + familySize + "\n";
        for (Object item : foodItems.keySet()) {
            int quantity = (int) foodItems.get(item);
            if (quantity != 0) {
                outputPrint += "\n" + item.toString();
                if (quantity > 1) {
                    outputPrint += " (" + quantity + ")";
                }
            }
        }

        TextView textView = new TextView(this);
        textView.setTextSize(28);
        textView.setText(outputPrint);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);

        htmlDocument += "<html><body><h1>" + shopperName + " (" + familySize + ")" + "</h1><font size=\"5\">";

        for (Object item : foodItems.keySet()){

            int quantity = (int) foodItems.get(item);
            if (quantity != 0) {
                htmlDocument += item;
                if (quantity > 1) {
                    htmlDocument += " (" + quantity + ")";
                }
                htmlDocument += "<br>";
            }
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy-hh-mm");
        String dateText = simpleDateFormat.format(new Date());

        htmlDocument += "<font size=\"3\">" + dateText + "</font>";
        htmlDocument += "</font></body></html>";

        doWebViewPrint();
    }


    /** Called when the user clicks the New Order button */
    public void newOrder(View view) {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the Print button */
    public void printScreen(View view) {
        doWebViewPrint();
    }

    private WebView mWebView;

    private void doWebViewPrint() {
        // Create a WebView object specifically for printing
        WebView webView = new WebView(this);
        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //Log.i(TAG, "page finished loading " + url);
                createWebPrintJob(view);
                mWebView = null;
            }
        });


        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null);

        // Keep a reference to WebView object until you pass the PrintDocumentAdapter
        // to the PrintManager
        mWebView = webView;
    }

    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

        // Create a print job with name and adapter instance
        String jobName = getString(R.string.app_name) + " Document";
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

        // Save the job object for later status checking
        //mPrintJobs.add(printJob);
    }
}


package com.example.andy.test2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.security.auth.x500.X500Principal;

public class MainActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSv = new Button(this);

        TextView tvSv = new TextView(this);
    }

    private static final X500Principal DEBUG_DN = new X500Principal("CN=followme,OU=followme,O=followme");
    private boolean isDebuggable(Context ctx)
    {
        boolean debuggable = false;

        try
        {
            PackageInfo pinfo = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), PackageManager.GET_SIGNATURES);
            Signature signatures[] = pinfo.signatures;

            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            for ( int i = 0; i < signatures.length;i++)
            {
                ByteArrayInputStream stream = new ByteArrayInputStream(signatures[i].toByteArray());
                X509Certificate cert = (X509Certificate) cf.generateCertificate(stream);
                debuggable = cert.getSubjectX500Principal().equals(DEBUG_DN);
                if (debuggable)
                    break;
            }
        }
        catch (PackageManager.NameNotFoundException e)
        {
            //debuggable variable will remain false
        }
        catch (CertificateException e)
        {
            //debuggable variable will remain false
        }
        return debuggable;
    }

    /**
     * Show hont message
     */
    public static void showHintDialog(Activity activity, String title, String message) {

        new AlertDialog.Builder(activity)
                .setTitle(title)
                .setCancelable(false)
                .setMessage(message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }
}

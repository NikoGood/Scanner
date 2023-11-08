package com.example.scanner;

import static android.webkit.URLUtil.isValidUrl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    private Button scanButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scanButton= findViewById(R.id.scan_button);
        resultTextView= findViewById(R.id.result_textview);

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Iniciar la actividad de escaneo de ZXing
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.setPrompt("Escanea un codigo de barras o QR");
                integrator.initiateScan();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode,data);

        // Procesar el resultado del escaneo
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            String scanContent = result.getContents();
            resultTextView.setText("Resultado del escaneo: " + scanContent);

            // Redirigir a la pagina web si se escanea una URL valida
            if (isValidUrl(scanContent)){
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanContent));
                startActivities(new Intent[]{browserIntent});
            }
        }
    }

    private boolean isValidUrl(String url){
        return url != null && (url.startsWith("http://") || url.startsWith("https://"));
    }
}
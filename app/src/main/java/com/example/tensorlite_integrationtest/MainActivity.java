package com.example.tensorlite_integrationtest;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//allows navigation from different views
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.Manifest;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.snackbar.Snackbar;

import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.tensorlite_integrationtest.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TFCamera";
    private static int REQUESTCODE_PERMISSION = 10;
    private static String[] REQUIRED_PERMISSION = new String[]{Manifest.permission.CAMERA};


    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        TensorFlowInterpretor interpretor = new TensorFlowInterpretor();
        interpretor.intitInterpretor();
        if(allPermissionGranted()){
            startCamera();
        }else{
            requestPermission();
        }
    }
    private void startCamera(){
    }

    private void closeCamera(){

    }
    private boolean allPermissionGranted(){
        for(String permission: REQUIRED_PERMISSION){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) return false;
        }
        return true;
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
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, REQUIRED_PERMISSION, REQUESTCODE_PERMISSION);
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUESTCODE_PERMISSION) {
            if (allPermissionGranted()) {
                startCamera();
            } else {
                Snackbar.make(binding.getRoot(), "Permissions not granted by the user.", Snackbar.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    @Override
    protected void onStop() {
        // Call the superclass method first.
        super.onStop();
        closeCamera();

    }
}
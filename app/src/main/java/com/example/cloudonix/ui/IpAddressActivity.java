package com.example.cloudonix.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.example.cloudonix.R;
import com.example.cloudonix.databinding.ActivityMainBinding;

public class IpAddressActivity extends AppCompatActivity {

    // Used to load the 'cloudonix' library on application startup.
    static {
        System.loadLibrary("cloudonix");
    }

    private ActivityMainBinding binding;

    private IpAddressViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViewModel();
        binding.btnGetIpAddress.setOnClickListener(v -> {
            String ip = getNativeIpAddress();
            binding.tvIpAddress.setText("");
            binding.ivIpResult.setVisibility(View.INVISIBLE);
            viewModel.getIpAddressResults(ip);


        });
    }


    private void showLoadingView(){
        binding.tvLoading.setVisibility(View.VISIBLE);

        binding.progressBar.setVisibility(View.VISIBLE);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


    private void clearLoadingView(){
        binding.tvLoading.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

private void initViewModel(){

    viewModel = new ViewModelProvider(
            this,
            ViewModelProvider.Factory.from(IpAddressViewModel.initializer)
    ).get(IpAddressViewModel.class);

    observeViewModel();
}
    private void observeViewModel(){

        viewModel.getIpAddressResultLiveData().observe(this, ipAddress -> {

            boolean isNat = ipAddress.isNat();
            binding.ivIpResult.setVisibility(View.VISIBLE);
            binding.tvIpAddress.setText(getString(R.string.ip_address, ipAddress.getAddress()));

            if(isNat){
                binding.ivIpResult.setImageResource(R.drawable.baseline_check_24);
            }else{
                binding.ivIpResult.setImageResource(R.drawable.baseline_error_24);

            }

        });

        viewModel.getIsLoadingLiveData().observe(this,showLoading -> {

            if(showLoading){
                showLoadingView();
            }else{
                clearLoadingView();
            }

        });

        viewModel.getShowErrorMessageLiveData().observe(this, s -> {

        });


    }
    /**
     * A native method that is implemented by the 'cloudonix' native library,
     * which is packaged with this application.
     */
    public native String getNativeIpAddress();
}
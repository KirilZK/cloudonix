package com.example.cloudonix.ui;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;
import android.os.CountDownTimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.example.cloudonix.App;
import com.example.cloudonix.data.DataManager;
import com.example.cloudonix.data.domain.IpAddressRequest;
import com.example.cloudonix.data.domain.IpAddressResponse;
import com.example.cloudonix.data.repository.ValidateIpAddressRepository;

public class IpAddressViewModel extends ViewModel {

    private IpAddressCallbackImpl callback = new IpAddressCallbackImpl();
    private final ValidateIpAddressRepository repository;

    private CountDownTimer timer;



    IpAddressViewModel(ValidateIpAddressRepository repository) {
        this.repository = repository;
    }
    private final MutableLiveData<IpAddressResponse> ipAddressResultLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> showErrorMessageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();




    public void getIpAddressResults(String ipAddress){


        repository.validateIpAddress(callback,ipAddress);
        startCountDownTimer();
    }

    private class IpAddressCallbackImpl implements ValidateIpAddressRepository.IpAddressCallback{


        @Override
        public void onIpAddressResponse(IpAddressResponse ipAddress) {
            ipAddressResultLiveData.postValue(ipAddress);
            cancelTimer();

        }

        @Override
        public void onError(String msg) {
          cancelTimer();
            showErrorMessageLiveData.postValue(msg);

        }
    }

    private void cancelTimer(){
        timer.cancel();
        if(isLoadingLiveData.getValue() != null && isLoadingLiveData.getValue()){
            isLoadingLiveData.postValue(false);
        }

    }


    /**
     * LiveData
     **/
    public LiveData<IpAddressResponse> getIpAddressResultLiveData() {
        return ipAddressResultLiveData;
    }

    public LiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }

    public LiveData<String> getShowErrorMessageLiveData() {
        return showErrorMessageLiveData;
    }




    private void startCountDownTimer(){

       timer =  new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                isLoadingLiveData.postValue(true);

            }
        }.start();
    }
    static final ViewModelInitializer<IpAddressViewModel> initializer = new ViewModelInitializer<>(
            IpAddressViewModel.class,
            creationExtras -> {
                App app = (App) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                return new IpAddressViewModel(DataManager.getInstance().getRepository());
            }
    );

}

package com.example.dispatch;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class sharedviewmodel extends ViewModel {
    private MutableLiveData<String> productId = new MutableLiveData<>();
    private MutableLiveData<String> truckId = new MutableLiveData<>();

    public void setProductId(String id) {
        productId.setValue(id);
    }

    public LiveData<String> getProductId() {
        return productId;
    }

    public void setTruckId(String id) {
        truckId.setValue(id);
    }

    public LiveData<String> getTruckId() {
        return truckId;
    }
}

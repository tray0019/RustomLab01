package algonquin.cst2335.rustomlab01.ui.data;

import android.widget.RadioButton;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel{

    public MutableLiveData<String> editString = new MutableLiveData<>();

    /*
    public MutableLiveData<Boolean> radioButton = new MutableLiveData<>();
    public MutableLiveData<Boolean> Switch = new MutableLiveData<>();
    public MutableLiveData<Boolean> checkBox = new MutableLiveData<>();
    */
    public MutableLiveData<Boolean> isSelected = new MutableLiveData<>();

}

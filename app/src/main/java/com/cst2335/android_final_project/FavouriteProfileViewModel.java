package com.cst2335.android_final_project;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

/**
 ViewModel class for managing favourite profile entries
 */
public class FavouriteProfileViewModel extends ViewModel {

    /**
     * MutableLiveData object containing an ArrayList of FavouriteProfileData objects
     */
    public MutableLiveData<ArrayList<FavouriteProfileData>> entries = new MutableLiveData<>();

    /**
     * Constructor for FavouriteProfileViewModel class
     * Initializes the MutableLiveData object to an empty ArrayList
     */
    public FavouriteProfileViewModel() {
        entries.setValue(new ArrayList<FavouriteProfileData>());
    }
}

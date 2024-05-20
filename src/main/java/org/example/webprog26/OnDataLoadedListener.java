package org.example.webprog26;

public interface OnDataLoadedListener {

    void onDataLoaded(final String data);

    void onFailure(final String errorMessage);
}

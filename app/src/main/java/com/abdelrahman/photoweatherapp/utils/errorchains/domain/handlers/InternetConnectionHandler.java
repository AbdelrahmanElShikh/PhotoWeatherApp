package com.abdelrahman.photoweatherapp.utils.errorchains.domain.handlers;

import androidx.fragment.app.Fragment;


import com.abdelrahman.photoweatherapp.utils.errorchains.domain.chains.DomainErrorChainLink;
import com.abdelrahman.photoweatherapp.utils.retrysnackbar.RetryConnectionErrorSnackBar;
import com.abdelrahman.photoweatherapp.utils.retrysnackbar.RetryHandler;

import java.net.SocketException;
import java.net.UnknownHostException;

public class InternetConnectionHandler extends DomainErrorChainLink {
    public static boolean isConnectionThrowable(Throwable throwable) {
        return throwable instanceof UnknownHostException || throwable instanceof SocketException;
    }

    @Override
    protected boolean handleError(Throwable throwable, Fragment fragment, RetryHandler retryHandler) {
        if (isConnectionThrowable(throwable)) {
            RetryConnectionErrorSnackBar.show(retryHandler, fragment);
            return true;
        }
        return false;
    }
}

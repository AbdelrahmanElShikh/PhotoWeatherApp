package com.abdelrahman.photoweatherapp.utils.errorchains.domain.handlers;

import androidx.fragment.app.Fragment;


import com.abdelrahman.photoweatherapp.utils.errorchains.domain.chains.DomainErrorChainLink;
import com.abdelrahman.photoweatherapp.utils.retrysnackbar.RetryHandler;
import com.abdelrahman.photoweatherapp.utils.retrysnackbar.RetryTimeoutErrorSnackBar;

import java.net.SocketTimeoutException;

public class TimeoutDomainErrorHandler extends DomainErrorChainLink {
    @Override
    protected boolean handleError(Throwable throwable, Fragment fragment, RetryHandler retryHandler) {
        if (throwable instanceof SocketTimeoutException) {
            RetryTimeoutErrorSnackBar.show(retryHandler, fragment);
            return true;
        }
        return false;
    }
}

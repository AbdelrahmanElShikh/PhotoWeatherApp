package com.abdelrahman.photoweatherapp.utils.errorchains.domain.chains;

import androidx.fragment.app.Fragment;

import com.abdelrahman.photoweatherapp.utils.errorchains.ChainLink;
import com.abdelrahman.photoweatherapp.utils.retrysnackbar.RetryHandler;


public abstract class DomainErrorChainLink extends ChainLink<DomainErrorChainLink> {

    void Proceed(Throwable throwable, Fragment fragment, RetryHandler retryHandler) {
        if (!handleError(throwable, fragment, retryHandler) && nextLink != null)
            nextLink.Proceed(throwable, fragment, retryHandler);
    }

    protected abstract boolean handleError(Throwable throwable, Fragment fragment, RetryHandler retryHandler);

}

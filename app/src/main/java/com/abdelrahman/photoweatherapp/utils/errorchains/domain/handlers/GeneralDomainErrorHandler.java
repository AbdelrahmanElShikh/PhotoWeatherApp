package com.abdelrahman.photoweatherapp.utils.errorchains.domain.handlers;

import androidx.fragment.app.Fragment;

import com.abdelrahman.photoweatherapp.utils.errorchains.domain.chains.DomainErrorChainLink;
import com.abdelrahman.photoweatherapp.utils.retrysnackbar.RetryGeneralErrorSnackBar;
import com.abdelrahman.photoweatherapp.utils.retrysnackbar.RetryHandler;

public class GeneralDomainErrorHandler extends DomainErrorChainLink {
    @Override
    protected boolean handleError(Throwable throwable, Fragment fragment,
                                  RetryHandler retryHandler) {
        RetryGeneralErrorSnackBar.show(retryHandler, fragment);
        return true;
    }
}

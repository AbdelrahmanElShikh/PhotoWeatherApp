package com.abdelrahman.photoweatherapp.utils.errorchains.domain.chains;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.abdelrahman.photoweatherapp.utils.retrysnackbar.RetryHandler;


public class DomainErrorChain {

    @NonNull
    private final DomainErrorChainLink chainHead;

    public DomainErrorChain(@NonNull DomainErrorChainLink chainHead) {
        this.chainHead = chainHead;
    }

    public void execute(Throwable throwable, Fragment fragment, RetryHandler retryHandler) {
        chainHead.Proceed(throwable, fragment, retryHandler);
    }

    public static Builder Builder() {
        return new Builder();
    }
}

package com.abdelrahman.photoweatherapp.utils.errorchains.domain.chains;


import com.abdelrahman.photoweatherapp.utils.errorchains.domain.handlers.GeneralDomainErrorHandler;
import com.abdelrahman.photoweatherapp.utils.errorchains.domain.handlers.InternetConnectionHandler;
import com.abdelrahman.photoweatherapp.utils.errorchains.domain.handlers.TimeoutDomainErrorHandler;

import java.util.ArrayList;
import java.util.List;

public class Builder {

    private final List<DomainErrorChainLink> DomainErrorChainLinks = new ArrayList<>();

    public Builder addChain(DomainErrorChainLink DomainErrorChainLink) {
        DomainErrorChainLinks.add(DomainErrorChainLink);
        return this;
    }

    private void setupChainsSequence() {
        if (DomainErrorChainLinks.size() > 0) {
            for (int i = 0; i < DomainErrorChainLinks.size() - 1; i++) {
                if (i < DomainErrorChainLinks.size() - 1) {
                    DomainErrorChainLink DomainErrorChainLink = DomainErrorChainLinks.get(i);
                    DomainErrorChainLink.setNextChain(DomainErrorChainLinks.get(i + 1));
                }
            }
        }
    }

    public DomainErrorChain build() {
        setupChainsSequence();
        return new DomainErrorChain(DomainErrorChainLinks.get(0));
    }

    public DomainErrorChain buildWithDefaultChainLinks() {
        DomainErrorChainLinks.add(new InternetConnectionHandler());
        DomainErrorChainLinks.add(new TimeoutDomainErrorHandler());
        DomainErrorChainLinks.add(new GeneralDomainErrorHandler());
        build();
        return new DomainErrorChain(DomainErrorChainLinks.get(0));
    }
}

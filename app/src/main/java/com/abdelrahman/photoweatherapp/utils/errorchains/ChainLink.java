package com.abdelrahman.photoweatherapp.utils.errorchains;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class ChainLink<T extends ChainLink> {
    @Nullable
    protected T nextLink;

    public void setNextChain(@NonNull T chainLink) {
        this.nextLink = chainLink;
    }
}

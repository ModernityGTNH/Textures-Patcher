package com.texturespatcher.pack;

// parsing result of {@code textures_patcher} in pack.mcmeta

public final class PackMeta {

    public final boolean enableTexturesPatcherBlocks;
    public final boolean enableSeparateForestryCircuitBoardTextures;
    public final boolean enableSeparateForestryElectronTubeTextures;

    public final boolean enableSeparateForestryHoneyDropTextures;

    public PackMeta(boolean enableTexturesPatcherBlocks, boolean enableSeparateForestryCircuitBoardTextures,
        boolean enableSeparateForestryElectronTubeTextures, boolean enableSeparateForestryHoneyDropTextures) {
        this.enableTexturesPatcherBlocks = enableTexturesPatcherBlocks;
        this.enableSeparateForestryCircuitBoardTextures = enableSeparateForestryCircuitBoardTextures;
        this.enableSeparateForestryElectronTubeTextures = enableSeparateForestryElectronTubeTextures;
        this.enableSeparateForestryHoneyDropTextures = enableSeparateForestryHoneyDropTextures;
    }

    // no flag is turned on
    public boolean isEmpty() {
        return !this.enableTexturesPatcherBlocks && !this.enableSeparateForestryCircuitBoardTextures
            && !this.enableSeparateForestryElectronTubeTextures
            && !this.enableSeparateForestryHoneyDropTextures;
    }

    @Override
    public String toString() {
        return "PackMeta{enableTexturesPatcherBlocks=" + this.enableTexturesPatcherBlocks
            + ", enableSeparateForestryCircuitBoardTextures=" + this.enableSeparateForestryCircuitBoardTextures
            + ", enableSeparateForestryElectronTubeTextures=" + this.enableSeparateForestryElectronTubeTextures
            + ", enableSeparateForestryHoneyDropTextures=" + this.enableSeparateForestryHoneyDropTextures
            + '}';
    }
}

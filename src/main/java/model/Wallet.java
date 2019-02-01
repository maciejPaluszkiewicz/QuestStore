package model;

import java.util.List;

public class Wallet {
    private int coolCoinsAmount;
    private  List<Artifact> artifactList;

    public Wallet(int coolCoinsAmount, List<Artifact> artifactList) {
        this.coolCoinsAmount = coolCoinsAmount;
        this.artifactList = artifactList;
    }

    public int getCoolCoinsAmount() {
        return coolCoinsAmount;
    }

    public List<Artifact> getArtifactList() {
        return artifactList;
    }
}

package model;

public class Wallet {
    private int coolCoinsAmount;
    private String artifactName;
    private int quantity;

    public Wallet(int coolCoinsAmount, String artifactName, int quantity) {
        this.coolCoinsAmount = coolCoinsAmount;
        this.artifactName = artifactName;
        this.quantity = quantity;
    }

    public int getCoolCoinsAmount() {
        return coolCoinsAmount;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public int getQuantity() {
        return quantity;
    }
}

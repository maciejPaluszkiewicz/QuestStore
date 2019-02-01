package model;

public class Artifact {
    private String artifactName;
    private int quantity;

    public Artifact(String artifactName, int quantity) {
        this.artifactName = artifactName;
        this.quantity = quantity;
    }

    public String getArtifactName() {
        return artifactName;
    }

    public int getQuantity() {
        return quantity;
    }
}

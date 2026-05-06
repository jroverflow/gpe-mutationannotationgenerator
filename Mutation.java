package com.biomatters.exampleSequenceAnnotationGeneratorPlugin;

public class Mutation {
    // data fields
    private int position;
    private String refBase;
    private String altBase;
    private String type;
    private String refAA;
    private String altAA;
    private String effect;
    private String description;

    // no-arg constructor
    public Mutation() {
        this.position = 0;
        this.refBase = null;
        this.altBase = null;
        this.type = "";
        this.refAA = "";
        this.altAA = "";
        this.effect = "";
        this.description = "";

    }

    // parameterized constructor
    public Mutation(int position, String refBase, String altBase) {
        this.position = position;
        this.refBase = refBase;
        this.altBase = altBase;
        this.type = "SNP";
        this.refAA = "";
        this.altAA = "";
        this.effect = "";
        this.description = refBase + ">" + altBase;
    }

    // getters and setters
    public int getPosition() { return position; }
    public void setPosition(int position) { this.position = position; }

    public String getRefBase() { return refBase; }
    public void setRefBase(String refBase) { this.refBase = refBase; }

    public String getAltBase() { return altBase; }
    public void setAltBase(String altBase) { this.altBase = altBase; }

    public String getType() { return type; }

    public String getRefAA() { return refAA; }
    public void setRefAA(String refAA) { this.refAA = refAA; }

    public String getAltAA() { return altAA; }
    public void setAltAA(String altAA) { this.altAA = altAA; }

    public String getEffect() { return effect; }
    public void setEffect(String effect) { this.effect = effect; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // mandatory toString() method overriding
    @Override
    public String toString() {
        return String.format("%s at %d: %s → %s, AA: %s → %s (%s) - %s",
                type, position, refBase, altBase, refAA, altAA, effect, description);
    }


}
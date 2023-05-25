package dev.kavu.gameapi;

public class Rule {

    private final String name;
    private boolean status;

    public Rule(String name) {
        this.name = name;
        this.status = false;
    }

    public Rule(String name, boolean status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public boolean check() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

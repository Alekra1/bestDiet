package com.example.bestdiet;

public class Variables {
    private String variableName;
    private String variableValue;

    // Constructor
    public Variables() {
    }

    // Constructor with parameters
    public Variables(String variableName, String variableValue) {
        this.variableName = variableName;
        this.variableValue = variableValue;
    }

    // Getter for variableName
    public String getVariableName() {
        return variableName;
    }

    // Setter for variableName
    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    // Getter for variableValue
    public String getVariableValue() {
        return variableValue;
    }

    // Setter for variableValue
    public void setVariableValue(String variableValue) {
        this.variableValue = variableValue;
    }
}


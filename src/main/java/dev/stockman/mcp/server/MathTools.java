package dev.stockman.mcp.server;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class MathTools {

    @Tool(description = "Adds two numbers")
    public static int add(int a, int b) {
        return a + b;
    }

    @Tool(description = "Multiplies two numbers")
    public static int multiply(int a, int b) {
        return a * b;
    }

}

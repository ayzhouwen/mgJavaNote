package com.syntax.dynamic.loadTrack;

public class Greeter {
private static Message s_message = new Message("Hello, World!");
    
    public void greet() {
        s_message.print(System.out);
    }
}

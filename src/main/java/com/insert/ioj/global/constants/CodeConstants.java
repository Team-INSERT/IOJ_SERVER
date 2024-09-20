package com.insert.ioj.global.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CodeConstants {
    public static final String C = """
        #include <stdio.h>
                
        int main() {
            printf("Hello World");
            return 0;
        }
        """;
    public static final String CPP = """
        #include <iostream>
                
        int main() {
            std::cout<<"Hello World";
            return 0;
        }
        """;
    public static final String JAVA = """
        import java.util.*;
        
        public class main {
            public static void main(String[] args) {
                System.out.println("Hello World");
            }
        }
        """;
    public static final String PYTHON = """
        print("Hello World")
        """;
}

package com.dejavu.utopia.utils;

import org.jetbrains.annotations.TestOnly;

import java.util.Stack;
import java.util.regex.Pattern;

public class ExpressionValidator {
    public static boolean isValidMathExpression(String expression) {
        // Check for合规的小数点使用，不允许连续小数点或小数点在首位或末尾
        Pattern decimalPattern = Pattern.compile("(?<![\\d.])(\\d+\\.\\d{1,2})(?!\\.)|\\d+");
        String[] parts = expression.split("[\\+\\-\\*/()]");
        for (String part : parts) {
            if (!decimalPattern.matcher(part).matches() && !part.trim().isEmpty()) {
                return false; // 小数点使用不合规
            }
        }
        // Stack to keep track of opening brackets
        Stack<Character> stack = new Stack<>();
        char[] charArray = expression.toCharArray();

        // Iterate through the expression
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];

            switch (ch) {
                case '(':
                    stack.push(ch);
                    break;
                case ')':
                    if (stack.isEmpty() || stack.pop() != '(') {
                        return false; // Unmatched closing bracket
                    }
                    break;
                // Additional checks for basic compliance (can be extended)
                case '*':
                case '/':
                    // Check for consecutive operators, but ignore if it's at the start or end of the expression
                    if (expression.startsWith(ch + "")) {
                        return false; // Consecutive operator not at start or end
                    }
                case '+':
                case '-':
                    if (i == 0) {
                        continue;
                    } else if(i==charArray.length-1){
                        return false;
                    } else {
                        char prev = charArray[i - 1];
                        if (
                                (Character.isDigit(prev) ||
                                        prev == ')' ||
                                        prev == '(' ||
                                        prev == '.')
                        ) {
                            continue;
                        } else {
                            return false; // Consecutive operator not at start or end
                        }
                    }
                case '.':
                    // Simple check for decimal point usage, more checks needed for full compliance
                    if (i == 0) {
                        return false; // Decimal point misuse
                    } else if (Character.isDigit(charArray[i - 1]) == false) {
                        return false;
                    } else if (i == charArray.length - 1) {
                        return false;
                    } else if (Character.isDigit(charArray[i + 1]) == false) {
                        return false;
                    }
                    break;
                default:
                    // Ignore digits and whitespace for this basic check
                    if (!Character.isDigit(ch) && !Character.isWhitespace(ch)) {
                        System.out.println("Unexpected character: " + ch);
                        return false; // Unexpected character
                    }
            }
        }

        // If stack is not empty, there are unmatched opening brackets
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        String expr1 = "((3.14+2*(4.5-1.2))/3)-0.000";
        String expr2 = "+3.14++21+0";
        String expr3 = "5+(((3-1))+7)*8-1";

        System.out.println(isValidMathExpression(expr1)); // Expected: true
        System.out.println(isValidMathExpression(expr2)); // Expected: false
        System.out.println(isValidMathExpression(expr3)); // Expected: false
    }
}
package com.dejavu.utopia.utils;

import java.text.DecimalFormat;
import java.util.*;
public class CalculatorUtil {

    // 计算表达式
    public static String calculate(String expression) {
        // 使用StringBuilder去除空格并构建处理后的字符串
        StringBuilder processedExp = new StringBuilder();
        for (char c : expression.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                processedExp.append(c);
            }
        }
        String exp = processedExp.toString();

        // 初始化栈
        Stack<Double> operandStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        // 解析并计算
        int index = 0;
        while (index < exp.length()) {
            char currentChar = exp.charAt(index);

            if (Character.isDigit(currentChar) || currentChar == '.') {
                // 解析数字（可能包含小数点）
                StringBuilder numberBuilder = new StringBuilder();
                while (index < exp.length() && (Character.isDigit(exp.charAt(index)) || exp.charAt(index) == '.')) {
                    numberBuilder.append(exp.charAt(index++));
                }
                operandStack.push(Double.parseDouble(numberBuilder.toString()));
            } else if (currentChar == '+' || currentChar == '-' || currentChar == '*' || currentChar == '/') {
                // 处理运算符
                while (!operatorStack.isEmpty() && precedence(operatorStack.peek()) >= precedence(currentChar)) {
                    char op = operatorStack.pop();
                    double b = operandStack.pop();
                    double a = operandStack.pop();
                    operandStack.push(applyOp(op, a, b));
                }
                operatorStack.push(currentChar);
                index++;
            } else if (currentChar == '(') {
                operatorStack.push(currentChar);
                index++;
            } else if (currentChar == ')') {
                // 遇到右括号，处理括号内的运算
                while (operatorStack.peek() != '(') {
                    char op = operatorStack.pop();
                    double b = operandStack.pop();
                    double a = operandStack.pop();
                    operandStack.push(applyOp(op, a, b));
                }
                operatorStack.pop(); // 弹出左括号
                index++;
            } else {
                // 如果遇到无法识别的字符，可以选择抛出异常或忽略
                index++;
            }
        }

        // 处理剩余的运算符
        while (!operatorStack.isEmpty()) {
            char op = operatorStack.pop();
            double b = operandStack.pop();
            double a = operandStack.pop();
            operandStack.push(applyOp(op, a, b));
        }
        DecimalFormat df = new DecimalFormat("0.######"); // 保留六位小数，不足六位时不补零
        return expression+"="+df.format(operandStack.pop());
    }

    // 其他辅助方法如 precedence 和 applyOp 保持不变
    // 计算优先级
    private static int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    // 应用运算符
    private static double applyOp(char op, double a, double b) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) throw new UnsupportedOperationException("除数不能为0");
                return a / b;
            default:
                throw new IllegalArgumentException("Unsupported operation");
        }
    }
}

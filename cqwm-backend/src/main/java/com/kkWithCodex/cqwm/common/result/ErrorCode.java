package com.kkWithCodex.cqwm.common.result;

/**
 * Canonical API codes for the platform.
 * 枚举 = 一组固定、有限、可列举的常量集合。
 * “带属性 + 构造方法”的枚举
 * 因为 enum 本质上是一种特殊的 class。
 */
public enum ErrorCode {
//    public final class ErrorCode extends Enum<ErrorCode> {
//    为什么 enum 本身不能写成 static？
//    static 只能用于 内部类
//    enum 是一种特殊的 顶级类（类似 class），不能声明为 static

    SUCCESS("0", "OK"), //每个枚举常量在创建时调用 enum 的构造方法。 等价于SUCCESS = new ErrorCode("0", "OK");
//    public static final ErrorCode SUCCESS = new ErrorCode("0","OK");  static修饰是属于类的，直接类名.变量
//    所有枚举常量（如 SUCCESS）本质上都是 public static final 的常量对象。
//    所以可以全局访问，它们跟类是不是 static 完全无关。
//    因为 Java 语言规范（JLS）规定：
//    枚举类中的每一个枚举常量，都被自动编译为 public static final。
    VALIDATION_ERROR("A0400", "参数校验失败"),
    AUTH_FAILED("A0301", "账户或密码错误"),
    ACCOUNT_DISABLED("A0302", "账户已停用"),
    NOT_FOUND("A0404", "资源不存在"),
    INTERNAL_ERROR("B0500", "服务器内部异常");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
//    private ErrorCode(String code, String message) { ... }
//    enum 构造方法一定是 private（默认也是 private）
//    外部不能 new，只能用定义好的常量。

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

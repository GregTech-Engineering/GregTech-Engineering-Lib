package com.gtelib.api.annotation.dynamic;

public interface DynamicInitialValueTypes {

    String KEY_MULTIPLY = "multiplier";
    TypeInfo MULTIPLY = new TypeInfo("multiplier", "倍率", "Multiplier");
    String KEY_AMOUNT = "amount";
    TypeInfo AMOUNT = new TypeInfo("amount", "数量", "Amount");
    String KEY_PROBABILITY = "probability";
    TypeInfo PROBABILITY = new TypeInfo("probability", "概率", "Probability");
    String KEY_AMPERAGE_OUT = "amperage_out";
    TypeInfo AMPERAGE_OUT = new TypeInfo("amperage_out", "输出电流", "Amperage Out");
    String KEY_MAX_PARALLEL = "max_parallel";
    TypeInfo MAX_PARALLEL = new TypeInfo("max_parallel", "并行数", "Max Parallel");
    String KEY_CAPACITY = "capacity";
    TypeInfo CAPACITY = new TypeInfo("capacity", "容量", "Capacity");

    record TypeInfo(String key, String cn, String en) {}
}

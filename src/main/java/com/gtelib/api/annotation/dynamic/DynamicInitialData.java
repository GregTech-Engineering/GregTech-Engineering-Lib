package com.gtelib.api.annotation.dynamic;

import com.gtelib.GTECore;
import com.gtelib.api.annotation.DataGeneratorScanned;
import com.gtelib.api.lang.CNEN;
import com.gtelib.api.registries.ScanningClass;
import com.gtelib.utils.collection.O2OOpenCacheHashMap;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

@DataGeneratorScanned
public class DynamicInitialData {

    public static final Map<String, CNEN> LANG = GTECore.isDataGen() ? new O2OOpenCacheHashMap<>() : null;
    public static final String PREFIX_DYNAMIC_VALUE = "gtecore.difficulty_config";
    private static final String PREFIX_DYNAMIC_VALUE_WITH_END = "gtecore.difficulty_config.";
    private static final String PREFIX_NAME = "gtecore.difficulty_config.name.";
    private static final String PREFIX_COMMENT = "gtecore.difficulty_config.comment.";
    public static final DynamicInitialData DEFAULT = new DynamicInitialData();
    private final List<Value> list = new ObjectArrayList<>();

    public void update(Object var1) {
        for (Value var3 : this.list) {
            try {
                var3.field.set(var1, var3.value);
            } catch (IllegalAccessException var5) {
                GTECore.LibLOGGER.error("Failed to set difficulty config value for field {}", var3.field.getName(), var5);
            }
        }
    }

    public void add(Object value) {
        this.list.add((Value) value);
    }

    public Object add(Field field) {
        field.setAccessible(true);
        DynamicInitialValue initialValue = field.getAnnotation(DynamicInitialValue.class);
        assert initialValue != null;
        if (ScanningClass.LANG != null) {
            ScanningClass.LANG.put(PREFIX_NAME + initialValue.key(), new CNEN(initialValue.cn(), initialValue.en()));
        }
        Object commentKeys = List.of();
        if (!initialValue.enComment().isEmpty() && !initialValue.cnComment().isEmpty()) {
            commentKeys = new ObjectArrayList<>();
            String[] enComments = initialValue.enComment().lines().toArray(String[]::new);
            String[] cnComments = initialValue.cnComment().lines().toArray(String[]::new);
            if (enComments.length != cnComments.length) {
                throw new IllegalArgumentException("enComment line number is not equal cnComment");
            }
            for (int i = 0; i < enComments.length; ++i) {
                String key = initialValue.key();
                String commentKey = PREFIX_COMMENT + key + "." + i;
                if (ScanningClass.LANG != null) {
                    ScanningClass.LANG.put(commentKey, new CNEN(cnComments[i], enComments[i]));
                }
                ((List) commentKeys).add(commentKey);
            }
        }
        Value newValue;
        Class<?> fieldType = field.getType();
        newValue = new Value(field, fieldType, initialValue.key(), (List) commentKeys, initialValue.en().contains("%s"));
        this.list.add(newValue);
        if (Modifier.isStatic(field.getModifiers())) {
            try {
                field.set((Object) null, newValue.value);
                return null;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            return newValue;
        }
    }

    public record Value(Field field, Object value, String key, List<String> commentTranKey, boolean hasFormatInName) {}
}

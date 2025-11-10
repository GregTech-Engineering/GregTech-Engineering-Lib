package com.gtelib.api.registries;

import com.gtelib.GTECore;
import com.gtelib.api.annotation.DataGeneratorScanned;
import com.gtelib.api.annotation.Scanned;
import com.gtelib.api.annotation.dynamic.DynamicInitialData;
import com.gtelib.api.annotation.dynamic.DynamicInitialValue;
import com.gtelib.api.annotation.language.RegisterEnumLang;
import com.gtelib.api.annotation.language.RegisterLanguage;
import com.gtelib.api.lang.CNEN;
import com.gtelib.utils.collection.O2OOpenCacheHashMap;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;

import com.google.common.collect.ImmutableMap;
import org.objectweb.asm.Type;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

public class ScanningClass {

    public static final Map<String, CNEN> LANG = GTECore.isDataGen() ? new O2OOpenCacheHashMap<>() : null;
    public static ImmutableMap<Class<?>, DynamicInitialData> VALUES;
    public static final ImmutableMap<Class<?>, DynamicInitialData> OBJECT_VALUES;

    public static void init() {}

    static {
        long startTime = System.currentTimeMillis();
        ImmutableMap.Builder<Class<?>, DynamicInitialData> classToDynamicInitialDataBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<Class<?>, DynamicInitialData> classToObjectDynamicInitialDataBuilder = ImmutableMap.builder();
        Type scannedType = Type.getType(Scanned.class);
        Type dataGeneratorScannedType = LANG == null ? null : Type.getType(DataGeneratorScanned.class);
        for (ModFileScanData scanData : ModList.get().getAllScanData()) {
            for (ModFileScanData.AnnotationData annotationData : scanData.getAnnotations()) {
                Type annotationType = annotationData.annotationType();
                if (Objects.equals(annotationType, scannedType) || dataGeneratorScannedType != null && Objects.equals(annotationType, dataGeneratorScannedType)) {
                    try {
                        Class<?> annotatedClass = Class.forName(annotationData.memberName());
                        DynamicInitialData dynamicInitialData = null;
                        DynamicInitialData objectDynamicInitialData = null;
                        for (Field field : annotatedClass.getDeclaredFields()) {
                            if (field.isAnnotationPresent(DynamicInitialValue.class)) {
                                if (dynamicInitialData == null) {
                                    dynamicInitialData = new DynamicInitialData();
                                }
                                Object fieldValue = dynamicInitialData.add(field);
                                if (fieldValue != null) {
                                    if (objectDynamicInitialData == null) {
                                        objectDynamicInitialData = new DynamicInitialData();
                                    }
                                    objectDynamicInitialData.add(fieldValue);
                                }
                            } else if (LANG != null && field.isAnnotationPresent(RegisterLanguage.class)) {
                                RegisterLanguage registerLanguageAnnotation = field.getAnnotation(RegisterLanguage.class);
                                try {
                                    assert registerLanguageAnnotation != null;
                                    String languageKey = registerLanguageAnnotation.key();
                                    if (languageKey.isEmpty()) {
                                        String namePrefix = registerLanguageAnnotation.namePrefix();
                                        if (!namePrefix.isEmpty()) {
                                            languageKey = namePrefix + "." + field.getName();
                                        } else {
                                            field.setAccessible(true);
                                            languageKey = (String) field.get(null);
                                            String valuePrefix = registerLanguageAnnotation.valuePrefix();
                                            if (!valuePrefix.isEmpty()) {
                                                languageKey = valuePrefix + "." + languageKey;
                                            }
                                        }
                                    }
                                    LANG.put(languageKey, new CNEN(registerLanguageAnnotation.cn(), registerLanguageAnnotation.en()));
                                } catch (IllegalAccessException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        if (dynamicInitialData != null) {
                            classToDynamicInitialDataBuilder.put(annotatedClass, dynamicInitialData);
                            if (objectDynamicInitialData != null) {
                                classToObjectDynamicInitialDataBuilder.put(annotatedClass, objectDynamicInitialData);
                            }
                        }
                        if (LANG != null && annotatedClass.isEnum()) {
                            RegisterEnumLang registerEnumLangAnnotation = annotatedClass.getAnnotation(RegisterEnumLang.class);
                            if (registerEnumLangAnnotation != null) {
                                O2OOpenCacheHashMap enumToChineseNameMap = new O2OOpenCacheHashMap();
                                O2OOpenCacheHashMap enumToEnglishNameMap = new O2OOpenCacheHashMap();
                                String keyPrefix = registerEnumLangAnnotation.keyPrefix();
                                Object[] enumConstants = annotatedClass.getEnumConstants();
                                for (Object enumConstant : enumConstants) {
                                    String enumName = ((Enum<?>) enumConstant).name();
                                    for (Field field : annotatedClass.getDeclaredFields()) {
                                        if (field.isAnnotationPresent(RegisterEnumLang.EnValue.class)) {
                                            RegisterEnumLang.EnValue enValueAnnotation = field.getAnnotation(RegisterEnumLang.EnValue.class);
                                            field.setAccessible(true);
                                            if (enValueAnnotation != null) {
                                                try {
                                                    String fullKey = keyPrefix + "." + enValueAnnotation.value() + "." + enumName;
                                                    String englishName = (String) field.get(enumConstant);
                                                    if (enumToChineseNameMap.containsKey(fullKey)) {
                                                        LANG.put(fullKey, new CNEN((String) enumToChineseNameMap.get(fullKey), englishName));
                                                        enumToChineseNameMap.remove(fullKey);
                                                    } else {
                                                        enumToEnglishNameMap.put(fullKey, englishName);
                                                    }
                                                } catch (IllegalAccessException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                        } else if (field.isAnnotationPresent(RegisterEnumLang.CnValue.class)) {
                                            RegisterEnumLang.CnValue cnValueAnnotation = field.getAnnotation(RegisterEnumLang.CnValue.class);
                                            field.setAccessible(true);
                                            if (cnValueAnnotation != null) {
                                                try {
                                                    String fullKey = keyPrefix + "." + cnValueAnnotation.value() + "." + enumName;
                                                    String chineseName = (String) field.get(enumConstant);
                                                    if (enumToEnglishNameMap.containsKey(fullKey)) {
                                                        LANG.put(fullKey, new CNEN(chineseName, (String) enumToEnglishNameMap.get(fullKey)));
                                                        enumToEnglishNameMap.remove(fullKey);
                                                    } else {
                                                        enumToChineseNameMap.put(fullKey, chineseName);
                                                    }
                                                } catch (IllegalAccessException e) {
                                                    throw new RuntimeException(e);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch (ClassNotFoundException ignored) {}
                }
            }
        }
        VALUES = classToDynamicInitialDataBuilder.build();
        OBJECT_VALUES = classToObjectDynamicInitialDataBuilder.build();
        GTECore.LibLOGGER.info("ScanningClass initialization time: {}ms", System.currentTimeMillis() - startTime);
    }
}

package org.hui1601.emulator.compiler.engine;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ScriptUtils {
    // Copyright (C) 2020 NenkaLab
    public static Object convert(ScriptableObject object) {
        Class<? extends ScriptableObject> clazz;
        ArrayList<String> functions;
        String[] list;

        clazz = object.getClass();
        functions = new ArrayList<>();

        for (Method method : clazz.getMethods())
            if (method.getAnnotation(JSFunction.class) != null) functions.add(method.getName());

        list = new String[functions.size()];

        functions.toArray(list);

        object.defineFunctionProperties(list, clazz, ScriptableObject.EMPTY);
        return object;
    }
}

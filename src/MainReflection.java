import model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws Exception {

/**
 * Провалившаяся попытка изменения значения static final поля "STORAGE_LIMIT",
 * final поле невозможно изменить, т.к. значение присваивается сразу на этапе компиляции.
 */
//        Field modifiersField = AbstractArrayStorage.class.getDeclaredField("STORAGE_LIMIT");
//        modifiersField.setAccessible(true);
//        modifiersField.set(null, 10);
//        System.out.println(modifiersField.get(null));

        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        System.out.println(field.getName());
        field.setAccessible(true);

        System.out.println(field.get(r));
        field.set(r, "new_uuid");

        // TODO : invoke r.toString via reflection
        Method method = Resume.class.getDeclaredMethod("toString");
        System.out.println("Invoke r.toString via reflection: " + method.invoke(r));

        System.out.println(r);
    }
}

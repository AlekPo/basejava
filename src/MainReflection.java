import model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws Exception {
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

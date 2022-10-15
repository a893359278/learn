package org.csp.learn.java.cls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author 陈少平
 * @date 2022-10-15 15:28
 */
public class UserDefineClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";

        // 因为传入的不是 类的全限定名称，而是 XX.class
        // 因此测试的时候，要加载的类需要与 ClassLoaderTest 同一个包
        InputStream is = this.getClass().getResourceAsStream(fileName);
        // 当前 class 获取不到资源，交由 父加载加载
        if (is == null) {
            return super.loadClass(name);
        }
        Class cls;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];

            int len;
            while ((len = is.read(buf)) >= 0) {
                baos.write(buf, 0, len);
            }

            buf = baos.toByteArray();
            int i = name.lastIndexOf(46);
            if (i != -1) {
                String pkgname = name.substring(0, i);
                Package pkg = this.getPackage(pkgname);
                if (pkg == null) {
                    this.definePackage(pkgname, (String) null, (String) null, (String) null, (String) null, (String) null, (String) null, (URL) null);
                }
            }

            cls = this.defineClass(name, buf, 0, buf.length);
        } catch (IOException ex) {
            throw new ClassNotFoundException(name, ex);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
            }
        }

        return cls;
    }

    public static void main(String[] args) throws ClassNotFoundException {
        String clsName = "org.csp.learn.java.cls.Test";

        UserDefineClassLoader userDefineClassLoader = new UserDefineClassLoader();
        Class<?> userCls = userDefineClassLoader.loadClass(clsName);

        Class<?> appCls = getSystemClassLoader().loadClass(clsName);

        System.out.println(userCls == appCls); // false

        UserDefineClassLoader userDefineClassLoader2 = new UserDefineClassLoader();
        Class<?> userCls2 = userDefineClassLoader2.loadClass(clsName);
        System.out.println(userCls2 == userCls); // false, 因为 并非由同一个类加载加载
    }
}

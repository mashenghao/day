package dynamicCompiler.p4;

import cn.hutool.core.io.FileUtil;

import java.io.File;

/**
 * 自定义classloader
 *
 * @author mash
 * @date 2022/12/08
 */
public class DirClassLoader extends ClassLoader {

    private final String dir;

    public DirClassLoader(String dir) {
        this.dir = dir;
    }

    //重写findClass方法，定义自己去获取class所在的位置。 也可以实现为从网络中读取，数据库中读取。
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //cn.A

        //dir+/CN/A.CLASS
        String path = name.replaceAll("\\.", "/") + ".class";
        File classFile = new File(dir, path);
        if (!classFile.exists()) {
            throw new ClassNotFoundException(name);
        }
        try {
            byte[] bytes = FileUtil.readBytes(classFile);
            //读取文件为字节数组，交由defineClass去将字节码二进制数组加载为Class对象
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
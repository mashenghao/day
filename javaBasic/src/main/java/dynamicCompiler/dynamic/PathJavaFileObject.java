package dynamicCompiler.dynamic;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;
import java.io.*;
import java.net.URI;

/**
 * 将 字节码封装为 文件对象
 *
 * @author mahao
 * @date 2022/12/09
 */
public class PathJavaFileObject implements JavaFileObject {

    private final String binaryName;

    private final URI uri;

    private InputStream in;

    private String name;

    public PathJavaFileObject(String binName, InputStream in) {
        this.uri = URI.create(binName);
        this.in = in;
        String path = this.uri.getPath();
        this.binaryName = path.replace('/', '.').substring(1, path.length() - 6);
    }

    public PathJavaFileObject(String binaryName, URI uri) {
        this.uri = uri;
        this.binaryName = binaryName;
        this.name = (uri.getPath() == null) ? uri.getSchemeSpecificPart() : uri.getPath();
    }

    public InputStream openInputStream() throws IOException {
        if (this.in != null)
            return this.in;
        return this.uri.toURL().openStream();
    }

    public String binaryName() {
        return this.binaryName;
    }

    public URI toUri() {
        return this.uri;
    }

    public String getName() {
        return this.name;
    }

    public OutputStream openOutputStream() {
        throw new UnsupportedOperationException();
    }

    public Reader openReader(boolean ignoreEncodingErrors) {
        throw new UnsupportedOperationException();
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        throw new UnsupportedOperationException();
    }

    public Writer openWriter() {
        throw new UnsupportedOperationException();
    }

    public long getLastModified() {
        return 0L;
    }

    public boolean delete() {
        throw new UnsupportedOperationException();
    }

    public JavaFileObject.Kind getKind() {
        return JavaFileObject.Kind.CLASS;
    }

    public boolean isNameCompatible(String simpleName, JavaFileObject.Kind kind) {
        String baseName = simpleName + kind.extension;
        return (kind.equals(getKind()) && (baseName.equals(getName()) || getName().endsWith("/" + baseName)));
    }

    public NestingKind getNestingKind() {
        throw new UnsupportedOperationException();
    }

    public Modifier getAccessLevel() {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return getClass().getSimpleName() + "{uri=" + this.uri + '}';
    }
}
package dk.ku.di.dms.vms.web_common;

import java.io.IOException;
import java.io.Writer;

public class CustomWriter extends Writer {

    StringBuilder builder;

    public CustomWriter() {
        this.builder = new StringBuilder();
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        builder.append(cbuf, off, len);
    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public void close() throws IOException {

    }


}
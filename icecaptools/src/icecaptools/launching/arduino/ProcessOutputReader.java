package icecaptools.launching.arduino;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.LinkedBlockingQueue;

public class ProcessOutputReader extends InputStream {
    protected LinkedBlockingQueue<Byte> queue;

    public ProcessOutputReader() {
        queue = new LinkedBlockingQueue<Byte>(10000);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {

        int avail;

        while (queue.size() == 0) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }

        avail = available();

        int count = 0;
        while ((count < len) && (count < avail)) {
            try {
                byte next = queue.take();
                if (next == -1)
                {
                    return -1;
                }
                else
                {
                    b[off + count] = next;
                }
                
            } catch (InterruptedException e) {
                return count;
            }
            count++;
        }
        return count;
    }

    @Override
    public int read() throws IOException {
        while (true) {
            try {
                byte b = queue.take();
                return b;
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e1) {
                }
            }
        }
    }

    @Override
    public int available() throws IOException {
        return queue.size();
    }
}

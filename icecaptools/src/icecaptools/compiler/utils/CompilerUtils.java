package icecaptools.compiler.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class CompilerUtils {

    private static class UsageReporter extends Job {
        private int methodCount;

        public UsageReporter(String name, int methodCount) {
            super(name);
            this.methodCount = methodCount;
        }

        @Override
        protected IStatus run(IProgressMonitor monitor) {
            URL url;
            HttpURLConnection conn;
            BufferedReader rd;
            String urlToRead = "http://www.icelab.dk/hvmforum/HVMForum?cmd=reportUsage&mcount=" + methodCount;

            try {
                url = new URL(urlToRead);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(5000);
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer response = new StringBuffer();
                String line;
                while ((line = rd.readLine()) != null) {
                    response.append(line + "\n");
                }
                rd.close();
            } catch (MalformedURLException e) {
            } catch (IOException e) {
            } catch (Throwable thr) {
                ;
            }
            return Status.OK_STATUS;
        }
    }

    public static void reportConversion(int methodCount) {
        UsageReporter ruJob = new UsageReporter("Report HVM Usage", methodCount);
        ruJob.setSystem(true);
        ruJob.schedule();
    }
}

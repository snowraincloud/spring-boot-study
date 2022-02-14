package pri.wjh.test.jgit

import org.apache.commons.io.FileUtils
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.ProgressMonitor
import org.eclipse.jgit.transport.CredentialsProvider
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import spock.lang.Specification

class CloneRemoteRepositoryTest extends Specification {

    private final static String REMOTE_URL = "https://gitee.com/snowrain_wjh/python-fun.git"

    /**
     * @link https://github.com/centic9/jgit-cookbook.git#CloneRemoteRepository
     */
    def "test clone remote repository"() {
        given:
        File localPath = File.createTempFile("TestGitRepository", "")
        if (!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath)
        }
        String username = System.getenv("GIT_USERNAME")
        String password = System.getenv("GIT_PASSWORD")
        CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(username, password)

        // then clone
        System.out.println("Cloning from " + REMOTE_URL + " to " + localPath)
        try (Git result = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setCredentialsProvider(credentialsProvider)
                .setDirectory(localPath)
                .setProgressMonitor(new SimpleProgressMonitor())
                .call()) {
            // Note: the call() returns an opened repository already which needs to be closed to avoid file handle leaks!
            System.out.println("Having repository: " + result.getRepository().getDirectory());
        }

//        // clean up here to not keep using more and more disk-space for these samples
        FileUtils.deleteDirectory(localPath);
    }

    private static class SimpleProgressMonitor implements ProgressMonitor {
        @Override
        public void start(int totalTasks) {
            System.out.println("Starting work on " + totalTasks + " tasks");
        }

        @Override
        public void beginTask(String title, int totalWork) {
            System.out.println("Start " + title + ": " + totalWork);
        }

        @Override
        public void update(int completed) {
            System.out.print(completed + "-");
        }

        @Override
        public void endTask() {
            System.out.println("Done");
        }

        @Override
        public boolean isCancelled() {
            return false;
        }
    }
}

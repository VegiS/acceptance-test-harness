package org.jenkinsci.test.acceptance.guice;

import com.google.inject.Inject;

/**
 * {@link Cleaner} at the end of each {@link TestScope}.
 *
 * Oftentimes marking your class with {@link AutoCleaned} gets the job done.
 *
 * @author Kohsuke Kawaguchi
 */
@TestScope
public class TestCleaner extends Cleaner {
    @Inject
    TestLifecycle lifecycle;

    @Override
    void performCleanUp() {
        super.performCleanUp();
        for (Object o : lifecycle.getInstances()) {
            if (o instanceof AutoCleaned) {
                try {
                    ((AutoCleaned)o).close();
                } catch (Throwable t) {
                    throw new AssertionError(o+" cleanup failed",t);
                }
            }
        }
    }
}

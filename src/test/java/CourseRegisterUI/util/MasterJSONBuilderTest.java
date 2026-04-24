package CourseRegisterUI.util;

import CourseRegisterUI.models.Root;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MasterJSONBuilderTest {

    @Test
    void buildSampleMaster_containsSampleUsersAndCourses() {
        Root root = MasterJSONBuilder.buildSampleMaster();

        assertNotNull(root);
        assertEquals(4, root.users().size());
        assertEquals(4, root.courses().size());
    }

    @Test
    void writeMasterToFile_writesJsonFile(@TempDir Path tempDir) {
        Root root = MasterJSONBuilder.buildSampleMaster();
        File file = tempDir.resolve("master.json").toFile();

        assertDoesNotThrow(() -> MasterJSONBuilder.writeMasterToFile(file, root));
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }
}

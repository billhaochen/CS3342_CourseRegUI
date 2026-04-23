package CourseRegisterUI.util;

import CourseRegisterUI.models.Root;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class JSONDeserializerTest {

    @Test
    void jsonToRoot_readsWrittenMasterFile(@TempDir Path tempDir) throws Exception {
        Root expected = MasterJSONBuilder.buildSampleMaster();
        File file = tempDir.resolve("master.json").toFile();
        MasterJSONBuilder.writeMasterToFile(file, expected);

        Root actual = JSONDeserializer.JSONToRoot(file.getAbsolutePath());

        assertEquals(expected.users().size(), actual.users().size());
        assertEquals(expected.courses().size(), actual.courses().size());
    }

    @Test
    void jsonToRoot_returnsEmptyRootForMissingFile(@TempDir Path tempDir) {
        Root root = JSONDeserializer.JSONToRoot(tempDir.resolve("missing.json").toString());

        assertNotNull(root);
        assertTrue(root.users().isEmpty());
        assertTrue(root.courses().isEmpty());
    }
}

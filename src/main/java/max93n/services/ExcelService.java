package max93n.services;

import java.io.FileInputStream;
import java.io.InputStream;

public interface ExcelService {
    void exportData();
    void importData(InputStream inputStream);
}

package org.bahmni.fileimport;

import org.apache.log4j.Logger;
import org.bahmni.csv.CSVEntity;
import org.bahmni.csv.EntityPersister;
import org.bahmni.fileimport.dao.JDBCConnectionProvider;

import java.io.File;

// External API to start the csv file import.
public class FileImporter<T extends CSVEntity> {
    private static Logger logger = Logger.getLogger(FileImporter.class);

    public boolean importCSV(String originalFileName, File csvFile, EntityPersister<T> persister, Class csvEntityClass, JDBCConnectionProvider jdbcConnectionProvider, String uploadedBy) {
        logger.info("Starting file import thread for " + csvFile.getAbsolutePath());
        try {
            Importer importer = ImportRegistry.register(originalFileName, csvFile, persister, csvEntityClass, uploadedBy);
            importer.start(jdbcConnectionProvider);
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
        logger.info("Initiated upload in background for " + csvFile.getAbsolutePath());
        return true;
    }

}
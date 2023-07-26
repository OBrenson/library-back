package boi.projs.library.service;

import boi.projs.library.domain.TextFile;
import boi.projs.library.logging.LoggableCrud;
import boi.projs.library.repository.TextFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@LoggableCrud
public class TextFileCrudService extends CrudService<TextFile> {

    @Autowired
    public TextFileCrudService(TextFileRepository textFileRepository) {
        super(textFileRepository);
    }

    public TextFile findByBookId(UUID bookId) {
        return ((TextFileRepository)repository).findByBookId(bookId);
    }
}

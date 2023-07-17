package boi.projs.library.repository;

import boi.projs.library.domain.TextFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TextFileRepository extends JpaRepository<TextFile, UUID> {

    TextFile findByBookId(UUID bookId);
}

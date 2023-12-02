package test.task.libraryservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.task.libraryservice.entity.Library;



@Repository
public interface LibraryRepository extends JpaRepository<Library, Long> {

}

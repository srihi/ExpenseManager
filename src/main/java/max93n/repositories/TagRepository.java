package max93n.repositories;

import max93n.entities.Tag;
import max93n.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long>{

    @Query("select t from Tag t where t.user = :user")
    List<Tag> getAllByUser(@Param("user")User user);

    @Query("select t from Tag t where t.name = :name")
    Tag getByName(@Param("name") String name);
}

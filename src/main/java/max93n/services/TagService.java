package max93n.services;

import max93n.entities.Tag;
import max93n.entities.User;

import java.util.List;

public interface TagService {

    List<Tag> getAllByUser(User user);

    void add(Tag tag);
    void edit(Tag tag);
    void remove(Tag tag);


}

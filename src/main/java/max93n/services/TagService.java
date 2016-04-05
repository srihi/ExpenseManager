package max93n.services;

import max93n.entities.Tag;
import max93n.entities.User;

import java.util.List;

public interface TagService {
    List<Tag> getAllByUser(User user);

    Tag getByName(String name);
}

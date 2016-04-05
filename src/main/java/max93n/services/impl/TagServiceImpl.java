package max93n.services.impl;

import max93n.entities.Tag;
import max93n.entities.User;
import max93n.repositories.TagRepository;
import max93n.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tagService")
public class TagServiceImpl implements TagService{

    @Autowired
    private TagRepository tagRepository;

    @Override
    public List<Tag> getAllByUser(User user) {
        return tagRepository.getAllByUser(user);
    }

    @Override
    public Tag getByName(String name) {
        return tagRepository.getByName(name);
    }
}

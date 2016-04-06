package max93n.services.impl;

import max93n.entities.Tag;
import max93n.entities.Transaction;
import max93n.entities.User;
import max93n.repositories.TagRepository;
import max93n.services.TagService;
import max93n.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("tagService")
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    public List<Tag> getAllByUser(User user) {
        return tagRepository.getAllByUser(user);
    }

    @Override
    public Tag getByName(String name) {
        return tagRepository.getByName(name);
    }

    @Override
    public boolean add(Tag tag) {

        if (tagRepository.getByName(tag.getName()) != null) {
            return false;
        }

        tagRepository.saveAndFlush(tag);
        return true;
    }

    @Override
    public boolean edit(Tag tag) {
        tagRepository.saveAndFlush(tag);
        return true;
    }

    @Override
    public void remove(Tag tag) {
        if (tag.getTransaction() != null) {
            for (Transaction transaction : tag.getTransaction()) {
                transaction.getTags().remove(tag);
                transactionService.save(transaction);
            }
            tag.setTransaction(null);

        }
        tagRepository.delete(tag.getId());
    }
}

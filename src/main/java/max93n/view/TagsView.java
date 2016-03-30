package max93n.view;


import max93n.entities.Tag;
import max93n.entities.User;
import max93n.services.TagService;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;

@ManagedBean
@ViewScoped
public class TagsView {

    @ManagedProperty("#{tagService}")
    private TagService tagService;
    private User user;

    private List<Tag> tags;

    private Tag currentTag;

    private String name;
    private String description;



    public TagsView() {}

    @PostConstruct
    public void init() {

        user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        tags = tagService.getAllByUser(user);

    }

    public void add() {

        Tag tag = new Tag();
        tag.setName(name);
        tag.setDescription(description);
        tag.setUser(user);

        if (tagService.add(tag)) {
            tags.add(tag);
        }

    }

    public void editCurrent() {
        tagService.edit(currentTag);
    }

    public void removeCurrent() {
        tagService.remove(currentTag);
        tags.remove(currentTag);
    }

    public void reset() {
        name = null;
        description = null;
    }


    public Tag getCurrentTag() {
        return currentTag;
    }

    public void setCurrentTag(Tag currentTag) {
        this.currentTag = currentTag;
    }

    public TagService getTagService() {
        return tagService;
    }

    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

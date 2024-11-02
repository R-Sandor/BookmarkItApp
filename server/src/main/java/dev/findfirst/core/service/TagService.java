package dev.findfirst.core.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import dev.findfirst.core.dto.BookmarkDTO;
import dev.findfirst.core.dto.TagDTO;
import dev.findfirst.core.model.jdbc.BookmarkTag;
import dev.findfirst.core.model.jdbc.TagJDBC;
import dev.findfirst.core.model.jpa.Tag;
import dev.findfirst.core.repository.jdbc.BookmarkJDBCRepository;
import dev.findfirst.core.repository.jdbc.BookmarkTagRepository;
import dev.findfirst.core.repository.jdbc.TagJDBCRepository;
import dev.findfirst.core.repository.jpa.TagRepository;
import dev.findfirst.security.userAuth.tenant.contexts.TenantContext;
import dev.findfirst.security.userAuth.tenant.repository.TenantRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

  private final TagJDBCRepository tagRepositoryJDBC;

  private final BookmarkTagRepository bookmarkTagRepository;

  private final TagRepository tagRepository;

  private final TenantContext tenantContext;

  private final TenantRepository tRepository;

  private final BookmarkJDBCRepository bookmarkRepo;

  private Tag addTag(String title) {
    return tagRepository.saveAndFlush(new Tag(title));
  }

  private TagJDBC addTagJDBC(String title) {
    var tenantId = tenantContext.getTenantId();
    var tenant = tRepository.findById(tenantId).orElseThrow();

    var tag = new TagJDBC(null, tenantId, tenant.getCreatedDate(), tenant.getCreatedBy(),
        tenant.getLastModifiedBy(), tenant.getLastModifiedDate(), title,
        new HashSet<BookmarkTag>());
    tag = tagRepositoryJDBC.save(tag);
    return tag;
  }

  /**
   * Simple checks if there is a tag with given tag title if not creates one and
   * returns the Tag.
   *
   * @param title
   * @return Tag existing tag with ID or a new Tag with the given title.
   */
  public TagDTO findOrCreateTag(String title) {
    Tag t = findByTagTitle(title).orElseGet(() -> addTag(title));

    var tBkmks = t.getBookmarks().stream().map(b -> new BookmarkDTO(b.getId(), b.getTitle(), b.getUrl(), b.getScreenshotUrl(), 
      b.getScrapable(), b.getCreatedDate(), b.getLastModifiedDate(), new ArrayList<TagDTO>())).toList();

    return new TagDTO(t.getId(), t.getTag_title(), tBkmks);
  }

  public List<TagDTO> convertTagJDBCToDTO(List<TagJDBC> tagEntities, int tenantId) {

    // Get the bookmarks that are associated to the Tag.
    return tagEntities.stream().map(ent -> {
      var bkmkIds = bookmarkTagRepository.getAllBookmarkIdsForTag(ent.getId(), tenantId);
      var bkmkEnts = bookmarkRepo.findAllById(bkmkIds);

      List<BookmarkDTO> bookmarkDTOs = new ArrayList<>();

      for (var b : bkmkEnts) {
        bookmarkDTOs.add(new BookmarkDTO(b.getId(), b.getTitle(),
            b.getUrl(), b.getScreenshotUrl(), b.getScrapable(), b.getCreatedDate(), b.getLastModifiedDate(),
            new ArrayList<TagDTO>()));
      }

      return new TagDTO(ent.getId(), ent.getTitle(), bookmarkDTOs);
    }).toList();
  }

  /**
   * Simple checks if there is a tag with given tag title if not creates one and
   * returns the Tag.
   *
   * @param title
   * @return Tag existing tag with ID or a new Tag with the given title.
   */
  public TagJDBC findOrCreateTagJDBC(String title) {
    return findByTagTitleJDBC(title).orElseGet(() -> addTagJDBC(title));
  }

  public TagJDBC getTagWithBookmarks(Long tagId) {
    TagJDBC tag = tagRepositoryJDBC.findById(tagId).orElseThrow();
    System.out.println(tag);
    List<BookmarkTag> bookmarks = bookmarkTagRepository.findByTagId(tag.getId());
    tag.setBookmarks(new HashSet<>(bookmarks));
    return tag;
  }

  /**
   * Create List of tags by titles. Creating a new tags for ones that do not exist
   * and returning
   * list of existing tags.
   *
   * @param titles List of strings
   */
  public List<TagDTO> addAll(String... titles) {
    return Arrays.stream(titles).map(t -> findOrCreateTag(t)).toList();
  }

  /**
   * Create List of tags by titles. Creating a new tags for ones that do not exist
   * and returning
   * list of existing tags.
   *
   * @param titles List of strings
   */
  public List<TagJDBC> addAllJDBC(String... titles) {
    return Arrays.stream(titles).map(t -> findOrCreateTagJDBC(t)).toList();
  }

  public List<Tag> deleteAllTags() {
    var userTags = tagRepository.findAll();
    tagRepository.deleteAll(userTags);
    return userTags;
  }

  public List<TagDTO> getTags() {
    var tagsJDBC = tagRepositoryJDBC.findAllByTenantId(tenantContext.getTenantId());
    return convertTagJDBCToDTO(tagsJDBC, tenantContext.getTenantId());
  }

  public List<Tag> getTagsByBookmarkId(long id) {
    return tagRepository.findTagsByBookmarkId(id);
  }

  public Optional<Tag> getTagByTitle(String title) {
    return tagRepository.findByTagTitle(title);
  }

  public Optional<Tag> findById(Long id) {
    return tagRepository.findById(id);
  }

  public Optional<TagJDBC> findByTagTitleJDBC(@NonNull String title) {
    return tagRepositoryJDBC.findByTitle(title, tenantContext.getTenantId());
  }

  public Optional<Tag> findByTagTitle(@NonNull String tag_title) {
    return tagRepository.findByTagTitle(tag_title);
  }
}

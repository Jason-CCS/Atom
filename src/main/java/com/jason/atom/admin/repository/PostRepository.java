package com.jason.atom.admin.repository;

import com.jason.atom.admin.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByAuthorId(Long authorId, Pageable pageable);

    List<Post> findTop5ByOrderByUpdateTimeDesc();

}

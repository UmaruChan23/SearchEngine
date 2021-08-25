package com.engine.searchengine.parser.repo;

import com.engine.searchengine.parser.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagesRepo extends JpaRepository<Page, Integer> {
}

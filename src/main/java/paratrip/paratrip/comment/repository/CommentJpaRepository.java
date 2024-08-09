package paratrip.paratrip.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import paratrip.paratrip.comment.entity.CommentEntity;

@Repository
public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {

}

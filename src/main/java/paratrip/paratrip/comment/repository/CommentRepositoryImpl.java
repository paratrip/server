package paratrip.paratrip.comment.repository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import paratrip.paratrip.comment.entity.CommentEntity;

@Component
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {
	private final CommentJpaRepository commentJpaRepository;

	@Override
	public CommentEntity saveCommentEntity(CommentEntity commentEntity) {
		return commentJpaRepository.save(commentEntity);
	}
}

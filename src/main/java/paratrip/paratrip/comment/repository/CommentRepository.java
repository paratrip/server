package paratrip.paratrip.comment.repository;

import paratrip.paratrip.comment.entity.CommentEntity;

public interface CommentRepository {
	CommentEntity saveCommentEntity(CommentEntity commentEntity);
}

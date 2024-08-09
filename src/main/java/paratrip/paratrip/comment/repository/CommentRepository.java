package paratrip.paratrip.comment.repository;

import paratrip.paratrip.comment.entity.CommentEntity;
import paratrip.paratrip.member.entity.MemberEntity;

public interface CommentRepository {
	CommentEntity saveCommentEntity(CommentEntity commentEntity);
	CommentEntity findByCommentSeq(Long commentSeq);
	CommentEntity findByCommentSeqAndMemberEntity(Long commentSeq, MemberEntity memberEntity);
}

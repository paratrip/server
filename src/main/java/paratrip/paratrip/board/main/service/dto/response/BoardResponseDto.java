package paratrip.paratrip.board.main.service.dto.response;

import static paratrip.paratrip.board.main.controller.vo.response.BoardResponseVo.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public class BoardResponseDto {
	public record AddBoardResponseDto(
		Long boardSeq
	) {
		public AddBoardResponse toAddBoardResponse() {
			return new AddBoardResponse(
				this.boardSeq
			);
		}
	}

	public record GetAllBoardResponseDto(
		GetAllBoardResponseDto.AllBoardMemberInfo memberInfo,
		GetAllBoardResponseDto.AllBoardBoardInfo boardInfo,
		GetAllBoardResponseDto.AllBoardCountInfo countInfo
	) {
		public record AllBoardMemberInfo(
			Long memberSeq,
			String userId,
			String profileImage
		) {

		}

		public record AllBoardBoardInfo(
			Long boardSeq,
			String title,
			String location,
			String content,
			ZonedDateTime updatedAt,
			List<String> imageURLs
		) {

		}

		public record AllBoardCountInfo(
			Long commentCnt,
			Long heartCnt,
			Long scrapCnt
		) {

		}
	}

	public record GetBoardResponseDto(
		GetBoardResponseDto.BoardCreatorInfo boardCreatorInfo,
		GetBoardResponseDto.BoardInfo boardInfo,
		GetBoardResponseDto.CountInfo countInfo,
		List<GetBoardResponseDto.CommentInfo> commentInfos
	) {
		public record BoardCreatorInfo(
			Long memberSeq,
			String userId,
			String profileImage
		) {

		}

		public record BoardInfo(
			Long boardSeq,
			String title,
			String content,
			String location,
			ZonedDateTime updatedAt,
			List<String> imageURLs
		) {

		}

		public record CountInfo(
			Long commentCnt,
			Long heartCnt,
			Long scrapCnt,
			Boolean heart,
			Boolean scrap
		) {

		}

		public record CommentInfo(
			Long commentSeq,
			String comment,
			ZonedDateTime updatedAt,
			Long memberSeq,
			String userId,
			String profileImage
		) {

		}
	}
}

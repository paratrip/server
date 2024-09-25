package paratrip.paratrip.board.main.controller.vo.request;

import static paratrip.paratrip.board.main.service.dto.request.BoardRequestDto.*;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public class BoardRequestVo {
	public record AddBoardRequest(
		@NotNull Long memberSeq,
		@NotNull String title,
		@NotNull String content,
		String location,
		List<MultipartFile> images
	) {
		public AddBoardRequestDto toAddBoardRequestDto() {
			return new AddBoardRequestDto(
				this.memberSeq,
				this.title,
				this.content,
				this.location,
				this.images
			);
		}
	}

	public record ModifyBoardRequest(
		@NotNull Long memberSeq,
		@NotNull Long boardSeq,
		@NotNull String title,
		@NotNull String content,
		String location,
		List<MultipartFile> images
	) {
		public ModifyBoardRequestDto toModifyBoardRequestDto() {
			return new ModifyBoardRequestDto(
				this.memberSeq,
				this.boardSeq,
				this.title,
				this.content,
				this.location,
				this.images
			);
		}
	}
}

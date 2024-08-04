package paratrip.paratrip.board.main.controller.vo.request;

import static paratrip.paratrip.board.main.service.dto.request.BoardRequestDto.*;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BoardRequestVo {
	public record AddBoardRequest(
		Long memberSeq,
		String title,
		String content,
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
		Long memberSeq,
		Long boardSeq,
		String title,
		String content,
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
package paratrip.paratrip.board.controller.vo.request;

import static paratrip.paratrip.board.service.dto.request.BoardRequestDto.*;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import paratrip.paratrip.board.service.dto.request.BoardRequestDto;

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
}

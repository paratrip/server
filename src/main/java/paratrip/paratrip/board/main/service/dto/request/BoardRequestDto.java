package paratrip.paratrip.board.main.service.dto.request;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BoardRequestDto {
	public record AddBoardRequestDto(
		Long memberSeq,
		String title,
		String content,
		String location,
		List<MultipartFile> images
	) {

	}

	public record ModifyBoardRequestDto(
		Long memberSeq,
		Long boardSeq,
		String title,
		String content,
		String location,
		List<MultipartFile> images
	) {

	}
}

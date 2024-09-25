package paratrip.paratrip.board.search.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.main.service.dto.response.BoardResponseDto;
import paratrip.paratrip.board.search.service.BoardDocumentsService;
import paratrip.paratrip.core.base.BaseResponse;

@RestController
@RequestMapping("/board/search")
@RequiredArgsConstructor
@Tag(name = "커뮤니티 검색 API", description = "담당자(박종훈)")
public class BoardDocumentsController {

	private final BoardDocumentsService boardDocumentsService;

	@GetMapping(name = "게시물 검색")
	@Operation(summary = "게시물 검색 API", description = "게시물 검색")
	public ResponseEntity<BaseResponse<List<BoardResponseDto.GetAllBoardResponseDto>>> getBoardDocuments(
		@Valid
		@RequestParam(value = "title") String title,
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "10") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		List<BoardResponseDto.GetAllBoardResponseDto> response
			= boardDocumentsService.getBoardDocuments(title, pageable);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}
}

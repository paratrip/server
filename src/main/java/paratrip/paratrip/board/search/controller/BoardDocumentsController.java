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
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.search.service.BoardDocumentsService;
import paratrip.paratrip.board.search.service.dto.response.BoardDocumentsResponseDto;
import paratrip.paratrip.core.base.BaseResponse;
import paratrip.paratrip.core.exception.GlobalExceptionHandler;

@RestController
@RequestMapping("/board/search")
@RequiredArgsConstructor
@Tag(name = "커뮤니티 검색 API", description = "담당자(박종훈)")
public class BoardDocumentsController {

	private final BoardDocumentsService boardDocumentsService;

	@GetMapping(name = "게시물 검색")
	@Operation(summary = "게시물 검색 API", description = "게시물 검색")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "요청에 성공하였습니다.",
			useReturnTypeSchema = true),
		@ApiResponse(
			responseCode = "S500",
			description = "500 SERVER_ERROR (나도 몰라 ..)",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "B001",
			description = "400 Invalid DTO Parameter errors / 요청 값 형식 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
	})
	@Parameters({
		@Parameter(
			name = "title",
			description = "검색 제목",
			example = "1",
			required = true),
		@Parameter(
			name = "page",
			description = "페이지 번호 (기본값: 0)",
			example = "0"),
		@Parameter(
			name = "size",
			description = "페이지당 항목 수 (기본값: 10)",
			example = "10")
	})
	public ResponseEntity<BaseResponse<List<BoardDocumentsResponseDto.GetBoardDocumentsResponseDto>>> getBoardDocuments(
		@Valid
		@RequestParam(value = "title") String title,
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "10") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		List<BoardDocumentsResponseDto.GetBoardDocumentsResponseDto> response
			= boardDocumentsService.getBoardDocuments(title, pageable);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}
}

package paratrip.paratrip.scrap.board.controller;

import static paratrip.paratrip.scrap.board.controller.vo.request.BoardScrapRequestVo.*;
import static paratrip.paratrip.scrap.board.controller.vo.response.BoardScrapResponseVo.*;
import static paratrip.paratrip.scrap.board.service.dto.response.BoardScrapResponseDto.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import paratrip.paratrip.board.main.service.dto.response.BoardResponseDto;
import paratrip.paratrip.scrap.board.service.BoardScrapService;
import paratrip.paratrip.scrap.board.validates.AddBoardScrapValidator;
import paratrip.paratrip.scrap.board.validates.DeleteBoardScrapValidator;
import paratrip.paratrip.core.base.BaseResponse;
import paratrip.paratrip.core.exception.GlobalExceptionHandler;

@RestController
@RequestMapping("/board-scrap")
@RequiredArgsConstructor
@Tag(name = "게시물 스크랩 API", description = "담당자(박종훈)")
public class BoardScrapController {
	private final AddBoardScrapValidator addBoardScrapValidator;
	private final DeleteBoardScrapValidator deleteBoardHeart;

	private final BoardScrapService boardScrapService;

	@PostMapping(name = "게시물 스크랩 생성")
	@Operation(summary = "게시물 스크랩 생성 API", description = "게시물 스크랩 생성")
	public ResponseEntity<BaseResponse> saveBoardScrap(
		@Valid
		@RequestBody AddBoardScrapRequest request
	) {
		// 유효성 검사
		addBoardScrapValidator.validate(request);

		// VO -> DTO
		boardScrapService.saveBoardScrap(request.toAddBoardScrapRequestDto());

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}

	@DeleteMapping(name = "게시물 스크랩 삭제")
	public ResponseEntity<BaseResponse> deleteBoardHeart(
		@Valid
		@RequestBody DeleteBoardScrapRequest request
	) {
		// 유효성 검사
		deleteBoardHeart.validate(request);

		// VO -> DTO
		boardScrapService.deleteBoardScrap(request.toDeleteBoardScrapRequestDto());

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}

	@GetMapping(name = "스크랩 게시물 조회")
	@Operation(summary = "스크랩 게시물 조회 API", description = "스크랩 게시물 조회")
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
		@ApiResponse(
			responseCode = "MSB003",
			description = "400 MEMBER_SEQ_BAD_REQUEST_EXCEPTION / Member Seq 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
	})
	@Parameters({
		@Parameter(
			name = "memberSeq",
			description = "Member Seq",
			example = "0"),
		@Parameter(
			name = "page",
			description = "페이지 번호 (기본값: 0)",
			example = "0"),
		@Parameter(
			name = "size",
			description = "페이지당 항목 수 (기본값: 10)",
			example = "10")
	})
	public ResponseEntity<BaseResponse<Page<BoardResponseDto.GetAllBoardResponseDto>>> getBoardScrap(
		@Valid
		@RequestParam("memberSeq") Long memberSeq,
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "10") int size
	) {
		Pageable pageable = PageRequest.of(page, size);

		// VO -> DTO
		Page<BoardResponseDto.GetAllBoardResponseDto> response = boardScrapService.getBoardScarp(memberSeq, pageable);

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}
}

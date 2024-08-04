package paratrip.paratrip.board.scrap.controller;

import static paratrip.paratrip.board.scrap.controller.vo.request.BoardScrapRequestVo.*;
import static paratrip.paratrip.board.scrap.controller.vo.response.BoardScrapResponseVo.*;
import static paratrip.paratrip.board.scrap.service.dto.response.BoardScrapResponseDto.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import paratrip.paratrip.board.scrap.service.BoardScrapService;
import paratrip.paratrip.board.scrap.validates.AddBoardScrapValidator;
import paratrip.paratrip.board.scrap.validates.DeleteBoardScrapValidator;
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
		@ApiResponse(
			responseCode = "BSB005",
			description = "400 BOARD_SEQ_BAD_REQUEST_EXCEPTION / Board Seq 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "BNCBMB006",
			description = "400 BOARD_NOT_CREATED_BY_MEMBER_BAD_REQUEST_EXCEPTION / Member가 해당 Board 작성자가 아닐 때 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
	})
	public ResponseEntity<BaseResponse<AddBoardScrapResponse>> saveBoardScrap(
		@Valid
		@RequestBody AddBoardScrapRequest request
	) {
		// 유효성 검사
		addBoardScrapValidator.validate(request);

		// VO -> DTO
		AddBoardScrapResponseDto addBoardScrapResponseDto
			= boardScrapService.saveBoardScrap(request.toAddBoardScrapRequestDto());

		// DTO -> VO
		AddBoardScrapResponse response = addBoardScrapResponseDto.toAddBoardScrapResponse();

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}

	@DeleteMapping(name = "게시물 스크랩 삭제")
	@Operation(summary = "게시물 스크랩 삭제 API", description = "게시물 스크랩 삭제")
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
		@ApiResponse(
			responseCode = "BSSB008",
			description = "400 BOARD_SCRAP_SEQ_BAD_REQUEST_EXCEPTION / Board Scrap Seq 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "BSNF004",
			description = "404 BOARD_SCRAP_NOT_FOUND_EXCEPTION / Member 가 설정한 스크랩 게시물이 없을 시 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
	})
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
}

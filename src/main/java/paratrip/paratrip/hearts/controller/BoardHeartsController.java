package paratrip.paratrip.hearts.controller;

import static paratrip.paratrip.hearts.controller.vo.request.BoardHeartsRequestVo.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import paratrip.paratrip.hearts.service.BoardHeartService;
import paratrip.paratrip.hearts.service.dto.response.BoardHeartResponseDto;
import paratrip.paratrip.hearts.validates.DecreaseBoardHeartsValidator;
import paratrip.paratrip.hearts.validates.IncreaseBoardHeartsValidator;
import paratrip.paratrip.core.base.BaseResponse;
import paratrip.paratrip.core.exception.GlobalExceptionHandler;

@RestController
@RequestMapping("/board-hearts")
@RequiredArgsConstructor
@Tag(name = "커뮤니티 좋아요", description = "담당자(박종훈)")
public class BoardHeartsController {
	private final IncreaseBoardHeartsValidator addBoardHeartsValidator;
	private final DecreaseBoardHeartsValidator decreaseBoardHeartsValidator;

	private final BoardHeartService boardHeartService;

	@PostMapping(value = "increase", name = "커뮤니티 게시물 좋아요 증가")
	@Operation(summary = "커뮤니티 게시물 좋아요 증가 API", description = "커뮤니티 게시물 좋아요 증가")
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
	})
	public ResponseEntity<BaseResponse<BoardHeartResponseDto.AddBoardHeartResponseDto>> increaseBoardHearts(
		@Valid
		@RequestBody IncreaseBoardHeartsRequest request
	) {
		// 유효성 검사
		addBoardHeartsValidator.validate(request);

		// VO -> DTO
		BoardHeartResponseDto.AddBoardHeartResponseDto response
			= boardHeartService.increaseBoardHearts(request.toIncreaseBoardHeartsRequestDto());

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}

	@PostMapping(value = "decrease", name = "커뮤니티 게시물 좋아요 감소")
	@Operation(summary = "커뮤니티 게시물 좋아요 감소 API", description = "커뮤니티 게시물 좋아요 감소")
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
	})
	public ResponseEntity<BaseResponse> decreaseBoardHearts(
		@Valid
		@RequestBody DecreaseBoardHeartsRequest request
	) {
		// 유효성 검사
		decreaseBoardHeartsValidator.validate(request);

		// VO -> DTO
		boardHeartService.decreaseBoardHearts(request.toDecreaseBoardHeartsRequestDto());

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}
}

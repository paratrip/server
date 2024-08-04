package paratrip.paratrip.board.heart.controller;

import static paratrip.paratrip.board.heart.controller.vo.request.BoardHeartRequestVo.*;
import static paratrip.paratrip.board.heart.controller.vo.response.BoardHeartResponseVo.*;
import static paratrip.paratrip.board.heart.service.dto.response.BoardHeartResponseDto.*;

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
import paratrip.paratrip.board.heart.service.BoardHeartService;
import paratrip.paratrip.board.heart.validates.AddBoardHeartValidator;
import paratrip.paratrip.core.base.BaseResponse;
import paratrip.paratrip.core.exception.GlobalExceptionHandler;

@RestController
@RequestMapping("/board-heart")
@RequiredArgsConstructor
@Tag(name = "회원 API", description = "담당자(박종훈)")
public class BoardHeartController {
	private final AddBoardHeartValidator addBoardHeartValidator;

	private final BoardHeartService boardHeartService;

	@PostMapping(name = "게시물 좋아요 생성")
	@Operation(summary = "게시물 좋아요 생성 API", description = "게시물 좋아요 생성")
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
	public ResponseEntity<BaseResponse<AddBoardHeartResponse>> saveBoardHear(
		@Valid
		@RequestBody AddBoardHeartRequest request
	) {
		// 유효성 검사
		addBoardHeartValidator.validate(request);

		// VO -> DTO
		AddBoardHeartResponseDto addBoardHeartResponseDto
			= boardHeartService.saveBoardHeart(request.toAddBoardHeartRequestDto());

		// DTO -> VO
		AddBoardHeartResponse response = addBoardHeartResponseDto.toAddBoardHeartResponse();

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}
}

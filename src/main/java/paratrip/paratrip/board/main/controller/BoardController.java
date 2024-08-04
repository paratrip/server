package paratrip.paratrip.board.main.controller;

import static paratrip.paratrip.board.main.controller.vo.request.BoardRequestVo.*;
import static paratrip.paratrip.board.main.controller.vo.response.BoardResponseVo.*;
import static paratrip.paratrip.board.main.service.dto.response.BoardResponseDto.*;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import paratrip.paratrip.board.main.service.BoardService;
import paratrip.paratrip.board.main.validates.AddBoardValidator;
import paratrip.paratrip.board.main.validates.ModifyBoardValidator;
import paratrip.paratrip.core.base.BaseResponse;
import paratrip.paratrip.core.exception.GlobalExceptionHandler;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Tag(name = "게시판 API", description = "담당자(박종훈)")
public class BoardController {
	private final AddBoardValidator addBoardValidator;
	private final ModifyBoardValidator modifyBoardValidator;

	private final BoardService boardService;

	@PostMapping(name = "게시물 생성")
	@Operation(summary = "게시물 생성 API", description = "제목/내용 필수, 지역/이미지 선택")
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
	public ResponseEntity<BaseResponse<AddBoardResponse>> saveBoard(
		@Valid
		@ModelAttribute AddBoardRequest request
	) throws IOException {
		// 유효성 검사
		addBoardValidator.validate(request);

		// VO -> DTO
		AddBoardResponseDto addBoardResponseDto = boardService.saveBoard(request.toAddBoardRequestDto());

		// DTO -> VO
		AddBoardResponse response = addBoardResponseDto.toAddBoardResponse();

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}

	@PutMapping(name = "게시물 수정")
	@Operation(summary = "게시물 수정 API", description = "제목/내용 필수, 지역/이미지 선택")
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
	public ResponseEntity<BaseResponse> modifyBoard(
		@Valid
		@ModelAttribute ModifyBoardRequest request
	) throws IOException {
		// 유효성 검사
		modifyBoardValidator.validate(request);

		// VO -> DTO
		boardService.modifyBoard(request.toModifyBoardRequestDto());

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}
}
package paratrip.paratrip.comment.controller;

import static paratrip.paratrip.comment.controller.vo.request.CommentRequestVo.*;
import static paratrip.paratrip.comment.controller.vo.response.CommentResponseVo.*;
import static paratrip.paratrip.comment.service.dto.response.CommentResponseDto.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import paratrip.paratrip.comment.validates.AddCommentValidator;
import paratrip.paratrip.comment.service.CommentService;
import paratrip.paratrip.comment.validates.ModifyCommentValidator;
import paratrip.paratrip.core.base.BaseResponse;
import paratrip.paratrip.core.exception.GlobalExceptionHandler;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
@Tag(name = "댓글 API", description = "담당자(박종훈)")
public class CommentController {
	private final AddCommentValidator addCommentRequestValidator;
	private final ModifyCommentValidator modifyCommentValidator;

	private final CommentService commentService;

	@PostMapping(name = "댓글 작성")
	@Operation(summary = "댓글 작성 API", description = "댓글 작성 API")
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
	public ResponseEntity<BaseResponse<AddCommentResponseVo>> addComment(
		@Valid
		@RequestBody AddCommentRequest request) {
		// 유효성 검사
		addCommentRequestValidator.validate(request);

		// VO -> DTO
		AddCommentResponseDto addCommentResponseDto = commentService.addComment(request.toAddCommentRequestDto());
		AddCommentResponseVo response = addCommentResponseDto.toAddCommentResponseVo();

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), response));
	}

	@PutMapping(name = "댓글 수정")
	@Operation(summary = "댓글 수정 API", description = "댓글 수정 API")
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
			responseCode = "CSB009",
			description = "400 COMMENT_SEQ_BAD_REQUEST_EXCEPTION / Comment Seq 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
		@ApiResponse(
			responseCode = "CNCBMB010",
			description = "400 COMMENT_NOT_CREATED_BY_MEMBER_BAD_REQUEST_EXCEPTION / Comment 작성자 요류",
			content = @Content(
				schema = @Schema(
					implementation = GlobalExceptionHandler.ErrorResponse.class))),
	})
	public ResponseEntity<BaseResponse> modifyComment(
		@Valid
		@RequestBody ModifyCommentRequest request) {
		// 유효성 검사
		modifyCommentValidator.validate(request);

		// VO -> DTO
		commentService.modifyComment(request.toModifyCommentRequestDto());

		return ResponseEntity.ok().body(BaseResponse.ofSuccess(HttpStatus.OK.value(), "SUCCESS"));
	}
}
